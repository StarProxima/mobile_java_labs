import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


interface Balance {
   double  getBalance();
   void  checkBalance();
}

interface Bill {
    void  deposit(double amount);
    void  withdraw(double amount);
    void  close();
}

abstract class Account  {
    private double balance;
    private Scanner scanner;

    public Account(double balance) {
        this.balance = balance;
        this.scanner = new Scanner(System.in);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void checkBalance() {
        System.out.println("Balance: $" + balance);
    }

    public void close() {
        balance = 0;
        System.out.println("Account closed");
    }

    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0;
    }


    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                '}';
    }
}

class SalaryAccount extends Account implements Balance, Bill {
    public SalaryAccount(double balance) {
        super(balance);
    }

    public void performAutopayments() {
        System.out.println("Performing autopayments...");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalaryAccount)) return false;
        if (!super.equals(o)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "SalaryAccount{" +
                "balance=" + getBalance() +
                '}';
    }
}

class CreditAccount extends Account implements Balance, Bill {
    private double interest;
    private int maturity;

    public CreditAccount(double balance, double interest, int maturity) {
        super(balance);
        this.interest = interest;
        this.maturity = maturity;
    }

    public double getInterest() {
        return interest;
    }

    public int getMaturity() {
        return maturity;
    }

    public void checkMaturityDate() {
        System.out.println("Checking maturity date...");
        System.out.println("Maturity date: " + maturity + " days");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditAccount)) return false;
        if (!super.equals(o)) return false;
        CreditAccount that = (CreditAccount) o;
        return Double.compare(that.interest, interest) == 0 &&
                maturity == that.maturity;
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "balance=" + getBalance() +
                ", interest=" + interest +
                ", maturity=" + maturity +
                '}';
    }
}

class DepositAccount extends Account implements Balance, Bill {
    private double interest;
    private int depositPeriod;

    public DepositAccount(double balance, double interest, int depositPeriod) {
        super(balance);
        this.interest = interest;
        this.depositPeriod = depositPeriod;
    }

    public double getInterest() {
        return interest;
    }

    public int getDepositPeriod() {
        return depositPeriod;
    }

    public void calculateInterestForElapsedPeriod() {
        System.out.println("Calculating the amount of interest for the elapsed period...");

        int elapsedPeriod;
        do {
            System.out.print("Enter the elapsed period in months: ");
            elapsedPeriod = getScanner().nextInt();
        } while (elapsedPeriod > depositPeriod);

        double interestAmount = (getBalance() * interest * elapsedPeriod) / 12;
        System.out.println("Interest for the elapsed period: $" + interestAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepositAccount)) return false;
        if (!super.equals(o)) return false;
        DepositAccount that = (DepositAccount) o;
        return Double.compare(that.interest, interest) == 0 &&
                depositPeriod == that.depositPeriod;
    }

    @Override
    public String toString() {
        return "DepositAccount{" +
                "balance=" + getBalance() +
                ", interest=" + interest +
                ", depositPeriod=" + depositPeriod +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {


        ArrayList<Account> accountss = new ArrayList<Account>();

        accountss.add(new SalaryAccount(1000));
        accountss.add( new CreditAccount(5000, 0.05, 3));
        accountss.add(new DepositAccount(2000, 0.08, 5));
        accountss.add( new SalaryAccount(1000));

        ArrayList<Account> accounts = new ArrayList<Account>();

        for (Account acc: accountss) {
            if(!accounts.contains(acc)) {
                accounts.add(acc);
            }
        }

        accounts.get(0).deposit(500);
        accounts.get(1).withdraw(1000);
        accounts.get(2).checkBalance();

        if(accounts.get(0) instanceof SalaryAccount) {
            ((SalaryAccount) accounts.get(0)).performAutopayments();
        }

        if(accounts.get(0) instanceof CreditAccount) {
            ((CreditAccount) accounts.get(0)).checkMaturityDate();
        }
        if(accounts.get(0) instanceof DepositAccount) {
            ((DepositAccount) accounts.get(0)).calculateInterestForElapsedPeriod();
        }

        for (Account account : accounts) {
            System.out.println(account);
            account.close();
        }
    }
}