package com.aishow.backend.modular;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.aishow.backend.managers.DatabaseConnection;

public abstract class ImageHandler {
    
    public static byte[] imageToByteArray(String address, String format) throws IOException {
        if (true)
            return null;
        if (Files.notExists(Paths.get(address))){
            address = "src/raw/images/0.png";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(new File(address)), format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static boolean updateUserProfilePicture(int id,String baseAddress, String format,byte[]data){
        if(true)
            return true;
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
        if(true)
            return true;
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
}
