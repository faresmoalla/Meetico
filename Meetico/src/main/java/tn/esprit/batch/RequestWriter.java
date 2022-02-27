package tn.esprit.batch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.entity.Request;
import tn.esprit.repository.RequestRepository;

public class RequestWriter implements ItemWriter<Request> {

	@Autowired
    RequestRepository requestRepository;

    @Override
    public void write(List<? extends Request> list) throws Exception {
      requestRepository.saveAllAndFlush(list);
    }

}
