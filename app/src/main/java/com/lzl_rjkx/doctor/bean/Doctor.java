package com.lzl_rjkx.doctor.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/4/5.
 */
@Table(name = "doctor")
public class Doctor {
    @Column(name = "msgId", isId = true, autoGen = false)
    private int msgId;
    @Column(name = "zanState")
    private int zanState;
    @Column(name = "collectState")
    private int collectState;

    public Doctor() {
    }

    public Doctor(int msgId, int zanState, int collectState) {
        this.msgId = msgId;
        this.zanState = zanState;
        this.collectState = collectState;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getZanState() {
        return zanState;
    }

    public void setZanState(int zanState) {
        this.zanState = zanState;
    }

    public int getCollectState() {
        return collectState;
    }

    public void setCollectState(int collectState) {
        this.collectState = collectState;
    }
}
