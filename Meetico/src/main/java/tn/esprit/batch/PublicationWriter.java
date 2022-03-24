//package tn.esprit.batch;
//
//import java.util.List;
//
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import lombok.extern.slf4j.Slf4j;
//import tn.esprit.entity.User;
//import tn.esprit.repository.UserRepository;
//@Slf4j
//public class PublicationWriter implements ItemWriter<User> {
//
//	@Autowired
//    UserRepository userrepository;
//	
//    @Override
//    public void write(List<? extends User> list) throws Exception {
//    	log.debug("item writer: {}", list.get(0));
//    	userrepository.saveAllAndFlush(list);
//   
//      
//     
//    
//    
//    
//    }
//    
//    
//    
//    
//}
