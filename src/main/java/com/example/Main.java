package src.main.java.com.example;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Product> inventory = new ArrayList<>();

    public static ArrayList<Product> getInventory() {
        return inventory;
    }

    public static void setInventory(ArrayList<Product> inventory) {
        Main.inventory = inventory;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View Inventory");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Handle menu choices
            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    updateProduct(scanner);
                    break;
                case 3:
                    deleteProduct(scanner);
                    break;
                case 4:
                    viewInventory();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addProduct(Scanner scanner) {
        try {
            System.out.print("Enter Product ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine();

            //Validate price
            double price = 0;
            while (true) {
                try {
                    System.out.print("Enter Product Price: $");
                    price = Double.parseDouble(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price. Please enter a valid number.");
                }
            }

            //Validate quantity
            int quantity = 0;
            while (true) {
                try {
                    System.out.print("Enter Product Quantity: ");
                    quantity = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity. Please enter a valid integer.");
                }
            }

            Product product = new Product(id, name, price, quantity);
            inventory.add(product);
            System.out.println("Product added successfully.");
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private static void updateProduct(Scanner scanner) {
        try {
            System.out.print("Enter Product ID to update: ");
            String id = scanner.nextLine();
            for (Product product : inventory) {
                if (product.getId().equals(id)) {
                    System.out.print("Enter new Product Name: ");
                    String name = scanner.nextLine();

                    //Validate price
                    double price = 0;
                    while (true) {
                        try {
                            System.out.print("Enter new Product Price: $");
                            price = Double.parseDouble(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid price. Please enter a valid number.");
                        }
                    }

                    //Validate quantity
                    int quantity = 0;
                    while (true) {
                        try {
                            System.out.print("Enter new Product Quantity: ");
                            quantity = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid quantity. Please enter a valid integer.");
                        }
                    }

                    product.setName(name);
                    product.setPrice(price);
                    product.setQuantity(quantity);
                    System.out.println("Product updated successfully.");
                    return;
                }
            }
            System.out.println("Product not found.");
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Enter Product ID to delete: ");
        String id = scanner.nextLine();
        for (Product product : inventory) {
            if (product.getId().equals(id)) {
                String name = product.getName();
                inventory.remove(product);
                System.out.println(name + " deleted successfully.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private static void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Product product : inventory) {
                System.out.println(product);
            }
        }
    }
}
