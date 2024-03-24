import javax.swing.*;
import implementations.Security;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;

public class BankUI extends JFrame {
    private Bank bank;
    private Security securityInstance;
    private JTextField nameField, usernameField, passwordField;
    private JButton createAccountButton, loginButton;
    private JLabel nameLabel, usernameLabel, passwordLabel, balanceLabel;
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

        JPanel inputPanel = loginPanel();
        cardPanel.add(inputPanel, "Login");
        cardPanel.add(mainMenuPanel(), "Main Menu");
        cardPanel.add(transactionPanel(), "Transaction");
        cardPanel.add(withdrawPanel(), "Withdraw");
        cardPanel.add(depositPanel(), "Deposit");
        cardPanel.add(transferPanel(), "Transfer");
        cardPanel.add(creditCardPanel(), "Credit Card");
        /*
         * cardPanel.add(applyCreditCardPanel(), "Apply Credit Card");
         * cardPanel.add(payCreditBillPanel(), "Pay Credit Card Bill");
         * cardPanel.add(viewCreditBillPanel(), "View Credit Bill");
         * cardPanel.add(creditWithdrawalPanel(), "Credit - Cash Withdrawal");
         * cardPanel.add(payCashAdvancePanel(), "Pay Cash Advance");
         */
        cardPanel.add(settingPanel(), "Setting");
        cardPanel.add(resetPasswordPanel(), "Reset Password");
        cardPanel.add(changeTransactionLimitPanel(), "Change Transaction Limit");
         
        cardPanel.add(loanPanel(), "Loan");
        cardPanel.add(applyLoanPanel(), "Apply Loan");
        //cardPanel.add(payLoanPanel(), "Pay Loan");
        cardPanel.add(viewLoanPanel(), "View Loan");
        
        add(cardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel loginPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameLabel = new JLabel("Name:");
        gbc.gridy = 0;
        inputPanel.add(nameLabel, gbc);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        inputPanel.add(nameField, gbc);

        usernameLabel = new JLabel("Username:");
        gbc.gridy = 2;
        inputPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        inputPanel.add(usernameField, gbc);

        passwordLabel = new JLabel("Password:");
        gbc.gridy = 4;
        inputPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        inputPanel.add(passwordField, gbc);

        createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });
        inputPanel.add(createAccountButton);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        inputPanel.add(loginButton);

        return inputPanel;
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

        bank.addCustomer(name, username, password);
        nameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        JOptionPane.showMessageDialog(this, "Account created successfully!");
    }

    private void login() {
        String loginUsername = usernameField.getText();
        String loginPassword = passwordField.getText();
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
                JOptionPane.showMessageDialog(this, "Wrong credentials. Please try again.");
                count++;
                needReEnter = true;
            } else {
                bank.setLoginDetails(loginUsername, loginPassword);
                int OTP = bank.generateOTP(loginUsername);
                int counter = 1;

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
                JOptionPane.showMessageDialog(this, "You have tried more than 3 attempts");
                return;
            }
        }

        if (count >= 3) {
            JOptionPane.showMessageDialog(this, "Too many failed attempt. You are locked out for 30 seconds. ");
            lockoutEndTime = System.currentTimeMillis() + 30000;
        }
    }

    public void setNewAccount(boolean isNew) {
        isNewAccount = isNew;
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
        withdrawPanel.add(withdrawAmountField, gbc);

        JButton submitButton = new JButton("Submit");
        
         submitButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            try{
            String withdrawAmountText = withdrawAmountField.getText();
            double withdrawAmount = Double.parseDouble(withdrawAmountText);
            double transactionLimit = bank.getTransactionLimit();
            
            if (bank.getBalance() > withdrawAmount && transactionLimit < withdrawAmount) {
                bank.withdraw(withdrawAmount, userInfo);
                JOptionPane.showMessageDialog(null, "Withdrawal successful");
                updateAccountBalance();
                cardLayout.show(cardPanel, "Transaction");
                } else {
                    JOptionPane.showMessageDialog(null,"Withdrawal failed. Please try again");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number");
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
        depositPanel.add(depositAmountField, gbc);

        JButton submitButton = new JButton("Submit");
        
        submitButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
        try {
            String depositAmountText = depositAmountField.getText();
            double depositAmount = Double.parseDouble(depositAmountText);
            boolean depositSuccessful = bank.deposit(depositAmount, userInfo);
            if(depositSuccessful){
                JOptionPane.showMessageDialog(null, "Deposit Successful");
                updateAccountBalance();
                cardLayout.show(cardPanel, "Transaction");
            } else {
                JOptionPane.showMessageDialog(null, "Deposit Failed. Please try again.");
            }
         } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
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
        transferPanel.add(transferField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            try{
            String recipient = accountHolderField.getText();
            double amount = Double.parseDouble(transferField.getText());
            boolean transferSuccessful = bank.transferAmount(amount, recipient);
            if (transferSuccessful) {
                JOptionPane.showMessageDialog(null, "Transfer successful");
                updateAccountBalance();
                cardLayout.show(cardPanel, "Transaction");
            } else {
                JOptionPane.showMessageDialog(null, "Transfer failed. Please try again");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
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

    /*
     * private JPanel applyCreditCardPanel() {
     * return applyCreditCardPanel();
     * }
     * 
     * private JPanel payCreditBillPanel() {
     * return payCreditBillPanel();
     * }
     * 
     * private JPanel viewCreditBillPanel() {
     * return viewCreditBillPanel();
     * }
     * 
     * private JPanel creditWithdrawalPanel() {
     * return creditWithdrawalPanel();
     * }
     * 
     * private JPanel payCashAdvancePanel() {
     * return payCashAdvancePanel();
     * }
     */

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

        JTextField oldPasswordField = new JTextField();
        oldPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        resetPasswordPanel.add(oldPasswordField, gbc);

        JLabel newPassword = new JLabel("Please enter new password:");
        gbc.gridy = 2;
        resetPasswordPanel.add(newPassword, gbc);

        JTextField newPasswordField = new JTextField();
        newPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        resetPasswordPanel.add(newPasswordField, gbc);

        JLabel reenterNewPassword = new JLabel("Please reenter new password:");
        gbc.gridy = 4;
        resetPasswordPanel.add(reenterNewPassword, gbc);

        JTextField reenterNewPasswordField = new JTextField();
        newPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        resetPasswordPanel.add(reenterNewPasswordField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            

            String newPassword = newPasswordField.getText();
            String reenterNewPassword = reenterNewPasswordField.getText();

            if (!newPassword.equals(reenterNewPassword)) {
                JOptionPane.showMessageDialog(null, "The new passwords does not match");
            }

            bank.resetPassword(userInfo, newPassword);
            JOptionPane.showMessageDialog(null, "Password has been successfully changed");

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
                        JOptionPane.showMessageDialog(null, "Transaction Limit must be more than 0.");
                        return;
                    }
                    boolean limitUpdated = bank.changeTransactionLimit(newLimit, userInfo);
                    if (limitUpdated) {
                        JOptionPane.showMessageDialog(null, "Transaction Limit updated successfully.");
                        cardLayout.show(cardPanel, "Setting");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update the Transaction Limit.");
                    }
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
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
        applyLoanPanel.add(loanAmountTextField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    double loanAmount = Double.parseDouble(loanAmountTextField.getText());
                    int customerId = bank.getAccountNo();
                    bank.applyLoan(customerId, loanAmount);
                    JOptionPane.showMessageDialog(null, "Loan application submitted successfully");
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
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
     

    
    /*private JPanel payLoanPanel() {
        JPanel payLoanPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        

        return payLoanPanel();
     }*/
     
    
    private JPanel viewLoanPanel() {
        JPanel viewLoanPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel titleLabel = new JLabel("Outstanding Loans", SwingConstants.CENTER);
        gbc.gridy = 0;
        viewLoanPanel.add(titleLabel, gbc);

        JTextArea loanTextArea = new JTextArea(10, 40);
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

        updateLoanDisplay(loanTextArea);

        return viewLoanPanel;
    }
     
    public void updateLoanDisplay(JTextArea loansTextArea){
        new Thread(() -> {
            System.out.println(bank.getLoanString().toString());
            String loansDisplay = bank.getLoanString().toString();
            SwingUtilities.invokeLater(() -> loansTextArea.setText(loansDisplay.toString()));
        }).start();


    }// you need any help just drop me a tttext in tele
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankUI("Banking System");
            }
        });
    }
}
