package com.calendlygui;

import com.calendlygui.model.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class CalendlyApplication extends Application {
    public static User user = null;
    public static String token; // Lưu token xác thực
    public static Socket client;

    public static PrintWriter out;
    public static BufferedReader in;
    public static ObjectInputStream inObject;
    public static ObjectOutputStream outObject;

    
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CalendlyApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Lỗi khi tải giao diện: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void shutdown() {
        try {
            if (inObject != null) inObject.close();
            if (outObject != null) outObject.close();
            if (out != null) out.close();
            if (in != null) in.close();
            if (!client.isClosed()) client.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
        }
    }

    public static String generateToken() {
        return UUID.randomUUID().toString(); 
    }
}
