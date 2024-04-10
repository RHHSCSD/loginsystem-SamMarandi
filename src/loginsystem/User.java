/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author bmara
 */
public class User {
    
    private final static String delimeter = ",";
    private static String fileName = "UserFileLoginSystem.txt";
    private String name;
    private String age;
    private String birthLocation;
    private String email;
    private String address;
    private String username;
    private String password;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    private String salt;
    public User(String username, String password, String name, 
            String age, String birthLocation, String email
            ,String address){
        
        this.username = username;
        this.password = password;
        this.name =name;
        this.age = age;
        this.email = email;
        this.birthLocation = birthLocation;
        this.address = address;
    }
    public void register(){
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthLocation() {
        return birthLocation;
    }

    public void setBirthLocation(String birthLocation) {
        this.birthLocation = birthLocation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public static String getDelimeter() {
        return delimeter;
    }

    public static String getFileName() {
        return fileName;
    }
    
    
}

