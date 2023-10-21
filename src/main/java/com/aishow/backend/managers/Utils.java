package com.aishow.backend.managers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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


    public static int sumOfArray(ArrayList<Integer> array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    public static Time stringToTime (String s){
        if (s.length() == 5){
            s+= ":00";
        }
        var x = s.split(":");
        Time toReturn = new Time(Integer.parseInt(x[0]), Integer.parseInt(x[1]), Integer.parseInt(x[2]));
        return toReturn;
    }

    public static Object runMethodReflection(Object holder, String methodName, Class[] paramTypes, Object[] params){
        Method toRun = null;
        Object toReturn = null;
        try{
            try {
                toRun = holder.getClass().getMethod(methodName, paramTypes);
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                toReturn = toRun.invoke(holder, params);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception ex){
            System.out.println("Deu erro na invocação");
        }
        return toReturn;
    }

}
