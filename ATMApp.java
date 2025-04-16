import java.util.Scanner;

// 1. Class to represent the user's Bank Account
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}

// 2. ATM Class
class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void withdraw(double amount) {
        if (account.withdraw(amount)) {
            System.out.println("Withdrawal successful. Amount withdrawn: ₹" + amount);
        } else {
            System.out.println("Withdrawal failed. Check balance or enter valid amount.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            account.deposit(amount);
            System.out.println("Deposit successful. Amount deposited: ₹" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void checkBalance() {
        System.out.println("Current Balance: ₹" + account.getBalance());
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n==== ATM Menu ====");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Enter a number (1-4): ");
                scanner.next(); // consume invalid input
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to withdraw: ₹");
                    double withdrawAmount = scanner.nextDouble();
                    withdraw(withdrawAmount);
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ₹");
                    double depositAmount = scanner.nextDouble();
                    deposit(depositAmount);
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select from 1 to 4.");
            }
        } while (choice != 4);

        scanner.close();
    }
}

// 3. Main class
public class ATMApp {
    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(1000.0); // Starting balance ₹1000
        ATM atm = new ATM(userAccount);
        atm.start();
    }
}

