package tn.esprit.batch;

import org.springframework.jdbc.core.RowMapper;
import tn.esprit.entity.Gender;
import tn.esprit.entity.Request;
import tn.esprit.entity.Status;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestMapper implements RowMapper<Request> {
	
    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
    	return Request.builder().requestId(rs.getLong("request_id")).email(rs.getString("email")).firstName(rs.getString("first_name")).gender(Gender.valueOf(rs.getString("gender"))).lastName(rs.getString("last_name")).sendTime(rs.getDate("send_time")).status(Status.valueOf(rs.getString("status"))).build();
    }
    
}
