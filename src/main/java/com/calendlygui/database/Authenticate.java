package com.calendlygui.database;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Objects;

import static com.calendlygui.constant.ConstantValue.*;
import static com.calendlygui.utils.Helper.createResponseWithUser;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.time.Instant;

public class Authenticate {

    private static final SecretKey secretKey = new SecretKeySpec("1233213123123123".getBytes(), "AES");



    // Method to generate a token
    public static String gen_token(String email) {
        try {
            // Create a payload with email and timestamps
            String payload = "{"
                    + "\"email\":\"" + email + "\","
                    + "\"issuedAt\":\"" + Instant.now().toString() + "\","
                    + "\"expiry\":\"" + Instant.now().plusSeconds(360000).toString() + "\""
                    + "}";

            // Encrypt the payload using AES
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedPayload = cipher.doFinal(payload.getBytes());

            // Encode the encrypted payload to Base64 for safe transmission
            return Base64.getEncoder().encodeToString(encryptedPayload);
        } catch (Exception e) {
            throw new RuntimeException("Error generating token: " + e.getMessage(), e);
        }
    }

    // Method to verify a token
    public static void verify_token(String token) {
        try {
            // Decode the Base64 token
            byte[] decodedToken = Base64.getDecoder().decode(token);

            // Decrypt the token using AES
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedPayload = cipher.doFinal(decodedToken);

            // Return the decrypted payload as a string
           
        } catch (Exception e) {
            System.out.println("token: " + e.getMessage());
           
        }
    }

    
    public static String register(String email, String name, String password, boolean gender, boolean isTeacher) {
        Connection conn = SqlConnection.connect();
        String insertUserQuery = "insert into users(name,email,gender, is_teacher) values (? ,?, ?, ?) returning register_datetime";
        String insertLoginQuery = "insert into login(email, password) values (? ,?)";
        Timestamp registerDatetime = null;
        // Update users table
        try {
            PreparedStatement insertUserPs = conn.prepareStatement(insertUserQuery);
            insertUserPs.setString(1, name);
            insertUserPs.setString(2, email);
            insertUserPs.setBoolean(3, gender);
            insertUserPs.setBoolean(4, isTeacher);
            ResultSet insertUserRs = insertUserPs.executeQuery();
            while (insertUserRs.next()) {
                registerDatetime = insertUserRs.getTimestamp("register_datetime");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            if (e.getSQLState().equals("23505")) {
                return String.valueOf(ACCOUNT_EXIST);
            }
            return String.valueOf(SQL_ERROR);
        }


        // Update login table
        try {
            PreparedStatement insertLoginPs = conn.prepareStatement(insertLoginQuery);
            String hash = BCrypt.hashpw(password, BCrypt.gensalt());
            insertLoginPs.setString(1, email);
            insertLoginPs.setString(2, hash);
            insertLoginPs.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return String.valueOf(SQL_ERROR);
        }

        String getIdQuery = "select * from " + USERS + " where " + EMAIL + " = ?";
        int id = 0;
        try {
            PreparedStatement getIdPs = conn.prepareStatement(getIdQuery);
            getIdPs.setString(1, email);
            ResultSet getIdRs = getIdPs.executeQuery();
            while (getIdRs.next()){
                id = getIdRs.getInt(ID);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return String.valueOf(SQL_ERROR);
        }


        return createResponseWithUser(
        		CREATE_SUCCESS,
                id,
                name,
                email,
                registerDatetime.toString(),
                String.valueOf(isTeacher),
                String.valueOf(gender));
    }

    public static String signIn(String email, String password) {
        Connection conn = SqlConnection.connect();
        String query = "select password from login where email = ?";
        String query2 = "select * from users where email = ?";
        String hash = null;
        int id = 0;
        String username = null;
        boolean gender = false, isTeacher = false;
        Timestamp registerDatetime = null;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hash = rs.getString("password");
            }
            if (Objects.equals(hash, null)) {
                return String.valueOf(ACCOUNT_NOT_EXIST);
            }
        } catch (SQLException e) {
            return String.valueOf(SQL_ERROR);
        }
        if (BCrypt.checkpw(password, hash)) {
            try {
                PreparedStatement ps2 = conn.prepareStatement(query2);
                ps2.setString(1, email);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    id = rs2.getInt(ID);
                    username = rs2.getString(NAME);
                    gender = rs2.getBoolean(GENDER);
                    isTeacher = rs2.getBoolean(IS_TEACHER);
                    registerDatetime = rs2.getTimestamp(REGISTER_DATETIME);
                }
            } catch (SQLException e) {
                return String.valueOf(SQL_ERROR);
            }
        } else {
            return String.valueOf(INVALID_PASSWORD);
        }
        return createResponseWithUser(AUTHENTICATE_SUCCESS, id, username, email, registerDatetime.toString(), String.valueOf(isTeacher), String.valueOf(gender));
    }

    public static void main(String[] args) {
        // Example email to generate a token
        String email = "user@example.com";
        
        // Generate a token for the email
        String token = gen_token(email);
        System.out.println("Generated Token: " + token);
        
        // Verify the generated token
        String decryptedPayload = verify_token(token);
        System.out.println("Decrypted Payload: " + decryptedPayload);
    }
}