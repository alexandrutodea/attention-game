package com.example.p3finalproject.model;

import com.example.p3finalproject.exceptions.EmptyFieldsException;
import com.example.p3finalproject.exceptions.InvalidPasswordException;
import com.example.p3finalproject.exceptions.InvalidUsernameException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player implements Identifiable {
    @Id @GeneratedValue
    private int id;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean admin;
    @OneToMany(mappedBy = "player", orphanRemoval = true)
    List<Result> results;

    public Player() {

    }

    public Player(String username, String password, boolean admin) throws InvalidUsernameException, InvalidPasswordException, EmptyFieldsException {

        if (username.length() < 1 || password.length() < 1) {
            throw new EmptyFieldsException();
        } else if (!username.matches("[A-z0-9]{5,}")) {
            throw new InvalidUsernameException();
        } else if (!password.matches("[A-z0-9]{5,}")) {
            throw new InvalidPasswordException();
        }

        this.username = username;
        this.password = password;
        this.admin = admin;
        this.results = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
    }

    public void addResult(Result result) {
        this.results.add(result);
    }

    public String getMaskedPassword() {
        return "*".repeat(password.length());
    }

    @Override
    public String getIdentifier() {
        return getUsername();
    }

    @Override
    public String toString() {
        return "User{" +
               "username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", admin=" + admin +
               '}';
    }
}
