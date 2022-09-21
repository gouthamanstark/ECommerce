package de.buw.se4de;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;

public class User {
String username="";
String password="";


int row=0;

ArrayList<Integer> itemsInCart=new ArrayList<Integer>(); // Stores the items that are ordered by the user
ArrayList<Double> overAllPrice=new ArrayList<Double>(); // Price of individual items will be stored
ArrayList<Integer> rowData=new ArrayList<>();



    public User(String username,String password,int row){
        this.username=username;
        this.password=password;
        this.row=row;


    }
}
