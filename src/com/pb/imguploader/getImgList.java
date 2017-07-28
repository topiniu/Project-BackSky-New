package com.pb.imguploader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class getImgList
 */
@WebServlet("/getImgList")
public class getImgList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getImgList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();

		Properties prop = new Properties();
		ClassLoader loader = this.getClass().getClassLoader();
		
		prop.load(loader.getResourceAsStream("default.properties"));
		
		
		FileTest ft = new FileTest();

		String saveImgListPath = getServletContext().getRealPath(prop.getProperty("_IMGLISTFILENAME"));
		File q = new File(saveImgListPath);
		if(!q.exists()){
			q.getParentFile().mkdirs();
			out.print("");
			return;
		}
		
		try {
			ArrayList<Map> m = ft.getDataN(saveImgListPath);
//			for(Map l: m){
//				System.out.println("full= " + l.get("_fullPath"));
//				System.out.println("com= " + l.get("_compressedPath"));
//			}
			Gson gson = new Gson();
			out.print(gson.toJson(m));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
