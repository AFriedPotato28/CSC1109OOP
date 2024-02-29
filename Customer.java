public class Customer {
    private int customerId;
    private String name;
    private String username;
    private String password;

    // Constructor
    public Customer(int customerId, String name, String username, String password){
        this.customerId = customerId;
        this.name = name;
        this.username = username;
        this.password = password;
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
