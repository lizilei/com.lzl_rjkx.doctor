package com.lzl_rjkx.doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class HotArticle {


    /**
     * code : 0
     * data : {"list":[{"favCount":0,"msgType":2,"mediaUrl":"","msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":3,"opId":703,"msgId":263,"readCount":0,"zanCount":0,"msgContent":"886不是吗","opName":"李春","msgTitle":"测试1","reCount":3,"sTime":"2016-04-08 10:04"},{"favCount":1,"msgType":2,"mediaUrl":"","msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":1,"opId":1,"msgId":268,"readCount":0,"zanCount":0,"msgContent":"<p>这是一个文章这是一个文章这是一个文章这是一个文章这是一个文章这是一个文章<br><\/p>","opName":"ADMIN","msgTitle":"这是一个文章","reCount":4,"sTime":"2016-04-08 10:20"},{"favCount":0,"msgType":2,"mediaUrl":"","msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":3,"opId":705,"msgId":269,"readCount":0,"zanCount":0,"msgContent":"是我想对<img alt='' src='http://test.waikegj.com/SurgicalKeeper/dataupload/other/2016-04-08/1477960010722950.png' />","opName":"郝东生","msgTitle":"标题","reCount":0,"sTime":"2016-04-08 11:11"},{"favCount":0,"msgType":2,"mediaUrl":"","msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":3,"opId":705,"msgId":274,"readCount":0,"zanCount":0,"msgContent":"The fact ","opName":"郝东生","msgTitle":"The ","reCount":0,"sTime":"2016-04-08 14:35"},{"favCount":0,"msgType":2,"mediaUrl":"","msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":3,"opId":705,"msgId":277,"readCount":0,"zanCount":0,"msgContent":"I love it and ","opName":"郝东生","msgTitle":"The ","reCount":0,"sTime":"2016-04-08 14:41"},{"favCount":0,"msgType":2,"mediaUrl":"","msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":3,"opId":705,"msgId":278,"readCount":0,"zanCount":0,"msgContent":"You","opName":"郝东生","msgTitle":"The best of ","reCount":0,"sTime":"2016-04-08 14:42"},{"favCount":0,"msgType":2,"mediaUrl":"","msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":3,"opId":705,"msgId":279,"readCount":0,"zanCount":0,"msgContent":"The only ","opName":"郝东生","msgTitle":"You","reCount":0,"sTime":"2016-04-08 14:43"},{"favCount":0,"msgType":2,"mediaUrl":"","msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":3,"opId":705,"msgId":280,"readCount":0,"zanCount":0,"msgContent":"I don't have ","opName":"郝东生","msgTitle":"Yeah I'm ","reCount":0,"sTime":"2016-04-08 14:43"},{"favCount":0,"msgType":2,"mediaUrl":"","msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":3,"opId":705,"msgId":281,"readCount":0,"zanCount":0,"msgContent":"The best ","opName":"郝东生","msgTitle":"It would ","reCount":0,"sTime":"2016-04-08 14:44"},{"favCount":0,"msgType":2,"mediaUrl":"","msgImg":"http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg","opType":3,"opId":705,"msgId":282,"readCount":0,"zanCount":0,"msgContent":"I don't have ","opName":"郝东生","msgTitle":"Uii I","reCount":0,"sTime":"2016-04-08 14:44"}]}
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
         * favCount : 0
         * msgType : 2
         * mediaUrl :
         * msgImg : http://attched.yiyuan591.com/dataupload/lecture/photo/LECTURE_PAV_6224990476792542.jpg
         * opType : 3
         * opId : 703
         * msgId : 263
         * readCount : 0
         * zanCount : 0
         * msgContent : 886不是吗
         * opName : 李春
         * msgTitle : 测试1
         * reCount : 3
         * sTime : 2016-04-08 10:04
         */

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            private int favCount;
            private int msgType;
            private String mediaUrl;
            private String msgImg;
            private int opType;
            private int opId;
            private int msgId;
            private int readCount;
            private int zanCount;
            private String msgContent;
            private String opName;
            private String msgTitle;
            private int reCount;
            private String sTime;

            public int getFavCount() {
                return favCount;
            }

            public void setFavCount(int favCount) {
                this.favCount = favCount;
            }

            public int getMsgType() {
                return msgType;
            }

            public void setMsgType(int msgType) {
                this.msgType = msgType;
            }

            public String getMediaUrl() {
                return mediaUrl;
            }

            public void setMediaUrl(String mediaUrl) {
                this.mediaUrl = mediaUrl;
            }

            public String getMsgImg() {
                return msgImg;
            }

            public void setMsgImg(String msgImg) {
                this.msgImg = msgImg;
            }

            public int getOpType() {
                return opType;
            }

            public void setOpType(int opType) {
                this.opType = opType;
            }

            public int getOpId() {
                return opId;
            }

            public void setOpId(int opId) {
                this.opId = opId;
            }

            public int getMsgId() {
                return msgId;
            }

            public void setMsgId(int msgId) {
                this.msgId = msgId;
            }

            public int getReadCount() {
                return readCount;
            }

            public void setReadCount(int readCount) {
                this.readCount = readCount;
            }

            public int getZanCount() {
                return zanCount;
            }

            public void setZanCount(int zanCount) {
                this.zanCount = zanCount;
            }

            public String getMsgContent() {
                return msgContent;
            }

            public void setMsgContent(String msgContent) {
                this.msgContent = msgContent;
            }

            public String getOpName() {
                return opName;
            }

            public void setOpName(String opName) {
                this.opName = opName;
            }

            public String getMsgTitle() {
                return msgTitle;
            }

            public void setMsgTitle(String msgTitle) {
                this.msgTitle = msgTitle;
            }

            public int getReCount() {
                return reCount;
            }

            public void setReCount(int reCount) {
                this.reCount = reCount;
            }

            public String getSTime() {
                return sTime;
            }

            public void setSTime(String sTime) {
                this.sTime = sTime;
            }
        }
    }
}
