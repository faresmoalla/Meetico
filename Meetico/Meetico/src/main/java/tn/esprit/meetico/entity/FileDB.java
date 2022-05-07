package tn.esprit.meetico.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class FileDB implements Serializable {


	private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  
  private Long id;
  
  @NonNull
  private String name;
  
  @NonNull
  private String type;
  
  @Lob
  @NonNull
  private byte[] data;
  
//  @ManyToOne
//@JsonIgnore 
//  private Trip trip;
  
  
  @ManyToOne
  @JsonBackReference
  //@JsonIgnore
  private Publication publication;

  
  
 
}