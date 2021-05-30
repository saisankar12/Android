package in.apssdc.attendance.common;

public class DataModel {

    private int iconid;
    private String title;

    public DataModel(int iconid, String title)
    {
        this.iconid=iconid;
        this.title=title;
    }
    public int getIconid() {
        return iconid;
    }

    public void setIconid(int iconid) {
        this.iconid = iconid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
