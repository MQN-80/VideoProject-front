package bean;

import org.json.JSONException;
import org.json.JSONObject;

public class UserMessage {
    private int code;  //代表信息类型
    private int member_id; //代表成员id
    private String member_name; //代表成员昵称
    private String group_id; //代表群id
    private String group_name; //代表群名称
    public UserMessage(int code,int member_id,String member_name,String group_id,String group_name){
        this.code=code;
        this.member_id=member_id;
        this.member_name=member_name;
        this.group_id=group_id;
        this.group_name=group_name;
    }
    private String message;  //聊天具体内容

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    /**
     *
     * @return 将该类的属性以json形式进行封装
     * @throws JSONException
     */
    public String toJson() throws JSONException {
        JSONObject message=new JSONObject();
        message.put("code",this.code);
        message.put("member_id",this.member_id);
        message.put("member_name",this.member_name);
        message.put("group_id",this.group_id);
        message.put("group_name",this.group_name);
        message.put("message",this.message);
        return message.toString();
    }
}
