package ru.vichukano.crvt_test.Model;

public class Item {
    private long id;
    private String phone;
    private String fio;
    private String company;

    public Item() {

    }

    public Item(String phone, String fio, String company) {
        this.phone = phone;
        this.fio = fio;
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
