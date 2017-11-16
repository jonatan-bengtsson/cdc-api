package com.tingcore.cdc;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan(nameGenerator = Application.CustomBeanNameGenerator.class)
@EnableAutoConfiguration(exclude = JpaRepositoriesAutoConfiguration.class)
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private final Environment env;

    @Autowired
    public Application(final Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        // set the JVM timezone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        if (env.getActiveProfiles().length == 0) {
            LOG.warn("No Spring profile configured, running with default configuration");
        }
    }

    static class CustomBeanNameGenerator extends AnnotationBeanNameGenerator {
        @Override
        protected String buildDefaultBeanName(final BeanDefinition definition,
                                              final BeanDefinitionRegistry registry) {
            return BeanDefinitionReaderUtils.generateBeanName(definition, registry);
        }
    }

}
