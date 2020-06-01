package modules;
/*
 *
 * @author caio
 */
public class Investment {
    // Attributes
    private String CODE;
    private String PRICE;
    private String AMOUNT;
    private String DATE;
    private String REASON;
    private String DELETIONDATE;
    
    // Constructors
    public Investment(String c, String p, String a, String d, String r, String dd){
        this.CODE = c;
        this.PRICE = p;
        this.AMOUNT = a;
        this.DATE = d;
        this.REASON = r;
        this.DELETIONDATE = dd;
    }
    
    public String getCode(){
        return CODE;
    }
    
    public void setCode(String c){
        this.CODE = c;
    }
    
    public String getPrice(){
        return PRICE;
    }
    
    public void setPrice(String p){
        this.PRICE = p;
    }
    
    public String getAmount(){
        return AMOUNT;
    }
    
    public void setAmount(String a){
        this.AMOUNT = a;
    }
    
    public String getDate(){
        return DATE;
    }
    
    public void setDate(String d){
        this.DATE = d;
    }
    
    public String getReason(){
        return REASON;
    }
    
    public void setReason(String r){
        this.REASON = r;
    }
    
    public String getDeletionDate(){
        return DELETIONDATE;
    }
    
    public void setDeletionDate(String dd){
        this.DELETIONDATE = dd;
    }
}