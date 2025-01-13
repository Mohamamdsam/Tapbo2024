public class LuxuryHouse extends House {
    private String luxuryFeature;

    public LuxuryHouse(String address, double price, String luxuryFeature) {
        super(address, price);
        this.luxuryFeature = luxuryFeature;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + ", Luxury Feature: " + luxuryFeature;
    }
}