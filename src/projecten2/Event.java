package projecten2;


public class Event extends Melding{
    private int meldingId;
    private Gebruiker gebruiker;
    private String titel;
    private String datum;
    private String tijdstip;
    private String locatie;
    private String type;
    private String omschrijving;
    private int afbeeldingId;
    
    public Event(int eventId, Gebruiker gebruiker, String titel, String datum, 
            String tijdstip, String locatie, String type, String omschrijving){
	this.meldingId = eventId;
        this.gebruiker = gebruiker;
        this.titel = titel;
        this.datum = datum;
        this.tijdstip = tijdstip;
        this.locatie = locatie;
        this.type = type;
        this.omschrijving = omschrijving;
    }

    public Event() {
        this(0, new Gebruiker(), "Geen titel","","","","Andere","Geen omschrijving");
    }

    public int getMeldingId() {
        return meldingId;
    }

    public void setMeldingId(int eventId){
        this.meldingId = eventId;
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
    
    public String getDatum(){
        return datum;
    }
    
    public void setDatum(String datum){
        this.datum = datum;
    }
    
    public String getTijdstip(){
        return tijdstip;
    }
    
    public void setTijdstip(String tijdstip){
        this.tijdstip = tijdstip;
    }
    
    public String getLocatie(){
        return locatie;
    }
    
    public void setLocatie(String locatie){
        this.locatie = locatie;
    }
    
    public String getType(){
        return type;
    }
    
    public void setType(String type){
        this.type = type;
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
