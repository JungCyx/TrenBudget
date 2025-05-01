package edu.csusm.View;

import edu.csusm.Controller.UserController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginGUI extends JFrame {

    private final UserController controller = new UserController();
    public static CardLayout cardLayout;
    public static JPanel mainPanel;

    public LoginGUI() {
        
        // Initialize frame properties
        setTitle("TrenBudget");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setPreferredSize(new Dimension(1300, 800));

        // Setup layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);
        mainPanel.add(new CreateAccountGUI(), "Register");

        // Add only the login view initially
        JPanel loginPanel = createLoginPanel();
        mainPanel.add(loginPanel, "Login");

        // Display the login screen
        cardLayout.show(mainPanel, "Login");

        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Left side panel with blue background
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 123, 255)); // Blue color
        leftPanel.setPreferredSize(new Dimension(200, 600));
        panel.add(leftPanel, BorderLayout.WEST);

        // Main form panel
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome Label
        JLabel titleLabel = new JLabel("Welcome to TrenBudget", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 123, 255)); // Blue color
        formPanel.add(titleLabel, BorderLayout.NORTH);

        // Form elements
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username Label and Field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JTextField userField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        fieldsPanel.add(userField, gbc);

        // Password Label and Field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JPasswordField passField = new JPasswordField(15);

        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        fieldsPanel.add(passField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setBackground(Color.blue); // Blue color
        loginButton.setForeground(Color.WHITE);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        fieldsPanel.add(loginButton, gbc);

        // Register link at the bottom
        JLabel registerLabel = new JLabel("I don't have an account.");
        registerLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(new Color(0, 123, 255)); // Blue color
        registerButton.setBorder(BorderFactory.createEmptyBorder());

        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        registerPanel.setBackground(Color.WHITE);
        registerPanel.add(registerLabel);
        registerPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        fieldsPanel.add(registerPanel, gbc);

        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.CENTER);

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText(); // Get the username
                String password = new String(passField.getPassword()); // Get the password

                // Authenticate the user's info
                if (controller.authenticateUser(username, password)) {
                    // Initialize other views upon successful login
                    initializeViews();

                    // Redirect to the Dashboard page
                    cardLayout.show(mainPanel, "Dashboard");
                } else {
                    JOptionPane.showMessageDialog(panel, "Incorrect credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to the Register page
                cardLayout.show(mainPanel, "Register");
            }
        });

        return panel;
    }

    private void initializeViews() {
        // Add additional views
        mainPanel.add(new DashboardGUI(), "Dashboard");
        mainPanel.add(new BudgetGUI(), "Budget");
        mainPanel.add(new SavingsGUI(), "Savings");
        mainPanel.add(new TransactionGUI(), "Transaction");
    }
}

