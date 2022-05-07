package tn.esprit.meetico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.meetico.entity.DestionationVisitorsCount;
import tn.esprit.meetico.entity.StatMeilleurDesitnation;
@Repository
public interface destionationVisitorsCountRepository extends JpaRepository<DestionationVisitorsCount, Integer> {
	
	@Query("Select count(*) FROM DestionationVisitorsCount r  where r.detination =:destination  ")
	int destination(@Param("destination") String destination);
	@Query("Select r FROM DestionationVisitorsCount r  where r.detination =:destination  ")
	DestionationVisitorsCount findBydetination(@Param("destination") String destination);

}
