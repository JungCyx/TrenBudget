package View;

import Main.Controller.UserController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginGUI extends JPanel {

    private final UserController controller = new UserController();

    public LoginGUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Set a preferred size for the panel
        setPreferredSize(new Dimension(800, 600));

        // Left side panel with blue background
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 123, 255)); // Blue color
        leftPanel.setPreferredSize(new Dimension(200, getHeight())); // Proportional to the width
        add(leftPanel, BorderLayout.WEST);

        // Main form panel
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(formPanel, BorderLayout.CENTER);

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
        loginButton.setBackground(new Color(0, 123, 255)); // Blue color
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

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText(); // get the username
                String password = new String(passField.getPassword()); // get the password

                // authenticate the user's info
                if (controller.authenticateInput(username, password)) {
                    // Redirect the user to the Dashboard page if authenticated
                    Mainframe.cardLayout.show(Mainframe.mainPanel, "Dashboard");
                } else {
                    // error message if the user inputs wrong credentials
                    JOptionPane.showMessageDialog(null, "Incorrect credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect the user to the Register page
                Mainframe.cardLayout.show(Mainframe.mainPanel, "Register");
            }
        });
    }

    // Main method to test GUI
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        LoginGUI loginPanel = new LoginGUI();
        frame.add(loginPanel, BorderLayout.CENTER);

        // Set frame size and allow resizing
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
