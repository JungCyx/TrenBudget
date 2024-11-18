package Swing;

import java.util.Scanner;

public class Budget {

    //Method to create a new budget
    public void createBudget(){

        String category;
        String frequency;
        double amount;
        Scanner scanner = new Scanner(System.in);
        boolean setNotification;
        boolean validInput = false;

        //First the user must input a category
        System.out.print("Enter category:");
        category = scanner.nextLine();

        //User inputs budget amount
        System.out.print("Enter the amount you would like to spend");
        String inputUserAmount = scanner.nextLine();
        amount = Double.parseDouble(inputUserAmount);




    }
    
}
