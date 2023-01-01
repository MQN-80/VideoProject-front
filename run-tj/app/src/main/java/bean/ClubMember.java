package bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClubMember implements Serializable
{
    public List<String> Member_Id;
    public List<String> Member_Name;
    public List<String> Member_Phone;

    public ClubMember(){
        Member_Id=new ArrayList<>();
        Member_Name=new ArrayList<>();
        Member_Phone=new ArrayList<>();
    }

    public void addClubMember(String id,String name,String phone){
        Member_Id.add(id);
        Member_Name.add(name);
        Member_Phone.add(phone);
    }

}
