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
    public List<String> Rank;

    public ClubMember(){
        Member_Id=new ArrayList<>();
        Member_Name=new ArrayList<>();
        Rank=new ArrayList<>();
    }

    public void addClubMember(String id,String name,String rank){
        Member_Id.add(id);
        Member_Name.add(name);
        Rank.add(rank);
    }

}
