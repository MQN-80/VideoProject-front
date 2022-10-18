package bean;
/**
 * Copyright 2022 bejson.com
 */

/**
 * Auto-generated: 2022-10-18 23:46:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class LoginEntity {

    private int code;
    private String msg;

    public Data data;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public static class Data {

        private String tokenName;
        private String tokenValue;
        private boolean isLogin;
        private String loginId;
        private String loginType;
        private long tokenTimeout;
        private long sessionTimeout;
        private int tokenSessionTimeout;
        private int tokenActivityTimeout;
        private String loginDevice;
        private String tag;
        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
        }
        public String getTokenName() {
            return tokenName;
        }

        public void setTokenValue(String tokenValue) {
            this.tokenValue = tokenValue;
        }
        public String getTokenValue() {
            return tokenValue;
        }

        public void setIsLogin(boolean isLogin) {
            this.isLogin = isLogin;
        }
        public boolean getIsLogin() {
            return isLogin;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }
        public String getLoginId() {
            return loginId;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }
        public String getLoginType() {
            return loginType;
        }

        public void setTokenTimeout(long tokenTimeout) {
            this.tokenTimeout = tokenTimeout;
        }
        public long getTokenTimeout() {
            return tokenTimeout;
        }

        public void setSessionTimeout(long sessionTimeout) {
            this.sessionTimeout = sessionTimeout;
        }
        public long getSessionTimeout() {
            return sessionTimeout;
        }

        public void setTokenSessionTimeout(int tokenSessionTimeout) {
            this.tokenSessionTimeout = tokenSessionTimeout;
        }
        public int getTokenSessionTimeout() {
            return tokenSessionTimeout;
        }

        public void setTokenActivityTimeout(int tokenActivityTimeout) {
            this.tokenActivityTimeout = tokenActivityTimeout;
        }
        public int getTokenActivityTimeout() {
            return tokenActivityTimeout;
        }

        public void setLoginDevice(String loginDevice) {
            this.loginDevice = loginDevice;
        }
        public String getLoginDevice() {
            return loginDevice;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
        public String getTag() {
            return tag;
        }

    }
}
/**
 * Copyright 2022 bejson.com
 */


/**
 * Auto-generated: 2022-10-18 23:46:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */

