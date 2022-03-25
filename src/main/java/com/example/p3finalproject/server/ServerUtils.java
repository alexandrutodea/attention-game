package com.example.p3finalproject.server;

import com.example.p3finalproject.exceptions.EmptyDatabaseException;
import com.example.p3finalproject.exceptions.IncorrectPasswordException;
import com.example.p3finalproject.exceptions.IncorrectUsernameException;
import com.example.p3finalproject.exceptions.InvalidPasswordException;
import com.example.p3finalproject.model.Player;

import javax.persistence.*;
import java.util.List;

public class ServerUtils {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jbd-pu");
    private static int currentlyLoggedInUserID = -1;
    private static boolean currentUserAdmin = false;

    private ServerUtils() {

    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static List<Player> retrievePlayers(EntityManager entityManager) {
        Query query = entityManager.createQuery("from Player");
        List<Player> players = query.getResultList();
        return players;
    }

    public static int login(String username, String password, List<Player> players) throws IncorrectUsernameException, EmptyDatabaseException, IncorrectPasswordException {

        if (players.size() == 0) {
            throw new EmptyDatabaseException();
        }

        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                if (player.getPassword().equals(password)) {
                    currentUserAdmin = player.isAdmin();
                    return player.getId();
                } else {
                    throw new IncorrectPasswordException();
                }
            }
        }

        throw new IncorrectUsernameException();
    }

    public static int retrieveCurrentlyLoggedInPlayerID() {
        return currentlyLoggedInUserID;
    }

    public static void updateCurrentlyLoggedInPlayerID(int userID) {
        currentlyLoggedInUserID = userID;
    }

    public static boolean isCurrentUserAdmin() {
        return currentUserAdmin;
    }

}
