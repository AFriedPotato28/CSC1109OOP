import implementations.Bank;
import implementations.Security;
import models.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class BankUI extends JFrame{
    private Bank bank;
    private Security securityInstance;
    private JTextField nameField, usernameField, passwordField,usernameLoginField,passwordLoginField;
    private JButton createAccountButton, loginButton;
    private JLabel nameLabel, usernameLabel, passwordLabel;
    private JComboBox<Double> annualIncomeComboBox;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private boolean isNewAccount = false;
    private transactionModel transactionModel;
    private loansModel loanModel;
    private creditCardModel creditCardModel;
    private settingsModel settingsModel;
    private insuranceModel insuranceModel;
    private foreignExchangeModel foreignExchangeModel;
    private String userInfo = "";

    public BankUI(String title) {
        super(title);
        bank = new Bank("Random");
        securityInstance = new Security();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        loanModel = new loansModel(bank, userInfo, cardLayout, cardPanel);
        creditCardModel = new creditCardModel(bank,userInfo,cardLayout,cardPanel);
        settingsModel = new settingsModel(bank,userInfo,cardLayout,cardPanel);
        insuranceModel = new insuranceModel(bank,userInfo,cardLayout,cardPanel);
        foreignExchangeModel = new foreignExchangeModel(bank,userInfo,cardLayout,cardPanel);
        transactionModel  = new transactionModel(bank,userInfo,cardLayout,cardPanel);

        JPanel inputPanel = frontPanel();
        cardPanel.add(inputPanel, "Front Page");
        cardPanel.add(loginPanel(), "Login");
        cardPanel.add(registerPanel(), "Register");
        cardPanel.add(branchPanel(), "Branch");
        cardPanel.add(mainMenuPanel(), "Main Menu");
        cardPanel.add(transactionModel.withdrawPanel(), "Withdraw");
        cardPanel.add(transactionModel.depositPanel(), "Deposit");
        cardPanel.add(transactionModel.transferPanel(), "Transfer");
        
        /*Credit Card Area */
        cardPanel.add(creditCardModel.creditCardPanel(), "Credit Card");
        cardPanel.add(creditCardModel.applyCreditCardPanel(), "Apply Credit Card");
        cardPanel.add(creditCardModel.payCreditBillPanel(), "Pay Credit Card Bill");
        cardPanel.add(creditCardModel.cancelCreditCardPanel(), "Cancel Credit Card");
        cardPanel.add(creditCardModel.viewCreditBillPanel(), "View Credit Bill");
        cardPanel.add(creditCardModel.creditWithdrawalPanel(), "Credit - Cash Withdrawal");
        cardPanel.add(creditCardModel.payCashAdvancePanel(), "Pay Cash Advance");

        /*Insurance Area */
        cardPanel.add(insuranceModel.insurancePanel(), "Insurance");
        cardPanel.add(insuranceModel.applyInsurancePanel(), "Apply Insurance");
        cardPanel.add(insuranceModel.viewInsurancePanel(), "View Insurance");
        cardPanel.add(insuranceModel.cancelInsurancePanel(), "Cancel Insurance");

        /*Foreign Exchange */
        cardPanel.add(foreignExchangeModel.foreignExchangePanel(), "Foreign Exchange");
        cardPanel.add(foreignExchangeModel.viewForeignExchangePanel(), "View Foreign Exchange");

        /*Settings */
        cardPanel.add(settingsModel.settingPanel(), "Setting");
        cardPanel.add(settingsModel.resetPasswordPanel(), "Reset Password");
        cardPanel.add(settingsModel.changeTransactionLimitPanel(), "Change Transaction Limit");
        
        /*Loans */
        cardPanel.add(loanModel.loanPanel(), "Loan");
        cardPanel.add(loanModel.applyLoanPanel(), "Apply Loan");
        cardPanel.add(loanModel.payLoanPanel(), "Pay Loan");
        cardPanel.add(loanModel.viewLoanPanel(), "View Loan");
        
        add(cardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setUserInfo(String loginUsername){
        this.loanModel.setUserInfo(loginUsername);
        this.creditCardModel.setUserInfo(loginUsername);
        this.settingsModel.setUserInfo(loginUsername);
        this.insuranceModel.setUserInfo(loginUsername);
        this.foreignExchangeModel.setUserInfo(loginUsername);
        this.transactionModel.setUserInfo(loginUsername);
        this.userInfo = loginUsername;
    }    

    private void clearUserInfo(){
        this.loanModel.setUserInfo("");
        this.creditCardModel.setUserInfo("");
        this.settingsModel.setUserInfo("");
        this.insuranceModel.setUserInfo("");
        this.foreignExchangeModel.setUserInfo("");
        this.transactionModel.setUserInfo("");
        this.userInfo = "";
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

        Double[] annualIncome = {15000.00, 20000.00, 25000.00, 30000.00};
        annualIncomeComboBox = new JComboBox<>(annualIncome);
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
        double annualIncome = Double.parseDouble(String.valueOf(annualIncomeComboBox.getSelectedItem()));

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

        bank.addCustomer(name, username, password,annualIncome);
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
                        setUserInfo(loginUsername);
                        bank.populateUserInfo(userInfo);
                        securityInstance.logActivity(bank.getAccountInfo().getCustomerId(), 1);
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
                cardPanel.add(transactionModel.transactionPanel(),"Transaction");
                transactionModel.updateAccountBalance();
                cardLayout.show(cardPanel, "Transaction");

            }
        });
        buttonPanel.add(transactionButton);

        JButton creditCardButton = new JButton("Credit Card");
        creditCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Credit Card");
            }
        });
        buttonPanel.add(creditCardButton);

        JButton loanButton = new JButton("Loan");
        loanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Loan");
            }
        });
        buttonPanel.add(loanButton);

        JButton insuranceButton = new JButton("Insurance");
        insuranceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Insurance");
            }
        });
        buttonPanel.add(insuranceButton);

        JButton foreignExButton = new JButton("Foreign Exchange");
        foreignExButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Foreign Exchange");
            }
        });
        buttonPanel.add(foreignExButton);

        JButton settingButton = new JButton("Setting");
        settingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Setting");
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
                clearUserInfo();
            }
        });
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logOutButton);
        mainMenuPanel.add(logoutPanel, BorderLayout.NORTH);
        mainMenuPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainMenuPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankUI("Banking System");
            }
        });
    }
}
