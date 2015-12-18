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
	List<Bouquet> findUserByBouquet(User user);
	
	void addNewBouquet(Bouquet bouqet);
	void updateBouquet(Bouquet bouqet);
	void deleteBouquet(User user,Bouquet bouqet);
	List<Bouquet> getAllBouquets();
	Bouquet findBouquetById(Bouquet bouquet);
	List<Bouquet> findBouquetByColor(String color);
	void addBouquetToUser(Bouquet bouquet, User user);
}
