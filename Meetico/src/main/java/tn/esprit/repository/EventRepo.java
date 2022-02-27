package tn.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.entity.Event;




@Repository
public interface EventRepo extends JpaRepository<Event , Integer> {

}
