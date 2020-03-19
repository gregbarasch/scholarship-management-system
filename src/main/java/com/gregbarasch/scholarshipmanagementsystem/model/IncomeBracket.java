package com.gregbarasch.scholarshipmanagementsystem.model;

public enum IncomeBracket {
    LOW, MEDIUM, HIGH;

    public static IncomeBracket fromString(String s) {
        switch (s) {
            case "l":
            case "LOW":
                return IncomeBracket.LOW;
            case "m":
            case "MEDIUM":
                return IncomeBracket.MEDIUM;
            case "h":
            case "HIGH":
                return IncomeBracket.HIGH;
            default:
                return null;

        }
    }
}
