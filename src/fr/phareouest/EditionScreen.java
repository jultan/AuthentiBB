package fr.phareouest;

import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import fr.phareouest.util.Base32String;
import fr.phareouest.util.Base32String.DecodingException;


import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Screen;
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

public class EditionScreen extends MainScreen implements FieldChangeListener {

    private Font myFont;
    private String mySecret = "JBSWY3DFEZFEZ";
    private RichTextField myToken;
    private GaugeField barreDeProgression;
    private LabelField messageFin;
    private BasicEditField ligne1;
    private ButtonField boutValider; 
    private ButtonField boutAnnuler; 
    
    public void fieldChanged(Field field, int context) {
    	if (field == boutValider) {
    	Dialog.inform("Clé enregistrée !");
    	}
    	if (field == boutAnnuler) {
    		getScreen().close();	
    	}
    	}
    	

    public EditionScreen() {
        super( MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR );
        setTitle( "PhareOuest Security Token" );
        
        
        
     
    
        ligne1 = new BasicEditField("","contenu");
        add(ligne1);
        
        boutValider = new ButtonField("Valider", ButtonField.CONSUME_CLICK);
        boutValider.setChangeListener(this);
        add(boutValider);
        
        boutAnnuler = new ButtonField("Annuler", ButtonField.CONSUME_CLICK);
        boutAnnuler.setChangeListener(this);
        add(boutAnnuler);
     

    }
    
//    public boolean onClose()
//    {
//    	Dialog.inform("On ferme !");
//         return true;
//    }



}
