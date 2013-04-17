package projecten2;

import java.sql.Date;


public class Melding {
    
   
    private int meldingId;
    private Gebruiker gebruiker;
    private String titel;
    private String type;
    private String locatie;
    private String omschrijving;
    private Date datum;
    private Afbeelding afbeelding;
    
    
    public Melding(String meldingType, int meldingId, Gebruiker gebruiker, String titel,
                    String type, String locatie, String omschrijving, Date datum, Afbeelding afbeelding){
       
        this.meldingId = meldingId;
        this.gebruiker = gebruiker;
        this.titel = titel;
        this.type = type;
        this.locatie = locatie;
        this.omschrijving = omschrijving;
        this.datum = datum;
        this.afbeelding = afbeelding;
    }
    
    public Melding() {
        this("situatie", 0, new Gebruiker(), "Geen titel","Andere","","Geen omschrijving", null, null);
    }
    
   
    public int getMeldingId(){
        return meldingId;
    }
    
    public void setMeldingId(int meldingId){
        this.meldingId = meldingId;
    }
    
    public Gebruiker getGebruiker(){
        return gebruiker;
    }
    
    public void setGebruiker(Gebruiker gebruiker){
        this.gebruiker = gebruiker;
    }
    
    public String getTitel(){
        return titel;
    }
    
    public void setTitel(String titel){
        this.titel = titel;
    }
    
    public String getType(){
        return type;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String getLocatie(){
        return locatie;
    }
    
    public void setLocatie(String locatie){
        this.locatie = locatie;
    }
    
    public String getOmschrijving(){
        return omschrijving;
    }
    
    public void setOmschrijving(String omschrijving){
        this.omschrijving = omschrijving;
    }
    
    public Date getDatum(){
        return datum;
    }
    
    public void setDatum(Date datum){
        this.datum = datum;
    }
    
    public Afbeelding getAfbeelding(){
        return afbeelding;
    }
    
    public void setAfbeelding(Afbeelding afbeelding){
        this.afbeelding = afbeelding;
    }
    
}
