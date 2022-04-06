package com.java.ContactsSpringApp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.java.ContactsSpringApp.model.Contact;

@Repository
public class ContactDao {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbcTemplate;
	
	public List<Contact> query(Contact contact) {

		List<Contact> contacts = null;
		
		// build SQL
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder("SELECT * FROM CONTACTS.contacts "
				+ "WHERE 1=1 ");
		
		if (contact != null && contact.getId() != null) {
			sql.append("AND id = :id ");
			params.addValue("id", contact.getId());
		}
		sql.append(";");
		
		contacts = jdbcTemplate.query(sql.toString(), params,
				new BeanPropertyRowMapper<Contact>(Contact.class));
		
		return contacts;
	}
	
	public int addContact(Contact contact) {

		// build SQL
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = "INSERT INTO CONTACTS.contacts "
				+ "SELECT MAX(id)+1, :name, :number, :email, :address "
				+ "FROM CONTACTS.contacts; ";
		
		// Extract insert value from VO.
		params.addValue("name", contact.getName());
		params.addValue("number", contact.getNumber());
		params.addValue("email", contact.getEmail());
		params.addValue("address", contact.getAddress());
		
		return jdbcTemplate.update(sql, params);
	}
	
	public int updateContact(Contact contact) {
		
		// build SQL
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = "UPDATE CONTACTS.contacts SET "
				+ "name = :name, "
				+ "number = :number, "
				+ "email = :email, "
				+ "address = :address "
				+ "WHERE id = :id; ";

		// Extract update value from VO.
		params.addValue("name", contact.getName());
		params.addValue("number", contact.getNumber());
		params.addValue("email", contact.getEmail());
		params.addValue("address", contact.getAddress());
		params.addValue("id", contact.getId());
		
		return jdbcTemplate.update(sql, params);
	}
	
	public int deleteContact(Contact contact) {
		
		// build SQL
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = "DELETE FROM CONTACTS.contacts WHERE id = :id;";

		// Extract insert value from VO.
		params.addValue("id", contact.getId());
		
		return jdbcTemplate.update(sql, params);
	}
	
}
