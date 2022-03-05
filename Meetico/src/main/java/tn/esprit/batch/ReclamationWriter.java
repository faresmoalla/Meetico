package tn.esprit.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import tn.esprit.entity.Reclamation;
import tn.esprit.entity.User;
import tn.esprit.repository.UserRepository;
import tn.esprit.repository.reclamationRepository;

public class ReclamationWriter implements ItemWriter<User> {
	

		@Autowired
	    UserRepository userrepository;
		
	    @Override
	    public void write(List<? extends User> list) throws Exception {
	      userrepository.saveAllAndFlush(list);
	    }
	    
	}

