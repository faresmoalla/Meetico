package tn.esprit.meetico.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import tn.esprit.meetico.entity.Gender;
import tn.esprit.meetico.entity.Request;
import tn.esprit.meetico.entity.Status;

public class RequestMapper implements RowMapper<Request> {

	@Override
	public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Request.builder()
				.requestId(rs.getLong("request_id"))
				.email(rs.getString("email"))
				.firstName(rs.getString("first_name"))
				.gender(Gender.valueOf(rs.getString("gender")))
				.lastName(rs.getString("last_name"))
				.nic(rs.getLong("nic"))
				.phoneNumber(rs.getLong("phone_number"))
				.sendTime(rs.getDate("send_time"))
				.status(Status.valueOf(rs.getString("status")))
				.build();
	}

}
