package com.xiaxiao.rxandroid_test;

import java.util.List;

/**
 * Created by Administrator on 2016/11/23.
 */
public class ClassifyBean {

    /**
     * status : true
     * tngou : [{"description":"减肥瘦身,美丽一生,减肥资讯 瘦身资讯 ,减肥瘦身健康知识,减肥瘦身信息专题","id":11,
     * "keywords":"减肥瘦身","name":"减肥瘦身","seq":1,"title":"减肥瘦身"},{"description":"私密生活 两性健康 两性健康知识
     * 两性专题 性生活知识 私密资讯 ","id":7,"keywords":"私密生活","name":"私密生活","seq":2,"title":"私密生活"},
     * {"description":"女性保养 女性健康资讯 女性生活 女性健康 注意女性健康 女性专题 女性健康网","id":5,"keywords":"女性保养",
     * "name":"女性保养","seq":3,"title":"女性保养"},{"description":"男性健康 男性健康知识 男性健康资讯 男性专题 男性健康专题",
     * "id":4,"keywords":"男性健康","name":"男性健康","seq":4,"title":"男性健康"},{"description":"孕婴手册 孕妇健康
     * 怀孕健康知识 育儿健康 孕婴专题 孕婴健康专题","id":6,"keywords":"孕婴手册","name":"孕婴手册","seq":5,"title":"孕婴手册"},
     * {"description":"夫妻关系 夫妻情感 两性情感 健康情感 夫妻生活 夫妻专题 两性专题","id":13,"keywords":"夫妻情感",
     * "name":"夫妻情感","seq":5,"title":"夫妻情感"},{"description":"育儿宝典 婴儿护理 教育 健康知识 育儿知识 ","id":8,
     * "keywords":"育儿宝典","name":"育儿宝典","seq":6,"title":"育儿宝典"},{"description":"健康饮食 食品健康 饮食健康
     * 生活饮食 食品知识   饮食专题 ","id":3,"keywords":"健康饮食","name":"健康饮食","seq":7,"title":"健康饮食"},
     * {"description":"医疗护理 生活健康  预防疾病 疾病治疗 医疗知识 护理知识 ","id":12,"keywords":"医疗护理","name":"医疗护理",
     * "seq":7,"title":"医疗护理"},{"description":"老人健康 老人护理 老人生活 长寿知识 老人专题","id":1,
     * "keywords":"老人健康","name":"老人健康","seq":8,"title":"老人健康"},{"description":"孩子健康 孩子教育 孩子生活
     * 孩子成长 孩子健康专题","id":2,"keywords":"孩子健康","name":"孩子健康","seq":8,"title":"孩子健康"},
     * {"description":"四季养生 春节养生 夏季养生 秋季养生 冬季养生 养生健康知识 养生专题 ","id":10,"keywords":"四季养生",
     * "name":"四季养生","seq":10,"title":"四季养生"},{"description":"心里健康 心里疾病 心里医疗 心里咨询 健康知识 ","id":9,
     * "keywords":"心里健康","name":"心里健康","seq":11,"title":"心里健康"}]
     */

    private boolean status;
    private List<TngouBean> tngou;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTngou(List<TngouBean> tngou) {
        this.tngou = tngou;
    }

    public boolean getStatus() {
        return status;
    }

    public List<TngouBean> getTngou() {
        return tngou;
    }

    public static class TngouBean {
        /**
         * description : 减肥瘦身,美丽一生,减肥资讯 瘦身资讯 ,减肥瘦身健康知识,减肥瘦身信息专题
         * id : 11
         * keywords : 减肥瘦身
         * name : 减肥瘦身
         * seq : 1
         * title : 减肥瘦身
         */

        private String description;
        private int id;
        private String keywords;
        private String name;
        private int seq;
        private String title;

        public void setDescription(String description) {
            this.description = description;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }

        public String getKeywords() {
            return keywords;
        }

        public String getName() {
            return name;
        }

        public int getSeq() {
            return seq;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "TngouBean{" +
                    "description='" + description + '\'' +
                    ", id=" + id +
                    ", keywords='" + keywords + '\'' +
                    ", name='" + name + '\'' +
                    ", seq=" + seq +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ClassifyBean{" +
                "status=" + status +
                ", tngou=" + tngou +
                '}';
    }
}
