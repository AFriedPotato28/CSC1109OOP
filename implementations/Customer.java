package implementations;
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
     *
     * @param name     The name of the customer.
     * @param username The username of the customer.
     * @param password The password of the customer.
     */
    public Customer(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }


    /**
     * Construct a Customer object with specific customer ID, name, username, password, and salt.
     *
     * @param id       The unique identifier of the customer.
     * @param name     The name of the customer.
     * @param username The username of the customer.
     * @param password The password of the customer.
     * @param salt     The salt used for password hashing.
     */
    public Customer(int id, String name, String username, String password,String salt){
        this.customerId = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.salt = salt;

    }

    /**
     * Retrieves the username of the customer.
     * @return The username of the customer.
     */
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

    /**
     * Sets the salt used for password hashing.
     * @param salt The new salt of the customer.
     */
    public void setSalt(String salt){
        this.salt = salt;
    }

    /**
     * Sets the customer details including customer ID, password, and salt.
     *
     * @param customerId The customer ID.
     * @param password   The password of the customer.
     * @param salt       The salt used for password hashing.
     */
    public void setItems(int customerId, String password, String salt){
        this.customerId = customerId;
        this.password = password;
        this.salt = salt;
    }

 
}