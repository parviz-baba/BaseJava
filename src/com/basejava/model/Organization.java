package com.basejava.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final String name;
    private final String link;
    private final List<Period> periods;

    public Organization(String name, String link, List<Period> periods) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.link = link == null ? "" : link;
        this.periods = periods;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return name.equals(that.name) &&
               link.equals(that.link) &&
               periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, link, periods);
    }

    @Override
    public String toString() {
        return "Organization{" +
               "name='" + name + '\'' +
               ", website='" + link + '\'' +
               ", periods=" + periods +
               '}';
    }
}