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
		
		//�@�w�肵���f�B���N�g���z���̃R���g���[���[��AOP��K�p������
		@Around("execution(* jp_co.good_works.lesson.caloriemate.controller.*.*(..))")
		public String checkAuthenticated (ProceedingJoinPoint joinPoint ) throws Throwable{
			
			//�@���O�C���R���g���[���[��AOP�Ƃ��Ďw�肵�Ă���i�N���X�t�@�C�����w�肵�Ȃ���΂Ȃ�Ȃ��j
			//�@�@�C���X�^���X��
		CalLoginController loginController = getApplicationContext().getBean(CalLoginController.class);
		
		//�@���O�C���R���g���[���[��isLive���\�b�h���Ăяo���Atrue/false���Ԃ��Ă���̂ŏ������f
		if(loginController.isLive()) {
			//�@�w�肵��controller�ɑJ�ڂ���
			return (String) joinPoint.proceed(); 
		}
		//login.jsp�ɑJ�ڂ���
		return"redirect:/login";
	}
}

