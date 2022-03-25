package com.example.p3finalproject;

import com.example.p3finalproject.model.Player;
import com.example.p3finalproject.model.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    private static Result result;

    @BeforeAll
    public static void intialize() {
        result = new Result(new Player("alex5", "12345a", false), 50);
    }

    @Test
    public void resultGetsInitializedCorrectly() {
        assertEquals(50, result.getScore());
        assertEquals(result.getUser().getUsername(), "alex5");
        assertFalse(result.getUser().isAdmin());
        assertEquals(result.getScore(), 50);
    }

}