package code.chat.service;

import code.chat.Repo.UserDetailsRepo;
import code.chat.domain.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.dbunit.DataSourceDatabaseTester;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DatabaseSetup(value = "/data.xml", type = DatabaseOperation.INSERT)
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
		TransactionalTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class
})
public class AbstractTest {

	protected DataSourceDatabaseTester dataSourceDatabaseTester;

	@Autowired
	protected UserDetailsRepo userDetailsRepo;

	@Test
	public void testDbunit() {
		List<User> all = userDetailsRepo.findAll();
		Assert.assertEquals("John", all.get(0).getName());

	}
}
