import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class FlightBookingFrame extends Frame {
    private Connection connection;  
    private TextField sourceField;
    private TextField destinationField;
    private Button bookFlightButton;
    private TextArea infoTextArea;
    public FlightBookingFrame() {
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
        setTitle("Flight Booking");
        setSize(400, 400);
        setLayout(new GridLayout(8, 2));
        add(new Label("Source:"));
        sourceField = new TextField(20);
        add(sourceField);
        add(new Label("Destination:"));
        destinationField = new TextField(20);
        add(destinationField);
        bookFlightButton = new Button("Search Flights");
        add(new Label(""));
        add(bookFlightButton);
        infoTextArea = new TextArea(10,50);
        add(infoTextArea);
        bookFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 if (e.getActionCommand().equals("Search Flights")) {
                     String source1 = sourceField.getText().trim();
                     String destination1 = destinationField.getText().trim();
                bookFlight(source1,destination1);
            	 }
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
    private void bookFlight(String source1, String destination1) {
        	try  {
                String sqlQuery = "SELECT * FROM flight WHERE source = ? AND destination = ?";   
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                    preparedStatement.setString(1, source1);
                    preparedStatement.setString(2, destination1);                    
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        infoTextArea.setText("");  
                        while (resultSet.next()) {
                            String flightInfo = "Flight Number: " + resultSet.getString("flight_number") +
                                                ", Source: " + resultSet.getString("source") +
                                                ", Destination: " + resultSet.getString("destination") +
                                                ", Departure Date: " + resultSet.getString("departure_date") +
                                                ", Departure Time: " + resultSet.getString("departure_time")+
                                                ", Economy Class Price: "+resultSet.getInt("e_class")+
                                                ", Business Class Price: "+resultSet.getInt("b_class")+
                                                ", First Class Price: "+resultSet.getInt("f_class");
                            infoTextArea.append(flightInfo + "\n");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
  }
    public static void main(String[] args) {
        new FlightBookingFrame();
    }
}
