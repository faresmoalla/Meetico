package tn.esprit.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceAspect {

	/*
	 * @Around("execution(* tn.esprit.spring.service.*.*(..))") public Object
	 * profile(ProceedingJoinPoint pjp) throws Throwable { long start =
	 * System.currentTimeMillis(); Object obj = pjp.proceed(); long elapsedTime =
	 * System.currentTimeMillis() - start; log.info("Method execution time: " +
	 * elapsedTime + " milliseconds."); return obj; }
	 */

}
