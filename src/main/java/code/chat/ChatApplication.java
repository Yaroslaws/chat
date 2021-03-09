package code.chat;

import code.chat.Repo.MyServiceBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class
ChatApplication {

	public static void main(String[] args) throws InterruptedException {
	ConfigurableApplicationContext context = SpringApplication.run(ChatApplication.class, args);
		MyServiceBean beanService = context.getBean(MyServiceBean.class);
		beanService.startEngine();
		beanService.startEngine();



	}

}