package fr.phareouest;

import java.util.Hashtable;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.container.GridFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.StandardTitleBar;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.util.StringProvider;

public class EditionScreen extends MainScreen implements FieldChangeListener {

    private ButtonField boutValider; 
    private ButtonField boutAnnuler; 
    //private KeyManager keymanager = new KeyManager();
    private KeyManager keymanager;
    private BasicEditField inputKey = new BasicEditField(TextField.NO_NEWLINE);
    private BasicEditField inputName = new BasicEditField(TextField.NO_NEWLINE);
	private Border myBorder = BorderFactory.createBitmapBorder(new XYEdges(20, 16, 27, 23),Bitmap.getBitmapResource("img/corners.png"));
	private String myCurrentKey;
	private String myCurrentValue;
	private StandardTitleBar authBBTitleBar;
	private boolean isEdited;

	
    public EditionScreen(KeyManager kmgr) {
        super( MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR );
        //setTitle("Edition de la Clé");
        
        keymanager = kmgr;
        
        isEdited = false;
        
		authBBTitleBar = new StandardTitleBar()
        .addIcon("icon-tfa.png")
        .addTitle("authentiBB [ new key ]")
        .addClock()
        .addNotifications()
        .addSignalIndicator();
		setTitleBar(authBBTitleBar);
      
        LabelField labName = new LabelField("Key Provider :");
        labName.setPadding(10,10,10,10);
        add(labName);
        
        inputName.setBorder(myBorder);
        add(inputName);

        LabelField labKey = new LabelField("Key Value :");
        labKey.setPadding(10,10,10,10);
        add(labKey);
        
        inputKey.setBorder(myBorder);
        add(inputKey);
        
        GridFieldManager EndLine = new GridFieldManager(1,3,Field.FIELD_VCENTER);
	        EndLine.setColumnPadding(50);
	        boutValider = new ButtonField("Save", ButtonField.CONSUME_CLICK);
	        boutValider.setChangeListener(this);
	        EndLine.add(boutValider);
	        //EndLine.add(new LabelField("bouya"));
	        boutAnnuler = new ButtonField("Cancel", ButtonField.CONSUME_CLICK|Field.FIELD_RIGHT);
	        boutAnnuler.setChangeListener(this);
        EndLine.add(boutAnnuler);
        add(EndLine);

    }	
	

    public EditionScreen(String nameValue, KeyManager kmgr) {
        super( MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR );
        //setTitle("Edition de la Clé");
        
        keymanager = kmgr;
        
        isEdited = true;
        
		authBBTitleBar = new StandardTitleBar()
        .addIcon("icon-tfa.png")
        .addTitle("authentiBB [ update key] ")
        .addClock()
        .addNotifications()
        .addSignalIndicator();
		setTitleBar(authBBTitleBar);
        
		myCurrentKey = nameValue;
		myCurrentValue = keymanager.get(nameValue).toString();
      
        LabelField labName = new LabelField("Key Provider :");
        labName.setPadding(10,10,10,10);
        add(labName);
        
        inputName.setText(myCurrentKey);
        inputName.setEditable(false);
        inputName.setBorder(myBorder);
        add(inputName);

        LabelField labKey = new LabelField("Key Value :");
        labKey.setPadding(10,10,10,10);
        add(labKey);
        
        inputKey.setBorder(myBorder);
        inputKey.setText(myCurrentValue);
        add(inputKey);
        
        GridFieldManager EndLine = new GridFieldManager(1,3,Field.FIELD_VCENTER);
	        EndLine.setColumnPadding(50);
	        boutValider = new ButtonField("Save", ButtonField.CONSUME_CLICK);
	        boutValider.setChangeListener(this);
	        EndLine.add(boutValider);
	        //EndLine.add(new LabelField("bouya"));
	        boutAnnuler = new ButtonField("Cancel", ButtonField.CONSUME_CLICK|Field.FIELD_RIGHT);
	        boutAnnuler.setChangeListener(this);
        EndLine.add(boutAnnuler);
        add(EndLine); 

    }
    
    public boolean onClose()
   {
         return true;
   }
    



	protected void makeMenu(Menu menu, int instance) {
		super.makeMenu(menu, instance);
		if (isEdited){
			menu.add(new MenuItem(new StringProvider("Delete Key"), 20, 20) {
				public void run() {
					keymanager.remove(inputName.getText().trim());
					//EditionScreen EditionScreen = new EditionScreen(myCurrentKey);
					//UiApplication.getUiApplication().pushScreen(EditionScreen);
			    	myCurrentKey = keymanager.firstKey();
			    	//effacer le bouton
			    	getScreenBelow().doPaint();
					getScreen().close();
				}
			});
		}
	}
	
    public void fieldChanged(Field field, int context) {
    	if (field == boutValider) {
	    	keymanager.set(inputName.getText().trim(),inputKey.getText().trim());
	    	keymanager.commit();
	    	getScreen().close();
			UiApplication.getUiApplication().invokeLater(new Runnable(){
				public void run(){
					AuthentiBBScreen screen = (AuthentiBBScreen)UiApplication.getUiApplication().getActiveScreen();
					screen.genButtons(); 
				}
	});
	    	
    	}
    	if (field == boutAnnuler) {
    		getScreen().close();	
    		
    	}
    	}
    
    
    
}
