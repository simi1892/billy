package ch.simonegli.billy.bill;

import ch.simonegli.billy.customer.Customer;

import java.util.List;

public class Bill {

    private Customer customer;
    private List<Ride> rides;

    public Bill(Customer customer, List<Ride> rides) {
        this.customer = customer;
        this.rides = rides;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public java.math.BigDecimal getTotal() {
        return rides.stream()
                .map(Ride::getTotal)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

    private byte[] qrBillPng;

    public byte[] getQrBillPng() {
        return qrBillPng;
    }

    public void setQrBillPng(byte[] qrBillPng) {
        this.qrBillPng = qrBillPng;
    }
}
