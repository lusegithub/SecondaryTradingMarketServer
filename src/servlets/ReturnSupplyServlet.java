package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.supplyorbuy;
import DB.supplyorbuyDB;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ReturnSupplyServlet
 */
@WebServlet("/ReturnSupplyServlet")
public class ReturnSupplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnSupplyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String get=request.getParameter("get");
		List<supplyorbuy> supply = null;
        try {
			supply=supplyorbuyDB.supply();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        List<JSONObject> jsonArray=new ArrayList<JSONObject>();
        for(int i=0;i<supply.size();i++){
        	JSONObject json=new JSONObject();
        	json.put("username", supply.get(i).getUserid());
        	json.put("description", supply.get(i).getDescriptions());
        	json.put("nickname", supply.get(i).getNickname());
        	jsonArray.add(json);
        }
        String data=URLEncoder.encode(jsonArray.toString(),"UTF-8");
        
        response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		out.print(data);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
