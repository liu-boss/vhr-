package com.itdan.my_vhr.model;

import io.swagger.annotations.ApiModel;

import java.util.List;


@ApiModel(value = "分页查询结果集",description = "分页查询结果描述")
public class RespPageBean {
    private Long total;
    private List<?> data;

    public RespPageBean() {

    }

    public RespPageBean(Long total, List<?> data) {
        this.total = total;
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespPageBean{" +
                "total=" + total +
                ", data=" + data +
                '}';
    }
}
