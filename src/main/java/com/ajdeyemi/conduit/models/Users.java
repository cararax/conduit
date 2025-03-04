package com.ajdeyemi.conduit.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String username;
    private String password;
    // this is an enum. The value is by default stored as a number 
    // in the database if it is stored as a list
     @Enumerated(EnumType.STRING)
    private List<Roles> role;

    public Users() {
    }


    public Users(String email, String username, String password, List<Roles> role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public Users(long id, String email, String username, String password, List<Roles> role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Users(long id, String email, String username,  List<Roles> role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Roles> getRole() {
        return this.role;
    }

    public void setRole(List<Roles> role) {
        this.role = role;
    }

    public Users id(long id) {
        setId(id);
        return this;
    }

    public Users email(String email) {
        setEmail(email);
        return this;
    }

    public Users username(String username) {
        setUsername(username);
        return this;
    }

    public Users password(String password) {
        setPassword(password);
        return this;
    }

    public Users role(List<Roles> role) {
        setRole(role);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Users)) {
            return false;
        }
        Users users = (Users) o;
        return id == users.id && Objects.equals(email, users.email) && Objects.equals(username, users.username) && Objects.equals(password, users.password) && Objects.equals(role, users.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, role);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", email='" + getEmail() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }


 

}

