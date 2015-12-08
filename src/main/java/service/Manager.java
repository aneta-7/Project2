package service;

import java.util.List;

import domain.Bouqet;
import domain.User;

public interface Manager {

	void addUser(User user);
	List<User> getAllUsers();
	void deleteUser(User user);
	User findUserByNick(String nick);
	
	Long addNewBouqet(Bouqet bouqet);
	List<Bouqet> getAvailableBouqets();
	void disposeBouqet(User user, Bouqet bouqet);
	Bouqet findBouqetById(Long id);

	List<Bouqet> getOwnedBouqets(User user);
	void sellBouqet(Long userId, Long bouqetId);

}
