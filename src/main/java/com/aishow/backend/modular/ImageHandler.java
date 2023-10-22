package com.aishow.backend.modular;

import com.aishow.backend.data.DatabaseConnection;
import com.aishow.backend.data.StatementPreparer;
import com.aishow.backend.handlers.appinteraction.PathfindHandler;
//Azure
import com.azure.core.credential.*;
import com.azure.core.util.BinaryData;
import com.azure.identity.*;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.azure.storage.blob.specialized.*;
import com.azure.storage.common.*;
import com.sun.jna.platform.unix.X11.XClientMessageEvent.Data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import org.springframework.core.io.ClassPathResource;

public abstract class ImageHandler {
    private static BlobServiceClient blobServiceClient;

    public static void GetBlobServiceClientTokenCredential() {
		TokenCredential credential = new DefaultAzureCredentialBuilder().build();
	
		// Azure SDK client builders accept the credential as a parameter
		// TODO: Replace <storage-account-name> with your actual storage account name
		blobServiceClient = new BlobServiceClientBuilder()
				.endpoint("https://aishow.blob.core.windows.net/")
				.credential(credential)
				.buildClient();
	}

	public static boolean uploadBlob(String path, byte[] bytes) throws IOException{
		BlockBlobClient blockBlobClient = blobServiceClient
			.getBlobContainerClient("images")
			.getBlobClient(path)
			.getBlockBlobClient();
		System.out.println(blockBlobClient.getBlobUrl());
		BinaryData data = BinaryData.fromBytes(bytes);
		System.out.println(data.getLength());
        blockBlobClient.upload(data);
		return true;
	}

    public static boolean deleteBlob(String path){
        BlobClient blockBlobClient = blobServiceClient
			.getBlobContainerClient("images")
			.getBlobClient(path);
            return blockBlobClient.deleteIfExists();
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

    public static boolean updateUserProfilePicture(int id, String format,byte[]data){
        try{
        String time = Long.toString(System.currentTimeMillis());
        String name = String.valueOf(id)+time+"."+format;
        String x = new PathfindHandler().getUserBaseUrl(id);

        if (updateUserImageUrl(id,name)){
            uploadBlob(name, data);
            if (!x.equals("0.png"))
                deleteBlob(x);
            return true;
        }

        return false;
        } catch (IOException | SQLException ex){
            System.out.println("Exceção update imagem");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean updateServicePicture(int id, String format,byte[]data){
        try{
        String time = Long.toString(System.currentTimeMillis());
        String name = String.valueOf(id)+time+"."+format;
        String x = new PathfindHandler().getServiceBaseUrl(id);

        if (updateServiceImageUrl(id, name)){
            uploadBlob("services/"+name, data);
            if (!x.equals("0.png"))
                deleteBlob("services/"+x);
            return true;
        }

        return false;
        } catch (IOException | SQLException ex){
            System.out.println("Exceção update imagem");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean updateUserImageUrl(int id, String newUrl){
        try {
            PreparedStatement st = StatementPreparer.updateUserImageUrl(DatabaseConnection.getConnection(), id, newUrl);
            return DatabaseConnection.runUpdate(st) == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean updateServiceImageUrl(int id, String newUrl){
        try {
            PreparedStatement st = StatementPreparer.updateTemplateImageUrl(DatabaseConnection.getConnection(), id, newUrl);
            return DatabaseConnection.runUpdate(st) == 1;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
