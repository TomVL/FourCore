package projecten2;

import java.sql.Date;


public class Situatie extends Melding{
    
    
    private int meldingId;
    private Gebruiker gebruiker;
    private String titel;
    private String type;
    private String locatie;
    private String omschrijving;
    private Date datum;
    private Afbeelding afbeelding;
    
    public Situatie(int situatieId, Gebruiker gebruiker, String titel,
                    String type, String locatie, String omschrijving, Date datum, Afbeelding afbeelding){
        
        this.meldingId = situatieId;
        this.gebruiker = gebruiker;
        this.titel = titel;
        this.type = type;
        this.locatie = locatie;
        this.omschrijving = omschrijving;
        this.datum = datum;
        this.afbeelding = afbeelding;
    }
    
    public Situatie() {
        this(0, new Gebruiker(), "Geen titel","Andere","","Geen omschrijving", null, null);
    }
    
   
}
