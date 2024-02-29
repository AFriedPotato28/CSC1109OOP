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

    public String getUserName(){
        return this.username;
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
     * Set the password of the customer
     * @param password The password of the customer
     */
    public void setPassword(String password){
        this.password = password;
    }

    public void createCustomerAccount(int customerID,String name,String username,String password){
        this.customerId = customerID;
        this.name = name;
        this.username = username;
        this.password = password;
    }

}