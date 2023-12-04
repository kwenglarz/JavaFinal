/*
 ** Class Name: IFT 210
 ** Kayana Wenglarz
 ** Date Created: 10/02/2021
 ** Purpose: Final Course Project
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class PortfolioManager
{
    // class level references
    private static Scanner scnr = new Scanner(System.in);
    private static ArrayList<TransactionHistory> portfolioList = new ArrayList<TransactionHistory>();
    private static double cash = 0.0;


    public static void main(String[] args)
    {

        // Create the loop control variable
        int choice = 0;

        // Create the loop that runs until zero (0) is chosen

        do
        {
            // Show menu and get user's choice
            System.out.println("\nKayana Wenglarz Brokerage Account\n");
            System.out.println("0: Exit");
            System.out.println("1: Deposit Cash");
            System.out.println("2: Withdraw Cash");
            System.out.println("3: Buy Stock");
            System.out.println("4: Sell Stock");
            System.out.println("5: Display Transaction History");
            System.out.println("6: Display Portfolio");

            
            System.out.print("\nEnter option (0 to 6): ");
            choice = scnr.nextInt();

            // Run code based on user choice
            switch(choice) {
                case 0:
                    System.out.println("\nGoodbye!");
                    break;
                case 1:
                    depositCash();
                    break;
                case 2: 
                    withdrawCash();
                    break;
                case 3:
                    buyStock();
                    break;
                case 4:
                    sellStock();
                    break;
                case 5:
                    displayTransactionHistory();
                    break;
                case 6:
                    displayPortfolio();
                    break;
                default:
                    System.out.println("\nError. Please select from the menu.\n");
                    break;
            }
        }
        while (choice != 0);
    }


    // DEPOSIT CASH METHOD
    private static void depositCash() {
        // get date and amount of cash 
        System.out.print("Enter deposit date: ");
        String date = scnr.next();
        System.out.print("Enter amount of deposit: ");
        

        try {
            // check if user input is a number
            double amount = scnr.nextDouble();
            // increase cash and make cash transaction
            cash += amount;
            TransactionHistory lineItem = new TransactionHistory("CASH", date, "DEPOSIT", amount, 1.0);
            portfolioList.add( lineItem );
        } catch (Exception ex) {
            System.out.println("\nInput must be a number.");
            scnr.next();
            return;
        }
        
        //blank line
        System.out.println();
    }

    // WITHDRAW CASH METHOD
    private static void withdrawCash() {
        // get date and amount of cash
        System.out.print("Enter withdraw date: ");
        String date = scnr.next();
        System.out.print("Enter amount to withdraw: ");
        
        try {
            // ensures input is number
            double amount = scnr.nextDouble();
            // decrease cash
            if (amount > cash) {
                System.out.print("\nError. You cannot withdraw more cash than you have available.");
            } else {
                cash -= amount;
                TransactionHistory lineItem = new TransactionHistory("CASH", date, "WITHDRAW", amount * -1, 1.0);
                portfolioList.add(lineItem);
            }
        } catch (Exception ex) {
            System.out.println("\nInput must be a number.");
            scnr.next();
            return;
        }

        //blank line
        System.out.println();
    }
    
    // BUY STOCK METHOD 
    private static void buyStock() {
        // Get stock info
        System.out.print("Enter stock purchase date: ");
        String date = scnr.next();
        System.out.print("Enter stock ticker: ");
        String ticker = scnr.next();
        ticker = ticker.toUpperCase();
        System.out.print("Enter stock quantity: ");
        double quantity = scnr.nextDouble();
        System.out.print("Enter stock cost (basis): ");
        double cost = scnr.nextDouble();
        
        // check to see if we have the money to purchase stock
        double totalCost = quantity * cost;
        if (totalCost > cash) {
            System.out.print("\nError. You do not have the cash for that purchase. \n");
        } else {
             // if we have the money, make stock transaction for transaction history
            portfolioList.add( new TransactionHistory(ticker, date, "BUY", quantity, cost ) );
        
            // deduct cash and make the cash transaction line item
            cash -= totalCost;
            portfolioList.add( new TransactionHistory("CASH", date, "WITHDRAW", totalCost * -1, 1.0) );
        }
        // blank line
        System.out.println();
    }

    // SELLING STOCK METHOD 
    private static void sellStock() {
        // Get stock info
        System.out.print("Enter stock sell date: ");
        String date = scnr.next();
        System.out.print("Enter stock ticker: ");
        String ticker = scnr.next().toUpperCase();
        System.out.print("Enter stock quantity to sell: ");
        double quantity = scnr.nextDouble();
        System.out.print("Enter stock cost (basis): ");
        double cost = scnr.nextDouble();

        // This checks if we have enough of the inputted stock to sell 
        int currentTotalQuantity = 0;
        for (TransactionHistory record : portfolioList) {
            if (record.getTicker().equals(ticker)) {
                if(record.getTransType().equals("BUY")) {
                    currentTotalQuantity += record.getQty();
                } else {
                    currentTotalQuantity -= record.getQty();
                }
            }
        }
        
        double newBalance = quantity * cost;
        if (currentTotalQuantity < quantity) {
            System.out.println("\nError. Must have enough stock to sell. Must enter " + currentTotalQuantity + " or less.");
        } else if(newBalance <= 0) {
            System.out.println("\nInvalid input. Must be greater than 0 to process transaction.");
        } else {
            portfolioList.add( new TransactionHistory(ticker, date, "SELL", quantity, cost ) );
            
            //Add cash and make transaction line item
            cash += newBalance;
            portfolioList.add( new TransactionHistory("CASH", date, "DEPOSIT", newBalance, 1.0));
        }

        //Blank line
        System.out.println();
    }

    // DISPLAYING TRANSACTION HISTORY METHOD
    private static void displayTransactionHistory() {
        // show heading
        System.out.println();
        System.out.println("\t\tKayana Wenglarz Brokerage Account");
        System.out.println("\t\t=================================");
        System.out.println();
        System.out.printf("%-16s%-10s%10s%15s     %s%n", "Date", "Ticker", "Quantity", "Cost Basis","Trans Type");
        System.out.println("==================================================================");

        // show the records
        for (TransactionHistory record : portfolioList) {
            String costBasis = String.format("$%.2f", record.getCostBasis());
            System.out.printf("%-16s%-10s%10.0f%15s     %s%n", record.getTransDate(), record.getTicker(), record.getQty(), costBasis, record.getTransType());
        }
    }

    // DISPLAYING PORTFOLIO
    private static void displayPortfolio() {
        //show header
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        System.out.println("\nPortfolio as of: " + dtf.format(LocalDateTime.now()));
        System.out.println("====================================\n");
        System.out.printf("%-8s%s%n", "Ticker", "Quantity");
        System.out.println("================");

        // show cash
        System.out.printf("%-8s%.2f%n", "CASH", cash);

        // get stocks
        ArrayList<String> stocks = new ArrayList<String>();
        for (TransactionHistory record : portfolioList) {
            if (record.getTicker().equals("CASH")) {
                continue;
            }
            if (stocks.contains(record.getTicker()) == false) {
                stocks.add(record.getTicker());
            }
        }    
            
        // add and subtract to get the number of new stock
        for (String stock : stocks) {
            double total = 0.0;

            for (TransactionHistory record : portfolioList) {
                if (record.getTicker().equals(stock)) {
                    if(record.getTransType().equals("BUY")) {
                        total += record.getQty();
                    } else {
                        total -= record.getQty();
                    }
                }
            }

            //show stock quantity
            if (total > 0) {
                System.out.printf("%-8s%.0f%n", stock, total);
            }  
        }
        //blank line
        System.out.println();
    }
}