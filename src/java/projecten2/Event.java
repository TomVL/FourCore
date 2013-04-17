package projecten2;

import java.sql.Date;


public class Event extends Melding{
    
    
    private int meldingId;
    private Gebruiker gebruiker;
    private String titel;
    private String locatie;
    private String type;
    private String omschrijving;
    private Date datum;
    private Afbeelding afbeelding;
    
    public Event(int eventId, Gebruiker gebruiker, String titel, 
             String locatie, String type, String omschrijving, Date datum, Afbeelding afbeelding){
        
        
	this.meldingId = eventId;
        this.gebruiker = gebruiker;
        this.titel = titel;
        this.datum = datum;
        this.locatie = locatie;
        this.type = type;
        this.omschrijving = omschrijving;
    }

    public Event() {
        this(0, new Gebruiker(), "Geen titel","","Andere","Geen omschrijving",null, null);
    }


}
