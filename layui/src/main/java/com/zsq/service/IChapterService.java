package com.zsq.service;

import com.zsq.pojo.Chapter;

import java.util.List;

/**
 * @author 张子涵
 * @create 2022/5/23 18:38
 */
public interface IChapterService {

    void addFatherChapter(Chapter chapter);

    void addSonChapter(Chapter chapter);

    Chapter findById(Integer id);

    void updateFather(Chapter chapter);

    void updateSon(Chapter chapter);

    List<Chapter> getChapter(Integer course_id);

    Integer delFather(Integer id);

    Integer delSon(Integer id);
}
