import java.awt.*;
import java.awt.event.*;
public class AirlineReservationSystem extends Frame {
    private Button registrationButton;
    private Button loginButton;
    private Button flightBookingButton;
    private Button ticketBookingButton;
    private Button viewTicketbookingInfoButton;
    public AirlineReservationSystem() {
        initializeUI();
    }
    private void initializeUI() {
        setTitle("Airline Reservation System");
        setSize(1920, 1080);
        setLayout(new GridLayout(6, 1));
        registrationButton = new Button("Registration");
        loginButton = new Button("Login");
        flightBookingButton = new Button("Flight Booking");
        ticketBookingButton = new Button("Ticket Booking");
        viewTicketbookingInfoButton = new Button("View User Ticket Information");
        add(registrationButton);
        add(loginButton);
        add(flightBookingButton);
        add(ticketBookingButton);
        add(viewTicketbookingInfoButton);
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationFrame();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginFrame();
            }
        });
        flightBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFlightBookingFrame();
            }
        });
        ticketBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTicketBookingFrame();
            }
        });
        viewTicketbookingInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openViewTicketBookingInfoFrame();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }
    private void openRegistrationFrame() {
        new RegistrationFrame();
    }
    private void openLoginFrame() {
        new LoginFrame();
    }
    private void openFlightBookingFrame() {
        new FlightBookingFrame();
    }
    private void openTicketBookingFrame() {
        new TicketBookingFrame();
    }
    private void openViewTicketBookingInfoFrame() {
        new ViewTicketBookingInfoFrame();
    }
    public static void main(String[] args) {
        new AirlineReservationSystem();
    }
}
