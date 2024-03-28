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

public class foreignExchangeModel {

    private Bank bank; 
    private String userInfo; 
    private CardLayout cardLayout; 
    private JPanel cardPanel; 

    public foreignExchangeModel(Bank bank, String userInfo, CardLayout cardLayout, JPanel cardPanel) {
        this.bank = bank;
        this.userInfo = userInfo;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    }
 

    
    public JPanel foreignExchangePanel() {
        JPanel foreignExchangePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        foreignExchangePanel.add(messageLabel, gbc);

        JButton viewForeignExButton = new JButton("View Foreign Exchange");
        viewForeignExButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 1;
        gbc.insets.top = verticalSpacing;
        viewForeignExButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "View Foreign Exchange");
            }
        });
        foreignExchangePanel.add(viewForeignExButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 2;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Main Menu");
            }
        });
        foreignExchangePanel.add(backButton, gbc);

        return foreignExchangePanel;
    }

    public JPanel viewForeignExchangePanel() {
        JPanel viewForeignExchangePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int verticalSpacing = 10;
        gbc.insets = new Insets(verticalSpacing, 0, 0, 0);

        JLabel messageLabel = new JLabel("Please choose an action:");
        gbc.gridy = 0;
        gbc.insets.top = 0;
        viewForeignExchangePanel.add(messageLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 30));
        gbc.gridy = 6;
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Foreign Exchange");
            }
        });
        viewForeignExchangePanel.add(backButton, gbc);

        return viewForeignExchangePanel;
    }
    
}
