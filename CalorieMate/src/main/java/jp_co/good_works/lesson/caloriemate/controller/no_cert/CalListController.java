package jp_co.good_works.lesson.caloriemate.controller.no_cert;

import java.sql.SQLException;
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
import jp_co.good_works.lesson.caloriemate.logic.CalNameListInfo;


@Controller
@Scope("session")
public class CalListController {
	
	//　DAO（データベース接続）のインスタンス化
		CalUserDao dao = new CalUserDao();
		 CalGoForm calGoForm = new CalGoForm();

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String initializeList(Model model) throws SQLException {

		String SQL = "select id,name,cal from list;";
		List<CalNameListInfo> nameList = dao.namesearch(SQL);
		
		SQL ="select cal from list";
		List<Integer>calList =dao.calget(SQL);
		  int cal =0;
		for(int i=0; i< calList.size(); i++)
		{
		    cal +=calList.get(i);
		   
		}
		model.addAttribute("totalcal",cal);
		model.addAttribute("message","合計");
		model.addAttribute("nameList",nameList);
		return "list";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public String executeSearch(Model model,@Validated @ModelAttribute 
			CalGoForm calGoForm,CalNameListInfo calNameListInfo,BindingResult result,RedirectAttributes redirectAttr) throws SQLException {
	
		String SQL;
		
	if(calGoForm.getFlg()==1) {
		return "redirect:/search";
	}if(calGoForm.getFlg()==2) {
		 SQL = "DELETE FROM list ;";
		 dao.reset(SQL);
		return "redirect:/login";
	}
	//CalNameListInfo calNameListInfo1=new CalNameListInfo();
	int id = calNameListInfo.getId();
	
	 SQL = "DELETE FROM list WHERE id='"+id+"';";
	
	System.out.println(SQL);
	dao.delete(SQL);
	
	 SQL = "select id,name,cal from list;";
	List<CalNameListInfo> nameList = dao.namesearch(SQL);
	
	SQL ="select cal from list";
	List<Integer>calList =dao.calget(SQL);
	  int cal =0;
	for(int i=0; i< calList.size(); i++)
	{
	    cal +=calList.get(i);
	   
	}
	model.addAttribute("totalcal",cal);
	model.addAttribute("message","合計");
	model.addAttribute("nameList",nameList);
	
	
	
	return "list";
	}
	
}
