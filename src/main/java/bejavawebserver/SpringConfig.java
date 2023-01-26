//package bejavawebserver;
//
//import bejavawebserver.repository.JdbcRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//public class SpringConfig {
//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource){
//        this.dataSource = dataSource;
//    }
//
//    @Bean
//    public JdbcRepository jdbcRepository(){
//        return new JdbcRepository(dataSource);
//    }
//}
