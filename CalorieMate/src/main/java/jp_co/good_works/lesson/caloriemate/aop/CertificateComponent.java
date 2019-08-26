package jp_co.good_works.lesson.caloriemate.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.WebContentGenerator;

import jp_co.good_works.lesson.caloriemate.controller.no_cert.CalLoginController;


	@Aspect
	@Component
	public class CertificateComponent extends WebContentGenerator{
		
		//　指定したディレクトリ配下のコントローラーにAOPを適用させる
		@Around("execution(* jp_co.good_works.lesson.caloriemate.controller.*.*(..))")
		public String checkAuthenticated (ProceedingJoinPoint joinPoint ) throws Throwable{
			
			//　ログインコントローラーをAOPとして指定している（クラスファイルを指定しなければならない）
			//　　インスタンス化
		CalLoginController loginController = getApplicationContext().getBean(CalLoginController.class);
		
		//　ログインコントローラーのisLiveメソッドを呼び出し、true/falseが返ってくるので条件判断
		if(loginController.isLive()) {
			//　指定したcontrollerに遷移する
			return (String) joinPoint.proceed(); 
		}
		//login.jspに遷移する
		return"redirect:/login";
	}
}

