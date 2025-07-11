package ch.simonegli.billy.bill;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Ride {

    private LocalDate date;
    private BigDecimal distance;
    private BigDecimal pricePerKm;

    public Ride() {}

    public Ride(LocalDate date, BigDecimal distance, BigDecimal pricePerKm) {
        this.date = date;
        this.distance = distance;
        this.pricePerKm = pricePerKm;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(BigDecimal pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public BigDecimal getTotal() {
        return distance.multiply(pricePerKm);
    }
}
