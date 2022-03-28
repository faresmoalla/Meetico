package tn.esprit.meetico.batch;


import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.UserRepository;
@Slf4j
public class PublicationWriter implements ItemWriter<User> {

	@Autowired
    UserRepository userrepository;
	
    @Override
    public void write(List<? extends User> list) throws Exception {
      userrepository.saveAllAndFlush(list);
    }
    
    
    
    
    
}
