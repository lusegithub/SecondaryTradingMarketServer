package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.User;
import DB.UserDB;
import json.JsonParser;

/**
 * Servlet implementation class PasswordModifyServlet
 */
@WebServlet("/PasswordModifyServlet")
public class PasswordModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		StringBuffer buffer = new StringBuffer();
        String line = null;
        try
        {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8")); 
            //BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String result=URLDecoder.decode(buffer.toString(),"UTF-8");
        //String result = new String(buffer.toString().getBytes("ISO-8859-1"),"UTF-8");
        //System.out.println(result);
        
        
        List<String> data=JsonParser.getPasswordModifyData(result);
        String username=data.get(0);
        String ans=data.get(1);
        String oldpassword=data.get(2);
        String newpassword=data.get(3);
        User user=new User(username,oldpassword);
        
        response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String msg="false";
        try {
			if(UserDB.updateNewUserPassword(user,oldpassword,ans,newpassword)){
				msg="true";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.print(msg);
		out.flush();
		out.close();
	}

}
