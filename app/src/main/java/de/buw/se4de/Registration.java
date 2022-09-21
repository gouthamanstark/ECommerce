package de.buw.se4de;
import java.util.Scanner;
import java.lang.Math;
public class Registration {

    public String firstName = "";

    public String lastName = "";
    public String username = "";
    public long phoneNumber = 0;
    public int postalCode = 0;
    public String password="";
    public String address="";
    Scanner scan = new Scanner(System.in);

// Allows the new user to create an account
    public void registerNewUser() {
        int availability=0;
        System.out.println("Hello New User !! Welcome to Weimar - E-Commerce !!");
        System.out.println("Please enter the below details for creating your new account !");
        while(availability==0){
            System.out.print("Enter you desired username : ");
            this.username=scan.next();
            System.out.println("*****************************************");
            // Checks whether the username is available
            if(DatabaseOperations.usernameAvailabilityCheck(this.username)==false){
                System.out.println("The username you have entered is already taken !! Please try again !");
            }
            else{ // Accepts the username if it's unique
                availability=1;
                System.out.println("Congratulations! This username is available !");
            }
        }
        System.out.println("Enter your first name : ");
        this.firstName=scan.next();
        System.out.println("Enter your last name : ");
        this.lastName=scan.next();
        System.out.println("*****************************************");

        // Accepts the phone number and checks whether it's a 10 digit number
        while(availability==1){
            System.out.println("Enter your Phone Number : ");
            this.phoneNumber=scan.nextLong();
            this.phoneNumber=Math.abs(this.phoneNumber);
            if(String.valueOf(this.phoneNumber).length()!=10 ){
                System.out.println("This is not valid ! Please enter a valid 10-digit phone number !");
            }
            else{
                availability+=1;
            }
        }
        System.out.println("*****************************************");
        // Accepts the postal code and confirms it to be a 5 digit number
        while(availability==2){
            System.out.println("Enter your postal code : ");
            this.postalCode=scan.nextInt();
            if(String.valueOf(this.postalCode).length()!=5){
                System.out.println("Entered postal code is not valid !! Please enter a valid Postal Code ");
            }
            else{
                availability+=1;
            }
        }
        System.out.println("*****************************************");

        System.out.println("Enter your address : ");
        this.address=scan.next();
        System.out.println("*****************************************");
        System.out.println("Enter your password: ");
        this.password=scan.next();
        System.out.println("*****************************************");
DatabaseOperations.registerNewUser(this); // Registers the new user
    }
}


