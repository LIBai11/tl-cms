package com.zsq.mapper;

import com.zsq.pojo.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> getCourseList(Course course);

    @Update("update oc_course set del_flag= 1 where course_id =#{course_id}")
    void delCourse(Integer course_id);
    @Delete("delete from oc_subject_course where course_id=#{course_id}")
    void delCourse_subject(Integer course_id);

    void addCourse(Course course);
    @Insert("insert into oc_subject_course (course_id,subject_id) values(#{course_id},#{subject_id})")
    void addCourse_subject(@Param("course_id") Integer course_id, @Param("subject_id") Integer subject_id);

    Course findCourseById(Integer course_id);

    void updateCourse(Course course);

    @Update("update oc_subject_course set subject_id=#{subject_id} where course_id=#{course_id}")
    void updateSubject_course(@Param("course_id") Integer course_id, @Param("subject_id") Integer subject_id);
}
