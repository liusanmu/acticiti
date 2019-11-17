package com.activiti.variable;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: lsm
 * @description:
 * @create: 2019-11-17 15:57
 */
public class Holiday implements Serializable {

    private String id;
    private String holidayName; //姓名
    private Date beginTime;
    private Date endTime; //
    private Float num;  // 请假天数
    private String type; //请假类型
    private String reason; //事由

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Float getNum() {
        return num;
    }

    public void setNum(Float num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
