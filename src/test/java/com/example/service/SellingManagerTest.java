package com.example.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Bouquet;
import com.example.domain.User;
import com.example.service.Manager;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans.xml" })
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
	
	private final int ID_1 = 1;
	private final int ID_2 = 2;

	
	
	@Before
	public void before(){
		User user = new User();
		user.setName(NAME_1);
		user.setNick(NICK_1);
		// ... other properties here

		// Nick jest unikatowy
		// dodanie nowego uzytkownika
		manager.addUser(user);
	}
	@Test
	public void addClientCheck() {

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
		
		
		manager.addNewBouquet(bouquet);
	
		List<Bouquet> retrievedBouquet = manager.findBouquetByColor(COLOR_1);
		assertEquals(TYPE_1, ((Bouquet) retrievedBouquet).getType());
		assertEquals(COLOR_1, ((Bouquet) retrievedBouquet).getColor());
		
		List<Bouquet> bouquets = manager.getAllBouquets();
		Bouquet bouquetRetrieved = bouquets.get(0);
		
		bouquetRetrieved.setColor(COLOR_1);
		
		assertEquals(COLOR_1, bouquetRetrieved.getColor());
		
		
//		
//		//dodawanie
//		Bouquet bouquet = new Bouquet();
//		bouquet.setType(TYPE_1);
//		bouquet.setColor(COLOR_1);
//		// ... other properties here


	//	Long bouquetId = manager.addNewBouquet(bouquet);	
		//szukanie po id
	//	Bouquet retrievedBouquet = manager.findBouquetById(bouquetId);
	//	assertEquals(TYPE_1, retrievedBouquet.getType());
	//	assertEquals(COLOR_1, retrievedBouquet.getColor());
		// ... check other properties here	
	}
//	@Test
//	public void sellBouquetCheck() {
//
//		User user = new User();
//		user.setName(NAME_2);
//		user.setNick(NICK_2);
//		//dodanie uzytkownika
//		manager.addUser(user);
//		//szukanie po nick'u
//		User retrievedUser = manager.findUserByNick(NICK_2);
//		
//		Bouquet bouquet = new Bouquet();
//		bouquet.setType(TYPE_2);
//		bouquet.setColor(COLOR_2);
//		//dodanie nowego bukietu
//	//	Long bouquetId = manager.addNewBouquet(bouquet);
//
//	//	manager.sellBouquet(retrievedUser.getId(), bouquetId);
//
////		List<Bouquet> ownedBouquets = manager.getOwnedBouquets(retrievedUser);
////
////		assertEquals(1, ownedBouquets.size());
////		assertEquals(TYPE_2, ownedBouquets.get(0).getType());
////		assertEquals(COLOR_2, ownedBouquets.get(0).getColor());
//	}
}