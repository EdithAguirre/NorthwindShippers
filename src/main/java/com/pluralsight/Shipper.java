package com.pluralsight;

public class Shipper {
    private String companyName, phone;
    private int shipperId;

    public Shipper(int shipperId, String companyName, String phone) {
        this.shipperId = shipperId;
        this.companyName = companyName;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-20s %s",shipperId, companyName, phone);
    }
}
