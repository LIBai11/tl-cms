package com.zsq.pojo;

import lombok.Data;

@Data
public class Course {
    private Integer course_id;  //课程id
    private String course_title;  //课程标题
    private String course_subtitle;  //课程副标题
    private String course_desc;  //课程描述
    private String course_detail;  //课程详情
    private String is_free;  //是否免费
    private double course_price;  // 课程价格
    private String is_discount;  //是否打折
    private String discount_desc;  //打折描述
    private double discount_price;  //折后价
    private String course_level;  //课程难度
    private Integer participations_count;  //参与人数
    private String course_cover; //课程封面
    private String course_banner;  //课程轮播图
    private String is_carousel;  //是否轮播
    private String is_recommend;  //是否精品推荐
    private String course_status;  //课程状态
    private String show_detail_img;  //是否启用图片详情
    private String detail_img;  //图片详情
    private String enable;  //是否启用
    private String del_flag;  //是否删除
    private String remark;  //备注说明
    private String course_if_foreign; //是否对外展示
    private Integer view_counts;

    /**
     * 查询字段,专题名称
     */
    private Integer subject_id;
}
