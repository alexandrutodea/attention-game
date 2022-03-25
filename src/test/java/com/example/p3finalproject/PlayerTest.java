package com.example.p3finalproject;

import com.example.p3finalproject.exceptions.EmptyFieldsException;
import com.example.p3finalproject.exceptions.InvalidPasswordException;
import com.example.p3finalproject.exceptions.InvalidUsernameException;
import com.example.p3finalproject.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private static Player player;

    @BeforeAll
    public static void initialize() {
        player = new Player("alextodea", "12345a", false);
    }

    @Test
    public void userInitializedCorrectly() {
        assertEquals("alextodea", player.getUsername());
        assertEquals("12345a", player.getPassword());
        assertFalse(player.isAdmin());
        assertNotNull(player.getResults());
    }

    @Test
    public void invalidUsernameExceptionTest() {
        assertThrows(InvalidUsernameException.class, () -> {
            new Player("alex", "password123", false);
        });
    }

    @Test
    public void invalidPasswordExceptionTest() {
        assertThrows(InvalidPasswordException.class, () -> {
            new Player("alex5", "pas", false);
        });
    }

    @Test
    public void emptyFieldsExceptionTest() {
        assertThrows(EmptyFieldsException.class, () -> {
            new Player("", "password123", false);
        });
    }

    @Test
    public void emptyFieldsExceptionTest2() {
        assertThrows(EmptyFieldsException.class, () -> {
            new Player("alex5", "", false);
        });
    }

}