package models;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import implementations.Bank;
import implementations.CreditCard;

public class creditCardModel {
    private Bank bank; 
    private String userInfo; 
    private CardLayout cardLayout; 
    private JPanel cardPanel; 
    private JTextArea creditTextArea;
    private JComboBox<String> creditCardDropDown,cardNumbersComboBox,paycardNumbersComboBox, cancelCreditCardDrop;
    private JLabel cardCountLabel;
    private CreditCard card;


    public creditCardModel(Bank bank, String userInfo, CardLayout cardLayout, JPanel cardPanel) {
        this.bank = bank;
        this.userInfo = userInfo;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
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

        JButton CancelCreditCardButton = new JButton("Cancel Credit Card");
        CancelCreditCardButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        CancelCreditCardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCancelDropDown();
                cardLayout.show(cardPanel, "Cancel Credit Card");
            }
        });
        creditCardPanel.add(CancelCreditCardButton, gbc);        

        JButton payCreditBillButton = new JButton("Pay Credit Card Bill");
        payCreditBillButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        payCreditBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCreditCardPanel();
                cardLayout.show(cardPanel, "Pay Credit Card Bill");
            }
        });
        creditCardPanel.add(payCreditBillButton, gbc);

        JButton viewCreditBillButton = new JButton("View Credit Bill");
        viewCreditBillButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 4;
        viewCreditBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCreditTextArea();
                cardLayout.show(cardPanel, "View Credit Bill");
            }
        });
        creditCardPanel.add(viewCreditBillButton, gbc);

        JButton cashWithdrawalButton = new JButton("Credit - Cash Withdrawal");
        cashWithdrawalButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        cashWithdrawalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCreditCardWithdrawPanel();
                cardLayout.show(cardPanel, "Credit - Cash Withdrawal");
            }
        });
        creditCardPanel.add(cashWithdrawalButton, gbc);

        JButton cashAdvancePaymentButton = new JButton("Pay Cash Advance");
        cashAdvancePaymentButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        cashAdvancePaymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCashAdvancePanel();
                cardLayout.show(cardPanel, "Pay Cash Advance");
            }
        });
        creditCardPanel.add(cashAdvancePaymentButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 7;
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
                        int annualIncome = 10000; // Hardcoded for now
                        CreditCard card = bank.applyCreditCard(customerId,annualIncome);
                        JOptionPane.showMessageDialog(null,"Successfully applied a new credit card. Your card no is " + card.getCardNumber());
                        
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

    private void updateCancelDropDown(){
        new Thread(() -> {
            try {
                SwingUtilities.invokeLater(() -> {
                    ArrayList<String> creditCardNumbers = bank.getCreditCardNumbers();
                    cancelCreditCardDrop.removeAllItems();

                    if(creditCardNumbers.size() == 0){
                        cancelCreditCardDrop.setVisible(false);
                    } else {
                        for (String creditCardNumber : creditCardNumbers){
                            cancelCreditCardDrop.addItem(creditCardNumber);
                        }
                        cancelCreditCardDrop.revalidate();
                        cancelCreditCardDrop.repaint();
                        cancelCreditCardDrop.setVisible(true);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public JPanel cancelCreditCardPanel() {
        JPanel cancelCreditCardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel messageLabel = new JLabel("Cancel Credit Card:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        cancelCreditCardPanel.add(messageLabel, gbc);

        cancelCreditCardDrop = new JComboBox<>();
        gbc.gridy = 1;
        cancelCreditCardPanel.add(cancelCreditCardDrop, gbc);

        try {
            JButton cancelButton = new JButton("Cancel Card");
            cancelButton.setPreferredSize(new Dimension(200, 30));
            gbc.gridy = 2;
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(cancelCreditCardDrop.getSelectedItem() == null){
                        JOptionPane.showMessageDialog(null, "Unable to remove card", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (bank.cancelCreditCardForGUI(cancelCreditCardDrop.getSelectedItem().toString())){
                        updateCancelDropDown();
                        JOptionPane.showMessageDialog(null, "Succesfully removed card");
                        return;

                    }else{
                        JOptionPane.showMessageDialog(null, "Unable to remove card", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;

                    }
                }
            });
            cancelCreditCardPanel.add(cancelButton, gbc);

        }catch (NoSuchElementException e){
            System.out.println("No element exists");
        }
        
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        gbc.insets.top = 10;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Credit Card");
            }
        });
        cancelCreditCardPanel.add(backButton, gbc);

        return cancelCreditCardPanel;
    }

    public void updateCreditCardPanel(){
        new Thread(() -> {
            try {
                SwingUtilities.invokeLater(() -> {
                    ArrayList<String> creditCardNumbers = bank.getCreditCardNumbers();
                    creditCardDropDown.removeAllItems();

                    if(creditCardNumbers.size() == 0){
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

        JLabel selectedCardBalanceLabel = new JLabel(); // Label to show the selected credit card's balance
        gbc.gridy = 1;
        payCreditBillPanel.add(selectedCardBalanceLabel, gbc);

        creditCardDropDown = new JComboBox<>();
        gbc.gridy = 2;
        payCreditBillPanel.add(creditCardDropDown, gbc);
        creditCardDropDown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if(event.getStateChange() == ItemEvent.SELECTED){
                    // String cardNo = (String) creditCardDropDown.getSelectedItem();
                    // double balance = card.getBalance();
                    // selectedCardBalanceLabel.setText("Outstanding balance: $" + balance);
                }
            }
        });

        JLabel paymentLabel = new JLabel("Insert your payment amount");
        gbc.gridy = 3;
        gbc.insets.top = 2;
        payCreditBillPanel.add(paymentLabel, gbc);

        JTextArea amountField = new JTextArea();
        gbc.gridy = 4;

        amountField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9'
                        || event.getKeyChar() == (char) 8 || event.getKeyChar() == (char) 46) {
                            amountField.setEditable(true);

                } else {
                    amountField.setEditable(false);
                }
            }
        });

        payCreditBillPanel.add(amountField,gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardNo = (String) creditCardDropDown.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
            
                if (amount <= 0.0) {
                    JOptionPane.showMessageDialog(null, "Amount cannot less than or equal to 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                
            
            }
        });
        payCreditBillPanel.add(submitButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
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
                    ArrayList<String> creditCardNumbers = bank.getCreditCardNumbers();
                    cardNumbersComboBox.removeAllItems();

                    if(creditCardNumbers.size() == 0){
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

        amountField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9'
                        || event.getKeyChar() == (char) 8 || event.getKeyChar() == (char) 46) {
                            amountField.setEditable(true);

                } else {
                    amountField.setEditable(false);
                }
            }
        });

        creditWithdrawalPanel.add(amountField, gbc);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy++;
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardNumber = (String) cardNumbersComboBox.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0.0) {
                        JOptionPane.showMessageDialog(null, "Amount cannot less than or equal to 0", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }
                
                if(bank.cashAdvancedWithdrawlForGUI(cardNumber, amount)){
                    JOptionPane.showMessageDialog(null,"Successfully withdraw " + amount + " from your credit card");
                    enterAmountLabel.setText("");
                    return;
                }else{
                    JOptionPane.showMessageDialog(null,"Failed to withdraw " + amount + " from your credit card due to some factors","ERROR", JOptionPane.ERROR_MESSAGE);
                }
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
                    ArrayList<String> creditCardNumbers = bank.getCreditCardNumbers();
                    paycardNumbersComboBox.removeAllItems();

                    if(creditCardNumbers.size() == 0){
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

        amountField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9'
                        || event.getKeyChar() == (char) 8 || event.getKeyChar() == (char) 46) {
                            amountField.setEditable(true);

                } else {
                    amountField.setEditable(false);
                }
            }
        });

        payCashAdvancePanel.add(amountField, gbc);

        JButton payButton = new JButton("Pay");
        payButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy++;
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCard = (String) paycardNumbersComboBox.getSelectedItem(); // Get the selected CreditCard object
                double amount = Double.parseDouble(amountField.getText());
                
                if (amount <= 0.0) {
                    JOptionPane.showMessageDialog(null, "Amount cannot less than or equal to 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (bank.payCashAdvancePayablesForGUI(selectedCard,amount)) {
                    amountField.setText("");
                    JOptionPane.showMessageDialog(null,"Succesfully repaid " + amount + " Your balance is left with " + bank.getBalance());
                    return;
                } 

                JOptionPane.showMessageDialog(null,"Failed to repay " + amount + " from your credit card due to some factors","ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });
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
