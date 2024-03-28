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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import implementations.Bank;

public class loansModel {

    private Bank bank; 
    private String userInfo; 
    private CardLayout cardLayout; 
    private JPanel cardPanel; 
    private JTextArea loanTextArea;

    public loansModel(Bank bank, String userInfo, CardLayout cardLayout, JPanel cardPanel) {
        this.bank = bank;
        this.userInfo = userInfo;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    
    public JPanel loanPanel() {
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

    public JPanel applyLoanPanel() {
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
    
    public JPanel payLoanPanel() {
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
    
    public JPanel viewLoanPanel() {
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
     
    private void updateLoanDisplay(){
        new Thread(() -> {
            System.out.println(bank.getLoanString().toString());
            String loansDisplay = bank.getLoanString().toString();
            SwingUtilities.invokeLater(() -> loanTextArea.setText(loansDisplay));
        }).start();
    }
}
