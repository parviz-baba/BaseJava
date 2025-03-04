package com.basejava.storage;

import com.basejava.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = createResume("1234", "Babayev Parviz");
        System.out.println("Resume: " + resume.getFullName());

        System.out.println("Contacts:");
        for (ContactType type : ContactType.values()) {
            String contact = resume.getContact(type);
            if (contact != null) {
                System.out.println(type + ": " + contact);
            }
        }

        System.out.println("\nSections:");
        for (SectionType type : SectionType.values()) {
            Section section = resume.getSection(type);
            if (section != null) {
                System.out.println(type + ": " + section);
            }
        }
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, "+994 51 711 77 72");
        resume.addContact(ContactType.EMAIL, "parviz.babayev@gmail.com");
        resume.addContact(ContactType.LINKEDIN, "linkedin.com");
        resume.addSection(SectionType.PERSONAL, new TextSection("Responsible, hard-working, team player"));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Seeking a Senior Java Developer role"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(
                Arrays.asList("Java", "Spring Boot", "PostgreSQL", "Docker")
        ));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(
                Arrays.asList("Developed a scalable Java backend system",
                        "Optimized database queries by 40%",
                        "Mentored junior developers")
        ));

        List<Organization.Position> googlePeriods = Arrays.asList(
                new Organization.Position(LocalDate.of(2020, Month.JANUARY, 1),
                        LocalDate.of(2023, Month.DECEMBER, 31),
                        "Junior Developer", "Worked on AI projects")
        );

        List<Organization.Position> amazonPeriods = Arrays.asList(
                new Organization.Position(LocalDate.of(2017, Month.MAY, 1),
                        LocalDate.of(2019, Month.DECEMBER, 31),
                        "Software Engineer", "Developed cloud solutions")
        );

        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                Arrays.asList(new Organization(new Link("Google", "https://google.com"), googlePeriods),
                        new Organization(new Link("Amazon", "https://amazon.com"), amazonPeriods))
        ));

        List<Organization.Position> universityPeriods = Arrays.asList(
                new Organization.Position(LocalDate.of(2012, Month.SEPTEMBER, 1),
                        LocalDate.of(2016, Month.JUNE, 30),
                        "Bachelor in Computer Science", "Graduated with honors")
        );

        resume.addSection(SectionType.EDUCATION, new OrganizationSection(
                Arrays.asList(new Organization(new Link("MIT", "https://mit.edu"), universityPeriods))
        ));

        return resume;
    }
}