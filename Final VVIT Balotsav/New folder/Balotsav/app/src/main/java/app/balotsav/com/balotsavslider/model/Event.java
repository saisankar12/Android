package app.balotsav.com.balotsavslider.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Event implements Parcelable {
    String name;
    int j, sj, s;
    int max, team;
    boolean registered;

    public Event() {
    }

    public Event(String name, int j, int sj, int s, int max, int team, boolean registered) {
        this.name = name;
        this.j = j;
        this.sj = sj;
        this.s = s;
        this.max = max;
        this.team = team;
        this.registered = registered;
    }

    protected Event(Parcel in) {
        name = in.readString();
        j = in.readInt();
        sj = in.readInt();
        s = in.readInt();
        max = in.readInt();
        team = in.readInt();
        registered = in.readByte() != 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getSj() {
        return sj;
    }

    public void setSj(int sj) {
        this.sj = sj;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(j);
        dest.writeInt(sj);
        dest.writeInt(s);
        dest.writeInt(max);
        dest.writeInt(team);
        dest.writeByte((byte) (registered ? 1 : 0));
    }
}
