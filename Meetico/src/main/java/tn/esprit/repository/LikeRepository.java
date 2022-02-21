package tn.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.entities.PostLike;

@Repository
public interface LikeRepository extends JpaRepository<PostLike, Long> {

}
