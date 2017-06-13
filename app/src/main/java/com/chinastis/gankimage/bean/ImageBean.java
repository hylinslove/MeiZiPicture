package com.chinastis.gankimage.bean;

import java.util.List;

/**
 * Created by MENG on 2017/6/13.
 */

public class ImageBean {

    /**
     * error : false
     * results : [{"_id":"593dde44421aa92c73b647f5","createdAt":"2017-06-12T08:20:20.475Z","desc":"6-12","publishedAt":"2017-06-12T11:11:11.25Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgi3vd6irmj20u011i439.jpg","used":true,"who":"代码家"},{"_id":"5939fcb1421aa92c7be61bd5","createdAt":"2017-06-09T09:41:05.305Z","desc":"6-9","publishedAt":"2017-06-09T12:50:03.131Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgepc1lpvfj20u011i0wv.jpg","used":true,"who":"dmj"},{"_id":"5938c377421aa92c7be61bcb","createdAt":"2017-06-08T11:24:39.838Z","desc":"6-8","publishedAt":"2017-06-08T11:27:47.21Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgdmpxi7erj20qy0qyjtr.jpg","used":true,"who":"daimajia"},{"_id":"593774e7421aa92c79463375","createdAt":"2017-06-07T11:37:11.749Z","desc":"6-7","publishedAt":"2017-06-07T11:43:31.396Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgchgnfn7dj20u00uvgnj.jpg","used":true,"who":"daimajia"},{"_id":"5936223c421aa92c73b647c7","createdAt":"2017-06-06T11:32:12.609Z","desc":"6-6","publishedAt":"2017-06-06T11:36:13.568Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fgbbp94y9zj20u011idkf.jpg","used":true,"who":"dmj"},{"_id":"5934d287421aa92c73b647ba","createdAt":"2017-06-05T11:39:51.13Z","desc":"6-5","publishedAt":"2017-06-05T11:44:53.909Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fga6auw8ycj20u00u00uw.jpg","used":true,"who":"daimajia"},{"_id":"5931fd87421aa92c769a8c03","createdAt":"2017-06-03T08:06:31.648Z","desc":"6-3","publishedAt":"2017-06-03T11:15:41.272Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/d23c7564ly1fg7ow5jtl9j20pb0pb4gw.jpg","used":true,"who":"dmj"},{"_id":"5930e560421aa92c7be61b93","createdAt":"2017-06-02T12:11:12.694Z","desc":"6-2","publishedAt":"2017-06-02T12:26:37.346Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/d23c7564ly1fg6qckyqxkj20u00zmaf1.jpg","used":true,"who":"daimajia"},{"_id":"592f5798421aa92c769a8bf1","createdAt":"2017-06-01T07:54:00.225Z","desc":"6-1","publishedAt":"2017-06-01T14:35:22.88Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fg5dany6uzj20u011iq60.jpg","used":true,"who":"daimajia"},{"_id":"5927bc01421aa92c79463324","createdAt":"2017-05-26T13:24:17.785Z","desc":"5-26","publishedAt":"2017-05-26T13:43:32.128Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg","used":true,"who":"daimajia"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 593dde44421aa92c73b647f5
         * createdAt : 2017-06-12T08:20:20.475Z
         * desc : 6-12
         * publishedAt : 2017-06-12T11:11:11.25Z
         * source : chrome
         * type : 福利
         * url : https://ws1.sinaimg.cn/large/610dc034ly1fgi3vd6irmj20u011i439.jpg
         * used : true
         * who : 代码家
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
