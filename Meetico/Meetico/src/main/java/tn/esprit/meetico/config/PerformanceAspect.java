package tn.esprit.meetico.config;

import java.util.Calendar;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.meetico.entity.StatMeilleurDesitnation;
import tn.esprit.meetico.repository.StatMeilleurDesitnationRepository;
import tn.esprit.meetico.service.TripServiceImpl;


@Component
@Aspect
@Slf4j
public class PerformanceAspect {
	@Autowired
	TripServiceImpl tripSer;
	@Autowired
	StatMeilleurDesitnationRepository srepo;
	/*
	@Around("execution(* tn.esprit.spring.service.*.*(..))")
public Object profile(ProceedingJoinPoint pjp) throws Throwable {
long start = System.currentTimeMillis();
Object obj = pjp.proceed();
long elapsedTime = System.currentTimeMillis() - start;
log.info("Method execution time: " + elapsedTime + " milliseconds.");
return obj;
}*/
	@After("execution(public String tn.esprit.meetico.service.TripServiceImpl.favoriteDestination())")
	public void logMethodEntry(JoinPoint joinPoint) {
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		
		//LocalDateTime d = LocalDateTime.now(); 
		//String date =dtf.format(d);
		String s = tripSer.meilleurDestination();
		Calendar now = Calendar.getInstance(); 
		int year = now.get(Calendar.YEAR); 
		int month = now.get(Calendar.MONTH)+1; 
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY); 
		int minute = now.get(Calendar.MINUTE); 
		int second = now.get(Calendar.SECOND); 
		int millis = now.get(Calendar.MILLISECOND);
		String date = year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second+":"+millis;
		
		StatMeilleurDesitnation n =new StatMeilleurDesitnation();
		n.setDate(date);
		n.setDestination(s);
		srepo.save(n);
		
		
	}

}
