package tn.esprit.meetico.batch;


import java.sql.ResultSet;
import java.sql.SQLException;

import tn.esprit.meetico.entity.Gender;
import tn.esprit.meetico.entity.User;
import org.springframework.jdbc.core.RowMapper;

import tn.esprit.meetico.entity.Gender;
import tn.esprit.meetico.entity.Reclamation;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.entity.reclamationPriority;
import tn.esprit.meetico.entity.reclamationType;
public class PublicationMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

		return User.builder().userId(rs.getLong("user_id")).active(rs.getBoolean("active"))
				.birthday(rs.getDate("birthday")).city(rs.getString("city")).email(rs.getString("email"))
				.firstName(rs.getString("first_name")).gender(Gender.valueOf(rs.getString("gender")))
				.lastName(rs.getString("last_name")).password(rs.getString("password"))
				.phoneNumber(rs.getLong("phone_number")).picturePath(rs.getString("picture_path"))
				.username(rs.getString("username")).verificationCode(rs.getInt("verification_code")).build();
	
	}
}
