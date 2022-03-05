package tn.esprit.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tn.esprit.entity.Gender;
import tn.esprit.entity.Reclamation;
import tn.esprit.entity.User;
import tn.esprit.entity.reclamationPriority;
import tn.esprit.entity.reclamationType;

public class ReclamationMapper implements RowMapper<User> {
		
	    @Override
	    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	
	    	
	    	return User
	    			.builder()
	                .userId(rs.getLong("user_id"))
	                .active(rs.getBoolean("active"))
	                .birthday(rs.getDate("birthday"))
	                .city(rs.getString("city"))
	                .email(rs.getString("email"))
	                .firstName(rs.getString("first_name"))
	                .gender(Gender.valueOf(rs.getString("gender")))
	                .lastName(rs.getString("last_name"))
	                .password(rs.getString("password"))
	                .phoneNumber(rs.getLong("phone_number"))
	                .street(rs.getString("street"))
	                .username(rs.getString("username"))
	                .zipCode(rs.getInt("zip_code"))
	                .build();
	    	
	    	
	    	/*return Reclamation
	                .builder()
	                .idReclamation(rs.getInt("id_reclamation"))
	                .type(reclamationType.valueOf(rs.getString("type")))
	                .description(rs.getString("description"))
	                .picture(rs.getString("picture"))
	                .file(rs.getString("file"))
	                .sendingDate(rs.getDate("sending_date"))
	                .lastModificationDate(rs.getDate("last_modification_date"))
	                .priority(reclamationPriority.valueOf(rs.getString("priority")))
	                .answerDate(rs.getDate("answer_date"))
	                .status(rs.getBoolean("status"))
	                .user(rs.getLong("user"))
	                .build();*/
	           

}
	}
