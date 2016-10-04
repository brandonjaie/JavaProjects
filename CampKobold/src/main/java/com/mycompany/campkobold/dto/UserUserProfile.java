/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dto;

import com.mycompany.campkobold.dao.UserDao;
import java.util.ArrayList;
import java.util.Objects;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Brandon
 */
public class UserUserProfile {

    private int userId;

    @NotEmpty(message = "You must supply a value for  Username.")
    @Length(max = 50, message = "Username must be no longer to 50 characters in length.")
    private String userName;

    @NotEmpty(message = "You must enter a password.")
    @Length(min = 6, max = 50, message = "Your password can be a minimum of 6 characters and a maximum of 25.")
    private String password;

    private String confirmPassword;
    private ArrayList<String> authorities = new ArrayList<>();
    private int enabled;
    private Authority authority;
    private String isAdmin;
    private String isEmployee;

    @NotEmpty(message = "You must supply a value for First Name.")
    @Length(max = 50, message = "First Name must be no longer to 50 characters in length.")
    private String firstName;

    @NotEmpty(message = "You must supply a value for Last Name.")
    @Length(max = 50, message = "Last Name must be no longer to 50 characters in length.")
    private String lastName;

    @NotEmpty(message = "You must supply a value for Email.")
    @Email(message = "Please enter a valid email address.")
    @Length(max = 50, message = "Email must be no more than 50 characters in length.")
    private String email;

    @NotEmpty(message = "You must supply a value for Phone.")
    @Length(max = 12, message = "Phone must be no more than 10 characters in length.")
    private String phone;

    private boolean available;

    public UserUserProfile(int userId,
            String userName,
            String password,
            ArrayList<String> authorities,
            int enabled,
            String firstName,
            String lastName,
            String email,
            String phone) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = email;

    }

    public UserUserProfile() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        String cPassword = getPassword();
        return cPassword;
    }

    public String getConfirmation() {

        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public ArrayList<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<String> authorities) {
        this.authorities = authorities;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public void addAuthority(String authority) {
        authorities.add(authority);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(String isEmployee) {
        this.isEmployee = isEmployee;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.userId;
        hash = 79 * hash + Objects.hashCode(this.userName);
        hash = 79 * hash + Objects.hashCode(this.password);
        hash = 79 * hash + Objects.hashCode(this.confirmPassword);
        hash = 79 * hash + Objects.hashCode(this.authorities);
        hash = 79 * hash + this.enabled;
        hash = 79 * hash + Objects.hashCode(this.authority);
        hash = 79 * hash + Objects.hashCode(this.isAdmin);
        hash = 79 * hash + Objects.hashCode(this.isEmployee);
        hash = 79 * hash + Objects.hashCode(this.firstName);
        hash = 79 * hash + Objects.hashCode(this.lastName);
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.phone);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserUserProfile other = (UserUserProfile) obj;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.enabled != other.enabled) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.confirmPassword, other.confirmPassword)) {
            return false;
        }
        if (!Objects.equals(this.isAdmin, other.isAdmin)) {
            return false;
        }
        if (!Objects.equals(this.isEmployee, other.isEmployee)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.authorities, other.authorities)) {
            return false;
        }
        if (!Objects.equals(this.authority, other.authority)) {
            return false;
        }
        return true;
    }

}
