package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
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
 * Servlet implementation class DataModifyServlet
 */
@WebServlet("/DataModifyServlet")
public class DataModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataModifyServlet() {
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
        
        List<String> data=JsonParser.getDataModifyData(result);
        String nickname=data.get(0);
		String sex=data.get(1);
		String school=data.get(2);
		String department=data.get(3);
		String major=data.get(4);
		String phone=data.get(5);
		String qq=data.get(6);
		String weixin=data.get(7);
		String username=data.get(8);
		User user=new User(nickname,sex,school,department,major,phone,qq,weixin);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String msg="true";
		try {
			UserDB.updateUser(user,username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.print(msg);
		out.flush();
		out.close();
	}

}
