package tn.esprit.meetico.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "file")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class FileDB {
	
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
  
  @ManyToOne
  private Trip trip;
  
  @ManyToOne
  private Publication publication;
 
}