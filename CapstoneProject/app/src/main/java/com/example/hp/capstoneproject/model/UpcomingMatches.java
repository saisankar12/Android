package com.example.hp.capstoneproject.model;

public class UpcomingMatches
{
    private String team_1;
    private String team_2;
    private String type;
    private String date;
    private String time;
    private String matchStarted;

    public UpcomingMatches(String team_1, String team_2, String type, String date, String time, String matchStarted)
    {
        this.team_1 = team_1;
        this.team_2 = team_2;
        this.type = type;
        this.date = date;
        this.time = time;
        this.matchStarted = matchStarted;
    }

    public String getTeam_1() {
        return team_1;
    }

    public void setTeam_1(String team_1) {
        this.team_1 = team_1;
    }

    public String getTeam_2() {
        return team_2;
    }

    public void setTeam_2(String team_2) {
        this.team_2 = team_2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMatchStarted() {
        return matchStarted;
    }

    public void setMatchStarted(String matchStarted) {
        this.matchStarted = matchStarted;
    }
}
