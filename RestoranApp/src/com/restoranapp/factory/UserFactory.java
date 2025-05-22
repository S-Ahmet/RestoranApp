package com.restoranapp.factory;

import com.restoranapp.model.*;

public class UserFactory {
    public static User createUser(int id, String username, String role) {
        if (role.equals("Manager")) {
            return new Manager(id, username);
        } else {
            return new Customer(id, username);
        }
    }
}
