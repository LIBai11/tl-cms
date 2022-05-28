package com.zsq.mapper;

import com.zsq.pojo.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubjectMapper {
    List<Subject> getSubjectList(Subject subject);

    @Delete(" update oc_subject set del_flag = 1 where subject_id=#{id}")
    void delSuj(Integer id);


    @Select("select * from oc_subject where subject_id=#{id}")
    Subject findById(Integer id);
    @Insert("insert into oc_subject (subject_title,subject_desc,subject_banner,subject_status,remark) " +
            "values(#{subject_title},#{subject_desc},#{subject_banner},#{subject_status},#{remark})")
    void addSuj(Subject subject);

    @Update("update oc_subject set subject_title=#{subject_title},subject_desc=#{subject_desc},subject_banner=#{subject_banner}," +
            "subject_status=#{subject_status},remark=#{remark} where subject_id=#{subject_id}")
    void updateSuj(Subject subject);
    @Select("select * from oc_subject")
    List<Subject> findAll();
}
