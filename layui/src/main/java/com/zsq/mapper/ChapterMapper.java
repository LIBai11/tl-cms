package com.zsq.mapper;

import com.zsq.pojo.Chapter;
import com.zsq.pojo.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 张子涵
 * @create 2022/5/23 18:40
 */
@Mapper
public interface ChapterMapper {

    @Insert("insert into oc_course_section (course_id, section_name, order_by, remark) " +
            "values(#{course_id}, #{section_name}, #{order_by}, #{remark})")
    void addFatherChapter(Chapter chapter);

    @Insert("insert into oc_course_section (course_id,section_name,parent_id,can_try,order_by,remark) " +
            "values(#{course_id},#{section_name},#{section_id},#{can_try},#{order_by},#{remark})")
    void addSonChapter(Chapter chapter);

    @Select("select * from oc_course_section where section_id=#{id} and del_flag = 0")
    Chapter findById(Integer id);

    List<Chapter> getChapter(Integer course_id);

    @Select("select * from oc_course_section where parent_id = #{parent_id} and del_flag = 0")
    List<Chapter> findSons(Integer parent_id);

    @Update("update oc_course_section set section_name=#{section_name},order_by=#{order_by},remark=#{remark} where section_id=#{section_id}")
    void updateFather(Chapter chapter);

    @Update("update oc_course_section set section_name=#{section_name},can_try=#{can_try},order_by=#{order_by},remark=#{remark} where section_id=#{section_id}")
    void updateSon(Chapter chapter);

    @Delete("update oc_course_section set del_flag = 1 where section_id=#{id} or parent_id=#{id}")
    void delFather(Integer id);

    @Delete(" update oc_course_section set del_flag = 1 where section_id=#{id}")
    void delSon(Integer id);

}
