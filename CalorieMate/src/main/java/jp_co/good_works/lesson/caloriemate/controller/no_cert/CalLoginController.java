package jp_co.good_works.lesson.caloriemate.controller.no_cert;

import java.sql.SQLException;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp_co.good_works.lesson.caloriemate.exception.CalLoginException;
import jp_co.good_works.lesson.caloriemate.form.CalLoginForm;
import jp_co.good_works.lesson.caloriemate.logic.CalLoginLogic;

@Controller
@Scope("session")
public class CalLoginController {

	private CalLoginLogic loginLogic = new CalLoginLogic();

	public boolean isLive () {
		return loginLogic.isLive();
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String initializeLogin (Model model) {
		CalLoginForm form = new CalLoginForm();
		model.addAttribute("message","���͂��Ă�������");
		model.addAttribute("calLoginForm",form);
		return "login";	

	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String executeLogin(Model model,@Validated @ModelAttribute CalLoginForm form,
			BindingResult result,RedirectAttributes redirectAttr) throws SQLException {
		//�o���f�[�V������if��
		if (!result.hasErrors()) {
			try {
				//
				loginLogic.login(form.getUserId(),form.getPassword());
				//hello.jsp�ɑJ�ځi�ȉ��̏����͍s���Ȃ��j
				return "redirect:/search";
			} catch (CalLoginException e) {
				model.addAttribute("message", e.getLocalizedMessage());
			}
		}else {
			model.addAttribute("message", "���[�U ID �A �p�X���[�h����͂��Ă��������B");
		}
		return "login";
	}

}


