package DB;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class supplyorbuyDB {
	public static Connection conn=null;
	public static Statement st=null;
	public static Connection getConnection() throws SQLException{
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}
		catch(ClassNotFoundException e){
			System.out.println("找不到驱动程序类，加载驱动失败");
			e.printStackTrace();
		}
		String url="jdbc:sqlserver://localhost:1433;databaseName=secondarytradingmarketDB";
		String username="sa";
		String password="83467";
		Connection conn=DriverManager.getConnection(url,username,password);
		return conn;
	}
	public static void supply(supplyorbuy supply) throws SQLException{
		conn=getConnection();
		String sql="insert into supply(userid,descriptions,nickname) values(?,?,?)";
		//String sql1="insert into picture values(?,?)";
		PreparedStatement ps=conn.prepareStatement(sql);
		//PreparedStatement ps1=conn.prepareStatement(sql1);
		try{
			
			ps.setString(1, supply.getUserid());
			ps.setString(2, supply.getDescriptions());
			ps.setString(3, supply.getNickname());
			ps.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}
		finally{
			ps.close();
			conn.close();
		}
	}
	public static  void buy(supplyorbuy buy) throws SQLException{
		conn=getConnection();
		String sql="insert into buy(userid,descriptions,nickname) values(?,?,?)";
		PreparedStatement ps=conn.prepareStatement(sql);
		try{
			
			ps.setString(1, buy.getUserid());
			ps.setString(2, buy.getDescriptions());
			ps.setString(3, buy.getNickname());
			ps.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}
		finally{
			ps.close();
			conn.close();
		}
	}
	public static  List<supplyorbuy> buy() throws SQLException{
		conn=getConnection();
		ResultSet rs=null;
		String sql="select number,userid,descriptions,nickname from buy order by number desc";
		try{
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			int i=0;
			supplyorbuy[] buy=new supplyorbuy[100];
			for(i=0;i<100;i++){
				buy[i]=new supplyorbuy();
			}
			int j=0;
			while(rs.next()){
				buy[j].setIdentity(rs.getInt("number"));
				buy[j].setUserid(rs.getString("userid"));
				buy[j].setDescriptions(rs.getString("descriptions"));
				buy[j].setNickname(rs.getString("nickname"));
				j++;
			}
			List<supplyorbuy> buyList=new ArrayList<supplyorbuy>();
			for(int x=0;x<j;x++){
				buyList.add(buy[x]);
			}
			return buyList;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			conn.close();
		}
		return null;
	}
	public static  List<supplyorbuy> supply() throws SQLException{
		conn=getConnection();
		ResultSet rs=null;
		String sql="select number,userid,descriptions,nickname from supply order by number desc";
		try{
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			int i=0;
			supplyorbuy[] supply=new supplyorbuy[100];
			for(i=0;i<100;i++){
				supply[i]=new supplyorbuy();
			}
			int j=0;
			while(rs.next()){
				supply[j].setIdentity(rs.getInt("number"));
				supply[j].setUserid(rs.getString("userid"));
				supply[j].setDescriptions(rs.getString("descriptions"));
				supply[j].setNickname(rs.getString("nickname"));
				j++;
			}
			List<supplyorbuy> supplyList=new ArrayList<supplyorbuy>();
			for(int x=0;x<j;x++){
				supplyList.add(supply[x]);
			}
			return supplyList;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			conn.close();
		}
		return null;
	}
	
	/*public static  void collect(String userid) throws SQLException{
		conn=getConnection();
		ResultSet rs=null;
		String sql="select number,userid,descriptions,type from supplyorbuy where userid='"+userid+"';";
		String sql1="";
		//PreparedStatement ps=;
		//String sql1="select photo from picture where";
		try{
			supplyorbuy[] refresh = null;
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			int i=0;
			while(rs.next()){
				refresh[i].setIdentity(rs.getInt("number"));
				//refresh[i].setUserid(rs.getString("userid"));
				refresh[i].setDescriptions(rs.getString("descriptions"));
				refresh[i].setType(rs.getString("type"));
				sql1="select count(*),photo from picture where number='"+refresh[i].getIdentity()+"';";
				PreparedStatement ps1=conn.prepareStatement(sql1);
				ResultSet rs1=ps1.executeQuery();
				refresh[i].setCount(rs.getInt("count(*)"));
				byte[] b=new byte[10240*20];
				for(int j=0;j<refresh[i].getCount();j++){
					while(rs.next()){
						InputStream in=rs.getBinaryStream("photo");
						refresh[i].setInput(in, i);
					}
				}
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			conn.close();
		}
	}
	*/
}
