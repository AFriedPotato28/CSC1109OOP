/**
 * Represent a customer associated with the Bank
 * Each customer has a Customer ID, name, username and password.
 */
public class Customer {
    private int customerId;
    private String name;
    private String username;
    private String password;

    /**
     * Construct a Customer object.
     */
    public Customer(){
    }

    /**
     * Retrieves the customer ID
     * @return The customer ID
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
     * Set the username of the customer
     * @param username The username of the customer
     */
    public void setUserName(String username){
        this.username = username;
    }

    /**
     * Set the password of the customer
     * @param password The password of the customer
     */
    public void setPassword(String password){
        this.password = password;
    }
}