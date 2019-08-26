package jp_co.good_works.lesson.caloriemate.controller.no_cert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jp_co.good_works.lesson.caloriemate.dao.CalUserDao;
import jp_co.good_works.lesson.caloriemate.form.CalGoForm;
import jp_co.good_works.lesson.caloriemate.form.CalListForm;
import jp_co.good_works.lesson.caloriemate.form.CalSearchForm;
import jp_co.good_works.lesson.caloriemate.logic.CalDetailInfo;
import jp_co.good_works.lesson.caloriemate.logic.CalSearchInfo;



@Controller
@Scope("session")
public class CalSearchController {

	//@Autowired
	//�ǉ����������̃��X�g
	//List<CalListForm>calListFormList = new ArrayList<CalListForm>();
	
	//�@�f�[�^�x�[�X����Ƃ��Ă����f�[�^�������t�H�[���̃C���X�^���X��
	CalSearchInfo searchInfo = new CalSearchInfo();
	//�@DAO�i�f�[�^�x�[�X�ڑ��j�̃C���X�^���X��
	CalUserDao dao = new CalUserDao();
	//�@�T�[�`�t�H�[���̃C���X�^���X�i���͓��e�����锠�̗p�Ӂj
    CalSearchForm searchform = new CalSearchForm();
    //�@�ڍ׃t�H�[���̃C���X�^���X�i�ڍ׃{�^���̏������锠�̗p�Ӂj
    CalDetailInfo detailInfo =new CalDetailInfo();
   //calListFormList�Ɋi�[���邽�߂̃t�H�[��
    CalListForm calListForm = new CalListForm();
    
    CalGoForm calGoForm = new CalGoForm();

    List<CalListForm>calListFormList = new ArrayList<CalListForm>();
	
    @RequestMapping(value = "/search", method = RequestMethod.GET)
	public String initializeSearch(Model model) throws SQLException {


		//�@�f�[�^�x�[�X�ڑ��i������ʂ̏����\���p�j
		//SQL����������dao(CalUserDao)��first���\�b�h���Ăяo���A
		//�@�߂�lCalSearchInfo�i���O�A�J�����[�̃Z�b�g�̏��j��foodList��������ŐV�����p�ӂ���foodList�Ɋi�[
		String SQL = "select foodname,foodcal from fooddata;";
		List<CalSearchInfo> foodList = dao.search(SQL);
		model.addAttribute("foodList",foodList);


		//�@�Z���N�g�{�^���p
		//�@������ʃJ�e�S���I���̃Z���N�g�{�^���̑I�����̃��X�g
		List<String> foodcate = new ArrayList<String>();
		//�@�I�����̒ǉ�
		foodcate.add("�w��Ȃ�");
		foodcate.add("��{���j���[");
		foodcate.add("�a�H");
		foodcate.add("�m�H");
		foodcate.add("����");
		foodcate.add("�f�U�[�g/���ݕ�");
		//foodcate�Ƃ������O�œ��e���������ɓn��
		model.addAttribute("foodcate",foodcate);

		model.addAttribute("message","���͂��Ă�������");
		model.addAttribute("calDetailInfo",detailInfo);
		model.addAttribute("calSearchForm",searchform);
		model.addAttribute("calGoForm",calGoForm);
		return "search";	

	}




	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String executeSearch(Model model,@Validated @ModelAttribute CalDetailInfo detailInfo,CalSearchForm searchform,CalListForm calListForm ,
			CalGoForm calGoForm,BindingResult result,RedirectAttributes redirectAttr) throws SQLException {
       
		//�v�Z�փ{�^���������ꂽ��ȉ��̏���
		if(calGoForm.getFlg()==1) {
			return "redirect:/list";
		}
		
		
		//�@search.jsp�œ��͂��ꂽ�������CalSearchForm����get����word�Ɋi�[
		String word = searchform.getWord();
		String cate = searchform.getFoodcate();
		
        //�Z���N�g�{�^���p
		List<String> foodcate = new ArrayList<String>();
		foodcate.add("�w��Ȃ�");
		foodcate.add("��{���j���[");
		foodcate.add("�a�H");
		foodcate.add("�m�H");
		foodcate.add("����");
		foodcate.add("�f�U�[�g/���ݕ�");
		model.addAttribute("foodcate",foodcate);
	
		//�ǉ��{�^���p
		//�����ꂽ�{�^���̗�������detailInfo�ɂ���̂ŁAget��foodname�Ɋi�[
        String foodname= detailInfo.getFoodname();
        int foodcal=detailInfo.getFoodcal();
        calListForm.setFoodname(foodname);
        calListForm.setFoodcal(foodcal);
        if(foodname !=null) {
        	calListFormList.add(calListForm);
        	String SQL = "INSERT INTO list(id,name,cal) VALUES("+calListFormList.size()+",'"+foodname+"',"+foodcal+");";
        	dao.insert(SQL);
        	model.addAttribute("message",foodname+"��ǉ����܂���");
        	model.addAttribute("calSearchForm",searchform);        	
    		return "search";
        }
		
        //�����p
        //�J�e�S���w�肠��A�Ȃ��̏�������
        String SQL;
        if(cate.equals("�w��Ȃ�")) {
			 SQL ="SELECT foodname,foodcal FROM fooddata WHERE foodname LIKE '%"+word+"%';";
		}else {
			 SQL ="SELECT foodname,foodcal FROM fooddata WHERE foodname LIKE '%"+word+"%' AND catname ='"+cate+"' ;";
		}
        //�f�[�^�x�[�X����Ƃ��Ă����f�[�^��CalSearchInfo�^��foodList�Ɋi�[
        List<CalSearchInfo> foodList = dao.search(SQL);
        model.addAttribute("foodList",foodList);
		model.addAttribute("calSearchForm",searchform);
		return "search";
	}

}


