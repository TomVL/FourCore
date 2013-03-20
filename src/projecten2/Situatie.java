package projecten2;


public class Situatie extends Melding{
    
    private int meldingId;
    private Gebruiker gebruiker;
    private String titel;
    private String type;
    private String locatie;
    private String omschrijving;
    private int afbeeldingId;
    
    public Situatie(int situatieId, Gebruiker gebruiker, String titel, String type, String locatie, String omschrijving){
        this.meldingId = situatieId;
        this.gebruiker = gebruiker;
        this.titel = titel;
        this.type = type;
        this.locatie = locatie;
        this.omschrijving = omschrijving;
    }
    
    public Situatie() {
        this(0, new Gebruiker(), "Geen titel","Andere","","Geen omschrijving");
    }
    
    public int getMeldingId(){
        return meldingId;
    }
    
    public void setMeldingId(int situatieId){
        this.meldingId = situatieId;
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
    
    public int getAfbeeldingId(){
        return afbeeldingId;
    }
    
    public void setAfbeeldingId(int afbeeldingId){
        this.afbeeldingId = afbeeldingId;
    }
}
