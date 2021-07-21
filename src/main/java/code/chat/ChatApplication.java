package code.chat;

import code.chat.Repo.MyServiceBean;
import code.chat.beans.House;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;


@SpringBootApplication
public class
ChatApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ChatApplication.class, args);
		MyServiceBean beanService = context.getBean(MyServiceBean.class);
		beanService.startEngine();
		beanService.startEngine();
		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
	    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanContext/ConfigHouse.xml");
		House house = (House) applicationContext.getBean("house");
		System.out.println(house.toString());

		String s1 = new String("1");
		String s2 = new String("1");

		System.out.println(s1 == s2);
		System.out.println(s1.equals(s2));

	}
}