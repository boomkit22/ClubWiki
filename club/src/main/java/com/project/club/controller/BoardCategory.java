package com.project.club.controller;

public enum BoardCategory {
    WORKS("활동"),
    EVENT("동아리행사"),
    PARTY("뒷풀이"),
    PERFORMANCE("공연"),
    MT("물품"),
    GOODS("공부"),
    PEOPLE("댄스"),
    OTHERS("기타");

    BoardCategory(String displayValue) {
        this.displayValue = displayValue;
    }

    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

}
