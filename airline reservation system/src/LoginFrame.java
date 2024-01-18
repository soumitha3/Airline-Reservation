import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class LoginFrame extends Frame {
    private Connection connection;
    private TextField usernameField;
    private TextField passwordField;
    private Button loginButton;
    public LoginFrame() {
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
        setTitle("Login");
        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        add(new Label("Username:"));
        usernameField = new TextField();
        add(usernameField);

        add(new Label("Password:"));
        passwordField = new TextField();
        passwordField.setEchoChar('*');
        add(passwordField);

        loginButton = new Button("Login");
        add(new Label(""));
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
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
    private void performLogin() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Login failed. Invalid username or password.");
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Login failed. Please try again.");
        }
    }
    public static void main(String[] args) {
        new LoginFrame();
    }
}
