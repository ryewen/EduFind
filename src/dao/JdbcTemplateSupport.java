package dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateSupport {

	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;
	
	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
