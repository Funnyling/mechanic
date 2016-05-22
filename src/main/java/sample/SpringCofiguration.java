package sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
class SpringCofiguration {

    private static final String PASS = "12345";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=hackathon";
    private static final String USERNAME = "postgres";
    private static final String DRIVER = "org.postgresql.Driver";


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASS);
        dataSource.setDriverClassName(DRIVER);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
