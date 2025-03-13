import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TicketBooking {
    private Connection conn;

    public TicketBooking() {
        conn = DBConnection.getConnection();
    }

    public void bookTicket(int userId, int busId, String travelDate, int seatNumber) {
        try {
            // ✅ First, check if the bus_id exists
            String checkBusQuery = "SELECT id FROM buses WHERE id = ?";
            PreparedStatement checkBusPst = conn.prepareStatement(checkBusQuery);
            checkBusPst.setInt(1, busId);
            ResultSet rs = checkBusPst.executeQuery();

            if (!rs.next()) {
                System.out.println(" Error: Bus ID " + busId + " does not exist! Please enter a valid Bus ID.");
                return;
            }

            // ✅ Check if the seat is already booked
            String checkSeatQuery = "SELECT * FROM tickets WHERE bus_id = ? AND seat_number = ? AND travel_date = ?";
            PreparedStatement checkSeatPst = conn.prepareStatement(checkSeatQuery);
            checkSeatPst.setInt(1, busId);
            checkSeatPst.setInt(2, seatNumber);
            checkSeatPst.setString(3, travelDate);
            ResultSet seatRs = checkSeatPst.executeQuery();

            if (seatRs.next()) {
                System.out.println(" Error: Seat " + seatNumber + " is already booked on Bus " + busId + " for " + travelDate);
                return;
            }

            // ✅ If all checks pass, book the ticket
            String query = "INSERT INTO tickets (user_id, bus_id, travel_date, seat_number) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, userId);
            pst.setInt(2, busId);
            pst.setString(3, travelDate);
            pst.setInt(4, seatNumber);
            pst.executeUpdate();
            System.out.println(" Ticket booked successfully for Bus " + busId + " on " + travelDate + ", Seat " + seatNumber + "!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewTickets(int userId) {
        try {
            String query = "SELECT t.id, t.bus_id, b.bus_name, t.travel_date, t.seat_number " +
                           "FROM tickets t JOIN buses b ON t.bus_id = b.id " +
                           "WHERE t.user_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            // ✅ Check if the ResultSet has any records
            if (!rs.isBeforeFirst()) { 
                System.out.println(" No tickets found for your account.");
                return;
            }

            System.out.println("\n===== Your Booked Tickets =====");
            System.out.printf("%-10s %-10s %-20s %-15s %-10s\n", "Ticket ID", "Bus ID", "Bus Name", "Travel Date", "Seat");
            System.out.println("--------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-10d %-20s %-15s %-10d\n",
                        rs.getInt("id"),
                        rs.getInt("bus_id"),
                        rs.getString("bus_name"),
                        rs.getString("travel_date"),
                        rs.getInt("seat_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
