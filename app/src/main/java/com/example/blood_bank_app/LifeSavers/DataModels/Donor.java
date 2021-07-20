package com.example.blood_bank_app.LifeSavers.DataModels;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Donor {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("city")
    @Expose
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}