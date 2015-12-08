package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import service.Manager;
import domain.User;
import domain.Bouqet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SellingManagerTest {

	@Autowired
	Manager manager;

	private final String NAME_1 = "Aneta";
	private final String NICK_1 = "1234";

	private final String NAME_2 = "Karol";
	private final String NICK_2 = "abcd";

	private final String TYPE_1 = "wiazanka";
	private final String COLOR_1 = "czerwony";
	
	private final String TYPE_2 = "bukiet";
	private final String COLOR_2 = "bialy";

	@Test
	public void addClientCheck() {

		List<User> retrievedUsers = manager.getAllUsers();

		// If there is a client with PIN_1 delete it
		for (User client : retrievedUsers) {
			if (client.getNick().equals(NICK_1)) {
				manager.deleteUser(client);
			}
		}

		User user = new User();
		user.setName(NAME_1);
		user.setNick(NICK_1);
		// ... other properties here

		// Pin is Unique
		manager.addUser(user);

		User retrievedUser = manager.findUserByNick(NICK_1);

		assertEquals(NAME_1, retrievedUser.getName());
		assertEquals(NICK_1, retrievedUser.getNick());
		// ... check other properties here
	}

	@Test
	public void addBouqetCheck() {

		Bouqet bouqet = new Bouqet();
		bouqet.setType(TYPE_1);
		bouqet.setColor(COLOR_1);
		// ... other properties here

		Long bouqetId = manager.addNewBouqet(bouqet);

		Bouqet retrievedBouqet = manager.findBouqetById(bouqetId);
		assertEquals(TYPE_1, retrievedBouqet.getType());
		assertEquals(COLOR_1, retrievedBouqet.getColor());
		// ... check other properties here

	}

	@Test
	public void sellBouqetCheck() {

		User user = new User();
		user.setName(NAME_2);
		user.setNick(NICK_2);

		manager.addUser(user);

		User retrievedUser = manager.findUserByNick(NICK_2);

		Bouqet bouqet = new Bouqet();
		bouqet.setType(TYPE_2);
		bouqet.setColor(COLOR_2);

		Long bouqetId = manager.addNewBouqet(bouqet);

		manager.sellBouqet(retrievedUser.getId(), bouqetId);

		List<Bouqet> ownedBouqets = manager.getOwnedBouqets(retrievedUser);

		assertEquals(1, ownedBouqets.size());
		assertEquals(TYPE_2, ownedBouqets.get(0).getType());
		assertEquals(COLOR_2, ownedBouqets.get(0).getColor());
	}

	// @Test -
	public void disposeCarCheck() {
		// Do it yourself
	}

}