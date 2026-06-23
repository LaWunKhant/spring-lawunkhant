package com.cmps.spring.form.player;

public record PlayerSearchForm(
    String name,
    Integer ageLower,
    Integer ageUpper
) {}