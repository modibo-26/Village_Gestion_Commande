package com.GestionCommande.Service;

import com.GestionCommande.Entity.User;

public interface IUserService {

    User createUser(User user);

    String setRole(long id, String role);

    void deleteUser(long id);


}
