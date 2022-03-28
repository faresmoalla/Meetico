package tn.esprit.meetico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.meetico.entity.ActivityField;

@Repository
public interface ActivityFieldRepository extends JpaRepository<ActivityField, Long> {}
