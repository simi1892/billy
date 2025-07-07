package ch.simonegli.billy.customer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomerFileManager {
    private static final String FILE_PATH = "customers.json";
    private final Gson gson;

    public CustomerFileManager() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public List<Customer> readCustomers() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type customerListType = new TypeToken<ArrayList<Customer>>() {}.getType();
            return gson.fromJson(reader, customerListType);
        } catch (IOException e) {
            // If file doesn't exist or other IO error, return empty list
            return new ArrayList<>();
        }
    }

    public void writeCustomers(List<Customer> customers) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(customers, writer);
        } catch (IOException e) {
            System.err.println("Error writing customers to file: " + e.getMessage());
        }
    }
}
