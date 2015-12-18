package com.example.service;

import java.util.List;

import com.example.domain.Bouquet;
import com.example.domain.User;

public interface Manager {

	void addUser(User user);
	List<User> getAllUsers();
	void deleteUser(User user);
	void updateUser(User user);
	User findUserById(User user);
	User findUserByNick(String nick);
	
	void addNewBouquet(Bouquet bouqet);
	void updateBouquet(Bouquet bouqet);
	void deleteBouquet(User user,Bouquet bouqet);
	List<Bouquet> getAllBouquets();
	Bouquet findBouquetById(Bouquet bouquet);
	List<Bouquet> findBouquetByColor(String color);
	boolean getAvailable(Bouquet bouquet);


}
