package de.buw.se4de;
import java.util.*;
import java.util.ArrayList;

public class EcommerceApp {



    public static void main(String args []){


        System.out.println("*****************************************");
        System.out.println("Welcome to the Weimar- Ecommerce(Ocean of electronic devices) Application !! \n\n We are here to serve all your electronic needs " );
        System.out.println("*****************************************");
        System.out.println("Enter '1' if you are a customer or '2' if you are delivery agent or '3' if you are a manager : ");
        Scanner scan= new Scanner(System.in);
        int typeOfUser=scan.nextInt();
        switch(typeOfUser){

            /* Existing customers will be asked to login and the new customers will be asked to register a new account */

           //  Customers belong to category 1, delivery agents belong to category 2 and managers belong to category 3
            case 1:

                System.out.println("Enter '1' if you are an existing customer or else enter '2' : ");
                int typeOfCustomer=scan.nextInt();
                if (typeOfCustomer==1){
                    Login login=new Login();
                    login.collectUserCredentials(1);
                }

                else{
                    Registration register= new Registration();
                    register.registerNewUser();
                }
                System.out.println("*****************************************");
                break;

                // Allows the delivery agents to login
            case 2:

                System.out.println("Welcome to the delivery agents' portal !! \n\n You can use this portal for updating the delivery status !");
                Login login=new Login();
                login.collectUserCredentials(2);
                System.out.println("*****************************************");
                break;

                // Allows the manager to login
            case 3:

                System.out.println("Welcome to the Manager portal !! \n\n ");
                Login managerLogin=new Login();
                managerLogin.collectUserCredentials(3);
                System.out.println("*****************************************");
                break;

            default:
                System.out.println("Sorry!! The selected option is not valid !! Please try again ");

        }



    }


}
