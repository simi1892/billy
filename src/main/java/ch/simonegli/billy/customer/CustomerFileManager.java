package ch.simonegli.billy.customer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerFileManager {
    
    private static final String FILE_PATH = "customers.json";
    private final ObjectMapper objectMapper;
    
    public CustomerFileManager() {
        objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter(); // Enable pretty printing
    }
    
    public List<Customer> readCustomers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try {
            TypeReference<List<Customer>> typeRef = new TypeReference<List<Customer>>() {};
            return objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            // If file doesn't exist or other IO error, return empty list
            System.err.println("Error reading customers from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public void writeCustomers(List<Customer> customers) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                      .writeValue(new File(FILE_PATH), customers);
        } catch (IOException e) {
            System.err.println("Error writing customers to file: " + e.getMessage());
        }
    }
}