package tn.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.entity.PostDislike;

@Repository
public interface DislikeRepository extends JpaRepository<PostDislike, Long>{

}
