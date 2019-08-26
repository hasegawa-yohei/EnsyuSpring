package jp_co.good_works.lesson.caloriemate.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jp_co.good_works.lesson.caloriemate.logic.CalLoginInfo;
import jp_co.good_works.lesson.caloriemate.logic.CalNameListInfo;
import jp_co.good_works.lesson.caloriemate.logic.CalSearchInfo;


public class CalUserDao {

	private static final String url = "jdbc:mysql://localhost/calsite?autoReconnect=true&useSSL=false";
	private static final String id = "root";
	private static final String pw = "password";
	//private static final String SQL =  "select * from user;";

	Connection cnct = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public CalLoginInfo select(String userID,String password) throws SQLException{

		CalLoginInfo loginInfo = null;

		try{
			Class.forName("com.mysql.jdbc.Driver");
			cnct= DriverManager.getConnection(url,id,pw);

			String quary = "select * from userinfo where userid = ? and password = ?";
			pst = cnct.prepareStatement(quary);
			pst.setString(1,userID);
			pst.setString (2,password);
			rs = pst.executeQuery();

			if(rs.next()) {
				loginInfo = new CalLoginInfo();
				loginInfo.setUserId(userID);
				loginInfo.setPassword(password);

			}

		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}


		return loginInfo;


	}

	//　検索画面の初期表示用のSQL接続メソッド
	//　SQL文を引数、CalSearchInfoを格納したListを戻り値に持つ
	public List<CalSearchInfo>  search(String SQL) throws SQLException{
		//　戻り値のリストを作成
		List<CalSearchInfo> foodList = new ArrayList<CalSearchInfo>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			cnct= DriverManager.getConnection(url,id,pw);
			pst = cnct.prepareStatement(SQL);
			rs = pst.executeQuery();



			while(rs.next()) {
				CalSearchInfo csi = new CalSearchInfo();
				csi.setFoodname(rs.getString("foodname"));
				csi.setFoodcal(rs.getInt("foodcal"));
				foodList.add(csi);
			}

		}catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}


		return foodList;



	}

	public void insert(String SQL) throws SQLException{

		try{
			Class.forName("com.mysql.jdbc.Driver");
			cnct= DriverManager.getConnection(url,id,pw);
			st = cnct.createStatement();
			st.executeUpdate(SQL);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<CalNameListInfo> namesearch(String SQL) throws SQLException{

		List<CalNameListInfo> nameList = new ArrayList<CalNameListInfo>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			cnct= DriverManager.getConnection(url,id,pw);
			pst = cnct.prepareStatement(SQL);
			rs = pst.executeQuery();

			while(rs.next()) {
				CalNameListInfo cnli = new CalNameListInfo();
				cnli.setId(rs.getInt("id"));
				cnli.setName(rs.getString("name"));
				cnli.setCal(rs.getInt("cal"));
				nameList.add(cnli);
			}
		}catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nameList;
	}

	public List<Integer> calget(String SQL) throws SQLException{

		List<Integer>calList=new ArrayList<Integer>();

		try{
			Class.forName("com.mysql.jdbc.Driver");
			cnct= DriverManager.getConnection(url,id,pw);
			pst = cnct.prepareStatement(SQL);
			rs = pst.executeQuery();

			while(rs.next()) {
				calList.add(rs.getInt("cal"));
			}
		}catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return calList;
	}

	public void delete(String SQL) throws SQLException{

		try{
			Class.forName("com.mysql.jdbc.Driver");
			cnct= DriverManager.getConnection(url,id,pw);
			st = cnct.createStatement();
			st.executeUpdate(SQL);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void reset(String SQL) throws SQLException{

		try{
			Class.forName("com.mysql.jdbc.Driver");
			cnct= DriverManager.getConnection(url,id,pw);
			st = cnct.createStatement();
			st.executeUpdate(SQL);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}