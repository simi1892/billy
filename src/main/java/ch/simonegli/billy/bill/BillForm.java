package ch.simonegli.billy.bill;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BillForm {
    private UUID customerId;
    private List<Ride> rides = new ArrayList<>();
    
    public BillForm() {}
    
    public BillForm(UUID customerId, List<Ride> rides) {
        this.customerId = customerId;
        this.rides = rides;
    }
    
    public UUID getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
    
    public List<Ride> getRides() {
        return rides;
    }
    
    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }
}
