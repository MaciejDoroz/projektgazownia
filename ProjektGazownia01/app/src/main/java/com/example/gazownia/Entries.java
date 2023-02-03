package com.example.gazownia;

public class Entries {
    private String name;
    private String surname;
    private String pesel;
    private String adres;
    private String entry;
    private String date;

    public Entries(){}

    public Entries(String name, String surname, String pesel, String adres, String entry, String date){
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.adres = adres;
        this.entry = entry;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
