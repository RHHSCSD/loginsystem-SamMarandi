/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loginsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael.roy-diclemen
 */
public class LoginSystem {

    /**
     * @param args the command line arguments
     */
    File f = new File(User.getFileName());
    File passwordsFile = new File("dictbadpass.txt");
    
    

    public static void main(String[] args) {
        // TODO code application logic here
        
        LoginSystem loginSystem = new LoginSystem();
        
        User userOne = new User("", "", "", "", "", "", "");
        userOne.setSalt(loginSystem.salt());
        RegisterJFrame registerUserOne = new RegisterJFrame(userOne, loginSystem);
        registerUserOne.setVisible(true);
        LoginJFrame loginJFrame = new LoginJFrame(userOne, loginSystem);
        loginJFrame.setVisible(true);
        loginSystem.loadUser();



    }
    /**
     * Takes in a user object, creates a printwriter,
     * checks if the line if full and moves the the line
     * under if needed, then it prints the values of the user onto the file
     * @param user 
     */
    public void saveUser(User user){
        

    try (PrintWriter pw = new PrintWriter(new FileWriter(f, true))){
        
        // read if line is empty if not add a line and then print these
        Scanner myReader = new Scanner(f);
        if ((myReader.hasNextLine())) {
            pw.println("");
        }
        pw.print(user.getUsername() + user.getDelimeter());
        pw.print(user.getPassword() + user.getDelimeter());
        pw.print(user.getName() + user.getDelimeter());
        pw.print(user.getAge() + user.getDelimeter());
        pw.print(user.getEmail() + user.getDelimeter());
        pw.print(user.getBirthLocation() + user.getDelimeter());
        pw.print(user.getAddress() + user.getDelimeter());
        pw.flush();
        pw.close();
          } catch (IOException ex) {
        System.out.println("Error");
    }
    }
    /**
     * Loads all of a user's data from a file onto an array.
     * 
     * @return An ArrayList containing User objects loaded from the file.
     */
    public ArrayList<User> loadUser() {
        ArrayList<User> users = new ArrayList<>();
        try (Scanner myReader = new Scanner(f)) {
            while (myReader.hasNextLine()) {
                String[] userData = myReader.nextLine().split(User.getDelimeter());
                User user = new User(userData[0], userData[1], userData[2], userData[3],
                        userData[4], userData[5], userData[6]);
                users.add(user);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
        }
        return users;
    }

    /**
     * Checks if a username is unique among existing users.
     * 
     * @param name  The username to check for uniqueness.
     * @param users The list of existing users.
     * @return      True if the username is unique, false otherwise.
     */
    public boolean isUniqueName(String name, ArrayList<User> users) {
        for (User currentUser : users) {
            if (name.equals(currentUser.getUsername())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a password meets the strength criteria.
     * Checks to ensure password doesn't match file of weak passwords
     * 
     * @param password The password to check for strength.
     * @return         True if the password is strong, false otherwise.
     */
    public boolean isPasswordStrong(String password) {
        if (password.length() < 5) {
            return false;
        }
        try (Scanner myReader = new Scanner(passwordsFile)) {
            while (myReader.hasNextLine()) {
                if (password.equals(myReader.nextLine())) {
                    return false;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
        }
        return true;
    }

    /**
     * Registers a new user.
     * Checks to ensure username is unique and password is strong
     * @param user The user object to be registered.
     */
    public void register(User user) {
        if (isUniqueName(user.getUsername(), loadUser())) {
            System.out.println("Username is unique");
            if (isPasswordStrong(user.getPassword())) {
                System.out.println("Password is strong");
                String saltedPassword = user.getPassword() + salt();
                user.setPassword(strengthenPassword(saltedPassword));
                saveUser(user);
            } else {
                System.out.println("Password is not strong");
            }
        } else {
            System.out.println("Username is not unique");
        }
    }

    /**
     * Generates a salt for password hashing.
     * 
     * @return The generated salt.
     */
    public String salt() {
        byte[] byteArray = new byte[5];
        new Random().nextBytes(byteArray);
        return new String(byteArray, Charset.forName("UTF-8"));
    }

    /**
     * Applies password strengthening (hashing) to a password.
     * 
     * @param password The password to be strengthened.
     * @return         The strengthened (hashed) password.
     */
    public String strengthenPassword(String password) {
        StringBuilder encryptedPassword = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] byteData = md.digest();
            for (byte aByteData : byteData) {
                encryptedPassword.append(Integer.toHexString((aByteData & 0xFF) | 0x100).substring(1, 3));
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encryptedPassword.toString();
    }
}