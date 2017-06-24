package com.mitix.yiming.bean;

/**
 * Created by oldflame on 2017/6/16.
 */
public class DesFiles {
    private Integer id;
    private String name;
    private String designcode;
    private String type;
    private String url;
    private String urlfix;
    private String content;
    private String extend1;
    private String extend2;

    public String getUrlfix() {
        return urlfix;
    }

    public void setUrlfix(String urlfix) {
        this.urlfix = urlfix;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesigncode() {
        return designcode;
    }

    public void setDesigncode(String designcode) {
        this.designcode = designcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
