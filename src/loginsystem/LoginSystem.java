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
        
        User userOne = new User("", "", "", "", "", "", "", "");
        final String actualSalt = loginSystem.salt();
        userOne.setSalt(actualSalt);
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
        pw.print(user.getSalt() + user.getDelimeter());
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
    public ArrayList<User> loadUser(){
        Scanner myReader;
        ArrayList<User> users = new ArrayList<>();
        try {
            myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String[] userData = myReader.nextLine().split(User.getDelimeter());
                User user = new User(userData[0], userData[1], userData[2], userData[3],
                userData[4], userData[5], userData[6], userData[7]);
                users.add(user);              
            }
            myReader.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        return users;
    }
    
    /**
     * Iterates through a list of users and ensures username entered is unique
     * @param name
     * @param users
     * @return 
     */
    public boolean isUniqueName(String name, ArrayList<User> users){
        for (User currentUser: users){
            if (name.equals(currentUser.getUsername())){
                return false;
            }
        }
        return true;
    }
    /**
     * Takes a password and ensures it is strong
     * by ensuring it doesn't match weak passwords
     * and is at least 5 characters
     * @param password
     * @return 
     */
    public boolean isPasswordStrong(String password){
        if (password.length() < 5){
            return false;
        }
        try {
            Scanner myReader = new Scanner(passwordsFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (password.equals(data)){
                    return false;
                }
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");;
        }
        return true;
    }
    /**
     * Takes in a user and if the user has unique charateristics
     * then the user is generated in the file
     * @param user 
     */
    public void register(User user){
        
        if (isUniqueName(user.getUsername(), loadUser()) == true){
            if (isPasswordStrong(user.getPassword())){
                System.out.println("User is generated");
            String password = user.getPassword() + user.getSalt();
            user.setPassword(strengthenPassword(password));
            saveUser(user);
        }
        else {
            System.out.println("password is not unqiue");
        }
        }
        else {
            System.out.println("Username is not unqiue");
        }
        
    }
    /**
     * Create a random string to behave like a salt for
     * encryption
     * @return 
     */
    public String salt(){
        String salt;
        byte[] byteArray = new byte[5];
        Random random = new Random();
        random.nextBytes(byteArray);
 
        salt = new String(byteArray, Charset.forName("UTF-8"));
        return salt;
    }
    
    /**
     * Takes in a password and encrypts it
     * @param password
     * @return 
     */
    public String strengthenPassword(String password){
        String encrypedPassword="";
        try {
            //password to be encrypted
            
            //java helper class to perform encryption
            MessageDigest md = MessageDigest.getInstance("MD5");
            //give the helper function the password
            md.update(password.getBytes());
            //perform the encryption
            byte byteData[] = md.digest();
            //To express the byte data as a hexadecimal number (the normal way)
            
            for (int i = 0; i < byteData.length; ++i) {
                encrypedPassword += (Integer.toHexString((byteData[i] & 0xFF) |
                        0x100).substring(1,3));
            }   } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

        return encrypedPassword;
        
    }
}