package models;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.util.NoSuchElementException;

import implementations.Bank;

public class creditCardModel {
    private Bank bank; 
    private String userInfo; 
    private CardLayout cardLayout; 
    private JPanel cardPanel; 
    private JTextArea creditTextArea;
    private JComboBox<String> creditCardDropDown,cardNumbersComboBox,paycardNumbersComboBox;
    private JLabel cardCountLabel;


    public creditCardModel(Bank bank, String userInfo, CardLayout cardLayout, JPanel cardPanel) {
        this.bank = bank;
        this.userInfo = userInfo;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    }
       
    public JPanel creditCardPanel() {
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
                updateCreditCardPanel();
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
                updateCreditCardWithdrawPanel();
                cardLayout.show(cardPanel, "Credit - Cash Withdrawal");
            }
        });
        creditCardPanel.add(cashWithdrawalButton, gbc);

        JButton cashAdvancePaymentButton = new JButton("Pay Cash Advance");
        cashAdvancePaymentButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        cashAdvancePaymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCashAdvancePanel();
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

    public JPanel applyCreditCardPanel() {
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

    public void updateCreditCardPanel(){
        new Thread(() -> {
            try {
                SwingUtilities.invokeLater(() -> {
                    String[] creditCardNumbers = bank.getCreditCardNumbers();

                    if(creditCardNumbers.length == 0){
                        creditCardDropDown.setVisible(false);
                    } else {
                        for (String creditCardNumber : creditCardNumbers){
                            creditCardDropDown.addItem(creditCardNumber);
                        }
                        creditCardDropDown.revalidate();
                        creditCardDropDown.repaint();
                        creditCardDropDown.setVisible(true);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    
    public JPanel payCreditBillPanel() {
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

        creditCardDropDown = new JComboBox<>();
        payCreditBillPanel.add(creditCardDropDown, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //submitCreditPayment();
            }
        });
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

    public JPanel viewCreditBillPanel() {
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

    private void updateCreditCardWithdrawPanel(){
        new Thread(() -> {
            try {
                SwingUtilities.invokeLater(() -> {
                    String[] creditCardNumbers = bank.getCreditCardNumbers();

                    if(creditCardNumbers.length == 0){
                        cardNumbersComboBox.setVisible(false);
                    } else {
                        for (String creditCardNumber : creditCardNumbers){
                            cardNumbersComboBox.addItem(creditCardNumber);
                        }
                        cardNumbersComboBox.revalidate();
                        cardNumbersComboBox.repaint();
                        cardNumbersComboBox.setVisible(true);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }


    public JPanel creditWithdrawalPanel() {
        JPanel creditWithdrawalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel selectCardLabel = new JLabel("Select the card number to withdraw cash from:");
        gbc.gridy++;
        creditWithdrawalPanel.add(selectCardLabel, gbc);

        cardNumbersComboBox = new JComboBox<>();
        gbc.gridy++;
        creditWithdrawalPanel.add(cardNumbersComboBox,gbc);

        JLabel enterAmountLabel = new JLabel("Enter the amount to withdraw:");
        gbc.gridy++;
        creditWithdrawalPanel.add(enterAmountLabel, gbc);

        JTextField amountField = new JTextField(20);
        gbc.gridy++;
        creditWithdrawalPanel.add(amountField, gbc);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy++;
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // String cardNumber = (String) cardNumbersComboBox.getSelectedItem();
                // double amount = Double.parseDouble(amountField.getText());
                // // Call the method to withdraw cash from the credit card
                // bank.(cardNumber, amount);
            }
        });
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


    private void updateCashAdvancePanel(){
        new Thread(() -> {
            try {
                SwingUtilities.invokeLater(() -> {
                    String[] creditCardNumbers = bank.getCreditCardNumbers();

                    if(creditCardNumbers.length == 0){
                        paycardNumbersComboBox.setVisible(false);
                    } else {
                        for (String creditCardNumber : creditCardNumbers){
                            paycardNumbersComboBox.addItem(creditCardNumber);
                        }
                        paycardNumbersComboBox.revalidate();
                        paycardNumbersComboBox.repaint();
                        paycardNumbersComboBox.setVisible(true);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public JPanel payCashAdvancePanel() {
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

        JLabel selectCardLabel = new JLabel("Select the card number to pay for:");
        gbc.gridy++;
        payCashAdvancePanel.add(selectCardLabel, gbc);

        paycardNumbersComboBox = new JComboBox<>();
        gbc.gridy++;
        payCashAdvancePanel.add(paycardNumbersComboBox, gbc);

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



}
