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
import javax.swing.JPanel;

import implementations.Bank;


public class insuranceModel{

    private Bank bank; 
    private String userInfo; 
    private CardLayout cardLayout; 
    private JPanel cardPanel; 

    public insuranceModel(Bank bank, String userInfo, CardLayout cardLayout, JPanel cardPanel) {
        this.bank = bank;
        this.userInfo = userInfo;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    }
 
    
    public JPanel insurancePanel() {
        JPanel insurancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        insurancePanel.add(messageLabel, gbc);

        JButton applyInsuranceButton = new JButton("Apply Insurance");
        applyInsuranceButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        gbc.insets.top = verticalSpacing;
        applyInsuranceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Apply Insurance");
            }
        });
        insurancePanel.add(applyInsuranceButton, gbc);

        JButton viewInsuranceButton = new JButton("View Insurance");
        viewInsuranceButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        gbc.insets.top = verticalSpacing;
        viewInsuranceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "View Insurance");
            }
        });
        insurancePanel.add(viewInsuranceButton, gbc);

        JButton cancelInsuranceButton = new JButton("Cancel Insurance");
        cancelInsuranceButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 3;
        gbc.insets.top = verticalSpacing;
        cancelInsuranceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Cancel Insurance");
            }
        });
        insurancePanel.add(cancelInsuranceButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
        insurancePanel.add(backButton, gbc);


        return insurancePanel;
    }

    public JPanel applyInsurancePanel() {
        JPanel applyInsurancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        applyInsurancePanel.add(messageLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Insurance");
            }
        });
        applyInsurancePanel.add(backButton, gbc);

        return applyInsurancePanel;
    }

    public JPanel viewInsurancePanel() {
        JPanel viewInsurancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        viewInsurancePanel.add(messageLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Insurance");
            }
        });
        viewInsurancePanel.add(backButton, gbc);

        return viewInsurancePanel;
    }

    public JPanel cancelInsurancePanel() {
        JPanel cancelInsurancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        cancelInsurancePanel.add(messageLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Insurance");
            }
        });
        cancelInsurancePanel.add(backButton, gbc);

        return cancelInsurancePanel;
    }
}