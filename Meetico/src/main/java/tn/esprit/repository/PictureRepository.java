package tn.esprit.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.entity.Picture;

public interface PictureRepository extends JpaRepository<Picture, Integer>{

}
