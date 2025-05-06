package edu.csusm.View;

import edu.csusm.Adapter.CurrencyExchangeService;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.io.IOException;
import org.json.JSONException;

public class CurrencyExchangeGUI extends JPanel {

    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;
    private JButton convertButton;
    private JButton backButton;
    private CurrencyExchangeService adapter; // Interface here

    // Constructor accepts any implementation of CurrencyExchangeService
    public CurrencyExchangeGUI(CurrencyExchangeService adapter) {
        this.adapter = adapter;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(38, 120, 190));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Currency Exchange", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Amount Field
        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(amountLabel, gbc);

        amountField = new JTextField(15);
        gbc.gridx = 1;
        centerPanel.add(amountField, gbc);

        // From Currency
        JLabel fromLabel = new JLabel("From:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(fromLabel, gbc);

        fromCurrency = new JComboBox<>(new String[]{"USD", "EUR", "JPY", "GBP"});
        gbc.gridx = 1;
        centerPanel.add(fromCurrency, gbc);

        // To Currency
        JLabel toLabel = new JLabel("To:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(toLabel, gbc);

        toCurrency = new JComboBox<>(new String[]{"USD", "EUR", "JPY", "GBP"});
        gbc.gridx = 1;
        centerPanel.add(toCurrency, gbc);

        // Convert Button
        convertButton = new JButton("Convert");
        convertButton.addActionListener(e -> convertCurrency());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(convertButton, gbc);

        // Result Label
        resultLabel = new JLabel("Converted Amount: ", JLabel.CENTER);
        gbc.gridy = 4;
        centerPanel.add(resultLabel, gbc);

        // Back Button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Dashboard"));
        gbc.gridy = 5;
        centerPanel.add(backButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void convertCurrency() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();

            // Use the adapter to get conversion result
            JSONObject response = adapter.convertCurrency(from, to, amount);

            // Check if response has the conversion_result
            if (response != null && response.has("conversion_result")) {
                double convertedAmount = response.getDouble("conversion_result");

                // Format the result
                DecimalFormat df = new DecimalFormat("#.##");
                resultLabel.setText("Converted Amount: " + df.format(convertedAmount) + " " + to);
            } else {
                JOptionPane.showMessageDialog(this, "Error: Conversion data is missing or invalid.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | JSONException e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch conversion data. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
