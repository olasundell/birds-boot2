package se.atrosys.birds.config;

import ch.qos.logback.access.servlet.TeeFilter;
import ch.qos.logback.access.tomcat.LogbackValve;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * TODO write documentation
 */
@Configuration
public class AccessLogConfig {
	@Bean(name = "TeeFilter")
	public Filter teeFilter() {
		return new ch.qos.logback.access.servlet.TeeFilter();
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

		LogbackValve logbackValve = new LogbackValve();

		// point to logback-access.xml
		logbackValve.setFilename("src/main/resources/logback-access.xml");

		tomcat.addContextValves(logbackValve);

		return tomcat;
	}
}
