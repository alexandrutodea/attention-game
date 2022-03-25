package com.example.p3finalproject.model;

import java.util.*;

public class Board implements Customizable {
    private int id;
    private String backgroundColor;
    private int numberOfButtons;
    private int currentRandomNumber;
    private List<Integer> randomNumbers;

    public Board() {

    }

    public Board(String backgroundColor, int numberOfButtons) {
        this.backgroundColor = backgroundColor;
        this.numberOfButtons = numberOfButtons;

    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getNumberOfButtons() {
        return numberOfButtons;
    }

    public void setNumberOfButtons(int numberOfButtons) {
        this.numberOfButtons = numberOfButtons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentRandomNumber() {
        return currentRandomNumber;
    }

    public void setCurrentRandomNumber(int currentRandomNumber) {
        this.currentRandomNumber = currentRandomNumber;
    }

    public List<Integer> getRandomNumbers() {
        return randomNumbers;
    }

    public void setRandomNumbers(List<Integer> randomNumbers) {
        this.randomNumbers = randomNumbers;
    }

    public void drawNewRandomNumber() {
        Random random = new Random();
        this.randomNumbers = new ArrayList<>();
        populateList(this.randomNumbers, numberOfButtons);
        Collections.shuffle(randomNumbers);
        this.currentRandomNumber = random.nextInt(this.numberOfButtons) + 1;
    }

    private static void populateList(List<Integer> list, int max) {
        for (int i = 1; i <= max; i++) {
            list.add(i);
        }
    }

    @Override
    public String toString() {
        return "ApplicationPreferences{" +
               "backgroundColor='" + backgroundColor + '\'' +
               ", numberOfButtons=" + numberOfButtons +
               '}';
    }

    @Override
    public void modifyColor(String color) {
        this.setBackgroundColor(color);
    }

}
