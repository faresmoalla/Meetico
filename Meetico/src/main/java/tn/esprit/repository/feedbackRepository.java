package tn.esprit.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.entity.Feedback;


public interface feedbackRepository extends JpaRepository<Feedback,Integer>{
	@Query("SELECT f FROM Feedback f join f.user u WHERE u.userId =:userId")
	 Set<Feedback> getAllFeedbacksClient(@Param(value = "userId") Long userId);
	@Query("SELECT f FROM Feedback f join f.users u WHERE u.userId = :userId")
	Set<Feedback> getFeedbacksClientTAG(@Param(value = "userId") Long userId);
}

