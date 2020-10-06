package baiwa.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import baiwa.util.CommonJdbcTemplate;


@Configuration

@EnableTransactionManagement
public class DataSourceConfig {
	
	
	@Autowired
	private DataSource dataSource;
	
	@Bean(name = "commonJdbcTemplate")
	public CommonJdbcTemplate commonJdbcTemplate(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
		return new CommonJdbcTemplate(jdbcTemplate);
	}
	
	
}
