package com.aishow.backend.managers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.STRING;

import com.sun.net.httpserver.HttpExchange;

public abstract class Utils {
    public static HashMap<String,byte[]> pages = new HashMap<String,byte[]>();
    public static void init () throws IOException{
        pages.put("Index", Files.readAllBytes(Paths.get("src/raw/dist/index.html")));
        pages.put("Main", Files.readAllBytes(Paths.get("src/raw/dist/main.js")));
        pages.put("Runtime", Files.readAllBytes(Paths.get("src/raw/dist/runtime.js")));;
        pages.put("Polyfills", Files.readAllBytes(Paths.get("src/raw/dist/polyfills.js")));
        pages.put("Styles", Files.readAllBytes(Paths.get("src/raw/dist/styles.css")));
        pages.put("Favicon", Files.readAllBytes(Paths.get("src/raw/dist/favicon.ico")));
        var x = Files.readAllLines((Paths.get("src/raw/info.txt")));
        //Scanner input = new Scanner(System.in);
        //ipAddress = input.nextLine();
        //input.close();
    }
    
    public static byte[] imageToByteArray(String address, String format) throws IOException {
        if (Files.notExists(Paths.get(address))){
            address = "src/raw/images/0.png";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(new File(address)), format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static boolean updateUserProfilePicture(int id,String baseAddress, String format,byte[]data){
        try{
        String time = Long.toString(System.currentTimeMillis());
        BufferedImage bImage2 = ImageIO.read(new ByteArrayInputStream(data));
        //BufferedImage bImage2 = ImageIO.read(bis);
        String name = baseAddress+time+"."+format;
        var x = DatabaseConnection.getUserImageUrl(id);

        if (DatabaseConnection.tryToUpdateUserImageUrl(id, String.valueOf(id)+String.valueOf(time)+"."+format)){
            var f = new File(name);
            System.out.println("USER");
            System.out.println(name);
            System.out.println("EXISTE: "+f.exists());
            System.out.println("Is Dir: "+f.isDirectory());
            System.out.println("Is File: " +f.isFile());
            System.out.println("Can write: "+f.canWrite());
            System.out.println("Can execute: "+f.canExecute());
            System.out.println("\n\n");
            ImageIO.write(bImage2, format, f);
            //UserConnectionManager.getInformation(host).setImageUrl(String.valueOf(baseAddress.charAt(baseAddress.length()-1))+time+"."+format);
            new File("src/raw/images/"+x).delete();
            return true;
        }

        return false;
        } catch (IOException ex){
            System.out.println("Exceção update imagem");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean updateServicePicture(String address, String format,byte[]data){
        try{
        String time = Long.toString(System.currentTimeMillis());
        BufferedImage bImage2 = ImageIO.read(new ByteArrayInputStream(data));
        //BufferedImage bImage2 = ImageIO.read(bis);
        String name = address+time+"."+format;
        var y = address.split("/");
        int id = Integer.parseInt(y[y.length-1]);
        var x = DatabaseConnection.getServiceImageUrl(id);

        if (DatabaseConnection.tryToUpdateServiceImageUrl(id, String.valueOf(id)+String.valueOf(time)+"."+format)){
            var f = new File(name);
            System.out.println("SERVICE");
            System.out.println(name);
            System.out.println("EXISTE: "+f.exists());
            System.out.println("Is Dir: "+f.isDirectory());
            System.out.println("Is File: " +f.isFile());
            System.out.println("Can write: "+f.canWrite());
            System.out.println("Can execute: "+f.canExecute());
            System.out.println("ID: "+id);
            ImageIO.write(bImage2, format, f);
            new File("src/raw/images/services/"+x).delete();
            return true;
        }

        return false;
        } catch (IOException ex){
            System.out.println("Exceção update imagem");
            System.out.println(ex.getMessage());
            return false;
        }
    }


    public static int sumOfArray(ArrayList<Integer> array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    public static Time stringToTime (String s){
        var x = s.split(":");
        Time toReturn = new Time(Integer.parseInt(x[0]), Integer.parseInt(x[1]), Integer.parseInt(x[2]));
        return toReturn;
    }

}
