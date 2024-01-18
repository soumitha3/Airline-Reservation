import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class ViewTicketBookingInfoFrame extends Frame {
    private Connection connection;
    private TextField userNameField;
    private Button viewInfoButton;
    private TextArea infoTextArea;

    public ViewTicketBookingInfoFrame() {
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
        setTitle("View Customer Ticket Booking Information");
        setSize(400, 300);
        setLayout(new GridLayout(3, 2));

        add(new Label("Username:"));
        userNameField = new TextField();
        add(userNameField);

        viewInfoButton = new Button("View Information");
        add(viewInfoButton);

        infoTextArea = new TextArea();
        add(infoTextArea);

        viewInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String username = userNameField.getText().trim();
                viewCustomerInfo(username);
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

    private void viewCustomerInfo(String username) {
    	 try{
             String sqlQuery = "SELECT * FROM ticket_booking WHERE username = ?";
             
             try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                 preparedStatement.setString(1, username);
                 
                 try (ResultSet resultSet = preparedStatement.executeQuery()) {
                     infoTextArea.setText("");  
                     while (resultSet.next()) {
                    	 int bookingID=resultSet.getInt("booking_id");
                         String flightNumber = resultSet.getString("flight_number");
                         String passportNumber=resultSet.getString("passport_number");
                         String classType = resultSet.getString("class");
                         double price = resultSet.getDouble("price");
                         infoTextArea.append("Booking ID: " + bookingID + "\nFlight Number: " + flightNumber + "\nPassport number: " + passportNumber + "\nClass: " + classType + "\nPrice: " + price + "\n");
                     }
                 }
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
             infoTextArea.setText("Error fetching data.");
         }
     
    }

    public static void main(String[] args) {
        new ViewTicketBookingInfoFrame();
    }
}

