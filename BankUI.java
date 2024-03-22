import javax.swing.*;

import implementations.Security;

import java.awt.*;
import java.awt.event.*;

public class BankUI extends JFrame {
    private Bank bank;
    private Security securityInstance;
    private JTextField nameField, usernameField, passwordField;
    private JButton createAccountButton, loginButton;
    private JLabel nameLabel, usernameLabel, passwordLabel;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public BankUI(String title) {
        super(title);
        bank = new Bank("Random");
        securityInstance = new Security();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel inputPanel = loginPanel();
        cardPanel.add(inputPanel, "Login");
        cardPanel.add(mainMenuPanel(), "Main Menu");
        add(cardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel loginPanel(){
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        nameLabel = new JLabel("Name:");
        inputPanel.add(nameLabel);
        nameField = new JTextField();
        inputPanel.add(nameField);
        usernameLabel = new JLabel("Username:");
        inputPanel.add(usernameLabel);
        usernameField = new JTextField();
        inputPanel.add(usernameField);
        passwordLabel = new JLabel("Password:");
        inputPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);
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
        while (!bank.validateLogin(loginUsername, loginPassword)) {
            JOptionPane.showMessageDialog(this, "Wrong credentials. Please try again.");
            loginUsername = JOptionPane.showInputDialog(this, "Please enter your username:");
            loginPassword = JOptionPane.showInputDialog(this, "Please enter your password:");
            break;
        }
        // Assuming OTP generation and authentication logic here

        JOptionPane.showMessageDialog(this, "Login successful!");
        cardLayout.show(cardPanel, "Main Menu");
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
        /*transactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                transaction();
            }
        });*/
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

    /*private JPanel transaction(){

        return transaction;
    }

    private JPanel creditCard(){
        return creditCard;
    }

    private JPanel setting(){
        return setting;
    }

    private JPanel loan(){
        return loan;
    }*/

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankUI("Banking System");
            }
        });
    }
}
