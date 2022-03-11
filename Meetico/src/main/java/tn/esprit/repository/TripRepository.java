package tn.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.entity.Trip;



public interface TripRepository extends JpaRepository <Trip,Integer> {

}
