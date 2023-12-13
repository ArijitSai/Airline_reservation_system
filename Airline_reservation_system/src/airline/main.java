package airline;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Flight {
    private String flightNumber;
    private String source;
    private String destination;
    private LocalDate date;
    private int capacity;
    private int availableSeats;

    public Flight(String flightNumber, String source, String destination, LocalDate date, int capacity) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.capacity = capacity;
        this.availableSeats = capacity;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean reserveSeats(int numSeats) {
        if (numSeats <= availableSeats) {
            availableSeats -= numSeats;
            return true;
        } else {
            System.out.println("Not enough available seats on this flight.");
            return false;
        }
    }

    public void increaseAvailableSeats(int numSeats) {
        availableSeats += numSeats;
    }
}

class Reservation {
    private String passengerName;
    private String flightNumber;
    private int numSeats;

    public Reservation(String passengerName, String flightNumber, int numSeats) {
        this.passengerName = passengerName;
        this.flightNumber = flightNumber;
        this.numSeats = numSeats;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getNumSeats() {
        return numSeats;
    }
}

class AirlineReservationSystem {
    private List<Flight> flights;
    private List<Reservation> reservations;

    public AirlineReservationSystem() {
        flights = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void displayFlights() {
        for (Flight flight : flights) {
            System.out.println("Flight: " + flight.getFlightNumber() +
                    ", Source: " + flight.getSource() +
                    ", Destination: " + flight.getDestination() +
                    ", Date: " + flight.getDate() +
                    ", Available Seats: " + flight.getAvailableSeats());
        }
    }

    public void makeReservation(String passengerName, String source, String destination, int numSeats) {
        for (Flight flight : flights) {
            if (flight.getSource().equals(source) &&
                    flight.getDestination().equals(destination) &&
                    flight.getAvailableSeats() >= numSeats) {
                if (flight.reserveSeats(numSeats)) {
                    Reservation reservation = new Reservation(passengerName, flight.getFlightNumber(), numSeats);
                    reservations.add(reservation);
                    System.out.println("Reservation successful for " + passengerName +
                            " from " + source + " to " + destination +
                            " on flight " + flight.getFlightNumber() +
                            " for " + numSeats + " seats.");

                    // Updated: Decrease available seats
                    System.out.println("Updated Available Seats: " + flight.getAvailableSeats());
                }
                return;
            }
        }
        System.out.println("No matching flight found from " + source + " to " + destination +
                " with sufficient available seats.");
    }

    public void cancelReservation(String passengerName, String flightNumber, int numSeats) {
        Reservation reservationToRemove = null;

        for (Reservation reservation : reservations) {
            if (reservation.getPassengerName().equals(passengerName) &&
                    reservation.getFlightNumber().equals(flightNumber) &&
                    reservation.getNumSeats() == numSeats) {
                reservationToRemove = reservation;
                break;
            }
        }

        if (reservationToRemove != null) {
            reservations.remove(reservationToRemove);

            // Update available seats
            Flight flight = getFlightByFlightNumber(flightNumber);
            flight.increaseAvailableSeats(numSeats);

            System.out.println("Reservation canceled for " + passengerName +
                    " on flight " + flightNumber + " for " + numSeats + " seats.");

            // Display available flights after cancellation
            displayAvailableFlights();
        } else {
            System.out.println("No matching reservation found for cancellation.");
        }
    }

    public void displayReservations() {
        for (Reservation reservation : reservations) {
            Flight flight = getFlightByFlightNumber(reservation.getFlightNumber());
            System.out.println("Passenger: " + reservation.getPassengerName() +
                    ", Flight: " + reservation.getFlightNumber() +
                    ", Source: " + flight.getSource() +
                    ", Destination: " + flight.getDestination() +
                    ", Date: " + flight.getDate() +
                    ", Seats: " + reservation.getNumSeats());
        }
    }

    public void displayAvailableFlights() {
        System.out.println("\nAvailable Flights after Reservation/Cancellation:");
        displayFlights();
    }

    private Flight getFlightByFlightNumber(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    public void makeReservationFromUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter passenger name:");
        String passengerName = scanner.nextLine();

        System.out.println("Enter source:");
        String source = scanner.nextLine().toUpperCase();

        System.out.println("Enter destination:");
        String destination = scanner.nextLine().toUpperCase();

        System.out.println("Enter number of seats to reserve:");
        int numSeats = scanner.nextInt();

        makeReservation(passengerName, source, destination, numSeats);
    }

    public void cancelReservationFromUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter passenger name for cancellation:");
        String passengerName = scanner.nextLine();

        System.out.println("Enter flight number for cancellation:");
        String flightNumber = scanner.nextLine();

        System.out.println("Enter number of seats to cancel:");
        int numSeats = scanner.nextInt();

        cancelReservation(passengerName, flightNumber, numSeats);
    }
}

public class main {
    public static void main(String[] args) {
        AirlineReservationSystem reservationSystem = new AirlineReservationSystem();

        // Adding flights to the system
        Flight flight1 = new Flight("F1", "KOLKATA", "MUMBAI", LocalDate.parse("2023-12-01"), 50);
        Flight flight2 = new Flight("F2", "MUMBAI", "KOLKATA", LocalDate.parse("2023-12-02"), 30);
        Flight flight3 = new Flight("F3", "KOLKATA", "BENGALORE", LocalDate.parse("2023-12-23"), 42);
        Flight flight4 = new Flight("F4", "MUMBAI", "DELHI", LocalDate.parse("2023-12-05"), 36);
        reservationSystem.addFlight(flight1);
        reservationSystem.addFlight(flight2);
        reservationSystem.addFlight(flight3);
        reservationSystem.addFlight(flight4);
        // Displaying available flights
        System.out.println("Available Flights:");
        reservationSystem.displayFlights();

        // Making reservations from user input
        reservationSystem.makeReservationFromUserInput();

        // Displaying reservations
        System.out.println("\nReservations:");
        reservationSystem.displayReservations();

        // Cancelling a reservation from user input
        reservationSystem.cancelReservationFromUserInput();
    }
}


