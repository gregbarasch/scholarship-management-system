package com.gregbarasch.scholarshipmanagementsystem.model;

public enum ProgramType {
    UNDERGRADUATE, GRADUATE;

    public static ProgramType fromString(String s) {
        switch (s) {
            case "u":
            case "UNDERGRADUATE":
                return ProgramType.UNDERGRADUATE;
            case "g":
            case "GRADUATE":
                return ProgramType.GRADUATE;
            default:
                return null;
        }
    }
}