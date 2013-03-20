package projecten2;


public class Gebruiker {
    
    private int gebruikerId;
    private String facebookAccount;
    private String twitterAccount;
    
    public Gebruiker(int gebruikerId, String facebookAccount, String twitterAccount){
        this.gebruikerId = gebruikerId;
        this.facebookAccount = facebookAccount;
        this.twitterAccount = twitterAccount;
    }
    
    public Gebruiker(){
        this(0,"","");
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
}
