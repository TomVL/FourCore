package projecten2;


public class Like {
    
    private int likeId;
    private Melding melding;
    private Gebruiker gebruiker;
    
    public Like(int likeId, Melding melding, Gebruiker gebruiker){
        this.likeId = likeId;
        this.melding = melding;
        this.gebruiker = gebruiker;
    }
    
    public Like(){
        this(0, new Melding(), new Gebruiker());
    }
    
    public int getLikeId(){
        return likeId;
    }
    
    public void setLikeId(int likeId){
        this.likeId = likeId;
    }
    
    public Melding getMeldingId(){
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
}
