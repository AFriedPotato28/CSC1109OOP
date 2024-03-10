import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


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
    private String filepath = "MOCK_DATA.csv";
    
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

    public void createCustomerAccount(int customerID, String name, String username, String password, String Salt) {
        this.customerId = customerID;
        this.name = name;
        this.username = username;
        this.password = password;
        this.salt = Salt;

        String[] dataToAppend = {String.valueOf(this.customerId),this.name, this.username, this.password, this.salt};

        String csvLine = Arrays.stream(dataToAppend)
                                .map(this::escapeDoubleQuotes)
                                .collect(Collectors.joining(","));

        //append data to next row
        try (FileWriter writer = new FileWriter(filepath, true)) {  // Append mode
            writer.append("\n"+ csvLine);
        } catch (IOException e) {
            System.err.println("Error appending to CSV: " + e.getMessage());
        }
    }

    private String escapeDoubleQuotes(String str) {
        if (str == null) {
            return ""; // Handle null values
        }
        StringBuilder sb = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch == '"' || ch == '\\' || ch == '\n' || ch == '\r' || ch == '\t' || ch == '\0' || ch == '\f') {
                sb.append('\\'); // Escape special characters
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}