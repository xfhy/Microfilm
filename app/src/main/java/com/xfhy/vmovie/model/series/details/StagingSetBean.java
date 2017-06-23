package com.xfhy.vmovie.model.series.details;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xfhy on 2017/6/21.
 * 系列详情页的分期  的bean
 * 比如从41-60集    81-85集
 */

public class StagingSetBean {

    /**
     * 从哪一集到哪一集
     */
    @SerializedName("from_to")
    private String fromTo;

    /**
     * 这一分期内的所有电影详情   比如从41-60集的所有数据
     */
    @SerializedName("list")
    private List<SetBean> setBeanList;

    public StagingSetBean(String fromTo, List<SetBean> setBeanList) {
        this.fromTo = fromTo;
        this.setBeanList = setBeanList;
    }

    public String getFromTo() {
        return fromTo;
    }

    public void setFromTo(String fromTo) {
        this.fromTo = fromTo;
    }

    public List<SetBean> getSetBeanList() {
        return setBeanList;
    }

    public void setSetBeanList(List<SetBean> setBeanList) {
        this.setBeanList = setBeanList;
    }

    @Override
    public String toString() {
        return "StagingSetBean{" +
                "fromTo='" + fromTo + '\'' +
                ", setBeanList=" + setBeanList +
                '}';
    }
}
