/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class NewPfFinalProject {
    public static File USER_FILE = new File("E:\\users.txt");

    public static void user(){
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        while (!loggedIn) {
        try {
                System.out.println("\n1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                    continue; // Restart the loop
                }
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        registerUser();
                        break;
                    case 2:
                        loggedIn = loginUser();
                        if(loggedIn){
                            System.out.println("Logged in. Welcome to the store");
                            productdetails();
                        }
                        break;
                    case 3:
                        System.out.println("Exiting. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select 1, 2, or 3.");
                }
            }catch (Exception e) {
                System.err.println("An error occurred: " + e.toString());
            }
           
        } 
    }
    
    private static void registerUser() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE, true))) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            writer.println(username + "," + password);
            System.out.println("Registration successful.");
        }
    }

    private static boolean loginUser() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    System.out.println("Login successful.");
                    return true;
                }
            }
            System.out.println("Invalid credentials. Please try again.");
        }
        return false;
    }
    public static void productdetails(){
        Scanner input = new Scanner(System.in);

        while (true) {
            int numberOfProductsInFile = getNumberOfProductsInFile("E:\\products.txt");
            String[][] dataLoadedFromFile = null;

            try {
                FileInputStream fis = new FileInputStream("E:\\products.txt");
                Scanner fileInput = new Scanner(fis);
                dataLoadedFromFile = new String[numberOfProductsInFile][5];

                for (int i = 0; i < numberOfProductsInFile; i++) {
                    dataLoadedFromFile[i][0] = fileInput.nextLine();
                    dataLoadedFromFile[i][1] = fileInput.nextLine();
                    dataLoadedFromFile[i][2] = fileInput.nextLine();
                    dataLoadedFromFile[i][3] = fileInput.nextLine();
                    dataLoadedFromFile[i][4] = fileInput.nextLine();
                }
                fis.close();
                fileInput.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            // for dispalying the whole product categories and products
            /*for (int i = 0; i < numberOfProductsInFile; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(dataLoadedFromFile[i][j] + "\t");
                }
                System.out.println("");
            }*/

            String[] category = new String[numberOfProductsInFile];
            int numberOfUniqueCategories = 0;

            for (int i = 0; i < numberOfProductsInFile; i++) {
                if (!Arrays.asList(category).contains(dataLoadedFromFile[i][0])) {
                    category[numberOfUniqueCategories] = dataLoadedFromFile[i][0];
                    numberOfUniqueCategories++;
                }
            }

            System.out.println("All available categories are: ");
            for (int i = 0; i < numberOfUniqueCategories; i++) {
                System.out.println(i + 1 + ". " + category[i]);
            }

            int categoryIndex;
            
            while (true) {         
                System.out.print("Enter your choice as index: ");
                categoryIndex = input.nextInt() - 1;
                if (categoryIndex >= 0 && categoryIndex < numberOfUniqueCategories) {
                    break;
                }
                System.out.println("Invalid Category.");
            }
            
            System.out.println("All products in " + category[categoryIndex] + " are:");
            for (int i = 0; i < numberOfProductsInFile; i++) {
                if (dataLoadedFromFile[i][0].equalsIgnoreCase(category[categoryIndex])) {
                    System.out.println(dataLoadedFromFile[i][1] + ". " + dataLoadedFromFile[i][2]);
                }
            }

            String productId = null;
            boolean found = false;
            while (!found) {
                System.out.println("Enter ID of product: ");
                productId = input.next();
                for (int j = 0; j < numberOfProductsInFile; j++) {
                    if (dataLoadedFromFile[j][1].equalsIgnoreCase(productId)) {
                        System.out.println("Details of selected products are: ");
                        System.out.println("Category: " + dataLoadedFromFile[j][0]);
                        System.out.println("Id: " + dataLoadedFromFile[j][1]);
                        System.out.println("Product: " + dataLoadedFromFile[j][2]);
                        System.out.println("Description: " + dataLoadedFromFile[j][3]);
                        System.out.println("Price: " + dataLoadedFromFile[j][4]);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Invalid ID.");
                }
            }

            List<String[]> cart = new ArrayList<>();
            while (true) {
                System.out.println("1. Add to Cart.");
                System.out.println("2. View Cart");
                System.out.println("3. Checkout");
                System.out.println("4. Go Back.");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        addToCart(dataLoadedFromFile, productId, cart, input);
                        break;
                    case 2:
                        viewCart(cart);
                        break;
                    case 3:
                        checkout(cart);
                        break;
                    case 4:
                        break;
                    case 5:
                        System.out.println("Exiting program. Goodbye!");
                        System.exit(0); // Terminate the program
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
                if (choice == 4) {
                    break;
                }
            }
        }
    }
    private static int getNumberOfProductsInFile(String fileName) {
        try (FileReader fr = new FileReader(fileName)) {
            int numberOfProductsInFile = countLines(fr);
            return numberOfProductsInFile / 5; // Assuming each product has 5 lines
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static int countLines(FileReader fr) throws IOException {
        int lineCount = 0;
        int i;
        boolean lastWasNewLine = true;

        while ((i = fr.read()) != -1) {
            if ((char) i == '\n') {
                lineCount++;
                lastWasNewLine = true;
            } else {
                lastWasNewLine = false;
            }
        }

        // Account for the last line if it wasn't terminated by a newline
        if (!lastWasNewLine) {
            lineCount++;
        }

        return lineCount;
    }

    private static void addToCart(String[][] dataLoadedFromFile, String productId, List<String[]> cart, Scanner input) {
        boolean found = false;
        for (String[] product : dataLoadedFromFile) {
            if (product[1].equalsIgnoreCase(productId)) {
                found = true;
                System.out.print("Enter the quantity for product '" + product[2] + "': ");
                int quantity = input.nextInt();
                if (quantity <= 0) {
                    System.out.println("Invalid quantity. Please enter a positive number.");
                    return;
                }

                // Check if the product already exists in the cart
                boolean productExistsInCart = false;
                for (String[] cartItem : cart) {
                    if (cartItem[1].equalsIgnoreCase(productId)) {
                        int currentQuantity = Integer.parseInt(cartItem[5]);
                        cartItem[5] = String.valueOf(currentQuantity + quantity); // Update the quantity
                        productExistsInCart = true;
                        break;
                    }
                }

                // If the product is not in the cart, add it
                if (!productExistsInCart) {
                    String[] cartItem = Arrays.copyOf(product, product.length + 1);
                    cartItem[5] = String.valueOf(quantity); // Adding quantity as the last element
                    cart.add(cartItem); // Add the new array to the cart
                }

                System.out.println("Product added to cart.");
                break; // Exit loop after adding product to cart
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }

    private static void viewCart(List<String[]> cart) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("Products in your cart:");
        System.out.println("---------------------------------");
        for (String[] item : cart) {
            System.out.println("Category: " + item[0] + ",\n Id: " + item[1] + ",\n Product: " + item[2] + ",\n  Description: " + item[3] + ",\n Price: " + item[4] + ",\n  Quantity: " + item[5]);
        }
    }

    private static void checkout(List<String[]> cart) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Add products before checkout.");
            return;
        }
        double totalAmount = 0;
        System.out.println("Checking out the following products:");
        System.out.println("------------------------------------------");
        for (String[] item : cart) {
            System.out.println("Category: " + item[0] + ",\n Id: " + item[1] + ",\n Product: " + item[2] + ",\n Description: " + item[3] + ",\n Price: " + item[4] + ", \n Quantity: " + item[5]);
            totalAmount += Double.parseDouble(item[4]) * Integer.parseInt(item[5]); // Price * Quantity
        }
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("----------------------------------------");
        System.out.println("Thank you for your purchase!");
        System.out.println("----------------------------------------");
        cart.clear();
    }
    public static void managerlogin(){
        String Password = "manager123";
        Scanner input = new Scanner(System.in);
        int count = 0;
        while(count<3){
        System.out.println("Hello manager");
        System.out.println("Enter your password: ");
        String pass = input.next();
        if(pass.equalsIgnoreCase(Password)){
            System.out.println("welcome");
            manager();
            break;
        }else{
            System.out.println("Invalid Password");
            count++;
            if(count == 3){
                break;
            }
        }}
        }
        
    public static void manager(){
        while(true){
        try{


        System.out.println("What you want to do");
        System.out.println("1.Add products");
        System.out.println("2.Modify price of the product");
        System.out.println("3.Delete product");
        System.out.println("4.Exit");
        Scanner input = new Scanner(System.in);
        int managerchoice = input.nextInt();
        switch (managerchoice){
            case 1:
            addproducts();
            break;
            case 2:
            modifyproduct();
            break;
            case 3:
            deleteproduct();
            break;
            case 4:
            System.out.println("Exiting store");
            System.exit(0);
            break;
            default:
            System.out.println("Invalid choice");
            input.close();
        }}catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
        public static void addproducts(){
            try{
                Scanner input = new Scanner(System.in);
                FileOutputStream fos = new FileOutputStream("E:\\products.txt",true);
                PrintStream ps = new PrintStream(fos);
                System.out.println("Enter category: ");
                String categorie = input.next();
                System.out.println("Enter id: ");
                String id = input.next();
                System.out.println("Enter name: ");
                String name = input.next();
                System.out.println("Enter description: ");
                String des = input.next();
                System.out.println("Enter price: ");
                int price = input.nextInt();
                ps.println(categorie);
                ps.println(id);
                ps.println(name);
                ps.println(des);
                ps.println(price);
            }catch(Exception e){
                System.out.println(e.toString());
            }
            while(true){
                try{
                System.out.println("1.Display products");
                System.out.println("2.Modify price of the product");
                System.out.println("3.Delete product");
                System.out.println("4.Exit");
                Scanner input = new Scanner(System.in);
                int managerchoice = input.nextInt();
                switch (managerchoice){
                    case 1:
                    productdetails();
                    break;
                    case 2:
                    modifyproduct();
                    break;
                    case 3:
                    deleteproduct();
                    break;
                    case 4:
                    System.out.println("Exiting store");
                    System.exit(0);
                    break;
                    default:
                    System.out.println("Invalid choice");
                    input.close();
                }}catch(Exception e){
                    System.out.println(e.toString());
                }
            }
        }
        public static void deleteproduct(){
            Scanner input = new Scanner(System.in);
            ArrayList<String> content = new ArrayList<>();
            System.out.println("Enter the id of product you want to delete");
            String st = input.nextLine();
            try {
                BufferedReader bf = new BufferedReader(new FileReader("E:\\products.txt"));
                String line;
                while((line = bf.readLine()) !=null ){
                    content.add(line);
                }
                bf.close();
            } catch (Exception e) {
                
            }
            
            boolean check = false;
    
    
            for(int i = 0;i<content.size();i++){
                
                if(content.get(i).equals(st)){
                    check = true;
                    content.remove(i);
                    for(int j = 0;j<4;j++){
                        content.remove(i-1);
                    }
                    
                    break;
                }
            }
            if(check){
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\products.txt"));
                for(String lines: content){
                    bw.write(lines);
                    bw.newLine();
                }bw.close();
            } catch (Exception e) {
                System.out.println(e.toString());
                
            }
            System.out.println("Product deleted successfully");
            while(true){
                try{
                System.out.println("1.Display products");
                System.out.println("2.Add product");
                System.out.println("3.Modify price of the product");
                System.out.println("4.Exit");
                Scanner sc = new Scanner(System.in);
                int managerchoice = sc.nextInt();
                switch (managerchoice){
                    case 1:
                    productdetails();
                    break;
                    case 2:
                    addproducts();
                    break;
                    case 3:
                    modifyproduct();
                    break;
                    case 4:
                    System.out.println("Exiting store");
                    System.exit(0);
                    break;
                    default:
                    System.out.println("Invalid choice");
                    input.close();
                }}catch(Exception e){
                    System.out.println(e.toString());
                }
            }
        }
            else{
                System.out.println("product not found");
            }
            //System.out.println("Product deleted successfully");
            
        } 
        
        public static void modifyproduct(){
            Scanner input = new Scanner(System.in);
            ArrayList<String>content = new ArrayList<>();
            System.out.println("Enter product id to modify: ");
            String str = input.next();
            try{
                BufferedReader br = new BufferedReader(new FileReader("E:\\products.txt"));
                String line;
                while((line = br.readLine()) != null){
                    content.add(line);
                }br.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
            boolean check = false;
            for(int i=0;i<content.size();i++){
                if(content.get(i).equals(str)){
                    check = true;
                    System.out.println("Current price is " + content.get(i+3));
                    System.out.println("Enter new price: ");
                    String newPrice = input.next();
                    content.set(i+3, newPrice);
                    System.out.println("Price Updated");
                    break;
                }
    
            }
            if(check){
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\products.txt"));
                    for(int i=0;i<content.size();i++){
                        String stri = content.get(i);
                        bw.write(stri);
                        bw.newLine();
                    }
                    bw.close();
                    while(true){
                        try{
                        System.out.println("1.Display products");
                        System.out.println("2.Add products");
                        System.out.println("3.Delete product");
                        System.out.println("4.Exit");
                        Scanner inp= new Scanner(System.in);
                        int managerchoice = inp.nextInt();
                        switch (managerchoice){
                            case 1:
                            productdetails();
                            break;
                            case 2:
                            addproducts();
                            break;
                            case 3:
                            deleteproduct();
                            break;
                            case 4:
                            System.out.println("Exiting store");
                            System.exit(0);
                            break;
                            default:
                            System.out.println("Invalid choice");
                            input.close();
                        }}catch(Exception e){
                            System.out.println(e.toString());
                        }
                    }
                
                }catch(Exception e){
                    System.out.println(e.toString());
                }
            }
        }  
    

    
    public static void main(String[]args){
        System.out.println("Welcome to the Virtual store");
        System.out.println("1.User");
        System.out.println("2.Manager");
        System.out.println("3.Exit");
        while(true){
        try{
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter your choice: ");
        int choice = input.nextInt();
        if(choice == 1){
            user();
        }else if(choice == 2){
            managerlogin();
        }else if(choice == 3){
            System.exit(0);
        }else{
            System.out.println(" Invalid choice");
        }
    
    }catch(Exception e){
        System.out.println(e.toString());
        System.out.println("Input must be a number");
    }               
    }
    }
}