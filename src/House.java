public class House {
    private String address;
    private double price;

    public House(String address, double price) {
        this.address = address;
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public double getPrice() {
        return price;
    }

    public String getDetails() {
        return "Address: " + address + ", Price: " + price;
    }
}