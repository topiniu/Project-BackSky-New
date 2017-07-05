package com.pb.imguploader;
import java.io.*;
import java.util.*;
import java.awt.image.*;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Color;


public class UntilClass {
    
    public static void main(String[] args) throws Exception {
//        File input = new File("t.png");
        
        
    }
    public String saveImg(File input,String savePath) throws Exception{
    	System.out.println(">>file name  " + input.getName());
        
        if(input.getName().endsWith("png"))
        {
            BufferedImage bufferedImage = ImageIO.read(input);
            
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage,0,0,Color.WHITE,null);
            File newJpg = new File(input.getName().split("\\.")[0] + ".jpg");
            ImageIO.write(newBufferedImage,"jpg",newJpg);
            
            
            
            return new UntilClass().compress(newJpg,newJpg.getName(),input.length(),savePath);
        }else{
            return new UntilClass().compress(input,null,input.length(),savePath);
        }
    }
    
    private String compress(File input,String newJpgName,long fullSize,String savePath) throws Exception{
        if(input == null)
            return null;
        
//        double inputSize = input.length();

		Properties prop = new Properties();
		ClassLoader loader = this.getClass().getClassLoader();
		
		prop.load(loader.getResourceAsStream("default.properties"));
		
        
        InputStream is = new FileInputStream(input);
        
        BufferedImage image = ImageIO.read(is);
        
        File compressedImageFile = new File(savePath + "/" + input.getName().split("\\.")[0]+".jpg");
        
        if(!compressedImageFile.exists()){
        	compressedImageFile.getParentFile().mkdirs();
        	System.out.println(">> Create dir successed");
        }
        
        OutputStream os =new FileOutputStream(compressedImageFile);
        
        ImageWriter jpgWriter =  (ImageWriter) ImageIO.getImageWritersByFormatName("jpg").next();
        
        
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        
        
        
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(Float.parseFloat(prop.getProperty("_COMPRESS_LEVEL")));
        
        
        jpgWriter.setOutput(ios);
        
        
        
        jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);
        
        System.out.println("COMPRESSED length = " + compressedImageFile.length());
        System.out.println("full size = " + fullSize);
        System.out.println(compressedImageFile.length() > fullSize);
        
//        if(compressedImageFile.length() > fullSize){
//        	System.out.println(">>> Go to re compress");
//        	compress(compressedImageFile, newJpgName, fullSize);
//        }
        
        if(newJpgName != null)
        	new File(newJpgName).delete();
        
        System.out.println(">>>Saved");
        
        os.close();
        ios.close();
        
        System.out.println(">>> compressed file Canonicalpath= " + compressedImageFile.getCanonicalPath() +
        		"\n>>> compressed file path= " + compressedImageFile.getPath());
        
        return "http://" + prop.getProperty("_DOMAIN") + ":" + prop.getProperty("_PORT")  + "/" + prop.getProperty("_PROJECTNAME") + prop.getProperty("_COMPRESSED_UPLOADIMGFOLDER") + compressedImageFile.getName();
        
    }
}
