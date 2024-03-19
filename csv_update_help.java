import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class csv_update_help {

    private static String tempFile = "temp.csv";

    /**
     * 
     * @param userInfo              the username of the account
     * @param customers             the list of customers
     * @param HashedPasswordandSalt the hashedpassword with salt included in raw
     *                              text string
     * @return
     * 
     */

    private csv_update_help() {
    }

    /**
     * 
     * @param userInfo
     * @param customers
     * @param HashedPasswordandSalt
     * @return
     * 
     */

    public static boolean updateCSVOfCustomerData(String userInfo, ArrayList<Customer> customers,
            ArrayList<String> HashedPasswordandSalt) {

        String fileName = "MOCK_DATA.csv";
        File oldFile = new File(fileName);
        File newFile = new File(tempFile);

        String[] titleToAppend = { "id", "name", "username", "password", "salt" };
        String csvLine = Arrays.stream(titleToAppend)
                .map(csv_update_help::escapeDoubleQuotes)
                .collect(Collectors.joining(","));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));) {
            writer.append(csvLine);
            for (Customer cust : customers) {
                int customerId = cust.getCustomerId();
                String customerName = cust.getName();
                String customerUserName = cust.getUserName();
                String customerPassword = cust.getPassword();
                String customerSalt = cust.getSalt();

                if (cust.getUserName().equals(userInfo)) {
                    customerPassword = HashedPasswordandSalt.get(0);
                    customerSalt = HashedPasswordandSalt.get(1);
                }

                String[] datatoAppend = { String.valueOf(customerId), customerName, customerUserName, customerPassword,
                        customerSalt };
                csvLine = Arrays.stream(datatoAppend).map(csv_update_help::escapeDoubleQuotes)
                        .collect(Collectors.joining(","));

                writer.append("\n" + csvLine);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        if (oldFile.delete()) {
            if (newFile.renameTo(oldFile)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param accounts
     * @param fromAccount
     * @param toAccount
     * @return
     * 
     */

    public static boolean updateCSVofTwoAccounts(HashMap<Integer, List<Account>> accounts, Account fromAccount,
            Account toAccount) {

        String file = "Account_Data.csv";

        File oldFile = new File(file);
        File newFile = new File(tempFile);

        String[] titleToAppend = { "AccountNo", "CustomerID", "accountType", "balance", "transactionLimit" };
        String csvLine = Arrays.stream(titleToAppend)
                .map(csv_update_help::escapeDoubleQuotes)
                .collect(Collectors.joining(","));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));) {
            writer.append(csvLine);

            for (Map.Entry<Integer, List<Account>> entry : accounts.entrySet()) {
                List<Account> accountList = entry.getValue();

                for (Account account : accountList) {
                    int accountNo = account.getAccountNo();
                    int userId = account.getCustomerId();
                    String accountType = account.getAccountType();
                    double balance = account.getBalance();
                    double transactionLimit = account.getTransactionLimit();

                    if (fromAccount.getAccountType().equalsIgnoreCase(accountType)
                            && accountNo == fromAccount.getAccountNo()) {
                        balance = fromAccount.getBalance();
                        transactionLimit = fromAccount.getTransactionLimit();
                    } else if (toAccount.getAccountType().equalsIgnoreCase(accountType)
                            && accountNo == toAccount.getAccountNo()) {
                        System.out.println(toAccount.getBalance());
                        balance = toAccount.getBalance();
                        transactionLimit = toAccount.getTransactionLimit();
                    }

                    String[] dataToAppend = { String.valueOf(accountNo), String.valueOf(userId),
                            account.getAccountType(), String.valueOf(balance), String.valueOf(transactionLimit) };
                    csvLine = Arrays.stream(dataToAppend)
                            .map(csv_update_help::escapeDoubleQuotes)
                            .collect(Collectors.joining(","));

                    writer.append("\n" + csvLine);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (oldFile.delete()) {
            if (newFile.renameTo(oldFile)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param accounts
     * @param accountStash
     * @return
     * 
     */

    public static boolean updateCSVOfAccount(HashMap<Integer, List<Account>> accounts, Account accountStash) {

        String file = "Account_Data.csv";

        File oldFile = new File(file);
        File newFile = new File(tempFile);

        String[] titleToAppend = { "AccountNo", "CustomerID", "accountType", "balance", "transactionLimit" };
        String csvLine = Arrays.stream(titleToAppend)
                .map(csv_update_help::escapeDoubleQuotes)
                .collect(Collectors.joining(","));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));) {
            writer.append(csvLine);

            for (Map.Entry<Integer, List<Account>> entry : accounts.entrySet()) {
                List<Account> accountList = entry.getValue();

                for (Account account : accountList) {
                    int accountNo = account.getAccountNo();
                    int userId = account.getCustomerId();
                    String accountType = account.getAccountType();
                    double balance = account.getBalance();
                    double transactionLimit = account.getTransactionLimit();

                    if (accountStash.getAccountType().equalsIgnoreCase(accountType)
                            && accountNo == accountStash.getAccountNo() && accountStash.getCustomerId() == userId) {
                        balance = accountStash.getBalance();
                        transactionLimit = accountStash.getTransactionLimit();
                    }

                    String[] dataToAppend = { String.valueOf(accountNo), String.valueOf(userId),
                            account.getAccountType(), String.valueOf(balance), String.valueOf(transactionLimit) };
                    csvLine = Arrays.stream(dataToAppend)
                            .map(csv_update_help::escapeDoubleQuotes)
                            .collect(Collectors.joining(","));

                    writer.append("\n" + csvLine);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (oldFile.delete()) {
            if (newFile.renameTo(oldFile)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param activity
     * @param customerId
     * 
     */

    public static void generateCSVofSecurity(String activity, int customerId) {
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
                .map(csv_update_help::escapeDoubleQuotes)
                .collect(Collectors.joining(","));

        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append("\n" + csvLine);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createCustomerAccount(Customer customer) {
        String filepath = "MOCK_DATA.csv";

        String[] dataToAppend = { String.valueOf(customer.getCustomerId()), customer.getName(), customer.getUserName(),
                customer.getPassword(), customer.getSalt() };

        String csvLine = Arrays.stream(dataToAppend)
                .map(csv_update_help::escapeDoubleQuotes)
                .collect(Collectors.joining(","));

        // append data to next row
        try (FileWriter writer = new FileWriter(filepath, true)) { // Append mode
            writer.append("\n" + csvLine);
        } catch (IOException e) {
            System.err.println("Error appending to CSV: " + e.getMessage());
        }
    }

    public static void generateCSVtoAccount(int customerID, Account account) {
        String filepath = "Account_Data.csv";

        String[] dataToAppend = { String.valueOf(account.getAccountNo()),
                String.valueOf(customerID), account.getAccountType(),
                String.valueOf(account.getBalance()),
                String.valueOf(account.getTransactionLimit()) };

        String csvLine = Arrays.stream(dataToAppend)
                .map(csv_update_help::escapeDoubleQuotes)
                .collect(Collectors.joining(","));

        // append data to next row
        try (FileWriter writer = new FileWriter(filepath, true)) { // Append mode
            writer.append("\n");
            writer.append(csvLine);
        } catch (IOException e) {
            System.err.println("Error appending to CSV: " + e.getMessage());
        }

    }

    public static void updateLoanToCsv(ArrayList<Loan> loans) {
        try (BufferedReader br = new BufferedReader(new FileReader("Loan_Data.csv"));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String header = br.readLine(); // Read and skip the header line
            bw.write(header + "\n"); // Write the header line to the new file

            String sLine;
            while ((sLine = br.readLine()) != null) {
                String[] data = sLine.split(",");
                int loanId = Integer.parseInt(data[0]);
                int customerId = Integer.parseInt(data[1]);
                double loanAmount = Double.parseDouble(data[2]);
                double interestRate = Double.parseDouble(data[3]);
                LocalDate loanDueDate = LocalDate.parse(data[4]);

                // Update records for customerId 3
                for (Loan loan : loans) {
                    if (loan.getLoanId() == loanId) {
                        loanAmount = loan.getLoanAmount();
                        interestRate = loan.getInterestRate();
                        loanDueDate = loan.getLoanDueDate();
                        break;
                    }
                }

                bw.write(String.format("%d,%d,%.2f,%.2f,%s\n",
                        loanId, customerId, loanAmount, interestRate, loanDueDate));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Replace the original file with the updated file
        File originalFile = new File("Loan_Data.csv");
        File tempsFile = new File(tempFile);
        tempsFile.renameTo(originalFile);
    }

    public static void updateExistingCreditCardBills(CreditCard card, String cardNo) {
        StringBuilder sb = new StringBuilder(
                "credit_card_id,customer_id,account_number,card_number,cvv,expiration_date,balance,remaining_credit,credit_limit,cash_advance,cash_advancement_limit\n");
        File originalFile = new File("mock_credit_card.csv");
        File newFile = new File(tempFile);

        try (BufferedReader br = new BufferedReader(new FileReader(originalFile))) {
            br.readLine();
            String sLine;
            while ((sLine = br.readLine()) != null) {
                String[] data = sLine.split(",");

                if (card.getCreditCardId() == Integer.parseInt(data[0]) && card.getCardNumber().equals(cardNo)) {
                    data[6] = String.valueOf(card.getBalance());
                    data[7] = String.valueOf(card.getRemainingCredit());
                    data[9] = String.valueOf(card.getCashAdvancePayable());
                }

                String line = String.join(",", data);
                sb.append(line + "\n");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile))) {
            bw.append(sb);

        } catch (IOException e) {
            return;
        }

        originalFile.delete();
        newFile.renameTo(originalFile);
    }

    private static String escapeDoubleQuotes(String str) {
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
