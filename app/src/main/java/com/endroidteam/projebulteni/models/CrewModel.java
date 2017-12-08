package com.endroidteam.projebulteni.models;

/**
 * Created by NuhKoca on 4.05.2016.
 */
public class CrewModel {
    String crewName,crewDuty;

    public CrewModel() {
    }

    public CrewModel(String crewName, String crewDuty) {
        this.crewName = crewName;
        this.crewDuty = crewDuty;
    }

    public String getCrewName() {
        return crewName;
    }

    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public String getCrewDuty() {
        return crewDuty;
    }

    public void setCrewDuty(String crewDuty) {
        this.crewDuty = crewDuty;
    }
}
