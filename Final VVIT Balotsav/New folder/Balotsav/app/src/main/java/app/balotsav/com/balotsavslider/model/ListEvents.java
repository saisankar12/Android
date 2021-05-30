package app.balotsav.com.balotsavslider.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ListEvents implements Parcelable {
    ArrayList<Event> events;

    public ListEvents() {
    }

    public ListEvents(ArrayList<Event> events) {
        this.events = events;
    }

    protected ListEvents(Parcel in) {
        events = in.createTypedArrayList(Event.CREATOR);
    }

    public static final Creator<ListEvents> CREATOR = new Creator<ListEvents>() {
        @Override
        public ListEvents createFromParcel(Parcel in) {
            return new ListEvents(in);
        }

        @Override
        public ListEvents[] newArray(int size) {
            return new ListEvents[size];
        }
    };

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(events);
    }
}