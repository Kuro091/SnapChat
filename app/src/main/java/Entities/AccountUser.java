package Entities;

import java.io.Serializable;
import java.util.Date;

public class AccountUser implements Serializable{
    private String id;
    private String name;
    private Date dob;
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean actived;

    public AccountUser(){}

    public AccountUser(String id,String name, Date dob, String username, String password, String email, String phone, boolean actived) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.actived = actived;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActived() {
        return actived;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }
}
