package com.itdan.my_vhr.model;


import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel(value = "邮件消息日志实体类")
public class MailSendLog {

    private  String msgId;

    private Integer empId;

    private Integer status;//0 消息投递中   1 投递成功   2投递失败

    private String routeKey;

    private  String exchange;

    private  Integer count;//重复次数

    private Date tryTime;//重复时间
    private Date createTime;
    private Date updateTime;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getTryTime() {
        return tryTime;
    }

    public void setTryTime(Date tryTime) {
        this.tryTime = tryTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MailSendLog{" +
                "msgId='" + msgId + '\'' +
                ", empId=" + empId +
                ", status=" + status +
                ", routeKey='" + routeKey + '\'' +
                ", exchange='" + exchange + '\'' +
                ", count=" + count +
                ", tryTime=" + tryTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
