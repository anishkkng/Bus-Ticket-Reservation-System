import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        TicketBooking booking = new TicketBooking();

        while (true) {
            System.out.println("\n===== Bus Ticket Reservation System =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String newPassword = scanner.nextLine();
                    if (user.register(newUsername, newPassword)) {
                        System.out.println("Registration successful! You can now log in.");
                    } else {
                        System.out.println("Registration failed. Try again.");
                    }
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    int userId = user.login(username, password);
                    
                    if (userId != -1) {
                        System.out.println("Login successful!");
                        handleUserActions(scanner, booking, userId);
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using the system. Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }

    private static void handleUserActions(Scanner scanner, TicketBooking booking, int userId) {
        while (true) {
            System.out.println("\n===== User Menu =====");
            System.out.println("1. Book a Ticket");
            System.out.println("2. View My Tickets");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Bus ID: ");
                    int busId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Travel Date (YYYY-MM-DD): ");
                    String travelDate = scanner.nextLine();
                    System.out.print("Enter Seat Number: ");
                    int seatNumber = scanner.nextInt();
                    scanner.nextLine();
                    booking.bookTicket(userId, busId, travelDate, seatNumber);
                    break;

                case 2:
                    booking.viewTickets(userId);
                    break;

                case 3:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }
}
