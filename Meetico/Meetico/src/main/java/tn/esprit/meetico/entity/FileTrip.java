package tn.esprit.meetico.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FileTrip implements Serializable {
	private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String type;
  @Lob
  private byte[] data;
  @OneToOne(mappedBy="trip")
  @JsonBackReference
  private Trip trip;
 
  public FileTrip(String name, String type, byte[] data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }
}