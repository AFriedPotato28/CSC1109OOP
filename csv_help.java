import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public default boolean updateCSVOfCustomerData(String userInfo, ArrayList<Customer> customers,ArrayList<String> HashedPasswordandSalt){

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
                return true;
            }
        }
        return false;
    }

    public default boolean updateCSVOfAccount(HashMap<Integer,List<Account>> accounts,Account accountStash, int userId){

        String file = "Account_Data.csv";
        String tempFile = "temp.csv";

        File oldFile = new File(file);
        File newFile = new File(tempFile);

        String[] titleToAppend = {"AccountNo","CustomerID","accountType","balance","transactionLimit"};
        String csvLine = Arrays.stream(titleToAppend)
                        .map(this::escapeDoubleQuotes)
                        .collect(Collectors.joining(","));

         try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));) {
            writer.append(csvLine);

            for (Map.Entry<Integer, List<Account>> entry : accounts.entrySet()) {
                List<Account> accountList = entry.getValue();
           
                for (Account account : accountList){
                    int accountNo = account.getAccountNo();
                    userId = account.getCustomerId();
                    String accountType = account.getAccountType();
                    double balance = account.getBalance();
                    double transactionLimit = account.getTransactionLimit();

                    if(accountStash.getAccountType().equalsIgnoreCase(accountType) && accountNo == accountStash.getAccountNo()){
                        userId = accountStash.getCustomerId();
                        balance = accountStash.getBalance();
                        transactionLimit = accountStash.getTransactionLimit();
                    }

                    String[] dataToAppend = {String.valueOf(accountNo),String.valueOf(userId),
                                            account.getAccountType(),String.valueOf(balance),String.valueOf(transactionLimit)};
                    csvLine = Arrays.stream(dataToAppend)
                                .map(this::escapeDoubleQuotes)
                                .collect(Collectors.joining(","));
                    
                    writer.append("\n" + csvLine);
                }            
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if(oldFile.delete()){
            if(newFile.renameTo(oldFile)){
                return true;
            }
        }
        return false;
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
