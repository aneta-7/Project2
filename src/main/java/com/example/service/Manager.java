package com.example.service;

import java.util.List;

import domain.Bouquet;
import domain.User;

public interface Manager {

	void addUser(User user);
	List<User> getAllUsers();
	void deleteUser(User user);
	void updateUser(User user);
	User findUserById(Long id);
	User findUserByNick(String nick);
	
	Long addNewBouquet(Bouquet bouqet);
	void updateBouquet(Bouquet bouqet);
	void deleteBouquet(Bouquet bouqet);
	List<Bouquet> getAvailableBouquets();
	void disposeBouquet(User user, Bouquet bouqet);
	Bouquet findBouquetById(Long id);
	Bouquet findBouquetByColor(String color);

	List<Bouquet> getOwnedBouquets(User user);
	void sellBouquet(Long userId, Long bouquetId);

}
