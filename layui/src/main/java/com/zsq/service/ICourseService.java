package com.zsq.service;


import com.zsq.pojo.Course;

import java.util.List;

public interface ICourseService {
    List<Course> getCourseList(Course course);

    void delCourse(Integer course_id);

    void addCourse(Course course);

    Course findCourseById(Integer course_id);

    void updateCourse(Course course);

    void delCourseByGroups(List<Integer> ids);
}
