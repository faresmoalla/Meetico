package tn.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {}