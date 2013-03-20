package Projecten2;


public class Melding {
    
      private int meldingId;
    private Gebruiker gebruiker;
    private String titel;
    private String type;
    private String locatie;
    private String omschrijving;
    private int afbeeldingId;

    public Melding(int meldingId, Gebruiker gebruiker, String type, String titel, String locatie, String omschrijving, int afbeeldingId) {
        this.meldingId = meldingId;
        this.gebruiker = gebruiker;
        this.titel = titel;
        this.locatie = locatie;
        this.omschrijving = omschrijving;
        this.afbeeldingId = afbeeldingId;
    }
    
    
    public Melding()
    {
        this(1,1, "Andere","Geen titel","","",1);
    }

    public int getMeldingId() {
        return meldingId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMeldingId(int meldingId) {
        this.meldingId = meldingId;
    }

    public int getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public int getAfbeeldingId() {
        return afbeeldingId;
    }

    public void setAfbeeldingId(int afbeeldingId) {
        this.afbeeldingId = afbeeldingId;
    }
    
}
