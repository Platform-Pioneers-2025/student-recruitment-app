package com.example.gradconnect2025;

public class Job {
    private String jobId;
    private String title;
    private String description;
    private String location;
    private String deadline;
    private String type;
    private String company;

    public Job() {}

    public Job(String jobId, String title, String description, String location, String deadline, String type, String company) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.deadline = deadline;
        this.type = type;
        this.company = company;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
