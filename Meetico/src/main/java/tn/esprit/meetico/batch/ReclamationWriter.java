package tn.esprit.meetico.batch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.UserRepository;

public class ReclamationWriter implements ItemWriter<User> {
	

		@Autowired
	    UserRepository userrepository;
		
	    @Override
	    public void write(List<? extends User> list) throws Exception {
	      userrepository.saveAllAndFlush(list);
	    }
	    
	}

