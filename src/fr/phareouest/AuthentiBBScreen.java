package fr.phareouest;

import java.util.Enumeration;
import java.util.Hashtable;

import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import fr.phareouest.util.Base32String;
import fr.phareouest.util.OtpUpdater;
import fr.phareouest.util.PasscodeGenerator;
import fr.phareouest.util.Base32String.DecodingException;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.FlowFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.GaugeField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.StandardTitleBar;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.util.StringProvider;

public class AuthentiBBScreen extends MainScreen implements FieldChangeListener {

	private Font myFont;
	private RichTextField myToken;
	private String myCurrentKey;
	private String myCurrentValue;
	private GaugeField barreDeProgression;
//	private PersistentObject persistentObject;
//	private Hashtable keyHash;
	private StandardTitleBar authBBTitleBar;
	private KeyManager keymanager = new KeyManager();
	private FlowFieldManager ThirdRow = new FlowFieldManager();



	public AuthentiBBScreen() {
		
		super( MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR );
		
		//persistentObject= PersistentStore.getPersistentObject(0x9787015f06321e7cL);
		//keyHash = (Hashtable)persistentObject.getContents();

		FlowFieldManager FirstRow = new FlowFieldManager();
		FlowFieldManager SecondRow = new FlowFieldManager();
		genButtonsLine(keymanager,ThirdRow); // new FlowFieldManager();
		
	
		myCurrentKey = keymanager.firstKey();
		myCurrentValue = keymanager.get(myCurrentKey).toString();
			
		authBBTitleBar = new StandardTitleBar()
        .addIcon("icon-tfa.png")
        .addTitle("authentiBB"+" ["+myCurrentKey+"] ")
        .addClock()
        .addNotifications()
        .addSignalIndicator();
		setTitleBar(authBBTitleBar);
		
		myToken = new RichTextField(computePin(myCurrentValue),RichTextField.TEXT_JUSTIFY_HCENTER);

		try
		{
			FontFamily ff = FontFamily.forName("Verdana");
			myFont = ff.getFont(Font.PLAIN, 120);
		}
		catch(final ClassNotFoundException cnfe)
		{
			AuthentiBB.getUiApplication().invokeLater(new Runnable()
			{
				public void run()
				{
					Dialog.alert("FontFamily.forName() threw " + cnfe.toString());
				}
			});              
		} 

		myToken.setFont(myFont);
		FirstRow.add(myToken);

		barreDeProgression = new GaugeField("",0,30,0,GaugeField.NO_TEXT);
		SecondRow.add(barreDeProgression);

		add(FirstRow);
		add(SecondRow);
		add(ThirdRow);

		OtpUpdater thread = new OtpUpdater(this);
		thread.start();
	}

	

	private void genButtonsLine(KeyManager kmgr, FlowFieldManager ffmgr) {
		// TODO Auto-generated method stub
		//FlowFieldManager f = new FlowFieldManager();
		Enumeration enumeration = kmgr.keys();
		//String[] tableValeurs = new String[kmgr.size()];
		if (ffmgr != null){
			ffmgr.deleteAll();
		}
		while(enumeration.hasMoreElements()){
			String key = enumeration.nextElement().toString();
			ButtonField bouton = new ButtonField(key, ButtonField.CONSUME_CLICK);
			bouton.setChangeListener(this);
			//tableValeurs[tabind] = key;
			ffmgr.add(bouton);
		}

		
	}



	public void appendLabelText() {
		
		long currentTimeSeconds = System.currentTimeMillis() / 1000;
		long currentSeconds = currentTimeSeconds % 30;

		myToken.setText(computePin(myCurrentValue));
		barreDeProgression.setValue((int)currentSeconds);
		//genButtonsLine(keymanager,ThirdRow);

	}
	
	public void onExposed(){
		genButtonsLine(keymanager,ThirdRow);
	}
	

	public void fieldChanged(Field field, int context) {
		if (field instanceof ButtonField) {
//			EditionScreen EditionScreen = new EditionScreen(((ButtonField) field).getLabel());
//			UiApplication.getUiApplication().pushScreen(EditionScreen);
			myCurrentKey = ((ButtonField) field).getLabel();
			myCurrentValue = keymanager.get(myCurrentKey).toString();
			myToken.setText(computePin(myCurrentValue));
			authBBTitleBar.addTitle("authentiBB"+" ["+myCurrentKey+"] ");
			
		}
//		if (field == bouton) {
//			Dialog.inform("Button Pressed!");
//			//EditionScreen EditionScreen = new EditionScreen("alpha");
//			//UiApplication.getUiApplication().pushScreen(EditionScreen);
//		}
	}

	public static String computePin(String secret) {
		try {
			final byte[] keyBytes = Base32String.decode(secret);
			Mac mac = new HMac(new SHA1Digest());
			mac.init(new KeyParameter(keyBytes));
			PasscodeGenerator pcg = new PasscodeGenerator(mac);
			return pcg.generateTimeoutCode();

		} catch (RuntimeException e) {
			return "General security exception";
		} catch (DecodingException e) {
			return "Decoding exception";
		}
	}

//	public boolean onClose()
//	{
//		return true;
//	}
	
	public void onFocus(){
		Dialog.alert("Focus");
	}

	protected void makeMenu(Menu menu, int instance) {
		super.makeMenu(menu, instance);
		menu.add(new MenuItem(new StringProvider("New Key"), 10, 10) {
			public void run() {
				EditionScreen EditionScreen = new EditionScreen(keymanager);
				UiApplication.getUiApplication().pushScreen(EditionScreen);
			}
		});
		menu.add(new MenuItem(new StringProvider("Update Key"), 20, 20) {
			public void run() {
				EditionScreen EditionScreen = new EditionScreen(myCurrentKey,keymanager);
				UiApplication.getUiApplication().pushScreen(EditionScreen);
			}
		});
	}
}
