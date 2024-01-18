import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TicketBookingFrame extends Frame {
    private Connection connection;
    //private TextField bookingIDField;
    private TextField flightNumberField;
    private TextField userNameField;
    private TextField passportNumberField;
    TextField classTextField;
    TextField resultTextField;
    //private TextField classField;
    
    //private TextField priceField;
    private Button searchButton;
    private Button bookTicketButton;

    public TicketBookingFrame() {
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
        setTitle("Ticket Booking");
        setSize(400, 400);
        setLayout(new GridLayout(8, 2));

        add(new Label("Flight Number:"));
        flightNumberField = new TextField();
        add(flightNumberField);

        add(new Label("Username:"));
        userNameField = new TextField();
        add(userNameField);

        add(new Label("Passport Number:"));
        passportNumberField = new TextField();
        add(passportNumberField);

        classTextField = new TextField(20);
        resultTextField = new TextField(20);
        searchButton = new Button("Get Price");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String flightClass = classTextField.getText().trim();
                String flightNumber= flightNumberField.getText().trim();
                fetchPrice(flightClass,flightNumber);
            }
        });

        add(new Label("Class: "));
        add(classTextField);
        add(searchButton);
        add(resultTextField);

        bookTicketButton = new Button("Book Ticket");
        add(new Label(""));
        add(bookTicketButton);

        bookTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTicket();
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
    private void fetchPrice(String flightClass,String flightNumber) {
        try {
            String sqlQuery = "SELECT " +flightClass+" FROM flight WHERE flight_number=?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
               // preparedStatement.setString(1, flightClass);
                preparedStatement.setString(1, flightNumber);
                
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultTextField.setText("");  // Clear previous results
                    while (resultSet.next()) {
                        int price = resultSet.getInt(flightClass);
                        String price1=String.valueOf(price);
                        resultTextField.setText(price1 +"\n");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void bookTicket() {
    	try {
            String flightNumber = flightNumberField.getText();
            String username = userNameField.getText();
            String passportNumber = passportNumberField.getText();
            String classField = classTextField.getText();
            String price =resultTextField.getText();
            
            String insertQuery = "INSERT INTO ticket_booking (flight_number, username, passport_number, class, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1,flightNumber );
            insertStatement.setString(2, username);
            insertStatement.setString(3, passportNumber);
            insertStatement.setString(4, classField);
            insertStatement.setString(5, price);

            int affectedRows = insertStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Booking successful!");
            } else {
                System.out.println("Booking failed. Please try again.");
            }

            insertStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Booking failed. Please try again.");
        }
       
    }

    public static void main(String[] args) {
        new TicketBookingFrame();
    }
}
