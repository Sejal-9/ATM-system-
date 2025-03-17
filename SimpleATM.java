import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class SimpleATMSystem {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, UserAccount> accounts = new ConcurrentHashMap<>();
    static String loggedInUser = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- ATM Interface ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> login();
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
        System.out.print("Enter your Name: ");
        String name = scanner.next();
        System.out.print("Set a 4-digit PIN: ");
        String pin = scanner.next();
        System.out.print("Enter your Mobile Number: ");
        String mobile = scanner.next();
        System.out.print("Enter your Bank Account Number: ");
        String accountNumber = scanner.next();

        if (accounts.containsKey(mobile)) {
            System.out.println("Mobile number already registered. Please login.");
        } else {
            accounts.put(mobile, new UserAccount(name, pin, accountNumber, 0.0));
            System.out.println("Registration successful!");
        }
    }

    static void login() {
        System.out.print("Enter your Mobile Number: ");
        String mobile = scanner.next();
        System.out.print("Enter your 4-digit PIN: ");
        String pin = scanner.next();

        Optional.ofNullable(accounts.get(mobile))
                .filter(account -> account.getPin().equals(pin))
                .ifPresentOrElse(account -> {
                    loggedInUser = mobile;
                    System.out.println("Login successful! Welcome, " + account.getName());
                    atmMenu();
                }, () -> System.out.println("Invalid credentials!"));
    }

    static void atmMenu() {
        boolean exitMenu = false;
        while (!exitMenu) {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. View Balance");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> depositMoney();
                case 2 -> withdrawMoney();
                case 3 -> viewBalance();
                case 4 -> exitMenu = true;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void depositMoney() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        accounts.computeIfPresent(loggedInUser, (key, account) -> {
            account.deposit(amount);
            System.out.println("Amount deposited successfully!");
            return account;
        });
    }

    static void withdrawMoney() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        accounts.computeIfPresent(loggedInUser, (key, account) -> {
            if (account.withdraw(amount)) {
                System.out.println("Amount withdrawn successfully!");
            } else {
                System.out.println("Insufficient balance!");
            }
            return account;
        });
    }

    static void viewBalance() {
        Optional.ofNullable(accounts.get(loggedInUser))
                .ifPresent(account -> System.out.println("Current Balance: " + account.getBalance()));
    }
}

class UserAccount {
    private final String name;
    private final String pin;
    private final String accountNumber;
    private double balance;

    public UserAccount(String name, String pin, String accountNumber, double balance) {
        this.name = name;
        this.pin = pin;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getName() { return name; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }

    public void deposit(double amount) { balance += amount; }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
