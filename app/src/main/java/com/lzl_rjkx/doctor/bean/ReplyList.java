package com.lzl_rjkx.doctor.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ReplyList {

    /**
     * code : 0
     * data : {"replyList":[{"replyTime":"2016-04-08 10:32","replyUserId":705,"replyUserType":3,"replyUserName":"郝东生","replyId":79,"replyContent":"Guys are I'm o"},{"replyTime":"2016-04-08 10:32","replyUserId":705,"replyUserType":3,"replyUserName":"郝东生","replyId":78,"replyContent":"Rudy "},{"replyTime":"2016-04-08 10:28","replyUserId":705,"replyUserType":3,"replyUserName":"郝东生","replyId":76,"replyContent":"We "},{"replyTime":"2016-04-08 10:28","replyUserId":705,"replyUserType":3,"replyUserName":"郝东生","replyId":75,"replyContent":"Really fun but "}]}
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
         * replyTime : 2016-04-08 10:32
         * replyUserId : 705
         * replyUserType : 3
         * replyUserName : 郝东生
         * replyId : 79
         * replyContent : Guys are I'm o
         */

        private List<ReplyListBean> replyList;

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class ReplyListBean {
            private String replyTime;
            private int replyUserId;
            private int replyUserType;
            private String replyUserName;
            private int replyId;
            private String replyContent;

            public String getReplyTime() {
                return replyTime;
            }

            public void setReplyTime(String replyTime) {
                this.replyTime = replyTime;
            }

            public int getReplyUserId() {
                return replyUserId;
            }

            public void setReplyUserId(int replyUserId) {
                this.replyUserId = replyUserId;
            }

            public int getReplyUserType() {
                return replyUserType;
            }

            public void setReplyUserType(int replyUserType) {
                this.replyUserType = replyUserType;
            }

            public String getReplyUserName() {
                return replyUserName;
            }

            public void setReplyUserName(String replyUserName) {
                this.replyUserName = replyUserName;
            }

            public int getReplyId() {
                return replyId;
            }

            public void setReplyId(int replyId) {
                this.replyId = replyId;
            }

            public String getReplyContent() {
                return replyContent;
            }

            public void setReplyContent(String replyContent) {
                this.replyContent = replyContent;
            }
        }
    }
}
