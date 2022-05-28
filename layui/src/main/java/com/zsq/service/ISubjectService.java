package com.zsq.service;

import com.zsq.pojo.Subject;

import java.util.List;

public interface ISubjectService {
    List<Subject> getSubjectList(Subject subject);

    void delSuj(Integer id);

    Subject findById(Integer id);

    void addSuj(Subject subject);

    void updateSuj(Subject subject);

    List<Subject> findAll();

}
