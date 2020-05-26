package com.ankit.educommunicate.model;

public class ComposeMessageRequest {

    private String School_Id;
    private String Stud_Id;
    private String MsgData;
    private String Subject;
    private String SenderIP;
    private String Recipient_Id;
    private String SentBy;

    public void setSubject(String subject) {
        Subject = subject;
    }

    public void setSentBy(String sentBy) {
        SentBy = sentBy;
    }

    public void setMsgData(String msgData) {
        MsgData = msgData;
    }

    public void setRecipient_Id(String recipient_Id) {
        Recipient_Id = recipient_Id;
    }

    public String getSubject() {
        return Subject;
    }

    public String getSentBy() {
        return SentBy;
    }

    public String getRecipient_Id() {
        return Recipient_Id;
    }

    public String getMsgData() {
        return MsgData;
    }

    public String getSchool_Id() {
        return School_Id;
    }

    public String getSenderIP() {
        return SenderIP;
    }

    public String getStud_Id() {
        return Stud_Id;
    }

    public void setSchool_Id(String school_Id) {
        School_Id = school_Id;
    }

    public void setSenderIP(String senderIP) {
        SenderIP = senderIP;
    }

    public void setStud_Id(String stud_Id) {
        Stud_Id = stud_Id;
    }
}
