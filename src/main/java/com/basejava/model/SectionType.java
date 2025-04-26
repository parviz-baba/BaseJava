package com.basejava.model;

public enum SectionType {
    OBJECTIVE("Позиция", SectionCategory.TEXT),
    PERSONAL("Личные качества", SectionCategory.TEXT),
    ACHIEVEMENT("Достижения", SectionCategory.LIST),
    QUALIFICATIONS("Квалификация", SectionCategory.LIST),
    EXPERIENCE("Опыт работы", SectionCategory.ORGANIZATION),
    EDUCATION("Образование", SectionCategory.ORGANIZATION);

    private final String title;
    private final SectionCategory category;

    SectionType(String title, SectionCategory category) {
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public boolean isText() {
        return category == SectionCategory.TEXT;
    }

    public boolean isList() {
        return category == SectionCategory.LIST;
    }

    public boolean isOrganization() {
        return category == SectionCategory.ORGANIZATION;
    }

    private enum SectionCategory {
        TEXT,
        LIST,
        ORGANIZATION
    }
}
