package lk.ijse.controller.model;

public class Package {
    private String packageId;
    private String name;
    private String type;
    private double price;


    public Package() {
    }


    public Package(String packageId, String name, String type, double price) {
        this.packageId = packageId;
        this.name = name;
        this.type = type;
        this.price = price;
    }


    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Package{" +
                "packageId='" + packageId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
