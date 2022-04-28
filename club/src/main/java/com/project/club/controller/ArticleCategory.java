package com.project.club.controller;

public enum ArticleCategory {
    Sports("운동"),
    Music("음악"),
    Tennis("테니스"),
    Programming("프로그래밍"),
    Study("공부"),
    Dance("댄스");

    ArticleCategory(String displayValue) {
        this.displayValue = displayValue;
    }

    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

}
