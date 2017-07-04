package com.pb.imguploader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.util.Properties;


/**
 * Servlet implementation class UploadImg
 */
@WebServlet("/uploadimg")
@MultipartConfig
public class UploadImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Fang wen cheng gong");

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");

		Properties prop = new Properties();
		ClassLoader loader = this.getClass().getClassLoader();
		
		prop.load(loader.getResourceAsStream("default.properties"));
		
		
		
		String description = request.getParameter("description");
		Part filePart = request.getPart("file");
		String fileName = " ";
		try{
			fileName = getSubmittedFileName(filePart);
		}catch(NullPointerException e){
			e.printStackTrace();
			fileName = filePart.getName();
		}
		
		InputStream inputStream = filePart.getInputStream();
		
		File uploads = new File(prop.getProperty("_FULL_UPLOADIMGFOLDER"));
		System.out.println(prop.getProperty("_FULL_UPLOADIMGFOLDER"));
		System.out.println(fileName);
		
		File file = new File(uploads,fileName);
		
		if(!file.exists()){
			file.getParentFile().mkdirs();
//			file.createNewFile();
		}
		
		InputStream input = inputStream;
		try{
			Files.copy(input, file.toPath());
		}catch(FileAlreadyExistsException e){
			file.delete();
			Files.copy(input, file.toPath());
			System.out.println(">>> Re creare file");
		}
		
		UntilClass uc = new UntilClass();
		try {
			uc.saveImg(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private static String getSubmittedFileName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
}
