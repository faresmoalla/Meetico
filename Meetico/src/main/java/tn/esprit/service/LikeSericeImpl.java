package tn.esprit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.repository.DislikeRepository;
import tn.esprit.repository.LikeRepository;

@Service
public class LikeSericeImpl implements ILikeService {
	@Autowired
	private LikeRepository likeRepo;
	
	@Autowired
	private DislikeRepository dislikeRepo;

}
