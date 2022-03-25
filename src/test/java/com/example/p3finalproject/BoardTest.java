package com.example.p3finalproject;

import com.example.p3finalproject.model.Board;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private static Board board;

    @BeforeAll
    public static void initialized() {
        board = new Board("green", 100);
    }

    @Test
    public void boardInitializedCorrectly() {
        assertEquals("green", board.getBackgroundColor());
        assertEquals(100, board.getNumberOfButtons());
    }

}