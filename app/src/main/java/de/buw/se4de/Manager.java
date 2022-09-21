package de.buw.se4de;

import java.util.*;

public class Manager {
    ArrayList l = new ArrayList();
    int items;
    public void managerOperations() {
        System.out.println("Enter 1 if you want to see inventory or 2 if you want to add items to inventory");
        Scanner scan = new Scanner(System.in);
        int select = scan.nextInt();
        switch (select) {
            case 1:
                System.out.println("Items present in Inventory");
                DatabaseOperations.listInventoryStock();  // Lists all the items and their details available in the inventory
                System.out.println("*****************************************");
                break;
            case 2:
                System.out.println("Select number of items to be stored");
                this.items = scan.nextInt();
                System.out.println("*****************************************");
                // Collects the product details to be updated in the inventory
                for (int i = 1; i <= items; i++) {
                    System.out.println("Enter name of the Item");
                    this.l.add(scan.next());
                    System.out.println("Enter the productID : ");
                    this.l.add(scan.nextInt());
                    System.out.println("Enter Quantity of the item");
                    this.l.add(scan.nextInt());
                    System.out.println("Enter price of the Single Item");
                    this.l.add(scan.nextDouble());
                    System.out.println("Enter supplier of the item");
                    this.l.add(scan.next());
                    System.out.println("*****************************************");
                }
              DatabaseOperations.addingNewItems(this.l);  // This method is used to insert new items to the inventory
                System.out.println("*****************************************");
            }
            }
        }
