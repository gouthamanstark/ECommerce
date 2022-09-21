package de.buw.se4de;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.*;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.crypto.Data;

public class Customer {

    Scanner scan= new Scanner(System.in);

    public double paymentAmount=0;
    public double overAllCost=0;    // Used to store the total cost of the order
    
    
    public void customerService(User user){


        System.out.println("Welcome to the customer service portal "+user.username);
        System.out.println("*****************************************");
        System.out.println("Press '1' to view your orders and their status or Press '2' to place a new order : ");
        int option=scan.nextInt();
        XSSFWorkbook workbook=DatabaseOperations.databaseConnection();
        XSSFSheet sheet=workbook.getSheetAt(0);

        XSSFRow row= sheet.getRow(user.row);
        System.out.println("*****************************************");
        if(option==1) {
            int noOfColumns = row.getLastCellNum();
            
            //Iterates over the given user row to find the orders places by that specific user
            if (noOfColumns >8) {
                System.out.println("Your current orders are listed below !! ");
                for (int i = 8; i <noOfColumns; i++) {

                    System.out.println((int)row.getCell(i).getNumericCellValue());
                }

                System.out.println("Please enter any order-id for detailed-information : ");
                int orderID = scan.nextInt();
                this.orderDetails(orderID);
            }

            else{
                System.out.println("Sorry !! You haven't placed any order yet !! ");

            }
        }
        
        
        else if(option==2){
            int choice=0;
            while(choice==0){
                int productID=0;
                int quantity=0;


                DatabaseOperations.listInventoryStock(); // Lists all the items available in the inventory
                System.out.println("Please enter product ID to place an order : ");
                productID=scan.nextInt();
                System.out.println("Please enter the quantity required : ");
                quantity=scan.nextInt();
                double price=DatabaseOperations.checkAvailability(productID,quantity,user); // Checks whether the product and the quantity is available in the inventory

                // The items and the overall prices for the order are added to the array list for further processing
                if (price!=0.0d){
                    user.itemsInCart.add(productID);
                    user.itemsInCart.add(quantity);
                    user.overAllPrice.add(price*quantity);
                }

                System.out.println("Press '0' to continue shopping or '1' to checkout : ");
                if(scan.nextInt()==1){
                    choice=1;
                }
            }
            // Allows the customers to make payment
            this.paymentProcessing(user);

        }
    }

    public  void paymentProcessing(User user){
        int orderID=0;
        System.out.println("The items in your cart is listed below !! \n");
        System.out.print("Product-ID" +" \t"+"Quantity"+"\t"+"Price \n");

        // Prints all the items in your order and their prices
        for(int i=0;i<user.itemsInCart.size();i+=2){
            int num=i/2;
            System.out.println((int)user.itemsInCart.get(i)+"\t"+user.itemsInCart.get(i+1)+"\t"+user.overAllPrice.get(num));

                this.overAllCost += user.overAllPrice.get(num);

        }
        System.out.println("Overall cost of your order : "+this.overAllCost);
        System.out.println("*****************************************");
        System.out.println("Enter the amount you would like to pay now : ");
        this.paymentAmount=scan.nextDouble();

        if(this.paymentAmount==this.overAllCost){
            System.out.println("Success !! Your order has been placed !! ");
            orderID=Customer.generateUniqueID();  // Generates unique-ID once the payment is successfull
            System.out.println("Your order-ID is " +(int) orderID);
            DatabaseOperations.newOrderEntry(orderID,user,0.0d); // New order placed is updated in the orders data page of the database
            DatabaseOperations.updateInventory(user); // Inventory is updated based on the items and the number of items ordered
        }
        else if(this.paymentAmount>this.overAllCost){
            System.out.println("Success !! Your order has been placed !!");
            orderID=Customer.generateUniqueID();
            System.out.println("Your order-ID is " + (int)orderID);
            System.out.println("You will receive "+(this.paymentAmount-this.overAllCost)+" as a refund from the delivery agent.");
            DatabaseOperations.newOrderEntry(orderID,user,(this.paymentAmount-this.overAllCost));   // New order placed is updated in the orders data page of the database
            DatabaseOperations.updateInventory(user);// Inventory is updated based on the items and the number of items ordered
        }
        else{
            System.out.println("Sorry !! We couldn't place your order !!");
            System.out.println("Press '0' to try payment again or press '1' to exit : ");
            if(scan.nextInt()==0){
                this.paymentProcessing(user);
            }
            else{
                System.out.println("Sorry that your order haven't placed!! You can come and try to make a new order later! ");
            }
        }

    }

    // Generates unique ID for the new order placed
    public static int generateUniqueID(){


        Random rand= new Random();
        int uniqueID=rand.nextInt(1000000);
        return uniqueID;
    }

// Gives detailed information about a specific order
    public void orderDetails(int orderID){
        XSSFWorkbook workbook=DatabaseOperations.databaseConnection();

        XSSFSheet sheet=workbook.getSheetAt(2);
        int noOfRows=sheet.getLastRowNum();
        System.out.println("The detailed information about the order " +orderID +" : ");
        // Searches the database to find the order and displays detailed information about it
        for(int i=1;i<noOfRows;i++){
            XSSFRow row=sheet.getRow(i);
            XSSFCell cell=row.getCell(0);
            if(cell.getNumericCellValue()==orderID){
                for(int j=1;j<4;j++){
                    System.out.print(row.getCell(j).getStringCellValue()+"\t");
                }
                System.out.println("\n");

            }
        }
        System.out.println("*****************************************");



    }


    }






