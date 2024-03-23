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
        add(cardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel loginPanel(){
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameLabel = new JLabel("Name:");
        gbc.gridy = 0;
        inputPanel.add(nameLabel,gbc);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        inputPanel.add(nameField,gbc);

        usernameLabel = new JLabel("Username:");
        gbc.gridy = 2;
        inputPanel.add(usernameLabel,gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        inputPanel.add(usernameField,gbc);

        passwordLabel = new JLabel("Password:");
        gbc.gridy = 4;
        inputPanel.add(passwordLabel,gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        inputPanel.add(passwordField,gbc);

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
        while (!securityInstance.validatePassword(password)) {
            password = JOptionPane.showInputDialog(this, "Password does not meet requirements. Please enter again:");
        }
        String accountType = JOptionPane.showInputDialog(this, "Please enter account type (1: Savings, 2: Normal):");
        while (!accountType.equals("1") && !accountType.equals("2")) {
            accountType = JOptionPane.showInputDialog(this, "Invalid account type. Please enter again:");
        }
        bank.addCustomer(name,username,password);
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
                JOptionPane.showMessageDialog(this, "Too many failed attempt, please try again after " + remainingTime + " seconds.");
                return;
            }

            if (needReEnter || count ==0 && isNewAccount){
            loginUsername = JOptionPane.showInputDialog(this, "Please enter your username:");
            JPasswordField passwordField = new JPasswordField();
            Object[] fields = {"Please enter your password:", passwordField};
            int confirm = JOptionPane.showConfirmDialog(this, fields, "Password", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                loginPassword = new String(passwordField.getPassword());
            } else{
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
                    cardLayout.show(cardPanel, "Main Menu");
                    return;
            }
        }

        if (count >= 3) {
            JOptionPane.showMessageDialog(this, "Too many failed attempt. You are locked out for 30 seconds. ");
            lockoutEndTime = System.currentTimeMillis() + 30000;
        }
        // Assuming OTP generation and authentication logic here
    }

    public void setNewAccount (boolean isNew) {
        isNewAccount = isNew;
    }

    private JPanel mainMenuPanel() {
        JPanel mainMenuPanel = new JPanel(new GridLayout(10,10));

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        JLabel welcomeLabel = new JLabel("Main Menu", SwingConstants.CENTER);
        JLabel messageLabel = new JLabel("Welcome! What would you like to do today?", SwingConstants.CENTER);
        welcomePanel.add(Box.createVerticalGlue());
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(messageLabel);
        welcomePanel.add(Box.createVerticalGlue());
        mainMenuPanel.add(welcomePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1,4,10,10));
        JButton transactionButton = new JButton("Transaction");
        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Transaction");
                transactionPanel();
            }
        });
        buttonPanel.add(transactionButton);

        JButton creditCardButton = new JButton("Credit Card");
       /*creditCardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                creditCard();
            }
        }); */
        buttonPanel.add(creditCardButton);

        JButton loanButton = new JButton("Loan");
        /*loanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                loan();
            }
        });*/
        buttonPanel.add(loanButton);

        JButton settingButton = new JButton("Setting");
        /*settingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                setting();
            }
        });*/
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

    private JPanel transactionPanel(){
        JPanel transactionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        double accountBalance = 0.0;
        try{
            accountBalance = bank.getBalance();
        } catch(NoSuchElementException e){
            System.out.println("User Information not set");
        } 
        JLabel balanceLabel = new JLabel(String.format("Account Balance: $.2f", accountBalance));
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridy = 0;
        transactionPanel.add(balanceLabel, gbc);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 1;
        transactionPanel.add(messageLabel,gbc);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Withdraw");
            }
        });
        transactionPanel.add(withdrawButton,gbc);

        JButton depositButton = new JButton("Deposit");
        depositButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        /*depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Deposit");
                depositPanel();
            }
        });*/
        transactionPanel.add(depositButton,gbc);

        JButton transferButton = new JButton("Transfer");
        transferButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 4;
        /*transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Transfer");
                transferPanel();
            }
        });*/
        transactionPanel.add(transferButton,gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
        transactionPanel.add(backButton,gbc);

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
        withdrawPanel.add(withdrawAmountField,gbc);

        JButton submitButton = new JButton("Submit");
        /*submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                withdraw();
            }
        });*/
        gbc.gridy = 2;
        withdrawPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel, "Transaction");
            }
        });
        gbc.gridy = 3;
        withdrawPanel.add(backButton,gbc);

        return withdrawPanel;
    }

     /*private JPanel depositPanel() {

        return depositPanel;
    }*/

     /*private JPanel transferPanel() {

        return transferPanel;
    }*/

    /*private JPanel creditCard(){
        return creditCardPanel;
    }

    private JPanel setting(){
        return settingPanel;
    }

    private JPanel loan(){
        return loanPanel;
    }*/

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankUI("Banking System");
            }
        });
    }
}
