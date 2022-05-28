package com.zsq.service.impl;

import com.zsq.mapper.CourseMapper;
import com.zsq.pojo.Course;
import com.zsq.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICourseServiceImpl implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList(Course course) {

        return courseMapper.getCourseList(course);
    }

    @Override
    public void delCourse(Integer course_id) {
        //删除中间表数据
        courseMapper.delCourse_subject(course_id);
        //删除课程
        courseMapper.delCourse(course_id);

    }

    @Override
    public void addCourse(Course course) {
        //是否打折  不打折则打折价等于原价
        if ("0".equals(course.getIs_discount())){
            course.setDiscount_price(course.getCourse_price());
        }
        courseMapper.addCourse(course);
        Integer course_id = course.getCourse_id();
        Integer subject_id = course.getSubject_id();
        courseMapper.addCourse_subject(course_id,subject_id);
    }

    @Override
    public Course findCourseById(Integer course_id) {
        return courseMapper.findCourseById(course_id);
    }

    @Override
    public void updateCourse(Course course) {
        //是否打折  不打折则打折价等于原价
        if ("0".equals(course.getIs_discount())){
            course.setDiscount_price(course.getCourse_price());
        }
        courseMapper.updateCourse(course);
        //新设其他关联关系
        courseMapper.updateSubject_course(course.getCourse_id(),course.getSubject_id());
    }

    @Override
    public void delCourseByGroups(List<Integer> ids) {
        for (Integer id : ids) {
            this.delCourse(id);
        }
    }
}
