package projecten2;

import java.awt.image.BufferedImage;
import java.io.File;



public class Afbeelding {
    
    private int afbeeldingId;
    private File afbeeldingIn;
    private BufferedImage afbeeldingUit;
    
    public Afbeelding(int afbeeldingId, File afbeeldingIn, BufferedImage afbeeldingUit){
        this.afbeeldingId = afbeeldingId;
        this.afbeeldingIn = afbeeldingIn;
        this.afbeeldingUit = afbeeldingUit;
    }
    
    public Afbeelding(){
        this(1, null, null);
    }
    
    public int getAfbeeldingId(){
        return afbeeldingId;
    }
    
    public void setAfbeeldingId(int afbeeldingId){
        this.afbeeldingId = afbeeldingId;
    }
    
    public File getAfbeeldingIn(){
        return afbeeldingIn;
    }
    
    public void setAfbeeldingIn(File afbeeldingIn){
        this.afbeeldingIn = afbeeldingIn;
    }
    
    public BufferedImage getAfbeeldingUit(){
        return afbeeldingUit;
    }
    
    public void setAfbeeldingUit(BufferedImage afbeeldingUit){
        this.afbeeldingUit = afbeeldingUit;
    }
    
}
