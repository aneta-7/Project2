package com.example.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.service.Manager;

import domain.Bouquet;
import domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SellingManagerTest{

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
	
	private final int ID = 1;

	@Test
	public void addClientCheck() {

		List<User> retrievedUsers = manager.getAllUsers();

		//jesli znajdzie uzytkownika po nick'u to usuwa go
		// If there is a client with NICK_1 delete it
		for (User client : retrievedUsers) {
			if (client.getNick().equals(NICK_1)) {
				manager.deleteUser(client);
			}
		}

		User user = new User();
		user.setName(NAME_1);
		user.setNick(NICK_1);
		// ... other properties here

		// Nick jest unikatowy
		// dodanie nowego uzytkownika
		manager.addUser(user);
		//szukanie po nick'u
		User retrievedUser = manager.findUserByNick(NICK_1);

		assertEquals(NAME_1, retrievedUser.getName());
		assertEquals(NICK_1, retrievedUser.getNick());
		// ... check other properties here
	}

	@Test
	public void addBouquetCheck() {

		Bouquet bouquet = new Bouquet();
		bouquet.setType(TYPE_1);
		bouquet.setColor(COLOR_1);
		// ... other properties here

		//dodawanie
		Long bouquetId = manager.addNewBouquet(bouquet);	
		//szukanie po id
		Bouquet retrievedBouquet = manager.findBouquetById(bouquetId);
		assertEquals(TYPE_1, retrievedBouquet.getType());
		assertEquals(COLOR_1, retrievedBouquet.getColor());
		// ... check other properties here	
	}
	@Test
	public void sellBouquetCheck() {

		User user = new User();
		user.setName(NAME_2);
		user.setNick(NICK_2);
		//dodanie uzytkownika
		manager.addUser(user);
		//szukanie po nick'u
		User retrievedUser = manager.findUserByNick(NICK_2);
		
		Bouquet bouquet = new Bouquet();
		bouquet.setType(TYPE_2);
		bouquet.setColor(COLOR_2);
		//dodanie nowego bukietu
		Long bouquetId = manager.addNewBouquet(bouquet);

		manager.sellBouquet(retrievedUser.getId(), bouquetId);

		List<Bouquet> ownedBouquets = manager.getOwnedBouquets(retrievedUser);

		assertEquals(1, ownedBouquets.size());
		assertEquals(TYPE_2, ownedBouquets.get(0).getType());
		assertEquals(COLOR_2, ownedBouquets.get(0).getColor());
	}
}