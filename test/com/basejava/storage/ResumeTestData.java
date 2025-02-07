package com.basejava.storage;

import com.basejava.model.*;

import java.time.LocalDate;
import java.util.Arrays;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Babayev Parviz");

        resume.addContact(ContactType.PHONE, "+994 51 711 77 72");

        resume.addContact(ContactType.EMAIL, "parviz.babayev1990@gmail.com");

        resume.addContact(ContactType.LINKEDIN, "linkedin.com/in/parviz-baba");

        resume.addSection(SectionType.PERSONAL, new TextSection("Responsible, hard-working, team player"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList(
                "Developed a scalable Java backend system",
                "Optimized database queries by 40%",
                "Mentored junior developers"
        )));
        resume.addSection(SectionType.QUALIFICATIONS,
                new ListSection(Arrays.asList("Java", "Spring", "PostgreSQL", "Docker")));

        Organization google = new Organization("Google", "https://google.com",
                Arrays.asList(new Period(LocalDate.of(2020, 1, 1),
                        LocalDate.of(2023, 12, 31),
                        "Junior Developer", "Worked on AI projects")));
        Organization amazon = new Organization("Amazon", "https://amazon.com",
                Arrays.asList(new Period(LocalDate.of(2017, 5, 1),
                        LocalDate.of(2019, 12, 31),
                        "Software Engineer", "Developed cloud solutions")));

        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(google, amazon)));

        Organization university = new Organization("MIT", "https://mit.edu",
                Arrays.asList(new Period(LocalDate.of(2012, 9, 1),
                        LocalDate.of(2016, 6, 30),
                        "Bachelor in Computer Science", "Graduated with honors")));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(university)));

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
            AbstractSection section = resume.getSection(type);
            if (section != null) {
                System.out.println(type + ": " + section);
            }
        }
    }
}