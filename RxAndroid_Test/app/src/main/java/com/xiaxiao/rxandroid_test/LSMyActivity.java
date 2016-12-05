package com.xiaxiao.rxandroid_test;

import java.util.ArrayList;

/**
 * Created by zhangjie on 5/21/16.
 */
public class LSMyActivity extends BaseModel{
    public int id;
    public int orderid;
    public int club_id;
    public int leader_userid;
    public int create_userid;
    public int topic_id;
    public int width;
    public int height;
    public int is_comment;
    public int pay_type;
    public int pay_status;
    public int applyNum;
    public int star;
    public int flag;
    public String leader_mobile;
    public int batch;

    public double consts;
    public double totprice;

    public String ordercode;
    public String topic_title;
    public String title;
    public String activity_code;
    public String image;
    public String createTime;
    public String comment;
    public String leader_nickname;
    public String create_user_nickname;
    public String create_user_headicon;
    public String leader_headicon;
    public String starttime;
//  备注
    public String remark;
//    取消报名理由
    public String reason;

    public ArrayList<String> leader_tag;
    public ArrayList<Applicant> apply_info;
    public ArrayList<String[]> orderbglist;

    public static class Applicant extends BaseModel{
//        public String name;
//        public String names;
//        public String mobile;
//        public String mobiles;
//        public String consts;
//        public String constss;

        public String name;
        public String sex;
        public String mobile;
        public String qq;
        public String postaladdress;
        public String consts;
        public String credentials;
        public String phone;

        @Override
        public String toString() {
            return "Applicant{" +
                    "consts='" + consts + '\'' +
                    ", name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", qq='" + qq + '\'' +
                    ", postaladdress='" + postaladdress + '\'' +
                    ", credentials='" + credentials + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LSMyActivity{" +
                "activity_code='" + activity_code + '\'' +
                ", id=" + id +
                ", orderid=" + orderid +
                ", club_id=" + club_id +
                ", leader_userid=" + leader_userid +
                ", create_userid=" + create_userid +
                ", topic_id=" + topic_id +
                ", width=" + width +
                ", height=" + height +
                ", is_comment=" + is_comment +
                ", pay_type=" + pay_type +
                ", pay_status=" + pay_status +
                ", applyNum=" + applyNum +
                ", star=" + star +
                ", flag=" + flag +
                ", leader_mobile='" + leader_mobile + '\'' +
                ", batch=" + batch +
                ", consts=" + consts +
                ", totprice=" + totprice +
                ", ordercode='" + ordercode + '\'' +
                ", topic_title='" + topic_title + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", createTime='" + createTime + '\'' +
                ", comment='" + comment + '\'' +
                ", leader_nickname='" + leader_nickname + '\'' +
                ", create_user_nickname='" + create_user_nickname + '\'' +
                ", create_user_headicon='" + create_user_headicon + '\'' +
                ", leader_headicon='" + leader_headicon + '\'' +
                ", starttime='" + starttime + '\'' +
                ", remark='" + remark + '\'' +
                ", reason='" + reason + '\'' +
                ", leader_tag=" + leader_tag +
                ", apply_info=" + apply_info +
                ", orderbglist=" + orderbglist +
                '}';
    }
}
