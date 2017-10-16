package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Component;

import teachnet.info.object.Student;

@Component
public class StudentDAOImpl extends JdbcTemplateSupport implements StudentDAO {

	private static final String SQL_DELETE_STUDENT = "DELETE FROM students WHERE username = ?";
	
	private static final String SQL_INSERT_STUDENT = "INSERT INTO students (username, password) VALUES (?, ?)";
	
	private static final String SQL_QUERY_STUDENT = "SELECT * FROM students WHERE username = ?";
	
	@Override
	public void saveStudent(String username, String password) {
		try {
			Student student = getJdbcTemplate().queryForObject(SQL_QUERY_STUDENT, new ParameterizedRowMapper<Student>() {
				@Override
				public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
					Student student = new Student();
					student.setUsername(rs.getString(1));
					student.setPassword(rs.getString(2));
					return student;
				}
				}, username);
		} catch(EmptyResultDataAccessException e) {
			getJdbcTemplate().update(SQL_DELETE_STUDENT, username);
			getJdbcTemplate().update(SQL_INSERT_STUDENT, username, password);
		}
	}
}
