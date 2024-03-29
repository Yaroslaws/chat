package code.chat.config;


import code.chat.Repo.MyServiceBean;
import code.chat.service.BeanCloneService;
import code.chat.service.BeanService;
import graphql.Scalars;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class AppConfig {

    @Bean
    public MyServiceBean myServiceBeanConfiguration() {
        return new BeanService();
    }

    @Bean
    @Primary
    public MyServiceBean myServiceBeanCloneConfiguration() {
        return new BeanCloneService();
    }



}
