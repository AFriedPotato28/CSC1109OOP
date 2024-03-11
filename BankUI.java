import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankUI extends JFrame {
    private Bank bank;
    private Security securityInstance;
    private JTextField nameField, usernameField, passwordField;
    private JButton createAccountButton, loginButton;
    private JLabel nameLabel, usernameLabel, passwordLabel;

    public BankUI(String title) {
        super(title);
        bank = new Bank("Random");
        securityInstance = new Security();

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

        add(inputPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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
        bank.addCustomer(new Customer(), name, username, password, accountType);
        JOptionPane.showMessageDialog(this, "Account created successfully!");
    }

    private void login() {
        String loginUsername = usernameField.getText();
        String loginPassword = passwordField.getText();
        while (!bank.validateLogin(loginUsername, loginPassword)) {
            JOptionPane.showMessageDialog(this, "Wrong credentials. Please try again.");
            loginUsername = JOptionPane.showInputDialog(this, "Please enter your username:");
            loginPassword = JOptionPane.showInputDialog(this, "Please enter your password:");
        }

        // Assuming OTP generation and authentication logic here

        JOptionPane.showMessageDialog(this, "Login successful!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankUI("Banking System");
            }
        });
    }
}
