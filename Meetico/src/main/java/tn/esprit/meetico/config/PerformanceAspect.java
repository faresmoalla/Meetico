package tn.esprit.meetico.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class PerformanceAspect {

	@Around("execution(* tn.esprit.meetico.service.*.*(..))")
	public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		 String name = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName() + "." + proceedingJoinPoint.getSignature().getName();
		long start = System.currentTimeMillis();
		Object obj = proceedingJoinPoint.proceed();
		long elapsedTime = System.currentTimeMillis() - start;
		log.info("Executin of method "+ name + " took " + elapsedTime + " milliseconds.");
		return obj;
	}
	
}
