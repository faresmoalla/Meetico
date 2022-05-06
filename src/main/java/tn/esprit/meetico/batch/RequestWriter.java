package tn.esprit.meetico.batch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.meetico.entity.Request;
import tn.esprit.meetico.repository.RequestRepository;

public class RequestWriter implements ItemWriter<Request> {

	@Autowired
    private RequestRepository requestRepository;

    @Override
    public void write(List<? extends Request> list) throws Exception {
      requestRepository.saveAllAndFlush(list);
    }

}
