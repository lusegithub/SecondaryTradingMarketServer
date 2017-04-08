package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.supplyorbuy;
import DB.supplyorbuyDB;
import json.JsonParser;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class SendServlet
 */
@WebServlet("/SendServlet")
public class SendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendServlet() {
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
        JSONObject jsonobject=JsonParser.getSend(result);
        System.out.println(result);
        String username=jsonobject.getString("username");
        String type=jsonobject.getString("type");
        String description=jsonobject.getString("description");
        String nickname=jsonobject.getString("nickname");
        if(type.equals("求购")){
        	supplyorbuy buy=new supplyorbuy(username,description,nickname);
        	try {
				supplyorbuyDB.buy(buy);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else if(type.equals("出售")){
        	supplyorbuy supply=new supplyorbuy(username,description,nickname);
        	try {
				supplyorbuyDB.supply(supply);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String msg="true";
        out.print(msg);
		out.flush();
		out.close();
	}

}
