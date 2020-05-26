package com.ankit.educommunicate.model;

import java.io.Serializable;

public class MessagesModel implements Serializable {

    private String Msg_Id;
    private String Subject;
    private String MsgData;
    private String SentDate;
    private String SentTime;
    private String Sender_Id;
    private String Recipient_Id;
    private String SentBy;
    private String MsgType;

    public String getMsg_Id() {
        return Msg_Id;
    }

    public String getMsgData() {
        return MsgData;
    }

    public String getMsgType() {
        return MsgType;
    }

    public String getRecipient_Id() {
        return Recipient_Id;
    }

    public String getSender_Id() {
        return Sender_Id;
    }

    public String getSentBy() {
        return SentBy;
    }

    public String getSentDate() {
        return SentDate;
    }

    public String getSentTime() {
        return SentTime;
    }

    public String getSubject() {
        return Subject;
    }

    public void setMsg_Id(String msg_Id) {
        Msg_Id = msg_Id;
    }

    public void setMsgData(String msgData) {
        MsgData = msgData;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public void setRecipient_Id(String recipient_Id) {
        Recipient_Id = recipient_Id;
    }

    public void setSender_Id(String sender_Id) {
        Sender_Id = sender_Id;
    }

    public void setSentBy(String sentBy) {
        SentBy = sentBy;
    }

    public void setSentDate(String sentDate) {
        SentDate = sentDate;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public void setSentTime(String sentTime) {
        SentTime = sentTime;
    }
}
