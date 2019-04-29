package fr.phareouest;

import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import fr.phareouest.util.Base32String;
import fr.phareouest.util.Base32String.DecodingException;


import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.container.GridFieldManager;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.GaugeField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;
import net.rim.device.api.ui.FieldChangeListener;

public class EditionScreen extends MainScreen implements FieldChangeListener {

    private Font myFont;
    private String mySecret = "JBSWY3DFEZFEZ";
    private RichTextField myToken;
    private GaugeField barreDeProgression;
    private LabelField messageFin;
    private BasicEditField inputName;
    private BasicEditField inputKey;
    private ButtonField boutValider; 
    private ButtonField boutAnnuler; 
   // private KeyManager keymanager = new KeyManager();

    
    
	Border myBorder = BorderFactory.createBitmapBorder(new XYEdges(20, 16, 27, 23),Bitmap.getBitmapResource("img/corners.png"));
	

    
    public void fieldChanged(Field field, int context) {
    	if (field == boutValider) {
    		KeyManager keymanager = new KeyManager();
	        //ligne1.getText();
	    	//keymanager.set(inputName.getText(),inputKey.getText());
    		//keymanager = new KeyManager();
    		//Object ik = inputKey.getText().trim();
	    	//String keyValue = new String("5614561");
    		String keyValue = new String(inputKey.getText().trim());
    		Dialog.inform(keyValue);
	    	//keymanager.set("alpha",keyValue);
	    	//keymanager.commit();
	    	//Dialog.inform("Clé enregistrée !");
	    	getScreen().close();
    	}
    	if (field == boutAnnuler) {
    		getScreen().close();	
    	}
    	}
    
 

    public EditionScreen() {
        super( MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR );
        setTitle("Edition");
        KeyManager keymanager = new KeyManager();
        
        LabelField labName = new LabelField("Nom du Service :");
        labName.setPadding(10,10,10,10);
        add(labName);
        BasicEditField inputName = new BasicEditField(TextField.NO_NEWLINE);
        inputName.setBorder(myBorder);
        add(inputName);

        LabelField labKey = new LabelField("Valeur de la Clé :");
        labKey.setPadding(10,10,10,10);
        add(labKey);
        BasicEditField inputKey = new BasicEditField(TextField.NO_NEWLINE);
        inputKey.setBorder(myBorder);
        String myValue = keymanager.get("alpha").toString();
        inputKey.setText(myValue);
        add(inputKey);
        
        GridFieldManager EndLine = new GridFieldManager(1,3,Field.FIELD_VCENTER);
        EndLine.setColumnPadding(50);
        
        
        
        boutValider = new ButtonField("Valider", ButtonField.CONSUME_CLICK);
        boutValider.setChangeListener(this);
        EndLine.add(boutValider);
        
        EndLine.add(new LabelField("bouya"));
        
        boutAnnuler = new ButtonField("Annuler", ButtonField.CONSUME_CLICK|Field.FIELD_RIGHT);
        boutAnnuler.setChangeListener(this);
        EndLine.add(boutAnnuler);

        
       
        
        
        add(EndLine);
        


    }
    
    public boolean onClose()
   {
         return true;
   }
    
}
