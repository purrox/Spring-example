
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 10)
public class DatasourceConfiguration {
	
	@Value("${jndi.provider.url}")
	private String providerURL;
	
	@Value("${jndi.security.principal}")
	private String securityPrincipal;
	
	@Value("${jndi.security.credentials}")
	private String securityCredentials;
	
	@Value("${jndi.datasource.name}")
	private String datasourceName;

	
	@Value("${spring.datasource.url}")
	private String jdbcUrl;
	
	@Value("${spring.datasource.username}")
	private String jdbcUsername;
	
	@Value("${spring.datasource.password}")
	private String jdbcPassword;
	
	@Value("${spring.datasource.driverClassName}")
	private String jdbcDriver;
	
	
	@Bean
    DataSource dataSource() {
        DataSource dataSource = null;
        
        //Use JNDI Datasource if present
        if(!datasourceName.isEmpty()) {
	        Properties env = new Properties();
	        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
	        
	        if(!providerURL.isEmpty()) {
	        	env.setProperty(Context.PROVIDER_URL, providerURL);
	        }
	        
	        if((!securityPrincipal.isEmpty()) && (securityCredentials.isEmpty())) {
	        	env.setProperty(Context.SECURITY_PRINCIPAL, securityPrincipal);
	        	env.setProperty(Context.SECURITY_CREDENTIALS, securityCredentials);
	        }
	        
	        JndiTemplate jndi = new JndiTemplate(env);
	        
	        try {
	        	dataSource = (DataSource) jndi.lookup(datasourceName);
	        } catch (NamingException e) {
	        	e.printStackTrace();
	        }
        }
        //Use JDBC Datasource 
        else {
        	dataSource = new DriverManagerDataSource();
            
            ((DriverManagerDataSource)dataSource).setDriverClassName(jdbcDriver);
            ((DriverManagerDataSource)dataSource).setUrl(jdbcUrl);
            ((DriverManagerDataSource)dataSource).setUsername(jdbcUsername);
            ((DriverManagerDataSource)dataSource).setPassword(jdbcPassword);
            
        }
        
        return dataSource;
    }
}
