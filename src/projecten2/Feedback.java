package projecten2;


public class Feedback {
    
    private Melding melding;
    private Gebruiker gebruiker;
    private int feedbackId;
    private String type;
    private String omschrijving;
    private int afbeeldingId;
    
    public Feedback(Melding melding, Gebruiker gebruiker, int feedbackId, String type, String omschrijving){
        this.melding = melding;
        this.gebruiker = gebruiker;
        this.feedbackId = feedbackId;
        this.type = type;
        this.omschrijving = omschrijving;
    }
    
    public Feedback(){
        this(new Melding(), new Gebruiker(), 0, "Andere", "Geen omschrijving");
    }
    
    public Melding getMelding(){
        return melding;
    }
    
    public void setMelding(Melding melding){
        this.melding = melding;
    }
    
    public Gebruiker getGebruiker(){
        return gebruiker;
    }
    
    public void setGebruiker(Gebruiker gebruiker){
        this.gebruiker = gebruiker;
    }
    
    public int getFeedbackId(){
        return feedbackId;
    }
    
    public void setFeedbackId(int feedbackId){
        this.feedbackId = feedbackId;
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
