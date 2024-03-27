package implementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class csv_update_help {

    private static String tempFile = "./CSV/temp.csv";

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

        String fileName = "./CSV/MOCK_DATA.csv";
        File oldFile = new File(fileName);
        File newFile = new File(tempFile);

        StringBuilder sb = new StringBuilder("id ,name, username, password, salt \n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));) {

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

                sb.append(customerId + "," + customerName + "," + customerUserName + "," + customerPassword + ","
                        + customerSalt + "\n");
            }

            writer.append(sb);
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
     * @param accountStash
     * @return
     * 
     */

    public static boolean updateCSVOfAccount(HashMap<Integer, ArrayList<Account>> accounts) {

        String file = "./CSV/Account_Data.csv";

        File oldFile = new File(file);
        File newFile = new File(tempFile);

        StringBuilder sb = new StringBuilder("AccountNo, CustomerID, accountType, balance,transactionLimit \n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));) {
            for (Map.Entry<Integer, ArrayList<Account>> entry : accounts.entrySet()) {
                for (Account account : entry.getValue()) {
                    DecimalFormat df = new DecimalFormat("##.00");
                    double balance = Double.parseDouble(df.format(account.getBalance()));

                    sb.append(account.getCustomerId() + "," + account.getCustomerId() + "," + account.getAccountType()
                            + "," + balance + "," + account.getTransactionLimit() + "\n");
                }
            }

            writer.append(sb);

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
        String fileName = "./CSV/Log-Tracking.csv";

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
        String filepath = "./CSV/MOCK_DATA.csv";

        String[] dataToAppend = { String.valueOf(customer.getCustomerId()), customer.getName(), customer.getUserName(),
                customer.getPassword(), customer.getSalt() };

        String csvLine = Arrays.stream(dataToAppend)
                .map(csv_update_help::escapeDoubleQuotes)
                .collect(Collectors.joining(","));

        // append data to next row
        try (FileWriter writer = new FileWriter(filepath, true)) { // Append mode
            writer.append(csvLine + "\n");
        } catch (IOException e) {
            System.err.println("Error appending to CSV: " + e.getMessage());
        }
    }

    public static void generateCSVtoAccount(int customerID, Account account) {
        String filepath = "./CSV/Account_Data.csv";

        String[] dataToAppend = { String.valueOf(account.getAccountNo()),
                String.valueOf(customerID), account.getAccountType(),
                String.valueOf(account.getBalance()),
                String.valueOf(account.getTransactionLimit()) };

        String csvLine = Arrays.stream(dataToAppend)
                .map(csv_update_help::escapeDoubleQuotes)
                .collect(Collectors.joining(","));

        // append data to next row
        try (FileWriter writer = new FileWriter(filepath, true)) { // Append mode
            writer.append(csvLine + "\n");
        } catch (IOException e) {
            System.err.println("Error appending to CSV: " + e.getMessage());
        }

    }

    public static void updateExistingCreditCardBills(CreditCard card, String cardNo) {
        DecimalFormat df = new DecimalFormat("##.00");
        StringBuilder sb = new StringBuilder(
                "credit_card_id,customer_id,account_number,card_number,cvv,expiration_date,balance,remaining_credit,credit_limit,cash_advancement_payable,cash_advanced_limit\n");
        File originalFile = new File("./CSV/mock_credit_card.csv");
        File newFile = new File(tempFile);

        try (BufferedReader br = new BufferedReader(new FileReader(originalFile))) {
            br.readLine();
            String sLine;
            while ((sLine = br.readLine()) != null) {
                String[] data = sLine.split(",");

                if (card.getCreditCardId() == Integer.parseInt(data[0]) && card.getCardNumber().equals(cardNo)) {
                    data[6] = String.valueOf(df.format(card.getBalance()));
                    data[7] = String.valueOf(df.format(card.getRemainingCredit()));
                    data[9] = String.valueOf(df.format(card.getCashAdvancePayable()));
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

    public static void addLoanToCsv(Loan newloan) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./CSV/Loan_Data.csv", true))) {
            // Append object to csv
            String loanData = newloan.getLoanId() + "," + newloan.getCustomerId() + "," + newloan.getLoanAmount() + ","
                    + newloan.getInterestRate() + "," + newloan.getLoanDueDate();
            bw.write(loanData);
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateCSVOfLoan(HashMap<Integer, ArrayList<Loan>> loanList) {
        File file = new File("./CSV/Loan_Data.csv");
        File newFile = new File(tempFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            StringBuilder sb = new StringBuilder("loan_id,customer_id,loan_amount,interest_rate,loan_due_date\n");

            for (Map.Entry<Integer, ArrayList<Loan>> entry : loanList.entrySet()) {
                for (Loan loan : entry.getValue()) {
                  
                    sb.append(loan.getLoanId() + "," + loan.getCustomerId() + "," + loan.getLoanAmount() + ","
                            + loan.getInterestRate() + "," + loan.getLoanDueDate() + "\n");
                }
            }

            writer.append(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
        newFile.renameTo(file);
    }

    public static void writeCSVToCreditCard(HashMap<Integer, ArrayList<CreditCard>> creditList) {
        File temp = new File(tempFile);
        File originalFile = new File("./CSV/mock_credit_card.csv");

        StringBuilder sb = new StringBuilder(
                "credit_card_id,customer_id,account_number,card_number,cvv,expiration_date,balance,remaining_credit,credit_limit,cash_advancement_payable,cash_advanced_limit \n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {

            for (Map.Entry<Integer, ArrayList<CreditCard>> entry : creditList.entrySet()) {
                for (CreditCard card : entry.getValue()) {
                    sb.append(card.getCreditCardId() + "," + card.getCustomerId() + "," +
                            card.getAccountNo() + "," + card.getCardNumber() + "," +
                            card.getEncryptedCVV() + "," + card.getExpiryDate() + "," +
                            card.getBalance() + "," + card.getRemainingCredit() + "," + card.getCreditLimit() + "," +
                            card.getCashAdvancePayable() + "," + card.getCashAdvanceLimit() + "\n");
                }
            }
            writer.append(sb);
        } catch (Exception e) {
            return;
        }

        originalFile.delete();
        temp.renameTo(originalFile);

    }

    /**
     * Attempts to update the credit card to the CSV file.
     * 
     * @param creditCard The credit card object to update.
     * 
     *                   try to append the credit card object to the
     *                   mock_credit_card.csv file.
     *                   Append Customer ID, Account Number, Card Number, CVV,
     *                   Expiry Date, Balance, Remaining Credit, Credit Limit, Cash
     *                   Advance Payable and Cash Advance Limit to the CSV file.
     *                   catch any exceptions and throw a new RuntimeException with
     *                   the exception message.
     */
    public static void updateCreditCardToCSV(CreditCard creditCard) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./CSV/mock_credit_card.csv", true))) {
            String[] dataToAppend = { String.valueOf(creditCard.getCreditCardId()),
                    String.valueOf(creditCard.getCustomerId()),
                    String.valueOf(creditCard.getAccountNo()), creditCard.getCardNumber(),
                    creditCard.getEncryptedCVV(), String.valueOf(creditCard.getExpiryDate()),
                    String.valueOf(creditCard.getBalance()), String.valueOf(creditCard.getRemainingCredit()),
                    String.valueOf(creditCard.getCreditLimit()), String.valueOf(creditCard.getCashAdvancePayable()),
                    String.valueOf(creditCard.getCashAdvanceLimit()) };
            String line = String.join(",", dataToAppend);
            bw.write(line);
            bw.newLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
