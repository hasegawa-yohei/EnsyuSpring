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
	//追加した料理のリスト
	//List<CalListForm>calListFormList = new ArrayList<CalListForm>();
	
	//　データベースからとってきたデータをいれるフォームのインスタンス化
	CalSearchInfo searchInfo = new CalSearchInfo();
	//　DAO（データベース接続）のインスタンス化
	CalUserDao dao = new CalUserDao();
	//　サーチフォームのインスタンス（入力内容を入れる箱の用意）
    CalSearchForm searchform = new CalSearchForm();
    //　詳細フォームのインスタンス（詳細ボタンの情報を入れる箱の用意）
    CalDetailInfo detailInfo =new CalDetailInfo();
   //calListFormListに格納するためのフォーム
    CalListForm calListForm = new CalListForm();
    
    CalGoForm calGoForm = new CalGoForm();

    List<CalListForm>calListFormList = new ArrayList<CalListForm>();
	
    @RequestMapping(value = "/search", method = RequestMethod.GET)
	public String initializeSearch(Model model) throws SQLException {


		//　データベース接続（検索画面の初期表示用）
		//SQL文を引数にdao(CalUserDao)のfirstメソッドを呼び出し、
		//　戻り値CalSearchInfo（名前、カロリーのセットの情報）のfoodListをこちらで新しく用意したfoodListに格納
		String SQL = "select foodname,foodcal from fooddata;";
		List<CalSearchInfo> foodList = dao.search(SQL);
		model.addAttribute("foodList",foodList);


		//　セレクトボタン用
		//　検索画面カテゴリ選択のセレクトボタンの選択肢のリスト
		List<String> foodcate = new ArrayList<String>();
		//　選択肢の追加
		foodcate.add("指定なし");
		foodcate.add("基本メニュー");
		foodcate.add("和食");
		foodcate.add("洋食");
		foodcate.add("中華");
		foodcate.add("デザート/飲み物");
		//foodcateという名前で内容をｊｓｐに渡す
		model.addAttribute("foodcate",foodcate);

		model.addAttribute("message","入力してください");
		model.addAttribute("calDetailInfo",detailInfo);
		model.addAttribute("calSearchForm",searchform);
		model.addAttribute("calGoForm",calGoForm);
		return "search";	

	}




	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String executeSearch(Model model,@Validated @ModelAttribute CalDetailInfo detailInfo,CalSearchForm searchform,CalListForm calListForm ,
			CalGoForm calGoForm,BindingResult result,RedirectAttributes redirectAttr) throws SQLException {
       
		//計算へボタンが押されたら以下の処理
		if(calGoForm.getFlg()==1) {
			return "redirect:/list";
		}
		
		
		//　search.jspで入力された文字列をCalSearchFormからgetしてwordに格納
		String word = searchform.getWord();
		String cate = searchform.getFoodcate();
		
        //セレクトボタン用
		List<String> foodcate = new ArrayList<String>();
		foodcate.add("指定なし");
		foodcate.add("基本メニュー");
		foodcate.add("和食");
		foodcate.add("洋食");
		foodcate.add("中華");
		foodcate.add("デザート/飲み物");
		model.addAttribute("foodcate",foodcate);
	
		//追加ボタン用
		//押されたボタンの料理名はdetailInfoにあるので、getしfoodnameに格納
        String foodname= detailInfo.getFoodname();
        int foodcal=detailInfo.getFoodcal();
        calListForm.setFoodname(foodname);
        calListForm.setFoodcal(foodcal);
        if(foodname !=null) {
        	calListFormList.add(calListForm);
        	String SQL = "INSERT INTO list(id,name,cal) VALUES("+calListFormList.size()+",'"+foodname+"',"+foodcal+");";
        	dao.insert(SQL);
        	model.addAttribute("message",foodname+"を追加しました");
        	model.addAttribute("calSearchForm",searchform);        	
    		return "search";
        }
		
        //検索用
        //カテゴリ指定ある、なしの条件判定
        String SQL;
        if(cate.equals("指定なし")) {
			 SQL ="SELECT foodname,foodcal FROM fooddata WHERE foodname LIKE '%"+word+"%';";
		}else {
			 SQL ="SELECT foodname,foodcal FROM fooddata WHERE foodname LIKE '%"+word+"%' AND catname ='"+cate+"' ;";
		}
        //データベースからとってきたデータをCalSearchInfo型のfoodListに格納
        List<CalSearchInfo> foodList = dao.search(SQL);
        model.addAttribute("foodList",foodList);
		model.addAttribute("calSearchForm",searchform);
		return "search";
	}

}


