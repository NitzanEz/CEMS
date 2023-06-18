package logic;

import java.io.Serializable;

public class RequestTime implements Serializable {
    private String examID;
    private String requestID;
    private String requestedBy;
    private String reason;
    private int extraTime;

    public String getRequestID() {
        return requestID;
    }

    public void setId(String requestID) {
        this.requestID = requestID;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String courseName) {
        this.examID = courseName;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public int getExtraTime() {
        return extraTime;
    }

    public void setExtraTime(int extraTime) {
        this.extraTime = extraTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RequestTime(String requestID, String examID, String requestedBy, int extraTime, String reason) {
        this.requestID = requestID;
        this.examID = examID;
        this.requestedBy = requestedBy;
        this.extraTime = extraTime;
        this.reason = reason;
    }

    public RequestTime(String examID, String requestedBy, int extraTime, String reason) {
        this.examID = examID;
        this.requestedBy = requestedBy;
        this.extraTime = extraTime;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "RequestTime [requestID=" + requestID + ", examID=" + examID + ", requestedBy=" + requestedBy
                + ", reason=" + reason + ", extraTime=" + extraTime + "]";
    }
}
