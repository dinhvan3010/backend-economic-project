package net.codejava.response;



import org.springframework.http.HttpStatus;

import net.codejava.utils.DateUtil;
import net.codejava.utils.StaticData;


public class StatusResp {
    private int errorCode;

    private String message;

    private long timeStamp;

    private Object data;


    public StatusResp() {
        errorCode = HttpStatus.OK.value();
        message = HttpStatus.OK.name();
        this.timeStamp = DateUtil.getCurrentTimeStamp();
    }

    public StatusResp(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timeStamp = DateUtil.getCurrentTimeStamp();
    }

    public void setError(StaticData.ERROR_CODE error){
        this.errorCode = error.getCode();
        this.message = error.getMessage();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
