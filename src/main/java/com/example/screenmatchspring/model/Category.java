package com.example.screenmatchspring.model;

public enum Category {
    ACTION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDY("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crimen");

    private String categoryOmbd;
    private String categoryEspanol;
    Category(String categoryOmbd, String categoryEspanol) {
        this.categoryOmbd = categoryOmbd;
        this.categoryEspanol = categoryEspanol;
    }

    public static Category fromString(String text) {
        for (Category categoria : Category.values()) {
            if (categoria.categoryOmbd.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("None category found: " + text);
    }

    public static Category fromEspanol(String text) {
        for (Category categoria : Category.values()) {
            if (categoria.categoryEspanol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("None category found: " + text);
    }
}
