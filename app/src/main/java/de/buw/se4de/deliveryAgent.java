package de.buw.se4de;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.*;


public class deliveryAgent {

    Scanner scan = new Scanner(System.in);

// Displays all the orders that are confirmed and the orders that are on the way. ALso allows the delivery agent to update the status of the order
    void orderToBeDelivered() {


    int option = 0;
    System.out.println("Press '1' to view the orders' to be delivered or '2' to update their status : ");
    option = scan.nextInt();


    if(option==1) {
        displayOrderDetails();
    }
    else {
        updatedeliveryStatus();
    }
        System.out.println("Press '1' to continue using the app or else press '0' : ");
    if(scan.nextInt()==1){
        orderToBeDelivered();
    }




    }


    // Allows the delivery agent to update the status of the order
    public static void updatedeliveryStatus() {

        XSSFWorkbook workbook = DatabaseOperations.databaseConnection();
        XSSFSheet sheet = workbook.getSheetAt(2);
        int row = sheet.getLastRowNum();

        Scanner scan = new Scanner(System.in);
        System.out.println("*****************************************");
        System.out.println("Enter order id to be updated");

        int orderNumber = scan.nextInt();

        // Searches the database to find the orders
        for (int i = 1; i <row; i++) {
            XSSFRow currentRow = sheet.getRow(i);
            XSSFCell cell = currentRow.getCell(0);

            if (cell.getNumericCellValue() == orderNumber) {
               XSSFCell cell1 = currentRow.getCell(3);

               // Updates the status of orders from "orderConfirmed" to "ontheway"
                if (cell1.getStringCellValue().equalsIgnoreCase("orderConfirmed")) {


                    System.out.println("Status updated  ");
                    cell1 = currentRow.createCell(3);
                    cell1.setCellValue((String) "ontheway");
                    break;

                }

                // Updates the status from "ontheway" to "Delivered"
                else if (cell1.getStringCellValue().equalsIgnoreCase("ontheway")) {

                    System.out.println("Status updated  ");
                    cell1 = currentRow.createCell(3);
                    cell1.setCellValue((String) "Delivered");
                    break;


                }

                else{
                    System.out.println("Invalid order ID");
                }
            }

        }



     // The below statements are used to make actual changes to the database and save them

        try {
            FileOutputStream outputStream = new FileOutputStream("src/main/resources/database.xlsx");
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }




    }





    // Lists all the orders that are confirmed and the orders that are on the way
    public static void displayOrderDetails(){
        System.out.println("*****************************************");
        System.out.println("The orders to be delivered");
        XSSFWorkbook workbook = DatabaseOperations.databaseConnection();
        XSSFSheet sheet = workbook.getSheetAt(2);
        int row = sheet.getLastRowNum();
        System.out.println("*****************************************");
        System.out.println("The orders which are confirmed");
        for (int i = 1; i <row; i++) {
            XSSFRow currentRow = sheet.getRow(i);
            XSSFCell cell = currentRow.getCell(3);


            if (cell.getStringCellValue().equalsIgnoreCase("orderConfirmed")) {

                cell = currentRow.getCell(0);
                System.out.println((int)cell.getNumericCellValue());
            }

        }
        System.out.println("*****************************************");
        System.out.println("The orders which are ontheway");
        for (int k= 1; k <row; k++){
            XSSFRow currentRow = sheet.getRow(k);
            XSSFCell cell = currentRow.getCell(3);
            if(cell.getStringCellValue().equalsIgnoreCase("ontheway")) {

                cell = currentRow.getCell(0);
                System.out.println((int)cell.getNumericCellValue());
            }

        }

    }
}

