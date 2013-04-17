package projecten2;


public class Gebruiker {
    
    private int gebruikerId;
    private String facebookAccount;
    private String twitterAccount;
    private Afbeelding afbeelding;
    
    public Gebruiker(int gebruikerId, String facebookAccount, String twitterAccount, Afbeelding afbeelding){
        this.gebruikerId = gebruikerId;
        this.facebookAccount = facebookAccount;
        this.twitterAccount = twitterAccount;
        this.afbeelding = afbeelding;
    }
    
    public Gebruiker(){
        this(0,"","", null);
    }
    
    public int getGebruikerId(){
        return gebruikerId;
    }
    
    public void setGebruikerId(int gebruikerId){
        this.gebruikerId = gebruikerId;
    }
    
    public String getFacebookAccount(){
        return facebookAccount;
    }
    
    public void setFacebookAccount(String facebookAccount){
        this.facebookAccount = facebookAccount;
    }
    
    public String getTwitterAccount(){
        return twitterAccount;
    }
    
    public void setTwitterAccount(String twitterAccount){
        this.twitterAccount = twitterAccount;
    }
    
    public Afbeelding getAfbeelding(){
        return afbeelding;
    }
    
    public void setAfbeelding(Afbeelding afbeelding){
        this.afbeelding = afbeelding;
    }
}
