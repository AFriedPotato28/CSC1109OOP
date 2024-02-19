public class Customer {
    private int customerId;
    private String name;
    private String username;
    private String password;

    public Customer(){
    }

    public int getCustomerId(){
        return this.customerId;
    }

    public String getName(){
        return this.name;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
