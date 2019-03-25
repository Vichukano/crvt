package ru.vichukano.crvt_test.Model;

import java.util.Objects;

/**
 * Entity of item.
 */
public class Item {
    private long id;
    private String phone;
    private String fio;
    private String company;
    private String email;

    public Item() {

    }

    public Item(String phone, String fio, String company, String email) {
        this.phone = phone;
        this.fio = fio;
        this.company = company;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(phone, item.phone)
                && Objects.equals(fio, item.fio)
                && Objects.equals(company, item.company)
                && Objects.equals(email, item.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, fio, company, email);
    }

    @Override
    public String toString() {
        return String.format(
                "Item: "
                        + "id=%d, "
                        + "phone=%s, "
                        + "fio=%s, "
                        + "company=%s, "
                        + "email=%s",
                id, phone, fio, company, email
        );
    }
}
