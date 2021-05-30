package app.balotsav.com.balotsavslider.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chandu on 21/9/18.
 */

public class Schools implements Parcelable {
    private String School_Name, School_Code, Board, Password, HeadMaster_Name, HeadMaster_Email, HeadMaster_PhNo,
            Coordinator_Name, Coordinator_PhNo, Town, District, State, PinCode;

    public Schools() {
    }

    public Schools(String school_Name, String school_Code, String board, String password, String headMaster_Name, String headMaster_Email, String headMaster_PhNo, String coordinator_Name, String coordinator_PhNo, String town, String district, String state, String pinCode) {
        School_Name = school_Name;
        School_Code = school_Code;
        Board = board;
        Password = password;
        HeadMaster_Name = headMaster_Name;
        HeadMaster_Email = headMaster_Email;
        HeadMaster_PhNo = headMaster_PhNo;
        Coordinator_Name = coordinator_Name;
        Coordinator_PhNo = coordinator_PhNo;
        Town = town;
        District = district;
        State = state;
        PinCode = pinCode;
    }

    protected Schools(Parcel in) {
        School_Name = in.readString();
        School_Code = in.readString();
        Board = in.readString();
        Password = in.readString();
        HeadMaster_Name = in.readString();
        HeadMaster_Email = in.readString();
        HeadMaster_PhNo = in.readString();
        Coordinator_Name = in.readString();
        Coordinator_PhNo = in.readString();
        Town = in.readString();
        District = in.readString();
        State = in.readString();
        PinCode = in.readString();
    }

    public static final Creator<Schools> CREATOR = new Creator<Schools>() {
        @Override
        public Schools createFromParcel(Parcel in) {
            return new Schools(in);
        }

        @Override
        public Schools[] newArray(int size) {
            return new Schools[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(School_Name);
        dest.writeString(School_Code);
        dest.writeString(Board);
        dest.writeString(Password);
        dest.writeString(HeadMaster_Name);
        dest.writeString(HeadMaster_Email);
        dest.writeString(HeadMaster_PhNo);
        dest.writeString(Coordinator_Name);
        dest.writeString(Coordinator_PhNo);
        dest.writeString(Town);
        dest.writeString(District);
        dest.writeString(State);
        dest.writeString(PinCode);
    }

    public String getSchool_Name() {
        return School_Name;
    }

    public void setSchool_Name(String school_Name) {
        School_Name = school_Name;
    }

    public String getSchool_Code() {
        return School_Code;
    }

    public void setSchool_Code(String school_Code) {
        School_Code = school_Code;
    }

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getHeadMaster_Name() {
        return HeadMaster_Name;
    }

    public void setHeadMaster_Name(String headMaster_Name) {
        HeadMaster_Name = headMaster_Name;
    }

    public String getHeadMaster_Email() {
        return HeadMaster_Email;
    }

    public void setHeadMaster_Email(String headMaster_Email) {
        HeadMaster_Email = headMaster_Email;
    }

    public String getHeadMaster_PhNo() {
        return HeadMaster_PhNo;
    }

    public void setHeadMaster_PhNo(String headMaster_PhNo) {
        HeadMaster_PhNo = headMaster_PhNo;
    }

    public String getCoordinator_Name() {
        return Coordinator_Name;
    }

    public void setCoordinator_Name(String coordinator_Name) {
        Coordinator_Name = coordinator_Name;
    }

    public String getCoordinator_PhNo() {
        return Coordinator_PhNo;
    }

    public void setCoordinator_PhNo(String coordinator_PhNo) {
        Coordinator_PhNo = coordinator_PhNo;
    }

    public String getTown() {
        return Town;
    }

    public void setTown(String town) {
        Town = town;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public static Creator<Schools> getCREATOR() {
        return CREATOR;
    }
}
