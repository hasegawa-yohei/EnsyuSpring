package jp_co.good_works.lesson.caloriemate.logic;

import java.sql.SQLException;

import jp_co.good_works.lesson.caloriemate.dao.CalUserDao;
import jp_co.good_works.lesson.caloriemate.exception.CalLoginException;
import jp_co.good_works.lesson.caloriemate.logic.CalLoginInfo;

public class CalLoginLogic {

	private CalLoginInfo loginInfo = null;
	public CalLoginInfo login(String userID,String password)throws CalLoginException{

		CalUserDao userDao = new CalUserDao();
		try {
			loginInfo =userDao.select(userID, password);
		
		if(loginInfo!=null){
		//String x= "guest";
		//String y= "guestguest";

		//if((userID.equals(x))&&(password.equals(y))) {
		//loginInfo�̃C���X�^���X��
				
		loginInfo =new CalLoginInfo(); 
		loginInfo.setUserId(userID);
		loginInfo.setPassword(password);
				 
		//}
			
		}else {

			throw new CalLoginException("���[�UID�܂��̓p�X���[�h���Ⴂ�܂�");
		}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  loginInfo;
		}
	
	public boolean isLive () {

		if(loginInfo!=null) {
			//�@���O�C������
			return true;
		}else {
            //�@���O�C�����s
			return false;
		}
	}
}
	

