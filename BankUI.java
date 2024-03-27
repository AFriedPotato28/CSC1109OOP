import javax.swing.*;

import implementations.Security;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;

public class BankUI extends JFrame {
    private Bank bank;
    private Security securityInstance;
    private JTextField nameField, usernameField, passwordField,usernameLoginField,passwordLoginField;
    private JTextArea loanTextArea, creditTextArea;
    private JButton createAccountButton, loginButton;
    private JLabel nameLabel, usernameLabel, passwordLabel, balanceLabel, cardCountLabel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private boolean isNewAccount = false;
    private String userInfo = "";

    public BankUI(String title) {
        super(title);
        bank = new Bank("Random");
        securityInstance = new Security();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel inputPanel = frontPanel();
        cardPanel.add(inputPanel, "Front Page");
        cardPanel.add(loginPanel(), "Login");
        cardPanel.add(registerPanel(), "Register");
        cardPanel.add(branchPanel(), "Branch");
        cardPanel.add(mainMenuPanel(), "Main Menu");
        cardPanel.add(withdrawPanel(), "Withdraw");
        cardPanel.add(depositPanel(), "Deposit");
        cardPanel.add(transferPanel(), "Transfer");
        cardPanel.add(creditCardPanel(), "Credit Card");
        cardPanel.add(payCreditBillPanel(), "Pay Credit Card Bill");
        cardPanel.add(viewCreditBillPanel(), "View Credit Bill");
        cardPanel.add(creditWithdrawalPanel(), "Credit - Cash Withdrawal");
        cardPanel.add(payCashAdvancePanel(), "Pay Cash Advance");

        cardPanel.add(insurancePanel(), "Insurance");
        cardPanel.add(applyInsurancePanel(), "Apply Insurance");
        cardPanel.add(viewInsurancePanel(), "View Insurance");
        cardPanel.add(cancelInsurancePanel(), "Cancel Insurance");

        cardPanel.add(foreignExchangePanel(), "Foreign Exchange");
        cardPanel.add(viewForeignExchangePanel(), "View Foreign Exchange");

        cardPanel.add(settingPanel(), "Setting");
        cardPanel.add(resetPasswordPanel(), "Reset Password");
        cardPanel.add(changeTransactionLimitPanel(), "Change Transaction Limit");
         
        cardPanel.add(loanPanel(), "Loan");
        cardPanel.add(applyLoanPanel(), "Apply Loan");
        cardPanel.add(payLoanPanel(), "Pay Loan");
        cardPanel.add(viewLoanPanel(), "View Loan");
        
        add(cardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel frontPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 75 , 10, 0);

        JLabel welcomeLabel = new JLabel("Welcome to Matthias Bank! ");
        gbc.gridy = 0;
        inputPanel.add(welcomeLabel, gbc);

        JLabel chooseActionLabel = new JLabel("What would you like to do today?");
        gbc.gridy = 1;
        inputPanel.add(chooseActionLabel, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Register");
            }
        });
        buttonsPanel.add(createAccountButton);

        loginButton = new JButton("Login");
        gbc.insets = new Insets(10, 0, 0, 0);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Login");
            }
        });
        buttonsPanel.add(loginButton);

        JButton BranchButton = new JButton("View Branch");
        BranchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Branch");
            }
        });
        buttonsPanel.add(BranchButton);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(buttonsPanel, gbc);

        return inputPanel;
    }

    private JPanel loginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        usernameLabel = new JLabel("Please enter your Username:");
        gbc.gridy = 2;
        loginPanel.add(usernameLabel, gbc);

        usernameLoginField = new JTextField();
        usernameLoginField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        loginPanel.add(usernameLoginField, gbc);
        
        passwordLabel = new JLabel("Please enter your Password:");
        gbc.gridy = 4;
        loginPanel.add(passwordLabel, gbc);

        passwordLoginField = new JPasswordField();
        passwordLoginField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        loginPanel.add(passwordLoginField, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        buttonsPanel.add(loginButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Front Page");
            }
        });
        buttonsPanel.add(backButton, gbc);

        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.gridy = 8;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(buttonsPanel, gbc);

        return loginPanel;
    }

    private JPanel registerPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameLabel = new JLabel("Please set a name for your account:");
        gbc.gridy = 0;
        registerPanel.add(nameLabel, gbc);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        registerPanel.add(nameField, gbc);

        usernameLabel = new JLabel("Please set a new Username:");
        gbc.gridy = 2;
        registerPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        registerPanel.add(usernameField, gbc);

        passwordLabel = new JLabel("Please set a new Password:");
        gbc.gridy = 4;
        registerPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        registerPanel.add(passwordField, gbc);

        JLabel reenterNewPassword = new JLabel("Please reenter the password:");
        gbc.gridy = 6;
        registerPanel.add(reenterNewPassword, gbc);

        JTextField reenterNewPasswordField = new JPasswordField();
        reenterNewPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 7;
        registerPanel.add(reenterNewPasswordField, gbc);

        JLabel annualIncomeLabel = new JLabel("Please choose your annual income:");
        gbc.gridy = 8;
        registerPanel.add(annualIncomeLabel, gbc);

        Integer[] annualIncome = {15000, 20000, 25000, 30000};
        JComboBox<Integer> annualIncomeComboBox = new JComboBox<>(annualIncome);
        annualIncomeComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 9;
        registerPanel.add(annualIncomeComboBox, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Password = passwordField.getText();
                String reenterNewPassword = reenterNewPasswordField.getText();

                if(!securityInstance.validatePassword(Password)){
                    JOptionPane.showMessageDialog(null, "Does not meet the basic requirements of the password of 1 .....","Error",JOptionPane.ERROR_MESSAGE); // help me change this later
                    return;
                }

                if (!Password.equals(reenterNewPassword) || Password.equals("")) {
                    JOptionPane.showMessageDialog(null, "The new passwords does not match or cannot be null.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;

                } else {
                    createAccount();
                    reenterNewPasswordField.setText("");
                cardLayout.show(cardPanel, "Front Page");
                }
            }
        });
        buttonsPanel.add(createAccountButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Front Page");
            }
        });
        buttonsPanel.add(backButton, gbc);

        gbc.gridy = 10;
        gbc.insets = new Insets(20, 0, 0 , 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPanel.add(buttonsPanel, gbc);

        return registerPanel;
    }

    private void createAccount() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        while (name.isBlank()) {
            name = JOptionPane.showInputDialog(this, "Please enter your name");
        }

        while (bank.validateUsername(username) || username.isBlank()) {

            JTextField usernameField = new JTextField();
            Object[] fields = { "Please enter a valid username", usernameField };
            int confirm = JOptionPane.showConfirmDialog(this, fields, "Username", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                username = usernameField.getText();
            } else {
                return;
            }
        }

        while (!securityInstance.validatePassword(password)) {
            JPasswordField passwordField = new JPasswordField();
            Object[] fields = { "Please enter a valid password", passwordField };
            int confirm = JOptionPane.showConfirmDialog(this, fields, "Password", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                password = new String(passwordField.getPassword());
            } else {
                return;
            }
        }

        if (bank.findUserExists(username)) {
            JOptionPane.showMessageDialog(null, "The username already exist.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        bank.addCustomer(name, username, password);
        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        JOptionPane.showMessageDialog(this, "Account created successfully!");
    }

    private void login() {
        String loginUsername = usernameLoginField.getText();
        String loginPassword = passwordLoginField.getText();
        int count = 0;
        long lockoutEndTime = 0;
        boolean needReEnter = false;
     
        while (count < 3) {
            if (System.currentTimeMillis() < lockoutEndTime) {
                long remainingTime = lockoutEndTime - System.currentTimeMillis() / 1000;
                JOptionPane.showMessageDialog(this,
                        "Too many failed attempt, please try again after " + remainingTime + " seconds.");
                return;
            }

            if (needReEnter || count == 0 && isNewAccount) {
                loginUsername = JOptionPane.showInputDialog(this, "Please enter your username:");
                JPasswordField passwordField = new JPasswordField();
                Object[] fields = { "Please enter your password:", passwordField };
                int confirm = JOptionPane.showConfirmDialog(this, fields, "Password", JOptionPane.OK_CANCEL_OPTION);
                if (confirm == JOptionPane.OK_OPTION) {
                    loginPassword = new String(passwordField.getPassword());
                } else {
                    return;
                }
                needReEnter = false;
            }

            if (!bank.validateLogin(loginUsername, loginPassword)) {
                JOptionPane.showMessageDialog(this, "Wrong credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                count++;
                needReEnter = true;
            } else {
                bank.setLoginDetails(loginUsername, loginPassword);
                int OTP = bank.generateOTP(loginUsername);
                int counter = 1;
                usernameLoginField.setText("");
                passwordLoginField.setText("");

                JDialog OTPDialog = new JDialog();
                OTPDialog.setTitle("One Time Password");
                OTPDialog.add(new JLabel("Your one time password is " + OTP));
                OTPDialog.setSize(250, 150);
                OTPDialog.setModal(false); // Make the dialog non-modal
                OTPDialog.setVisible(true);

                JTextField OTPField = new JTextField();

                OTPField.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent event) {
                        if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9'
                                || event.getKeyChar() == (char) 8) {
                            OTPField.setEditable(true);

                        } else {
                            OTPField.setEditable(false);
                        }
                    }
                });

                Object[] fields = { "Please enter your one time password", OTPField };

                while (counter <= 3 && JOptionPane.showConfirmDialog(this, fields, "One Time Password",
                        JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

                    if (!OTPField.getText().equals("")
                            && bank.authenticateOTP(loginUsername, Integer.parseInt(OTPField.getText()))) {
                        JOptionPane.showMessageDialog(this, "Login successful!");
                        userInfo = loginUsername;
                        bank.populateUserInfo(userInfo);
                        OTPDialog.setVisible(false);
                        cardLayout.show(cardPanel, "Main Menu");
                        return;
                    }

                    JOptionPane.showMessageDialog(this, "You have " + (3 - counter) + " attempts left");
                    counter++;
                }

                OTPDialog.setVisible(false);
                JOptionPane.showMessageDialog(this, "You have tried more than 3 attempts", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (count >= 3) {
            JOptionPane.showMessageDialog(this, "Too many failed attempt. You are locked out for 30 seconds. ", "Error", JOptionPane.ERROR_MESSAGE);
            lockoutEndTime = System.currentTimeMillis() + 30000;
        }
    }

    public void setNewAccount(boolean isNew) {
        isNewAccount = isNew;
    }

    private JPanel branchPanel(){
        JPanel branchPanel = new JPanel(new GridBagLayout());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Front Page");
            }
        });
        branchPanel.add(backButton);

        return branchPanel;
    }

    private JPanel mainMenuPanel() {
        JPanel mainMenuPanel = new JPanel(new GridLayout(10, 10));

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        JLabel welcomeLabel = new JLabel("Main Menu", SwingConstants.CENTER);
        JLabel messageLabel = new JLabel("Welcome! What would you like to do today?", SwingConstants.CENTER);
        welcomePanel.add(Box.createVerticalGlue());
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(messageLabel);
        welcomePanel.add(Box.createVerticalGlue());
        mainMenuPanel.add(welcomePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton transactionButton = new JButton("Transaction");
        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.add(transactionPanel(),"Transaction");
                updateAccountBalance();
                cardLayout.show(cardPanel, "Transaction");

            }
        });
        buttonPanel.add(transactionButton);

        JButton creditCardButton = new JButton("Credit Card");
        creditCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Credit Card");
                creditCardPanel();
            }
        });
        buttonPanel.add(creditCardButton);

        JButton loanButton = new JButton("Loan");
        loanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Loan");
                loanPanel();
            }
        });
        buttonPanel.add(loanButton);

        JButton insuranceButton = new JButton("Insurance");
        insuranceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Insurance");
                insurancePanel();
            }
        });
        buttonPanel.add(insuranceButton);

        JButton foreignExButton = new JButton("Foreign Exchange");
        foreignExButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Foreign Exchange");
                foreignExchangePanel();
            }
        });
        buttonPanel.add(foreignExButton);

        JButton settingButton = new JButton("Setting");
        settingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Setting");
                settingPanel();
            }
        });
        buttonPanel.add(settingButton);

        JButton logOutButton = new JButton("Logout");
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Login");
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logOutButton);
        mainMenuPanel.add(logoutPanel, BorderLayout.NORTH);
        mainMenuPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainMenuPanel;
    }

    public void updateAccountBalance() {
        new Thread(() -> {
            try {
                double accountBalance = bank.getBalance(); // Fetch balance in background thread
                // Now update the GUI on the EDT
                SwingUtilities.invokeLater(() -> {
                    balanceLabel.setText(String.format("Account Balance: $%.2f", accountBalance));
                    balanceLabel.revalidate();
                    balanceLabel.repaint();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private JPanel transactionPanel() {
        JPanel transactionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        double accountBalance = 0.0;

        try {
            accountBalance = bank.getBalance();
            System.out.println("Current account balance: " + accountBalance);
        } catch (NoSuchElementException e) {
            System.out.println("User Information not set");
        }

        balanceLabel = new JLabel(String.format("Account Balance: $%.2f",
                accountBalance));

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridy = 0;
        transactionPanel.add(balanceLabel, gbc);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 1;
        transactionPanel.add(messageLabel, gbc);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Withdraw");
            }
        });
        transactionPanel.add(withdrawButton, gbc);

        JButton depositButton = new JButton("Deposit");
        depositButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Deposit");
            }
        });
        transactionPanel.add(depositButton, gbc);

        JButton transferButton = new JButton("Transfer");
        transferButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 4;
        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Transfer");
            }
        });
        transactionPanel.add(transferButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
        transactionPanel.add(backButton, gbc);

        transactionPanel.revalidate();
        transactionPanel.repaint();

        return transactionPanel;
    }

    private JPanel withdrawPanel() {
        JPanel withdrawPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel withdrawMessage = new JLabel("Please state amount to withdraw:");
        gbc.gridy = 0;
        withdrawPanel.add(withdrawMessage, gbc);

        JTextField withdrawAmountField = new JTextField();
        withdrawAmountField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
       

        withdrawAmountField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9'
                        || event.getKeyChar() == (char) 8) {
                            withdrawAmountField.setEditable(true);

                } else {
                    withdrawAmountField.setEditable(false);
                }
            }
            });

        withdrawPanel.add(withdrawAmountField, gbc);

        JButton submitButton = new JButton("Submit");
        
         submitButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            try{
            String withdrawAmountText = withdrawAmountField.getText();
            double withdrawAmount = Double.parseDouble(withdrawAmountText);
            double transactionLimit = bank.getTransactionLimit();

            if (withdrawAmount <= 0){
                JOptionPane.showMessageDialog(null,"Value cannot be negative", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (bank.getBalance() >= withdrawAmount && withdrawAmount <= transactionLimit ) {
                bank.withdraw(withdrawAmount, userInfo);
                JOptionPane.showMessageDialog(null, "Withdrawal successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateAccountBalance();
                cardLayout.show(cardPanel, "Transaction");
                } else {
                    JOptionPane.showMessageDialog(null,"Withdrawal failed. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        });
         
        gbc.gridy = 2;
        withdrawPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Transaction");
            }
        });
        gbc.gridy = 3;
        withdrawPanel.add(backButton, gbc);

        return withdrawPanel;
    }

    private JPanel depositPanel() {
        JPanel depositPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel depositMessage = new JLabel("Please state amount to deposit:");
        gbc.gridy = 0;
        depositPanel.add(depositMessage, gbc);

        JTextField depositAmountField = new JTextField();
        depositAmountField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;

        depositAmountField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9'
                        || event.getKeyChar() == (char) 8) {
                            depositAmountField.setEditable(true);

                } else {
                    depositAmountField.setEditable(false);
                }
            }
        });

        depositPanel.add(depositAmountField, gbc);

        JButton submitButton = new JButton("Submit");
        
        submitButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        try {
            String depositAmountText = depositAmountField.getText();
            double depositAmount = Double.parseDouble(depositAmountText);

            if(depositAmount <= 0){
                JOptionPane.showMessageDialog(null, "Value cannot be negative", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean depositSuccessful = bank.deposit(depositAmount, userInfo);
            if(depositSuccessful){
                JOptionPane.showMessageDialog(null, "Deposit Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateAccountBalance();
                cardLayout.show(cardPanel, "Transaction");
            } else {
                JOptionPane.showMessageDialog(null, "Deposit Failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
         } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
         }
        }
        });
         
        gbc.gridy = 2;
        depositPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Transaction");
            }
        });
        gbc.gridy = 3;
        depositPanel.add(backButton, gbc);

        return depositPanel;
    }

    private JPanel transferPanel() {
        JPanel transferPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel AccountHolderMessage = new JLabel("Please type the username of account holder to transfer to: ");
        gbc.gridy = 0;
        transferPanel.add(AccountHolderMessage, gbc);

        JTextField accountHolderField = new JTextField();
        accountHolderField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        transferPanel.add(accountHolderField, gbc);

        JLabel transferMessage = new JLabel("Please type amount to transfer: ");
        gbc.gridy = 2;
        transferPanel.add(transferMessage, gbc);

        JTextField transferField = new JTextField();
        transferField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;

        transferField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9'
                        || event.getKeyChar() == (char) 8 || event.getKeyChar() == (char)46) {
                            transferField.setEditable(true);

                } else {
                    transferField.setEditable(false);
                }
            }
        });

        transferPanel.add(transferField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            try{
            String recipient = accountHolderField.getText();
            double amount = Double.parseDouble(transferField.getText());

            if(amount <= 0){
                JOptionPane.showMessageDialog(null, "Value cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(amount <= bank.getTransactionLimit()){
                JOptionPane.showMessageDialog(null, "Exceeded transaction limit", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean transferSuccessful = bank.transferAmount(amount, recipient);
            if (transferSuccessful) {
                JOptionPane.showMessageDialog(null, "Transfer successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateAccountBalance();
                cardLayout.show(cardPanel, "Transaction");
            } else {
                JOptionPane.showMessageDialog(null, "Transfer failed. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
         });
         
        gbc.gridy = 4;
        transferPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Transaction");
            }
        });
        gbc.gridy = 5;
        transferPanel.add(backButton, gbc);

        return transferPanel;
    }

    private JPanel creditCardPanel() {
        JPanel creditCardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        creditCardPanel.add(messageLabel, gbc);

        JButton ApplyCreditCardButton = new JButton("Apply Credit Card");
        ApplyCreditCardButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        gbc.insets.top = verticalSpacing;
        ApplyCreditCardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardPanel.add(applyCreditCardPanel(),"Apply Credit Card");
                updatecardCountLabel();
                cardLayout.show(cardPanel, "Apply Credit Card");
            }
        });
        creditCardPanel.add(ApplyCreditCardButton, gbc);

        JButton payCreditBillButton = new JButton("Pay Credit Card Bill");
        payCreditBillButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        payCreditBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Pay Credit Card Bill");
            }
        });
        creditCardPanel.add(payCreditBillButton, gbc);

        JButton viewCreditBillButton = new JButton("View Credit Bill");
        viewCreditBillButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        viewCreditBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCreditTextArea();
                cardLayout.show(cardPanel, "View Credit Bill");
            }
        });
        creditCardPanel.add(viewCreditBillButton, gbc);

        JButton cashWithdrawalButton = new JButton("Credit - Cash Withdrawal");
        cashWithdrawalButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 4;
        cashWithdrawalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Credit - Cash Withdrawal");
            }
        });
        creditCardPanel.add(cashWithdrawalButton, gbc);

        JButton cashAdvancePaymentButton = new JButton("Pay Cash Advance");
        cashAdvancePaymentButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        cashAdvancePaymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Pay Cash Advance");
            }
        });
        creditCardPanel.add(cashAdvancePaymentButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
        creditCardPanel.add(backButton, gbc);

        return creditCardPanel;
    }

    private void updatecardCountLabel(){
        new Thread(() -> {
            try {
                int custId = bank.retrieveUserInfo(userInfo).getCustomerId();// Fetch balance in background thread
                // Now update the GUI on the EDT
                SwingUtilities.invokeLater(() -> {
                    cardCountLabel.setText("You currently have " + String.valueOf(bank.getCreditCardCount(custId))  + " credit card(s).");
                    cardCountLabel.revalidate();
                    cardCountLabel.repaint();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private JPanel applyCreditCardPanel() {
        JPanel applyCreditCardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel messageLabel = new JLabel("Confirm New Credit Card Application:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        applyCreditCardPanel.add(messageLabel, gbc);
        try {
            
            int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();
            
            cardCountLabel = new JLabel();
            gbc.gridy = 1;
            applyCreditCardPanel.add(cardCountLabel, gbc);

            JButton applyButton = new JButton("Apply");
            applyButton.setPreferredSize(new Dimension(200, 30));
            gbc.gridy = 3;
            applyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int cardCount = bank.getCreditCardCount(customerId);

                    if (cardCount >= 2) {
                        JOptionPane.showMessageDialog(null, "You already have 2 credit cards. You cannot apply for more.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        updatecardCountLabel();
                        int accountNumber = bank.getAccountNo();
                        int annualIncome = 10000; // Hardcoded for now
                        bank.applyCreditCard(customerId,accountNumber,annualIncome);
                    }
                }
            });
            applyCreditCardPanel.add(applyButton, gbc);

        }catch (NoSuchElementException e){
            System.out.println("No element exists");
        }
        
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 4;
        gbc.insets.top = 10;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Credit Card");
            }
        });
        applyCreditCardPanel.add(backButton, gbc);

        return applyCreditCardPanel;
    }
    
    private JPanel payCreditBillPanel() {
        JPanel payCreditBillPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel messageLabel = new JLabel("Existing Credit Card Bills:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        payCreditBillPanel.add(messageLabel, gbc);

        //String[] creditCardNumbers = getCreditCardNumbers();
        //JComboBox<String> creditCardDropDown = new JComboBox<>(creditCardNumbers);
        //payCreditBillPanel.add(creditCardDropDown, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        // submitButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         //submitCreditPayment();
        //     }
        // });
        payCreditBillPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Credit Card");
            }
        });
        payCreditBillPanel.add(backButton, gbc);

        return payCreditBillPanel;
    }

    // private String[] getCreditCardNumbers(){

    //     return new String[] {"Card 1", "Card 2", "Card 3"};
    // }

    // private void submitCreditPayment(){
    // String selectedCard = creditCardDropDown.getSelectedItem();
    //     int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();
    //     List<CreditCard> cards = bank.getCreditCards(customerId);
    //     boolean hasBills = false;
    //     for (CreditCard card : cards) {
    //         if (card.getBalance() > 0) {
    //             JLabel cardLabel = new JLabel("Card " + card.getCardNumber() + " balance: " + card.getBalance());
    //             gbc.gridy++;
    //             payCreditBillPanel.add(cardLabel, gbc);

    //             JButton payButton = new JButton("Pay Bill");
    //             payButton.setPreferredSize(new Dimension(200, 30));
    //             payButton.addActionListener(new ActionListener() {
    //                 public void actionPerformed(ActionEvent e) {
    //                     String paymentAmountStr = JOptionPane.showInputDialog(null, "Enter the payment amount:", "Payment", JOptionPane.PLAIN_MESSAGE);
    //                     double paymentAmount = 0.0;
    //                     try {
    //                         paymentAmount = Double.parseDouble(paymentAmountStr);
    //                     } catch (NumberFormatException ex) {
    //                         JOptionPane.showMessageDialog(null, "Invalid payment amount... please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    //                         return;
    //                     }

    //                     if (paymentAmount <= 0.0) {
    //                         JOptionPane.showMessageDialog(null, "Payment amount cannot be lesser or equals to 0.0...", "Error", JOptionPane.ERROR_MESSAGE);
    //                         return;
    //                     }

    //                     // Find the credit card with the specific card number and pay the bill
    //                     Account savingsAccount = bank.getAccountInfo();
    //                     if (bank.payCreditBill(card.getCardNumber(), savingsAccount, paymentAmount)) {
    //                         // Payment successful
    //                         JOptionPane.showMessageDialog(null, "Payment of $" + paymentAmount + " for card ending in " +
    //                                 card.getCardNumber().substring(card.getCardNumber().length() - 4) + " was successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
    //                         cardLayout.show(cardPanel, "Credit Card");
    //                     } else {
    //                         // Payment failed
    //                         JOptionPane.showMessageDialog(null, "Payment of $" + paymentAmount + " for card ending in " +
    //                                 card.getCardNumber().substring(card.getCardNumber().length() - 4) + " failed.", "Error", JOptionPane.ERROR_MESSAGE);
    //                     }
    //                 }
    //             });
    //         }

    //         hasBills = true;
    //     }

    //     if (!hasBills) {
    //         JLabel noBillsLabel = new JLabel("No existing credit bills.");
    //         gbc.gridy++;
    //         payCreditBillPanel.add(noBillsLabel, gbc);
    //         }
    // }

    private void updateCreditTextArea(){
        new Thread(() -> {
            try {
                StringBuilder sb = bank.getCreditToString();
                // Now update the GUI on the EDT
                SwingUtilities.invokeLater(() -> {
                    creditTextArea.setText(sb.toString());
                    creditTextArea.revalidate();
                    creditTextArea.repaint();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    private JPanel viewCreditBillPanel() {
        JPanel viewCreditBillPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Existing Credit Card Bills:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        viewCreditBillPanel.add(messageLabel, gbc);

        
        creditTextArea = new JTextArea(10, 40);
        gbc.gridy = 1;
        creditTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(creditTextArea);
        viewCreditBillPanel.add(scrollPane, gbc);
        


        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy =2;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Credit Card");
            }
        });
        viewCreditBillPanel.add(backButton, gbc);

        return viewCreditBillPanel;
    }

    private JPanel creditWithdrawalPanel() {
        JPanel creditWithdrawalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();
        // List<CreditCard> cards = bank.getCreditCards(customerId);
        // boolean hasCards = false;
        // DefaultComboBoxModel<String> cardNumbersModel = new DefaultComboBoxModel<>();
        // for (CreditCard card : cards) {
        //     JLabel cardLabel = new JLabel("Card " + card.getCardNumber() + " Cash Advance Limit: " + card.getCashAdvanceLimit());
        //     gbc.gridy++;
        //     creditWithdrawalPanel.add(cardLabel, gbc);
        //     cardNumbersModel.addElement(card.getCardNumber());
        //     hasCards = true;
        // }

        // if (!hasCards) {
        //     JLabel noCardsLabel = new JLabel("No existing credit cards.");
        //     gbc.gridy++;
        //     creditWithdrawalPanel.add(noCardsLabel, gbc);
        // }

        JLabel selectCardLabel = new JLabel("Select the card number to withdraw cash from:");
        gbc.gridy++;
        creditWithdrawalPanel.add(selectCardLabel, gbc);

        // JComboBox<String> cardNumbersComboBox = new JComboBox<>(cardNumbersModel);
        // gbc.gridy++;
        // creditWithdrawalPanel.add(cardNumbersComboBox, gbc);

        JLabel enterAmountLabel = new JLabel("Enter the amount to withdraw:");
        gbc.gridy++;
        creditWithdrawalPanel.add(enterAmountLabel, gbc);

        JTextField amountField = new JTextField(20);
        gbc.gridy++;
        creditWithdrawalPanel.add(amountField, gbc);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy++;
        // withdrawButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         String cardNumber = (String) cardNumbersComboBox.getSelectedItem();
        //         double amount = Double.parseDouble(amountField.getText());
        //         // Call the method to withdraw cash from the credit card
        //         bank.cashAdvanceWithdrawal(cardNumber, amount);
        //     }
        // });
        creditWithdrawalPanel.add(withdrawButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy++;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Credit Card");
            }
        });
        creditWithdrawalPanel.add(backButton, gbc);

        return creditWithdrawalPanel;
    }

    private JPanel payCashAdvancePanel() {
        JPanel payCashAdvancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel messageLabel = new JLabel("Credit Cards with Cash Advance Payable:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        payCashAdvancePanel.add(messageLabel, gbc);

        // int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();
        // List<CreditCard> cards = bank.getCreditCards(customerId);
        // boolean hasCards = false;
        // DefaultComboBoxModel<String> cardNumbersModel = new DefaultComboBoxModel<>();
        // for (CreditCard card : cards) {
        //     if card.getCashAdvancePayable() > 0 {
        //         JLabel cardLabel = new JLabel("Card " + card.getCardNumber() + " Cash Advance Payable: " + card.getCashAdvancePayable());
        //         gbc.gridy++;
        //         payCashAdvancePanel.add(cardLabel, gbc);
        //         cardNumbersModel.addItem(card);
        //         hasCards = true;
        //     }
        // }

        // if (!hasCards) {
        //     JLabel noCardsLabel = new JLabel("No credit cards with cash advance payable.");
        //     gbc.gridy++;
        //     payCashAdvancePanel.add(noCardsLabel, gbc);
        // }

        JLabel selectCardLabel = new JLabel("Select the card number to pay for:");
        gbc.gridy++;
        payCashAdvancePanel.add(selectCardLabel, gbc);

        // JComboBox<String> cardNumbersComboBox = new JComboBox<>(cardNumbersModel);
        // gbc.gridy++;
        // payCashAdvancePanel.add(cardNumbersComboBox, gbc);

        JLabel enterAmountLabel = new JLabel("Enter the amount to pay:");
        gbc.gridy++;
        payCashAdvancePanel.add(enterAmountLabel, gbc);

        JTextField amountField = new JTextField(20);
        gbc.gridy++;
        payCashAdvancePanel.add(amountField, gbc);

        JButton payButton = new JButton("Pay");
        payButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy++;
        // payButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         CreditCard selectedCard = (CreditCard) cardNumbersComboBox.getSelectedItem(); // Get the selected CreditCard object
        //         double amount = Double.parseDouble(amountField.getText());
        //         if (selectedCard != null) {
        //             selectedCard.payCashAdvancePayable(amount); // need help adding account ???
        //         } else {
        //             System.out.println("No card selected.");
        //         }
        //     }
        // });
        payCashAdvancePanel.add(payButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Credit Card");
            }
        });
        payCashAdvancePanel.add(backButton, gbc);

        return payCashAdvancePanel;
    }

    private JPanel foreignExchangePanel() {
        JPanel foreignExchangePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        foreignExchangePanel.add(messageLabel, gbc);

        JButton viewForeignExButton = new JButton("View Foreign Exchange");
        viewForeignExButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        gbc.insets.top = verticalSpacing;
        viewForeignExButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "View Foreign Exchange");
            }
        });
        foreignExchangePanel.add(viewForeignExButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
        foreignExchangePanel.add(backButton, gbc);

        return foreignExchangePanel;
    }

    private JPanel viewForeignExchangePanel() {
        JPanel viewForeignExchangePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        viewForeignExchangePanel.add(messageLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Foreign Exchange");
            }
        });
        viewForeignExchangePanel.add(backButton, gbc);

        return viewForeignExchangePanel;
    }

    private JPanel insurancePanel() {
        JPanel insurancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        insurancePanel.add(messageLabel, gbc);

        JButton applyInsuranceButton = new JButton("Apply Insurance");
        applyInsuranceButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        gbc.insets.top = verticalSpacing;
        applyInsuranceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Apply Insurance");
            }
        });
        insurancePanel.add(applyInsuranceButton, gbc);

        JButton viewInsuranceButton = new JButton("View Insurance");
        viewInsuranceButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        gbc.insets.top = verticalSpacing;
        viewInsuranceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "View Insurance");
            }
        });
        insurancePanel.add(viewInsuranceButton, gbc);

        JButton cancelInsuranceButton = new JButton("Cancel Insurance");
        cancelInsuranceButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        gbc.insets.top = verticalSpacing;
        cancelInsuranceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Cancel Insurance");
            }
        });
        insurancePanel.add(cancelInsuranceButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
        insurancePanel.add(backButton, gbc);


        return insurancePanel;
    }

    private JPanel applyInsurancePanel() {
        JPanel applyInsurancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        applyInsurancePanel.add(messageLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Insurance");
            }
        });
        applyInsurancePanel.add(backButton, gbc);

        return applyInsurancePanel;
    }

    private JPanel viewInsurancePanel() {
        JPanel viewInsurancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        viewInsurancePanel.add(messageLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Insurance");
            }
        });
        viewInsurancePanel.add(backButton, gbc);

        return viewInsurancePanel;
    }

    private JPanel cancelInsurancePanel() {
        JPanel cancelInsurancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        cancelInsurancePanel.add(messageLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Insurance");
            }
        });
        cancelInsurancePanel.add(backButton, gbc);

        return cancelInsurancePanel;
    }

    private JPanel settingPanel() {
        JPanel settingPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        settingPanel.add(messageLabel, gbc);

        JButton resetPasswordButton = new JButton("Reset Password ");
        gbc.gridy = 1;
        gbc.insets.top = verticalSpacing;
        resetPasswordButton.setPreferredSize(new Dimension(200, 30));
        resetPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Reset Password");
            }
        });
        settingPanel.add(resetPasswordButton, gbc);

        JButton changeTransactionLimitButton = new JButton("Change Transaction Limit");
        gbc.gridy = 2;
        changeTransactionLimitButton.setPreferredSize(new Dimension(200,30));
        changeTransactionLimitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Change Transaction Limit");
            }
        });
        settingPanel.add(changeTransactionLimitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
        settingPanel.add(backButton, gbc);

        return settingPanel;
    }

    private JPanel resetPasswordPanel() {
        JPanel resetPasswordPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel oldPassword = new JLabel("Please enter old password:");
        gbc.gridy = 0;
        resetPasswordPanel.add(oldPassword, gbc);

        JTextField oldPasswordField = new JPasswordField();
        oldPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        resetPasswordPanel.add(oldPasswordField, gbc);

        JLabel newPassword = new JLabel("Please enter new password:");
        gbc.gridy = 2;
        resetPasswordPanel.add(newPassword, gbc);

        JTextField newPasswordField = new JPasswordField();
        newPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        resetPasswordPanel.add(newPasswordField, gbc);

        JLabel reenterNewPassword = new JLabel("Please reenter new password:");
        gbc.gridy = 4;
        resetPasswordPanel.add(reenterNewPassword, gbc);

        JTextField reenterNewPasswordField = new JPasswordField();
        newPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        resetPasswordPanel.add(reenterNewPasswordField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
           
            String newPassword = newPasswordField.getText();
            String reenterNewPassword = reenterNewPasswordField.getText();

            if (!bank.authenticateDetails(userInfo, oldPasswordField.getText())){
                JOptionPane.showMessageDialog(null, "Your current password does not match","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!securityInstance.validatePassword(newPassword)){
                JOptionPane.showMessageDialog(null, "Your new password does not meet the basic requirements of password", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(reenterNewPassword) || newPassword.equals("")) {
                JOptionPane.showMessageDialog(null, "The new passwords does not match or cannot be null.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            bank.resetPassword(userInfo, newPassword);
            oldPasswordField.setText("");
            reenterNewPasswordField.setText("");
            newPasswordField.setText("");
            JOptionPane.showMessageDialog(null, "Password has been successfully changed", "Success", JOptionPane.INFORMATION_MESSAGE);

           cardLayout.show(cardPanel, "Login");
            }
        });
        gbc.gridy = 6;
        resetPasswordPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Setting");
            }
        });
        gbc.gridy = 7;
        resetPasswordPanel.add(backButton, gbc);

        return resetPasswordPanel;
    }
   
    private JPanel changeTransactionLimitPanel() {
        JPanel changeTransactionLimitPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel transactionLimit = new JLabel("Please set new transaction limit:");
        gbc.gridy = 0;
        changeTransactionLimitPanel.add(transactionLimit, gbc);

        JTextField transactionLimitField = new JTextField();
        transactionLimitField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        changeTransactionLimitPanel.add(transactionLimitField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    int newLimit = Integer.parseInt(transactionLimitField.getText());

                    if (newLimit <= 0){
                        JOptionPane.showMessageDialog(null, "Transaction Limit must be more than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    boolean limitUpdated = bank.changeTransactionLimit(newLimit, userInfo);
                    if (limitUpdated) {
                        JOptionPane.showMessageDialog(null, "Transaction Limit updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(cardPanel, "Setting");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update the Transaction Limit.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridy = 2;
        changeTransactionLimitPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Setting");
            }
        });
        changeTransactionLimitPanel.add(backButton, gbc);

        return changeTransactionLimitPanel;
    }

    private JPanel loanPanel() {
        JPanel loanPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        loanPanel.add(messageLabel, gbc);

        JButton applyLoanButton = new JButton("Apply Loan");
        gbc.gridy = 1;
        gbc.insets.top = verticalSpacing;
        applyLoanButton.setPreferredSize(new Dimension(200, 30));
        applyLoanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Apply Loan");
            }
        });
        loanPanel.add(applyLoanButton, gbc);

        JButton payLoanButton = new JButton("Pay Loan");
        gbc.gridy = 2;
        payLoanButton.setPreferredSize(new Dimension(200, 30));
        payLoanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Pay Loan");
            }
        });
        loanPanel.add(payLoanButton, gbc);

        JButton viewLoanButton = new JButton("View Loan");
        gbc.gridy = 3;
        viewLoanButton.setPreferredSize(new Dimension(200, 30));
        viewLoanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "View Loan");
                updateLoanDisplay();
            }
        });
        loanPanel.add(viewLoanButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
        gbc.gridy = 4;
        loanPanel.add(backButton, gbc);

        return loanPanel;
    }

    private JPanel applyLoanPanel() {
        JPanel applyLoanPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel messageLabel = new JLabel("Please key in how much you wish to loan:");
        gbc.gridy = 0;
        applyLoanPanel.add(messageLabel, gbc);

        JTextField loanAmountTextField = new JTextField();
        loanAmountTextField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;

        loanAmountTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9'
                        || event.getKeyChar() == (char) 8 || event.getKeyChar() == (char) 46) {
                            loanAmountTextField.setEditable(true);

                } else {
                    loanAmountTextField.setEditable(false);
                }
            }
        });

        applyLoanPanel.add(loanAmountTextField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    double loanAmount = Double.parseDouble(loanAmountTextField.getText());
                    int customerId = bank.retrieveUserInfo(userInfo).getCustomerId();

                    if(!bank.checkLoanAmount()){
                        JOptionPane.showMessageDialog(null, "Please check whether your loan amount/balance is valid");
                        return;
                    }

                    bank.applyLoan(customerId, loanAmount);
                    JOptionPane.showMessageDialog(null, "Loan application submitted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    updateLoanDisplay();
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridy = 2;
        applyLoanPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Loan");
            }
        });
        applyLoanPanel.add(backButton, gbc);

      return applyLoanPanel;
      }
    
    private JPanel payLoanPanel() {
        JPanel payLoanPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5,5,5,5);

        JLabel loanIdLabel = new JLabel("Loan ID: ");
        gbc.gridy = 0;
        payLoanPanel.add(loanIdLabel, gbc);
        
        JTextField loanIdField = new JTextField(10);
        gbc.gridy = 1;
        payLoanPanel.add(loanIdField, gbc);
        
        JLabel repayAmountLabel = new JLabel("Repayment Amount: ");
        gbc.gridy = 2;
        payLoanPanel.add(repayAmountLabel, gbc);
        
        JTextField repayAmountField = new JTextField(10);
        gbc.gridy = 3;

        repayAmountField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9'
                        || event.getKeyChar() == (char) 8 || event.getKeyChar() == (char) 46) {
                            repayAmountField.setEditable(true);

                } else {
                    repayAmountField.setEditable(false);
                }
            }
        });

        payLoanPanel.add(repayAmountField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        try {
            int repayLoanId = Integer.parseInt(loanIdField.getText().trim());
            double repayLoanAmount = Double.parseDouble(repayAmountField.getText().trim());
            bank.repayLoan(repayLoanId, repayLoanAmount);
            JOptionPane.showMessageDialog(null, "Loan repayment successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateLoanDisplay();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error repaying." + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        });
        gbc.gridy = 4;
        payLoanPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Loan");
            }
        });
        gbc.gridy = 5;
        payLoanPanel.add(backButton, gbc);

        return payLoanPanel;
     } 
    
    private JPanel viewLoanPanel() {
        JPanel viewLoanPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel titleLabel = new JLabel("Outstanding Loans", SwingConstants.CENTER);
        gbc.gridy = 0;
        viewLoanPanel.add(titleLabel, gbc);

        loanTextArea = new JTextArea(10, 40);
        gbc.gridy = 1;
        loanTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(loanTextArea);
        viewLoanPanel.add(scrollPane, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Loan");
            }
        });
        viewLoanPanel.add(backButton, gbc);

        return viewLoanPanel;
    }
     
    public void updateLoanDisplay(){
        new Thread(() -> {
            System.out.println(bank.getLoanString().toString());
            String loansDisplay = bank.getLoanString().toString();
            SwingUtilities.invokeLater(() -> loanTextArea.setText(loansDisplay));
        }).start();


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankUI("Banking System");
            }
        });
    }
}
