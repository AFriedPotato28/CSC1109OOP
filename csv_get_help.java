import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class csv_get_help {

    public static void populateCustomersList(ArrayList<Customer> customers) {
        try (BufferedReader bur = new BufferedReader(new FileReader("MOCK_DATA.csv"))) {
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
        try (BufferedReader bur = new BufferedReader(new FileReader("Account_Data.csv"))) {
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

    public static void populateCurrencyList(HashMap<String, Currency> listofCurrencies) {

        try (BufferedReader bur = new BufferedReader(new FileReader("ForeignExchangeRate.csv"))){
            bur.readLine();
            String sLine;
            while((sLine = bur.readLine()) != null){
                String[] data = sLine.split(",");
                String Symbol = data[1];
                double purchasePrice = Double.parseDouble(data[2]);
                double sellPrice = Double.parseDouble(data[3]);
                String toSource = data[4];
                String fromSource = data[5];

                Currency currency = new Currency(Symbol, purchasePrice, sellPrice, fromSource, toSource);
                
                if(!listofCurrencies.containsKey(fromSource)){
                    listofCurrencies.put(fromSource,new Currency());
                }
                
                listofCurrencies.put(fromSource,currency);
            } 

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    

}
