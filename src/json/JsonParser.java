package json;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class JsonParser {

	public static List<String> getLoginData(String jsonData) {
		List<String> list=new ArrayList<String>();
		JSONObject jsonObject=JSONObject.fromObject(jsonData);
		String username=jsonObject.getString("username");
		String password=jsonObject.getString("password");
		list.add(username);
		list.add(password);
		return list;
		}
	public static List<String> getPasswordModifyData(String jsonData){
		List<String> list=new ArrayList<String>();
		JSONObject jsonObject=JSONObject.fromObject(jsonData);
		String username=jsonObject.getString("username");
		String ans=jsonObject.getString("ans");
		String oldpassword=jsonObject.getString("oldpassword");
		String newpassword=jsonObject.getString("newpassword");
		list.add(username);
		list.add(ans);
		list.add(oldpassword);
		list.add(newpassword);
		return list;
	}
	public static List<String> getDataModifyData(String jsonData){
		List<String> list=new ArrayList<String>();
		JSONObject jsonObject=JSONObject.fromObject(jsonData);
		String nickname=jsonObject.getString("nickname");
		String sex=jsonObject.getString("sex");
		String school=jsonObject.getString("school");
		String department=jsonObject.getString("department");
		String major=jsonObject.getString("major");
		String phone=jsonObject.getString("phone");
		String qq=jsonObject.getString("qq");
		String weixin=jsonObject.getString("weixin");
		String username=jsonObject.getString("username");
		list.add(nickname);
		list.add(sex);
		list.add(school);
		list.add(department);
		list.add(major);
		list.add(phone);
		list.add(qq);
		list.add(weixin);
		list.add(username);
		return list;
	}
	public static JSONObject getSend(String jsonData){
		JSONObject jsonObject=JSONObject.fromObject(jsonData);
		return jsonObject;
	}
}
