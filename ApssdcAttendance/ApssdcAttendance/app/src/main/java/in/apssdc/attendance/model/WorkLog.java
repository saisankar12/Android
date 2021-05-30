package in.apssdc.attendance.model;

import java.util.Date;

public class WorkLog extends BaseModel{

    private String employeeId;//employeeId
    private String traineeId;//employeeId
    private Date date;
    private String scheduledTask;//assigned task
    private String completeStatus;//task status-radio button
    private String percentTask;//percentage completion
    private String otherTask;//other works
    private String issues;
    private String name;
    private String placeofwork;
    private String WorkingHours;
    private String loginTime;
    private String logoutTime;
    private String college;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getScheduledTask() {
        return scheduledTask;
    }

    public void setScheduledTask(String scheduledTask) {
        this.scheduledTask = scheduledTask;
    }

    public String getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(String completeStatus) {
        this.completeStatus = completeStatus;
    }

    public String getPercentTask() {
        return percentTask;
    }

    public void setPercentTask(String percentTask) {
        this.percentTask = percentTask;
    }

    public String getOtherTask() {
        return otherTask;
    }

    public void setOtherTask(String otherTask) {
        this.otherTask = otherTask;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceofwork() {
        return placeofwork;
    }

    public void setPlaceofwork(String placeofwork) {
        this.placeofwork = placeofwork;
    }

    public String getWorkingHours() {
        return WorkingHours;
    }

    public void setWorkingHours(String workingHours) {
        WorkingHours = workingHours;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
