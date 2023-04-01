package com.project.CarRental2.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileImpl implements UploadFile {
	private final static int LENGTH_MAX=7;

	@Override
	public  String uploadSingleFile(MultipartFile file) {
		String fileName="";
		Path path= Paths.get("uploads/");
		try {
			InputStream inputStream= file.getInputStream();
			Date date= new Date();
			 SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMddhhmmss");  
			String d= formatter.format(date);

			fileName= file.getOriginalFilename().replaceAll(file.getOriginalFilename().trim(), d);
			System.err.println(fileName);
			 String alphanumericCharacters = "0123456789abcdefghijklmnopqrstuv";
			 
			    StringBuffer randomString = new StringBuffer(LENGTH_MAX);
			    Random random = new Random();
			 
			    for (int i = 0; i < LENGTH_MAX; i++) {
			        int randomIndex = random.nextInt(alphanumericCharacters.length());
			        char randomChar = alphanumericCharacters.charAt(randomIndex);
			        randomString.append(randomChar);
			    }
			    fileName= fileName+randomString+".jpg";
			    System.err.println(fileName);
			Files.copy(inputStream, path.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}


}
