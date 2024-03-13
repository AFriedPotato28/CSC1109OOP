/**
 * Represent a customer associated with the Bank
 * Each customer has a Customer ID, name, username and password.
 */
public class Customer {
    private int customerId;
    private String name;
    private String username;
    private String password;
    private String salt;
    
    /**
     * Construct a Customer object.
     */
    public Customer(){
    }

    public Customer(int id, String name, String username, String password,String salt){
        this.customerId = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.salt = salt;

    }
    

    public String getUserName(){
        return this.username;
    }

    /**
     * Retrieves the customer id
     * @return The customer id
     */
    public int getCustomerId(){
        return this.customerId;
    }

    /**
     * Retrieve the name of the customer.
     * @return The name of the customer
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retrieve the password of the customer.
     * @return The password of the customer
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Retrieve the salt of the customer.
     * @return The salt of the customer
     */
    public String getSalt(){
        return this.salt;
    }

    /**
     * Set the password of the customer
     * @param password The password of the customer
     */
    public void setPassword(String password){
        this.password = password;
    }

    public void setSalt(String salt){
        this.salt = salt;
    }


    public void setItems(int customerId, String password, String salt){
        this.customerId = customerId;
        this.password = password;
        this.salt = salt;
    }

 
}