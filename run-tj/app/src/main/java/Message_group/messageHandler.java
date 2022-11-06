package Message_group;

public interface messageHandler {
    public void sendMessage(String messageBody);   //发送信息到服务端
    public String getMessage();   //监听服务端信息
}
