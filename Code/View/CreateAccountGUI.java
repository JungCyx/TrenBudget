package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountGUI extends JPanel implements ActionListener {
    // Declare components
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JButton submitButton;

    public CreateAccountGUI() {
        // Set layout for the panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components

        // Labels
        JLabel firstNameLabel = new JLabel("First Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(firstNameLabel, gbc);

        JLabel lastNameLabel = new JLabel("Last Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lastNameLabel, gbc);

        JLabel emailLabel = new JLabel("Email Address:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(emailLabel, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordLabel, gbc);

        // Text Fields for user input
        firstNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(firstNameField, gbc);

        lastNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(lastNameField, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(emailField, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(passwordField, gbc);

        // Submit Button
        submitButton = new JButton("Create Account");
        submitButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(submitButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Retrieve the data entered by the user
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Simple validation (e.g., check if fields are empty)
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Show a confirmation message
                JOptionPane.showMessageDialog(this, "Account created successfully for: " + firstName + " " + lastName, "Success", JOptionPane.INFORMATION_MESSAGE);

                // Optionally, clear the fields after submission
                firstNameField.setText("");
                lastNameField.setText("");
                emailField.setText("");
                passwordField.setText("");
            }
        }
    }
}

