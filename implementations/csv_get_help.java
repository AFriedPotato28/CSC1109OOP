package implementations;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public final class csv_get_help {

    public static void populateCustomersList(ArrayList<Customer> customers) {
      
        try (BufferedReader bur = new BufferedReader(new FileReader("./CSV/MOCK_DATA.csv"))) {
            String sLine;
            bur.readLine();
            while ((sLine = bur.readLine()) != null) {
                String[] data = sLine.split(",");
                int id = Integer.parseInt(data[0]);
                String customerName = data[1];
                String customerUsername = data[2];
                String customerPassword = data[3];
                String Salt = data[4];

                Customer newCustomer = new Customer(id, customerName, customerUsername, customerPassword, Salt);
                customers.add(newCustomer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }

    public static void populateAccountList(HashMap<Integer, List<Account>> accounts) {
        try (BufferedReader bur = new BufferedReader(new FileReader("./CSV/Account_Data.csv"))) {
            String sLine;
            bur.readLine();
            while ((sLine = bur.readLine()) != null) {
                String[] data = sLine.split(",");
                int AccountID = Integer.parseInt(data[0]);
                int customerID = Integer.parseInt(data[1]);
                String accountType = data[2];
                double balance = Double.parseDouble(data[3]);
                double transactionLimit = Double.parseDouble(data[4]);

                Account account = new Account(AccountID, customerID, accountType, balance, transactionLimit);

                if (!accounts.containsKey(customerID)) {
                    accounts.put(customerID, new ArrayList<>());
                }

                accounts.get(customerID).add(account);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }

    public static void populateListLoan(HashMap<Integer, List<Loan>> loanList) {
        try (BufferedReader bur = new BufferedReader(new FileReader("./CSV/Loan_Data.csv"))) {
            String sLine;
            bur.readLine();
            while ((sLine = bur.readLine()) != null) {
                String[] data = sLine.split(",");
                int loanId = Integer.parseInt(data[0]);
                int customerId = Integer.parseInt(data[1]);
                double loanAmount = Double.parseDouble(data[2]);
                LocalDate date = LocalDate.parse(data[4]);

                Loan loan = new Loan(loanId, customerId, loanAmount,date);

                if((loanList.get(customerId) == null)){
                    loanList.put(customerId, new ArrayList<>());
                }

                loanList.get(customerId).add(loan);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
        
    }

    public static void populateCreditList(HashMap<Integer, List<CreditCard>> creditList) {
        try (BufferedReader bur = new BufferedReader(new FileReader("./CSV/mock_credit_card.csv"))) {
            String sLine;
            bur.readLine();
            while ((sLine = bur.readLine()) != null) {
                String[] data = sLine.split(",");

                int creditCardID = Integer.parseInt(data[0]);
                int customerID = Integer.parseInt(data[1]);
                int accountNo = Integer.parseInt(data[2]);
                String cardNo = data[3];
                String CVV = data[4];
                YearMonth expiryDate = YearMonth.parse(data[5]);
                double balance = Double.parseDouble(data[6]);
                double remainingCredit = Double.parseDouble(data[7]);
                int creditLimit = Integer.parseInt(data[8]);
                double cashAdvancedPayable = Double.parseDouble(data[9]);
                double cashAdvanceLimit = Double.parseDouble(data[10]);
                
                CreditCard card = new CreditCard(creditCardID, customerID, accountNo,balance, remainingCredit, creditLimit, cardNo, CVV,expiryDate,cashAdvancedPayable, cashAdvanceLimit);
                
                if (creditList.get(customerID) == null){
                    creditList.put(customerID,new ArrayList<>());
                }
                creditList.get(customerID).add(card);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }

}
