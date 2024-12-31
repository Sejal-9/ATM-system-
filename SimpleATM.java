package project;

import java.util.Scanner;

public class SimpleATM {
    static Scanner scanner = new Scanner(System.in);
    static String loggedInUser = null;
    static double balance = 0.0;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- ATM Interface ---");
            System.out.println("1. Login");
            System.out.println("2. New Customer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    if (login()) {
                        atmMenu();
                    }
                }
                case 2 -> registerCustomer();
                case 3 -> {
                    System.out.println("Thank you for using our ATM. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void registerCustomer() {
        scanner.nextLine();
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();
        System.out.print("Set a 4-digit PIN: ");
        String pin = scanner.next();
        System.out.println("Registration successful! You can now login.");
    }

    static boolean login() {
        System.out.print("Enter your PIN: ");
        String pinInput = scanner.next();

        // Hardcoded PIN for simplicity
        String correctPin = "1234";

        if (pinInput.equals(correctPin)) {
            loggedInUser = "User";
            System.out.println("Login successful! Welcome, " + loggedInUser);
            return true;
        } else {
            System.out.println("Invalid PIN! Please try again.");
            return false;
        }
    }

    static void atmMenu() {
        boolean exitMenu = false;
        while (!exitMenu) {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. View Account Statement");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> depositMoney();
                case 2 -> withdrawMoney();
                case 3 -> viewAccountStatement();
                case 4 -> {
                    System.out.println("Logged out successfully.");
                    exitMenu = true;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void depositMoney() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount. Transaction failed.");
        } else {
            balance += amount;
            System.out.println("Amount deposited successfully!");
        }
    }

    static void withdrawMoney() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount. Transaction failed.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance. Transaction failed.");
        } else {
            balance -= amount;
            System.out.println("Amount withdrawn successfully!");
        }
    }

    static void viewAccountStatement() {
        System.out.println("\n--- Account Statement ---");
        System.out.println("Balance: " + balance);
    }
}
