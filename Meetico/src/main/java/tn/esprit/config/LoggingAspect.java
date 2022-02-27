package tn.esprit.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LoggingAspect {
	//private static final Logger logger = LogManager.getLogger(LoggingAspect.class);
	//@Before("execution(* tn.esprit.service.UserService.addUser(..))")
	//@Before("execution(* tn.esprit.service.UserService.*(..))")
	// @Before("execution(* tn.esprit.service.*.*(..))")
	
	@Before("execution(* tn.esprit.service.*.ajouter*(..))")
	public void logMethodExit(JoinPoint joinPoint) {
	String name = joinPoint.getSignature().getName();
	//log.info("/////////////////////////Début Execution " + name + " : ");
	

	}
	
	
	
	/*
	 "execution(Modifiers-pattern? Ret-type-pattern Declaring-type-pattern?Name-
pattern(param-pattern) Throws-pattern?)"
	 
	? veut dire optionnel
1/ Modifier- pattern ? public private
2/  Ret-type-pattern : le type de retour.
3/Declaring-type-pattern? : nom de la classe y compris le package.
4/Name-pattern : nom de la méthode.
5/ Throws-pattern? : l'exception.
6/  " (..) " veut dire, 0 ou plusieurs parametres
	
	*/
	/*
	@Before("execution(* tn.esprit.service.*.*(..))")
	public void logMethodEntry(JoinPoint joinPoint) {
	String name = joinPoint.getSignature().getName();
	log.info("Before methode :::::::: " + name + " : ");
	}
	
	@AfterReturning("execution(* tn.esprit.service.*.*(..))")
	public void logMethodExit1(JoinPoint joinPoint) {
	String name = joinPoint.getSignature().getName();
	log.info("AfterReturning in method :::::::: " + name + " : ");
	}
	
	
	
	@AfterThrowing("execution(* tn.esprit.service.*.*(..))")
	public void logMethodExit2(JoinPoint joinPoint) {
	String name = joinPoint.getSignature().getName();
	log.info("after throwing in method ::::::::" + name + " : ");
	}
	


	
	
	*/
	
	
	
}