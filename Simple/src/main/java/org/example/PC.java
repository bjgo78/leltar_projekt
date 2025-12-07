package org.example;

public class PC {
    private int id;
    private String brand;
    private String version;
    private String ownerName;

    public PC(int id, String brand, String version, String ownerName) {
        this.id = id;
        this.brand = brand;
        this.version = version;
        this.ownerName = ownerName;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getVersion() {
        return version;
    }

    public String getOwnerName() {
        return ownerName;
    }
}