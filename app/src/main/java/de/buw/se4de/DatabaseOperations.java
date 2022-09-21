package de.buw.se4de;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.*;

public class DatabaseOperations {



    public static XSSFWorkbook databaseConnection(){
        XSSFWorkbook workbook=new XSSFWorkbook();

        try{
            FileInputStream inputStream = new FileInputStream("src/main/resources/database.xlsx");
            XSSFWorkbook workbook1= new XSSFWorkbook(inputStream);
            workbook=workbook1;



        }
        catch(Exception e){
            System.out.println(e);
        }

        return workbook;
    }


// Displays all the items in the inventory
public static void listInventoryStock() {
        Scanner scan = new Scanner(System.in);

        XSSFWorkbook workbook = DatabaseOperations.databaseConnection();
        XSSFSheet sheet = workbook.getSheetAt(1);
        int noOfRows = sheet.getLastRowNum();
        int noOfcols = sheet.getRow(1).getLastCellNum();
        // Searches through the database and prints all the products available
        for (int i = 0; i <=noOfRows; i++) {
            XSSFRow row = sheet.getRow(i);
            for(int c=0; c<noOfcols; c++) {
                XSSFCell cell =row.getCell(c);
                switch(cell.getCellTypeEnum()){
                    case STRING: System.out.print(cell.getStringCellValue()); break;
                    case NUMERIC: System.out.print((int)cell.getNumericCellValue()); break;
                }
                System.out.print(" | ");
            }
            System.out.println();
        }

    }

    // Allows the manager to add new items to the inventory
    static void addingNewItems(ArrayList l) {
        Scanner scan = new Scanner(System.in);
        try {
            XSSFWorkbook workbook = DatabaseOperations.databaseConnection();
            XSSFSheet sheet = workbook.getSheetAt(1);
            int noOfRows = sheet.getLastRowNum();
            int noOfcols = sheet.getRow(1).getLastCellNum();
            System.out.println("last row is" + noOfRows);
            System.out.println(l);
            // Add new items from the arraylist to the end of the database
            for(int i=0; i<l.size();i+=5) {

                XSSFRow currentRow = sheet.createRow(++noOfRows);
System.out.println(noOfRows);
                    currentRow.createCell(0).setCellValue((String) l.get(i));
                    currentRow.createCell(1).setCellValue((Integer) l.get(i+1));
                    currentRow.createCell(2).setCellValue((Integer) l.get(i+2));
                    currentRow.createCell(3).setCellValue((Double) l.get(i+3));
                    currentRow.createCell(4).setCellValue((String) l.get(i+4));

                }

            FileOutputStream OutputStream = new FileOutputStream("src/main/resources/database.xlsx");
            workbook.write(OutputStream);
            OutputStream.close();
            System.out.println("ExcelSheet Updated");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Press 1 if you want to go back to menu (or) 2 if you want to close the program");
        int c = scan.nextInt();
        switch(c){
            case 1:
                Manager m = new Manager();
                m.managerOperations();
                break;
            case 2:
                System.out.println("Program has been closed");
        }
    }

    // Checks whether the product and the entered quantity is available
    public static double checkAvailability(int productID, int quantity,User user){

        XSSFWorkbook workbook = DatabaseOperations.databaseConnection();
        XSSFSheet sheet=workbook.getSheetAt(1);
        int noOfRows= sheet.getLastRowNum();
        // Finds the product in the database and confirms that the entered quantity is available
        for(int i=1;i<noOfRows;i++){
            if(sheet.getRow(i).getCell(1).getNumericCellValue()==productID && sheet.getRow(i).getCell(2).getNumericCellValue()>=quantity){
                user.rowData.add(i);
                return sheet.getRow(i).getCell(3).getNumericCellValue();
            }

        }
        return 0.0d;
    }


// Checks whether the entered username is available or not
    public static Boolean usernameAvailabilityCheck(String username){

        XSSFWorkbook workbook= DatabaseOperations.databaseConnection();
        XSSFSheet sheet=workbook.getSheetAt(0);
        int noOfRows=sheet.getLastRowNum();
        // Returns false if the username is already taken
        for(int i=1;i<=noOfRows;i++){
            XSSFRow currentRow= sheet.getRow(i);
            if(currentRow.getCell(0).getStringCellValue().equalsIgnoreCase(username)){
                return false;
            }

        }
        return true;  // Returns true if the username is still available
    }

    // Allows the new customer to create an account
    public static void registerNewUser(Registration data){

        // Updates the database with the new customer data
        XSSFWorkbook workbook = DatabaseOperations.databaseConnection();
        XSSFSheet sheet= workbook.getSheetAt(0);
        int noOfRow=sheet.getLastRowNum();
        XSSFRow currentRow=sheet.createRow(noOfRow+1);
        XSSFCell currentCell =currentRow.createCell(0);
        currentCell.setCellValue((String) data.username);
        currentCell=currentRow.createCell(1);
        currentCell.setCellValue((String) data.firstName);
        currentCell=currentRow.createCell(2);
        currentCell.setCellValue((String) data.lastName);
        currentCell=currentRow.createCell(3);
        currentCell.setCellValue((Long) data.phoneNumber);
        currentCell=currentRow.createCell(4);
        currentCell.setCellValue((int) data.postalCode);
        currentCell=currentRow.createCell(5);
        currentCell.setCellValue((String) data.password);
        currentCell=currentRow.createCell(6);
        currentCell.setCellValue((String) data.address);
        currentCell=currentRow.createCell(7);
        currentCell.setCellValue((int) 1);

        try{
            FileOutputStream outputStream= new FileOutputStream("src/main/resources/database.xlsx");
            workbook.write(outputStream);
            outputStream.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        Login login=new Login();
        System.out.println("User registration successful !! " );
        System.out.println("*****************************************");
        System.out.println("Please login to use the application ! \n" );
        login.collectUserCredentials(1);

    }


// Updates the new order details in the order data page of the database
    public static void newOrderEntry(int orderID, User user,double balanceAmount){
        XSSFWorkbook workbook= databaseConnection();
        XSSFSheet sheet1=workbook.getSheetAt(0);
        XSSFRow row=sheet1.getRow(user.row);
        XSSFCell cell=sheet1.getRow(user.row).createCell(sheet1.getRow(user.row).getLastCellNum());
        cell.setCellValue(orderID); // Adds the order-ID to the customer row in the database
        XSSFSheet sheet2=workbook.getSheetAt(2);
        XSSFRow currentRow=sheet2.createRow(sheet2.getLastRowNum()+1);
        // Updates the order details in the order data page
        cell=currentRow.createCell(0);
        cell.setCellValue(orderID);

cell=currentRow.createCell(1);
cell.setCellValue(user.username);
cell=currentRow.createCell(2);
cell.setCellValue(row.getCell(6).getStringCellValue());
cell=currentRow.createCell(3);
cell.setCellValue("orderConfirmed");
cell=currentRow.createCell(4);
cell.setCellValue(balanceAmount);
String products="";

// Computes the products and their quantities involved in the order
for(int i=0;i<user.itemsInCart.size();i+=2){
    products=products+ user.itemsInCart.get(i)+"+"+user.itemsInCart.get(i+1)+"\n";
}

cell=currentRow.createCell(5);
cell.setCellValue(products);



        try{
            FileOutputStream outputStream= new FileOutputStream("src/main/resources/database.xlsx");
            workbook.write(outputStream);
            outputStream.close();
        }
        catch(Exception e){
            System.out.println(e);
        }



    }

    // Updates the inventory when a new order is placed and it is based on the products purchased and the number of items
    public static void updateInventory(User user){
        XSSFWorkbook workbook= DatabaseOperations.databaseConnection();
        XSSFSheet sheet=workbook.getSheetAt(1);

        for(int i=0;i<user.rowData.size();i++){
           XSSFRow currentRow=sheet.getRow(user.rowData.get(i));
           XSSFCell currentCell=currentRow.getCell(2);

           // Updates the inventory based on the order placed
           double noOfItems=currentCell.getNumericCellValue()-user.itemsInCart.get(i+1);
           currentCell.setCellValue(noOfItems);

        }

        try{
            FileOutputStream outputStream = new FileOutputStream("src/main/resources/database.xlsx");
            workbook.write(outputStream);
            outputStream.close();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }



}
