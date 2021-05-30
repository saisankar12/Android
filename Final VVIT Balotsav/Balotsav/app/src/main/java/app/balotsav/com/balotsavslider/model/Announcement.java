package app.balotsav.com.balotsavslider.model;

public class Announcement {
    private String notice;
    private String date;
    private String time;

    public Announcement() {
    }

    public Announcement(String notice, String date, String time) {
        this.notice = notice;
        this.time = time;
        this.date = date;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String timeStamp) {
        this.time = timeStamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
