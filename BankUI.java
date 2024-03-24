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
    private JLabel nameLabel, usernameLabel, passwordLabel;
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
        cardPanel.add(loanPanel(), "Loan");
        /*
         * cardPanel.add(applyLoanPanel(), "Apply Loan");
         * cardPanel.add(payLoanPanel(), "Pay Loan");
         * cardPanel.add(viewLoanPanel(), "View Loan");
         */
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

        while (username.isBlank()) {
            username = JOptionPane.showInputDialog(this, "Please enter username");
        }

        while (!securityInstance.validatePassword(password)) {
            password = JOptionPane.showInputDialog(this,
                    "Password does not meet requirements. Please enter again:");
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
                JOptionPane.showMessageDialog(this, "Login successful!");
                // OTP generation and authentication logic here.

                cardLayout.show(cardPanel, "Main Menu");
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
                cardLayout.show(cardPanel, "Transaction");
                transactionPanel();
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

    private JPanel transactionPanel() {
        JPanel transactionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        double accountBalance = 0.0;
        try {
            accountBalance = bank.getBalance();
        } catch (NoSuchElementException e) {
            System.out.println("User Information not set");
        }
        JLabel balanceLabel = new JLabel(String.format("Account Balance: $.2f", accountBalance));
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
        /*
         * submitButton.addActionListener(new ActionListener(){
         * public void actionPerformed(ActionEvent e){
         * withdraw();
         * }
         * });
         */
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
        /*
         * submitButton.addActionListener(new ActionListener(){
         * public void actionPerformed(ActionEvent e){
         * deposit();
         * }
         * });
         */
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

        JLabel AccountHolderMessage = new JLabel("Please type account holder to transfer to: ");
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
        /*
         * submitButton.addActionListener(new ActionListener(){
         * public void actionPerformed(ActionEvent e){
         * transfer();
         * }
         * });
         */
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

    /*
     * public void withdraw(){
     * 
     * }
     */

    /*
     * public void deposit(){
     * 
     * }
     */

    /*
     * public void transfer(){
     * 
     * }
     */

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

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
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

        JLabel reenterNewPassword = new JLabel("Please enter new password:");
        gbc.gridy = 4;
        resetPasswordPanel.add(reenterNewPassword, gbc);

        JTextField reenterNewPasswordField = new JTextField();
        newPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        resetPasswordPanel.add(reenterNewPasswordField, gbc);

        JButton submitButton = new JButton("Submit");
        /*
         * submitButton.addActionListener(new ActionListener(){
         * public void actionPerformed(ActionEvent e){
         * resetPassword();
         * }
         * });
         */
        gbc.gridy = 6;
        resetPasswordPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Setting");
            }
        });
        gbc.gridy = 5;
        resetPasswordPanel.add(backButton, gbc);

        return resetPasswordPanel;
    }

    /*
     * public void resetPassword(){
     * 
     * }
     */

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

        JButton applyLoanButton = new JButton("Apply Loan ");
        gbc.gridy = 1;
        gbc.insets.top = verticalSpacing;
        applyLoanButton.setPreferredSize(new Dimension(200, 30));
        applyLoanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, " Apply Loan");
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

    /*
     * private JPanel applyLoanPanel() {
     * return applyLoanPanel();
     * }
     */

    /*
     * private JPanel payLoanPanel() {
     * return payLoanPanel();
     * }
     */

    /*
     * private JPanel viewLoanPanel() {
     * return viewLoanPanel();
     * }
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankUI("Banking System");
            }
        });
    }
}
