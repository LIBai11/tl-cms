package com.zsq.service.impl;

import com.zsq.mapper.SubjectMapper;
import com.zsq.pojo.Subject;
import com.zsq.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements ISubjectService {
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<Subject> getSubjectList(Subject subject) {
        return subjectMapper.getSubjectList(subject);
    }

    @Override
    public void delSuj(Integer id) {
        subjectMapper.delSuj(id);
    }

    @Override
    public Subject findById(Integer id) {
        Subject subject = subjectMapper.findById(id);
        return subject;
    }

    @Override
    public void addSuj(Subject subject) {
        subjectMapper.addSuj(subject);
    }

    @Override
    public void updateSuj(Subject subject) {
        subjectMapper.updateSuj(subject);
    }

    @Override
    public List<Subject> findAll() {
        return subjectMapper.findAll();
    }
}
