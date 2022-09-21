package de.buw.se4de;
import java.sql.SQLOutput;
import java.util.*;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Login {

    public String username="";
    public String password="";
    public String databasePath="src/main/resources/database.xlsx";

    public String sessionID="";
    Scanner scan= new Scanner(System.in);

// This method is used to validate the credentials of the user
    
    public void validateCredentials(String username,String password,int categoryOfUser){

try {
    FileInputStream inputStream = new FileInputStream(databasePath);
    XSSFWorkbook workbook = new XSSFWorkbook(inputStream); // Creating the workbook object
    XSSFSheet sheet=workbook.getSheetAt(0);    // Selecting specific sheet in the workbook

    int noOfRows=sheet.getLastRowNum();


    for(int i=1;i<=noOfRows;i++){

        XSSFRow row=sheet.getRow(i);
        XSSFCell cell=row.getCell(0);


        if(cell.getStringCellValue().equalsIgnoreCase(username) ){ // Compares the entered username with the username column in the database
            XSSFCell pass=row.getCell(5);
            XSSFCell role=row.getCell(7);

            // Checks whether the password and the user category matches
            if(pass.getStringCellValue().equals(password) && role.getNumericCellValue()==categoryOfUser){
                System.out.println("Login Successfull !!");
                System.out.println("*****************************************");
                User user=new User(this.username,this.password,i);

                // Different methods are called based on the category of user

                if(categoryOfUser==1){
                    Customer customer=new Customer();
                    customer.customerService(user);
                    break;
                }
                else if(categoryOfUser==2){
                    deliveryAgent delivery= new deliveryAgent();
                    delivery.orderToBeDelivered();
                    break;
                }
                else if(categoryOfUser==3){
                    Manager manager= new Manager();
                    manager.managerOperations();
                    break;

                }

            }
           // System.out.println("*****************************************");
            else{
                System.out.println("Username or Password is not valid !! Please try again ");
            }
            //System.out.println("*****************************************");
        }

    }

}
catch (Exception e){
    System.out.println(e);
}



    }

// This method collects user credentials
    public void collectUserCredentials(int categoryOfUser){

        System.out.println("Please enter your username : \n");
        this.username=scan.next();
        System.out.println("                                                   ");
        System.out.println("Please enter your password : ");
        this.password=scan.next();



        this.validateCredentials(this.username,this.password,categoryOfUser);
    }
}
