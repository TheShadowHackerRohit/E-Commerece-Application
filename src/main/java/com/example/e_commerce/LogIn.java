package com.example.e_commerce;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogIn {

    private static  byte[] getSha(String input){

        try {

            MessageDigest md  = MessageDigest.getInstance("SHA-256");
            return  md.digest(input.getBytes(StandardCharsets.UTF_8));

        } catch(Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    private  static String getEncryptedPassword(String password){
        try {
            BigInteger num = new BigInteger(1,getSha(password));
            StringBuilder hexString = new StringBuilder(num.toString(16));
            // 16 => hexadecimal    8 => octal number
            return  hexString.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }


    public static Customer customerLogIn(String userEmail, String password) {
        //select * from customers where email = 'rohit@gmail.com' and password = 'abc';
        String encryptedPass = getEncryptedPassword(password);

       // System.out.println(encryptedPass); abc = ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad


        String query = "select * from customers where email = '" + userEmail + "' and password = '" + encryptedPass + "'";
        DataBaseConnection dbConn = new DataBaseConnection();

        try {
            ResultSet rs = dbConn.getQueryTable(query);


            if(rs != null && rs.next()){

                return new Customer(
                        rs.getInt("cid"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return  null;
    }

//    public static void main(String[] args) throws SQLException {
////        System.out.println(customerLogIn("rohit@gmail.com" , "abc"));
////        System.out.println(customerLogIn("rohita@gmail.com" , "ABC"));
//
//        //System.out.println(getEncryptedPassword("Rohit"));
//        //System.out.println(getEncryptedPassword("angad"));
//        System.out.println(getEncryptedPassword("abc"));
//
//
//    }
}
