package fr.phareouest;

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
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.GaugeField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.FieldChangeListener;

public class AuthentiBBScreen extends MainScreen implements FieldChangeListener {

    private Font myFont;
    private String mySecret = "JBSWY3DFEZFEZ";
    private RichTextField myToken;
    private GaugeField barreDeProgression;
    private LabelField messageFin;
    private BasicEditField ligne1;
    private ButtonField bouton1; 
    
    private PersistentObject persistentObject;
    static final long KEY = 0x9df9f961bc7d6baL;
    



    public AuthentiBBScreen() {
        super( MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR );
        setTitle( "PhareOuest Security Token" );
        persistentObject = PersistentStore.getPersistentObject(KEY);
        //messageFin = new LabelField("test");
        //add(messageFin);
      
        myToken = new RichTextField(computePin(mySecret),RichTextField.TEXT_JUSTIFY_HCENTER);
        
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
        add(myToken);
        
    
        barreDeProgression = new GaugeField("",0,30,0,GaugeField.NO_TEXT);
        add(barreDeProgression);
        add(new SeparatorField());
        ligne1 = new BasicEditField("","contenu");
        add(ligne1);
        
        bouton1 = new ButtonField("Google", ButtonField.CONSUME_CLICK);
        bouton1.setChangeListener(this);
        add(bouton1);
        
       
        OtpUpdater thread = new OtpUpdater(this);
        thread.start();
        add(new SeparatorField());

        

    }
    
    
    public void appendLabelText() {
    	long currentTimeSeconds = System.currentTimeMillis() / 1000;
    	long currentSeconds = currentTimeSeconds % 30;

    	myToken.setText(computePin(mySecret));
    	barreDeProgression.setValue((int)currentSeconds);

    	}
    
    public void fieldChanged(Field field, int context) {
    	if (field == bouton1) {
    	//Dialog.inform("Clear Button Pressed!");
    	EditionScreen EditionScreen = new EditionScreen();
    	UiApplication.getUiApplication().pushScreen(EditionScreen);
    	}
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


}
