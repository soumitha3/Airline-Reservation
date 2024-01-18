import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class RegistrationFrame extends Frame {
    private Connection connection;
    private TextField usernameField;
    private TextField passwordField;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private Button registerButton;
    public RegistrationFrame() {
        connectToDatabase();
        initializeUI();
    }
    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/airlinereservations", "root", " ");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    private void initializeUI() {
        setTitle("Registration");
        setSize(300, 200);
        setLayout(new GridLayout(6, 2));

        add(new Label("Username:"));
        usernameField = new TextField();
        add(usernameField);

        add(new Label("Password:"));
        passwordField = new TextField();
        passwordField.setEchoChar('*');
        add(passwordField);

        add(new Label("First Name:"));
        firstNameField = new TextField();
        add(firstNameField);

        add(new Label("Last Name:"));
        lastNameField = new TextField();
        add(lastNameField);

        add(new Label("Email:"));
        emailField = new TextField();
        add(emailField);

        registerButton = new Button("Register");
        add(new Label(""));
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });

        setVisible(true);
    }
    
    private void performRegistration() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();

            String insertQuery = "INSERT INTO user (username, password, first_name, last_name, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.setString(3, firstName);
            insertStatement.setString(4, lastName);
            insertStatement.setString(5, email);

            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed. Please try again.");
            }
            insertStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Registration failed. Please try again.");
        }
    }
    public static void main(String[] args) {
        new RegistrationFrame();
    }
}
