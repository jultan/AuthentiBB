package fr.phareouest;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.container.GridFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;
import net.rim.device.api.ui.FieldChangeListener;

public class EditionScreen extends MainScreen implements FieldChangeListener {

    private ButtonField boutValider; 
    private ButtonField boutAnnuler; 
    private KeyManager keymanager = new KeyManager();
    private BasicEditField inputKey = new BasicEditField(TextField.NO_NEWLINE);
    private BasicEditField inputName = new BasicEditField(TextField.NO_NEWLINE);
	private Border myBorder = BorderFactory.createBitmapBorder(new XYEdges(20, 16, 27, 23),Bitmap.getBitmapResource("img/corners.png"));
	

    public EditionScreen(String nameValue) {
        super( MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR );
        setTitle("Edition de la Clé");
      
        LabelField labName = new LabelField("Nom du Service :");
        labName.setPadding(10,10,10,10);
        add(labName);
        
        inputName.setText(nameValue);
        inputName.setEditable(false);
        inputName.setBorder(myBorder);
        add(inputName);

        LabelField labKey = new LabelField("Valeur de la Clé :");
        labKey.setPadding(10,10,10,10);
        add(labKey);
        
        inputKey.setBorder(myBorder);
        inputKey.setText(keymanager.get(nameValue).toString());
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

    
    public void fieldChanged(Field field, int context) {
    	if (field == boutValider) {
	    	keymanager.set(inputName.getText().trim(),inputKey.getText().trim());
	    	keymanager.commit();
	    	getScreen().close();
    	}
    	if (field == boutAnnuler) {
    		getScreen().close();	
    	}
    	}
    
    
    
}
