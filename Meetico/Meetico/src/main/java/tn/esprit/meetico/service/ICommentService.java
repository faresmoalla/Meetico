package tn.esprit.meetico.service;

import java.util.List;

import org.springframework.data.domain.Page;

import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.User;

public interface ICommentService {
	public List<Comment> ListAllCommentsAdmin(Long idPublicaiton,String field);
	
	public List<Comment> ListAllCommentsAdmin(String field);
	public Page<Comment> ListAllCommentsAdminPaginator(int offset, int pageSize);
	public void addcomments(Comment comment, Long idpub, User user);
	public void updateComment(Comment comment, Long idComment, User user);
	public void deleteComment(Long idComment);
	public List<Comment> listCommentsByPublication(Long idPublicaiton);
	public int verif(Comment c);
	public void sendsms(String str, int body);
	public void sendsms2(String str, int body);
	
	
}
