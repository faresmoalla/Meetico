package tn.esprit.meetico.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.meetico.entity.Feedback;


public interface FeedbackRepository extends JpaRepository<Feedback,Integer>{
	@Query("SELECT f FROM Feedback f join f.user u WHERE u.userId =:userId")
	 Set<Feedback> getAllFeedbacksClient(@Param(value = "userId") Long userId);
	@Query("SELECT f FROM Feedback f join f.users u WHERE u.userId = :userId")
	Set<Feedback> getFeedbacksClientTAG(@Param(value = "userId") Long userId);
	@Query("SELECT f FROM Feedback f join f.users u WHERE u.userId = :userId")
	Set<Integer> top3UsersWithHighestNumberOfFeedbacks(@Param(value = "userId") Long userId);
}
