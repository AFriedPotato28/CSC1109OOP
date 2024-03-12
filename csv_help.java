import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface csv_help {
    /**
     * 
     * @param userInfo the username of the account
     * @param customers the list of customers
     * @param HashedPasswordandSalt the hashedpassword with salt included in raw text string
     * @return 
     * 
     */
    public default boolean updateCSV(String userInfo, ArrayList<Customer> customers,ArrayList<String> HashedPasswordandSalt){

        String fileName = "MOCK_DATA.csv";
        String tempFile = "temp.csv";
        File oldFile = new File(fileName);
        File newFile = new File(tempFile);

        String[] titleToAppend = {"id","name","username","password","salt"};
        String csvLine = Arrays.stream(titleToAppend)
                                .map(this::escapeDoubleQuotes)
                                .collect(Collectors.joining(","));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));) {
            writer.append(csvLine);
            for (Customer cust : customers){
                int customerId = cust.getCustomerId();
                String customerName = cust.getName();
                String customerUserName = cust.getUserName();
                String customerPassword = cust.getPassword();
                String customerSalt = cust.getSalt();

                if(cust.getUserName().equals(userInfo)){
                    customerPassword = HashedPasswordandSalt.get(0);
                    customerSalt = HashedPasswordandSalt.get(1);
                }

                String[] datatoAppend = {String.valueOf(customerId),customerName,customerUserName,customerPassword,customerSalt};
                csvLine = Arrays.stream(datatoAppend).map(this::escapeDoubleQuotes)
                        .collect(Collectors.joining(","));

                writer.append("\n" + csvLine);
            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }


        if(oldFile.delete()){
            if(newFile.renameTo(oldFile)){
                System.out.println("Done deleting old file " + oldFile);
                return true;
            }
        }
        return false;
    }

      /**
     * Logs the activity of the user.
     * 
     * @param accountID      The accountID of the user.
     * @param activityNumber The activity number to be logged.
     */
    public default void logActivity(int accountID, int activityNumber) {
        /*
         * Log the activity based on the activity number
         * 1 - User logged in
         * 2 - User initiate bank transfer
         * 3 - User logged out
         * 4 - User initiate deposit
         * 5 - User initiate withdraw
         * Show the activity and the date and time
         */
        switch (activityNumber) {
            case 1:
                generateCSVofSecurity("Login", accountID);
                break;
            case 2:
                // Log the user bank transfer activity
                generateCSVofSecurity("Transfer Initialized", accountID);
                // Break the switch statement if the activity number is 2
                break;
            case 3:
                // Log the user logout activity
                // Break the switch statement if the activity number is 3
                generateCSVofSecurity("Logout", accountID);
                break;
            case 4:
                // Log the user deposit activity
                generateCSVofSecurity("Deposit", accountID);
            case 5:
                // Log the user withdraw activity
                generateCSVofSecurity("Withdraw", accountID);
        }
    }


    public default void generateCSVofSecurity(String activity, int customerId) {
        String fileName = "Log-Tracking.csv";

        LocalDateTime dateTimeObj = LocalDateTime.now();
        /*
         * Format the date and time
         * The format is dd-MM-yyyy HH:mm:ss
         */
        DateTimeFormatter dateFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDt = dateTimeObj.format(dateFormatObj);
        String formattedTime = dateTimeObj.format(timeFormatObj);

        String[] dataToAppend = { String.valueOf(customerId), formattedDt, formattedTime, activity };

        String csvLine = Arrays.stream(dataToAppend)
                .map(this::escapeDoubleQuotes)
                .collect(Collectors.joining(","));

        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append("\n" + csvLine);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public default void createCustomerAccount(Customer customer) {
        String filepath = "MOCK_DATA.csv";


        String[] dataToAppend = {String.valueOf(customer.getCustomerId()),customer.getName(), customer.getUserName(), customer.getPassword(), customer.getSalt()};

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

    public default void generateCSVtoAccount(int customerID, Account account) {
        String filepath = "Account_Data.csv";
        
        String[] dataToAppend = { String.valueOf(account.getAccountNo()),String.valueOf(customerID), account.getAccountType(), String.valueOf(account.getBalance()), String.valueOf(account.getTransactionLimit())};

        String csvLine = Arrays.stream(dataToAppend)
                .map(this::escapeDoubleQuotes)
                .collect(Collectors.joining(","));

        //append data to next row
        try (FileWriter writer = new FileWriter(filepath, true)) {  // Append mode
            writer.append("\n");
            writer.append(csvLine);
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
