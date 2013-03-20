package projecten2;

import java.awt.image.BufferedImage;


public class Afbeelding {
    
    private int afbeeldingId;
    private BufferedImage afbeelding;
    
    public Afbeelding(int afbeeldingId, BufferedImage afbeelding){
        this.afbeeldingId = afbeeldingId;
        this.afbeelding = afbeelding;
    }
    
    public int getAfbeeldingId(){
        return afbeeldingId;
    }
    
    public void setAfbeeldingId(int afbeeldingId){
        this.afbeeldingId = afbeeldingId;
    }
    
    public BufferedImage getAfbeelding(){
        return afbeelding;
    }
    
    public void setAfbeeldingId(BufferedImage afbeelding){
        this.afbeelding = afbeelding;
    }
    
}
