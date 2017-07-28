package com.pb.imguploader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileTest {

//	Properties prop = new Properties();
//	ClassLoader loader = this.getClass().getClassLoader();
	
	
	
	
	public void writeData(String path, String data)  throws Exception{
		File f = new File(path);
		if(!f.exists()){
//			f.getParentFile().mkdirs();
			f.createNewFile();
		}
		
		FileWriter fw = new FileWriter(f,true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(data);
		bw.newLine();
		
		bw.flush();
		bw.close();
		fw.close();
		
		System.out.println("数据已保存");
		
		
	}
	
	
	public Map<String, String> getData(String path) throws Exception{
		Map<String, String> result = new HashMap<String, String>();
		
//		prop.load(loader.getResourceAsStream("default.properties"));

		File f = new File(path);
		String dataString = "";
		if(!f.exists()){
			return null;
		}else{
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			while((line=br.readLine())!=null){
//				System.out.println("line=   " + line);
				result.put("fullsize",line.split("&")[0]);
				result.put("compressedSize",line.split("&")[1]);
			}
			
			br.close();
			fr.close();
		}
		
		return result;
	}
	public ArrayList<Map> getDataN(String path) throws Exception{
		ArrayList<Map> str = new ArrayList<Map>();

		File f = new File(path);
		String dataString = "";
		Map<String,String> m = new HashMap<String, String>();
		if(!f.exists()){
			return null;
		}else{
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			while((line=br.readLine())!=null){
//				System.out.println(line.equals(" "));
				m.put("_fullPath", line.split("&")[0]);
				m.put("_compressedPath", line.split("&")[1]);
				str.add(m);
			}
			
			br.close();
			fr.close();
		}
		
		return str;
	}
}
