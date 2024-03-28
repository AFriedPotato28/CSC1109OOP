package models;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import implementations.Bank;

public class transactionModel {

    private Bank bank; 
    private String userInfo; 
    private CardLayout cardLayout; 
    private JPanel cardPanel; 
    private JLabel balanceLabel;

    public transactionModel(Bank bank, String userInfo, CardLayout cardLayout, JPanel cardPanel) {
        this.bank = bank;
        this.userInfo = userInfo;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    }

    public void setUserInfo(String userInfo){
        this.userInfo = userInfo;
    }

    public void updateAccountBalance() {
        new Thread(() -> {
            try {
                double accountBalance = bank.getBalance(); // Fetch balance in background thread
                System.out.println(userInfo);
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

    public JPanel transactionPanel() {
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

    public JPanel withdrawPanel() {
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

    public JPanel depositPanel() {
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

    public JPanel transferPanel() {
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
    
}
