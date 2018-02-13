package com.sadi.sreda.adapter;


/**
 * Created by NanoSoft on 10/15/2017.
 */

public class ReGenerationInfo {
    private String projectName,technologyType,capacity,location,finance,completionDate,presentStatus;

    public ReGenerationInfo() {
    }

    public ReGenerationInfo(String projectName, String technologyType, String capacity, String location, String finance, String completionDate, String presentStatus) {
        this.projectName = projectName;
        this.technologyType = technologyType;
        this.capacity = capacity;
        this.location = location;
        this.finance = finance;
        this.completionDate = completionDate;
        this.presentStatus = presentStatus;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTechnologyType() {
        return technologyType;
    }

    public void setTechnologyType(String technologyType) {
        this.technologyType = technologyType;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getPresentStatus() {
        return presentStatus;
    }

    public void setPresentStatus(String presentStatus) {
        this.presentStatus = presentStatus;
    }
}
