package com.zsq.pojo;

import lombok.Data;

/**
 * @author 张子涵
 * @create 2022/5/23 18:46
 */
@Data
public class Chapter {
    private Integer section_id;
    private Integer course_id;
    private String section_name;
    private String section_type;
    private Integer section_video;
    private String can_try;
    private Integer order_by;
    private Integer parent_id;
    private String remark;
    private String del_flag;

}
