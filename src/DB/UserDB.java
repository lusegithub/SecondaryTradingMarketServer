package DB;

import java.sql.*;

public  class UserDB  {
	public UserDB(){
		
	}
	public static Connection conn=null;
	public static Statement st=null;
	
	public static   void registerService(User user) throws SQLException{
		conn=getConnection();
		/*
		String sql="insert into users"
				+ "(userid,username,nickname,userpassword,question,answer,sex,school,faculty,speciality,phone,QQ,weixin,usernumber)"
				+ "values('"+user.getUserid()+"',"+"'"+user.getUsername()+"',"+"'"+user.getNickname()+"',"+"'"+user.getUserpassword()+"',"
				+"'"+user.getQuestion()+"',"+"'"+user.getAnswer()+"',"+"'"+user.getSex()+"',"+"'"+user.getSchool()+"',"+"'"
				+user.getFaculty()+"',"+"'"+user.getSpeciality()+"',"+"'"+user.getPhone()+"',"+"'"+user.getQQ()+"',"+"'"
				+user.getWeixin()+"')";
				*/
		String sql="insert into users"
				+ "(userid,userpassword,question,answer)"
				+ "values('"+user.getUserid()+"',"+"'"+user.getUserpassword()+"',"
				+"'"+user.getQuestion()+"',"+"'"+user.getAnswer()+"')";
		try{
			st=conn.createStatement();
			st.executeQuery(sql);
			conn.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}
		finally{
			st.close();
			conn.close();
		}
	}
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
	public static  boolean login(User user) throws SQLException{
		boolean flag=false;
		String userid=user.getUserid();
		String userpassword=user.getUserpassword();
		conn=getConnection();
		try{
			st=conn.createStatement();
			String sql="select userid from users where userid='"+userid+"'"+" and userpassword='"+userpassword+"'";
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()){
				flag=true;
				rs.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			st.close();
			conn.close();
		}
		return flag;
	}
	public static  boolean registerTest(User user) throws SQLException{
		boolean flag=false;
		String userid=user.getUserid();
		conn=getConnection();
		try{
			st=conn.createStatement();
			String sql="select userid from users where userid='"+userid+"'";
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()){
				flag=true;
				rs.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			st.close();
			conn.close();
		}
		return flag;
	}
	public static  User selectUser(String userid) throws SQLException{
		ResultSet rs=null;
		//User userinfo=null;
		conn=getConnection();
		String sql="select * from users where userid='"+userid+"'";
		try{
			st=conn.createStatement();
			User user=new User();
			rs=st.executeQuery(sql);
			if(rs.next()){
				user.setUserid(rs.getString("userid"));
				user.setUsername(rs.getString("username"));
				user.setNickname(rs.getString("nickname"));
				user.setUserpassword(rs.getString("userpassword"));
				user.setQuestion(rs.getString("question"));
				user.setAnswer(rs.getString("answer"));
				user.setSex(rs.getString("sex"));;
				user.setSchool(rs.getString("school"));
				user.setFaculty(rs.getString("faculty"));
				user.setSpeciality(rs.getString("speciality"));
				user.setPhone(rs.getString("phone"));
				user.setQQ(rs.getString("QQ"));
				user.setWeixin(rs.getString("weixin"));
				return user;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			rs.close();
			st.close();
			conn.close();
		}
		return null;
	}
	public static void updateUser(User user,String userid) throws SQLException{
		String sql="update users set nickname='"+user.getNickname()+"',"+"sex='"+user.getSex()+"',"+"school='"
				+user.getSchool()+"',"+"faculty='"+user.getFaculty()+"',"+"speciality='"+
				user.getSpeciality()+"',"+"phone='"+user.getPhone()+"',"+"QQ='"+user.getQQ()+"',"+"weixin='"+
				user.getWeixin()+"' where userid='"+userid+"';";
				
		/*
		String sql="update users set nickname='"+user.getNickname()+"',"+"weixin='"+
				user.getWeixin()+"'where userid='"+userid+"';";
				*/
		//String sql="update users set nickname='"+user.getNickname()+"',sex='"+user.getSex()+"',school='"
		conn=getConnection();
		try{
			st=conn.createStatement();
			st.executeUpdate(sql);
			conn.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}
		finally{
			st.close();
			conn.close();
		}
	}
	public static boolean updateUserPassword(String userid,String userpassword,String answer) throws SQLException{
		ResultSet rs=null;
		String sql="select userid,userpassword,question,answer from users where userid='"+userid+"' and userpassword='"
				+userpassword+"' and answer='"+answer+"';";
		conn=getConnection();
		try{
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()){
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			conn.rollback();;
		}
		finally{
			rs.close();
			st.close();
			conn.close();
		}
		return false;
		/*
		ResultSet rs=null;
		String sql="select userid,userpassword,question,answer from users where userid='"+userid+"';";
		conn=getConnection();
		try{
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			User user=new User();
			while(rs.next()){
				user.setUserid(rs.getString("userid"));
				user.setUserpassword(rs.getString("userpassword"));
				user.setQuestion(rs.getString("question"));
				user.setAnswer(rs.getString("answer"));
			}
			System.out.println(user.getAnswer());
			System.out.println(user.getUserpassword());
			System.out.println(user.getUserid());
			if(user.getUserpassword()==userpassword&&user.getAnswer()==answer){
				return  true;
			}
			else{
				{
				if(user.getUserpassword()!=userpassword){
				}
				System.out.println("shujuku"+user.getUserpassword()+"hanshu"+userpassword);
				}
				if(user.getAnswer()!=answer){
					System.out.println("a"+user.getAnswer()+"    a"+answer);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			conn.rollback();;
		}
		finally{
			rs.close();
			st.close();
			conn.close();
		}
		return false;
		*/
	}
	public static boolean updateNewUserPassword(User user,String userpassword,String answer,String newuserpassword) throws SQLException{
		String sql="update users set userpassword='"+newuserpassword+"' where userid='"+user.getUserid()+"';";
		//conn=getConnection();
		String userid=user.getUserid();
		try{
			if(updateUserPassword(userid,userpassword,answer)){
				conn=getConnection();
				st=conn.createStatement();
				st.executeUpdate(sql);
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}
		finally{
			st.close();
			conn.close();
		}
		return false;
	}
}
