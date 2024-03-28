package models;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import implementations.Bank;
import implementations.Security;

public class settingsModel {

    private Bank bank; 
    private String userInfo; 
    private CardLayout cardLayout; 
    private JPanel cardPanel;
    private Security securityInstance; 
    
    public settingsModel(Bank bank, String userInfo, CardLayout cardLayout, JPanel cardPanel) {
        this.bank = bank;
        this.userInfo = userInfo;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public JPanel settingPanel() {
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
        resetPasswordButton.addActionListener((ActionListener) new ActionListener() {
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

    public JPanel resetPasswordPanel() {
        JPanel resetPasswordPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel oldPassword = new JLabel("Please enter old password:");
        gbc.gridy = 0;
        resetPasswordPanel.add(oldPassword, gbc);

        JTextField oldPasswordField = new JPasswordField();
        oldPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        resetPasswordPanel.add(oldPasswordField, gbc);

        JLabel newPassword = new JLabel("Please enter new password:");
        gbc.gridy = 2;
        resetPasswordPanel.add(newPassword, gbc);

        JTextField newPasswordField = new JPasswordField();
        newPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        resetPasswordPanel.add(newPasswordField, gbc);

        JLabel reenterNewPassword = new JLabel("Please reenter new password:");
        gbc.gridy = 4;
        resetPasswordPanel.add(reenterNewPassword, gbc);

        JTextField reenterNewPasswordField = new JPasswordField();
        newPasswordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 5;
        resetPasswordPanel.add(reenterNewPasswordField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
           
            String newPassword = newPasswordField.getText();
            String reenterNewPassword = reenterNewPasswordField.getText();

            if (!bank.authenticateDetails(userInfo, oldPasswordField.getText())){
                JOptionPane.showMessageDialog(null, "Your current password does not match","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!securityInstance.validatePassword(newPassword)){
                JOptionPane.showMessageDialog(null, "Your new password does not meet the basic requirements of password", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(reenterNewPassword) || newPassword.equals("")) {
                JOptionPane.showMessageDialog(null, "The new passwords does not match or cannot be null.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            bank.resetPassword(userInfo, newPassword);
            oldPasswordField.setText("");
            reenterNewPasswordField.setText("");
            newPasswordField.setText("");
            JOptionPane.showMessageDialog(null, "Password has been successfully changed", "Success", JOptionPane.INFORMATION_MESSAGE);

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
   
    public JPanel changeTransactionLimitPanel() {
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
                        JOptionPane.showMessageDialog(null, "Transaction Limit must be more than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    boolean limitUpdated = bank.changeTransactionLimit(newLimit, userInfo);
                    if (limitUpdated) {
                        JOptionPane.showMessageDialog(null, "Transaction Limit updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(cardPanel, "Setting");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update the Transaction Limit.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
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

}
