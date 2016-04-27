package com.lzl_rjkx.doctor.bean;

import java.util.List;

/**
 * Created by lzl_os on 16/3/10.
 */
public class Advert {

    /**
     * code : 0
     * data : {"adv":[{"newsId":12,"newsTitle":"ccc","newsImg":"http://test.waikegj.com/SurgicalKeeper/dataupload/adv/2016-03-15/ad_5079225952202404.jpg"},{"newsId":2,"newsTitle":"test2","newsImg":"http://test.waikegj.com/SurgicalKeeper/dataupload/adv/yiyuan2.jpg"},{"newsId":1,"newsTitle":"test1","newsImg":"http://test.waikegj.com/SurgicalKeeper/dataupload/adv/yiyuan1.jpg"}]}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * newsId : 12
         * newsTitle : ccc
         * newsImg : http://test.waikegj.com/SurgicalKeeper/dataupload/adv/2016-03-15/ad_5079225952202404.jpg
         */

        private List<AdvBean> adv;

        public List<AdvBean> getAdv() {
            return adv;
        }

        public void setAdv(List<AdvBean> adv) {
            this.adv = adv;
        }

        public static class AdvBean {
            private int newsId;
            private String newsTitle;
            private String newsImg;

            public int getNewsId() {
                return newsId;
            }

            public void setNewsId(int newsId) {
                this.newsId = newsId;
            }

            public String getNewsTitle() {
                return newsTitle;
            }

            public void setNewsTitle(String newsTitle) {
                this.newsTitle = newsTitle;
            }

            public String getNewsImg() {
                return newsImg;
            }

            public void setNewsImg(String newsImg) {
                this.newsImg = newsImg;
            }
        }
    }
}
