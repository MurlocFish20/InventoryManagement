package src.test.java.com.example;

import src.main.java.com.example.Main;
import src.main.java.com.example.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MainTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @BeforeEach
    public void setUp() {
        Main.setInventory(new ArrayList<>());
    }

    @Test
    public void testAddProduct() {
        String input = "1\nP001\nProduct1\n10.5\n5\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Main.main(new String[]{});

        assertTrue(Main.getInventory().size() == 1);
        assertEquals("P001", Main.getInventory().get(0).getId());
        assertEquals("Product1", Main.getInventory().get(0).getName());
        assertEquals(10.5, Main.getInventory().get(0).getPrice());
        assertEquals(5, Main.getInventory().get(0).getQuantity());
    }

    @Test
    public void testUpdateProduct() {
        Main.getInventory().add(new Product("P001", "Product1", 10.5, 5));

        String input = "2\nP001\nUpdatedProduct\n20.0\n10\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Main.main(new String[]{});

        assertTrue(Main.getInventory().size() == 1);
        assertEquals("P001", Main.getInventory().get(0).getId());
        assertEquals("UpdatedProduct", Main.getInventory().get(0).getName());
        assertEquals(20.0, Main.getInventory().get(0).getPrice());
        assertEquals(10, Main.getInventory().get(0).getQuantity());
    }

    @Test
    public void testDeleteProduct() {
        Main.getInventory().add(new Product("P001", "Product1", 10.5, 5));

        String input = "3\nP001\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Main.main(new String[]{});

        assertTrue(Main.getInventory().isEmpty());
    }

    @Test
    public void testViewgetInventory() {
        Main.getInventory().add(new Product("P001", "Product1", 10.5, 5));
        Main.getInventory().add(new Product("P002", "Product2", 20.0, 10));

        String input = "4\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Product [id= P001, name= Product1, price= $10.5, quantity= 5]"));
        assertTrue(output.contains("Product [id= P002, name= Product2, price= $20.0, quantity= 10]"));
    }

    @Test
    public void testInvalidChoice() {
        String input = "6\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Invalid choice. Please try again."));
    }

    @Test
    public void testAddProductInvalidPrice() {
        String input = "1\nP001\nProduct1\ninvalid\n10.5\n5\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Invalid price. Please enter a valid number."));
    }

    @Test
    public void testAddProductInvalidQuantity() {
        String input = "1\nP001\nProduct1\n10.5\ninvalid\n5\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Invalid quantity. Please enter a valid integer."));
    }
}