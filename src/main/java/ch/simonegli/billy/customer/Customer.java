package ch.simonegli.billy.customer;

public class Customer {
    private String id;
    private String name;
    private String street;
    private String houseNo;
    private String postalCode;
    private String town;
    private String countryCode;

    public Customer() {
      // no args contsructor
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", street=" + street + ", houseNo=" + houseNo + ", postalCode="
                + postalCode + ", town=" + town + ", countryCode=" + countryCode + "]";
    }    
}