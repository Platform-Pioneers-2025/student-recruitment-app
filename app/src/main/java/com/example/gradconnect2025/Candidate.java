package com.example.gradconnect2025;

public class Candidate {
    private String id;
    private String name;
    private String degree;
    private String university;
    private String profileImage;

    // Empty constructor needed for Firestore
    public Candidate() {}

    public Candidate(String id, String name, String degree, String university, String profileImage) {
        this.id = id;
        this.name = name;
        this.degree = degree;
        this.university = university;
        this.profileImage = profileImage;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }
    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }
    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
}