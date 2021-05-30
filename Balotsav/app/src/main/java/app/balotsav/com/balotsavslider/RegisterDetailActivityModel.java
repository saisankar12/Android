package app.balotsav.com.balotsavslider;

public class RegisterDetailActivityModel {
    String EventName="";
    int JnCount, SubCount, SrCount,team;

    public RegisterDetailActivityModel() {

    }

    public RegisterDetailActivityModel(String eventName, int jnBoyCount, int subjnrBoyCount, int srBoyCount) {
        EventName = eventName;
        JnCount = jnBoyCount;
        SubCount = subjnrBoyCount;
        SrCount = srBoyCount;team=-99;
    }

    public RegisterDetailActivityModel(String eventName,int j,int sj,int s, int team) {
        EventName = eventName;
        this.team = team;JnCount=-99;SubCount=-99;SrCount=-99;
    }

    public RegisterDetailActivityModel(String eventName, int jnCount, int srCount) {
        EventName = eventName;
        JnCount = jnCount;
        SrCount = srCount;SubCount=-99;team=-99;
    }
    public RegisterDetailActivityModel(String eventName, int srCount) {
        EventName = eventName;
        SrCount = srCount;JnCount=-99;SubCount=-99;team=-99;
    }
    public RegisterDetailActivityModel(String eventName, int subjCount,int srcount,int x,int y,int z) {
        EventName = eventName;
        SrCount = -99;JnCount=-99;SubCount=subjCount;team=-99;
    }
}
