package notgoingtoimplement;
public class implementedCodeButNotNeeded {

    // Currency

      // public void seeAllCurrencyExchanges() {
    //     StringBuilder sb = new StringBuilder();
    //     sb.append("Current supported exchange rate:\n");
    //     for (Entry<String, Currency> currency : this.listofCurrencies.entrySet()) {
    //         sb.append("Currency from " + currency.getValue().getToSource() + " to " +
    //                 currency.getKey() + " Purchase Price: " +
    //                 currency.getValue().getpurchasePrice() + " Selling Price: " +
    //                 currency.getValue().getsellingPrice()
    //                 + " \n");
    //     }
    //     System.out.println(sb.toString());
    // }

    // public void withdrawCurrency(String currencyName, double amount) {
    //     DecimalFormat df = new DecimalFormat("##.00");
    //     amount /= getCurrencyInformation(currencyName).getpurchasePrice();

    //     this.account.withdraw(Double.parseDouble(df.format(amount)));
    //     csv_update_help.updateCSVOfAccount(this.accounts, account);

    // }

    // private Currency getCurrencyInformation(String currencyName) {
    //     Currency currency = this.listofCurrencies.get(currencyName);
    //     return currency;
    // }

    // public boolean checkCurrency(String currency) {
    //     if (this.listofCurrencies.containsKey(currency)) {
    //         return true;
    //     }
    //     return false;
    // }

    // public Currency getRates(String currency) {
    //     if (this.listofCurrencies.containsKey(currency)) {
    //         return getCurrencyInformation(currency);
    //     }
    //     return null;
    // }

    // public static void populateBranchList(HashMap<Integer, Branch> listofBranch)
    // {
    // try (BufferedReader bur = new BufferedReader(new FileReader("branch.csv"))) {
    // bur.readLine();
    // String line;
    // while ((line = bur.readLine()) != null) {
    // String[] data = line.split(",");
    // // Branch branch = new Branch();

    // listofBranch.put(Integer.parseInt(data[0]), branch);
    // }

    // } catch (IOException e) {
    // return;
    // }
    // }

    //   public static void populateCurrencyList(HashMap<String, Currency> listofCurrencies) {

    //     try (BufferedReader bur = new BufferedReader(new FileReader("ForeignExchangeRate.csv"))) {
    //         bur.readLine();
    //         String sLine;
    //         while ((sLine = bur.readLine()) != null) {
    //             String[] data = sLine.split(",");
    //             String Symbol = data[1];
    //             double purchasePrice = Double.parseDouble(data[2]);
    //             double sellPrice = Double.parseDouble(data[3]);
    //             String toSource = data[4];
    //             String fromSource = data[5];

    //             Currency currency = new Currency(Symbol, purchasePrice, sellPrice, fromSource, toSource);

    //             if (!listofCurrencies.containsKey(fromSource)) {
    //                 listofCurrencies.put(fromSource, new Currency());
    //             }

    //             listofCurrencies.put(fromSource, currency);
    //         }

    //     } catch (IOException e) {
    //         throw new RuntimeException(e);
    //     }

    // }


    
    // private static void foreignExchangeOptions(Scanner scanner, Bank bank, String
    // userInfo) {
    // int choice = -1;
    // bank.seeAllCurrencyExchanges();

    // do {

    // System.out.println("1. Withdrawal of Foreign Exchange");
    // System.out.println("2. Exit");
    // System.out.println("Please enter 1 or 2 as your choice");
    // try {
    // choice = scanner.nextInt();
    // } catch (Exception e) {
    // System.out.println("Please enter a valid choice");
    // scanner = new Scanner(System.in);
    // choice = -1;
    // }

    // switch (choice) {
    // case 1:
    // exchangeMoney(scanner, bank, userInfo);
    // break;
    // default:
    // break;
    // }

    // } while (choice != 2);
    // }

    // private static void exchangeMoney(Scanner scanner, Bank bank, String
    // userInfo) {
    // try {
    // String chString = promptInput("Please enter a valid currency to exchange",
    // scanner);

    // while (!bank.checkCurrency(chString)) {
    // bank.seeAllCurrencyExchanges();
    // chString = promptInput("Please enter a valid currency to exchange", scanner);
    // }
    // System.out.println("Your bank has " + bank.getBalance() *
    // bank.getRates(chString).getpurchasePrice() + " "
    // + bank.getRates(chString).getSymbol() + " amount");

    // System.out.println("Please enter a valid amount to exchange");
    // int amount = scanner.nextInt();

    // while (!(amount >= 0 && amount < (bank.getBalance() *
    // bank.getRates(chString).getpurchasePrice()))) {
    // System.out.println("Please enter a valid amount to exchange");
    // amount = scanner.nextInt();
    // }

    // bank.withdrawCurrency(chString, amount);

    // } catch (InputMismatchException e) {
    // return;
    // }

    // }


    //Insurance

    // public void buyInsurance(int customerId, String insuranceType, String beneficiaryName) {
    //     Insurance insurance = new Insurance(customerId, insuranceType, beneficiaryName);
    //     addInsuranceToCSV(insurance);
    // }

    // public void addInsuranceToCSV(Insurance insurance) {
    //     try (BufferedWriter bw = new BufferedWriter(new FileWriter("mock_insurance_data.csv", true))) {
    //         // Append object to csv
    //         String insuranceData = insurance.getInsuranceID() + "," + insurance.getCustomerId() + ","
    //                 + insurance.getInsuranceType() + "," + insurance.getInsurancePremium()
    //                 + "," + insurance.getCoverageAmount() + "," + insurance.getBeneficiaryName();
    //         bw.write(insuranceData);
    //         bw.newLine();

    //     } catch (IOException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    
    // public static void insuranceOptions(Scanner scanner, Bank bank, String
    // userInfo) {
    // int choice = -1;
    // int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();
    // do {
    // System.out.println("\n1. View information on current Insurance");
    // System.out.println("2. Buy new Insurance");
    // System.out.println("3. Surrender Insurance");
    // System.out.println("4. Exit");

    // System.out.println("Please enter your chouice between 1 to 4");
    // try {
    // choice = scanner.nextInt();
    // } catch (Exception e) {
    // System.out.println("Enter a valid choice");
    // scanner = new Scanner(System.in);
    // choice = -1;
    // }

    // switch (choice) {
    // case 1:
    // int accountNo = bank.getAccountNo();
    // break;
    // case 2:
    // int insuranceChoice = -1;
    // String insuranceType = "";
    // System.out.println(
    // "Choose Insurance Type: \n 1. Health Insurance\n 2. Home Insurance \n 3.
    // Vehicle Insurance"
    // +
    // "\n 4. Life Insurance \n 5. Travel Insurance \n 6. Exit");

    // System.out.println(
    // "Enter the type of insurance you would like to purchase between 1 and 5, or
    // exit with 6");
    // try {
    // insuranceChoice = scanner.nextInt();
    // } catch (Exception e) {
    // System.out.println("Enter a valid choice");
    // scanner = new Scanner(System.in);
    // insuranceChoice = -1;
    // }
    // do {
    // if (insuranceChoice == 1) {
    // insuranceType = "Health";
    // break;
    // } else if (insuranceChoice == 2) {
    // insuranceType = "Home";
    // break;
    // } else if (insuranceChoice == 3) {
    // insuranceType = " Vehicle";
    // break;
    // } else if (insuranceChoice == 4) {
    // insuranceType = "Life";
    // break;
    // } else if (insuranceChoice == 5) {
    // insuranceType = "Travel";
    // break;
    // } else {
    // System.out.println("Invalid input");
    // break;
    // }
    // } while (insuranceChoice != 6);

    // Scanner scanner1 = new Scanner(System.in);
    // System.out.println("Enter Beneficiary Name:");
    // String beneficiaryName = scanner1.nextLine();

    // bank.buyInsurance(customerId, insuranceType, beneficiaryName);

    // break;
    // case 3:
    // break;
    // default:
    // break;
    // }
    // } while (choice != 4);
    // }

    
}
