package com.zsq.service.impl;

import com.zsq.mapper.ChapterMapper;
import com.zsq.pojo.Chapter;
import com.zsq.service.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张子涵
 * @create 2022/5/23 18:39
 */

@Service
public class IChapterServiceImpl implements IChapterService {

    @Autowired
    ChapterMapper chapterMapper;

    @Override
    public void addFatherChapter(Chapter chapter) {
        chapterMapper.addFatherChapter(chapter);
    }

    @Override
    public void addSonChapter(Chapter chapter) {
        chapterMapper.addSonChapter(chapter);
    }

    @Override
    public Chapter findById(Integer id) {
        Chapter chapter = chapterMapper.findById(id);
        return chapter;
    }

    @Override
    public void updateFather(Chapter chapter) {
        chapterMapper.updateFather(chapter);
    }

    @Override
    public void updateSon(Chapter chapter) {
        chapterMapper.updateSon(chapter);
    }

    @Override
    public List<Chapter> getChapter(Integer course_id) {
        List<Chapter> chapter = chapterMapper.getChapter(course_id);
        List<Chapter> chapterList = new ArrayList<Chapter>();
        if (chapter != null) {
            chapterList.addAll(chapter);
            for (int i = 0; i < chapter.size(); i++) {
                List<Chapter> sons = chapterMapper.findSons(chapter.get(i).getSection_id());
                chapterList.addAll(sons);
            }
        }
        return chapterList;
    }

    @Override
    public Integer delFather(Integer id) {
        chapterMapper.delFather(id);
        return id;
    }

    @Override
    public Integer delSon(Integer id) {
        chapterMapper.delSon(id);
        return id;
    }
}
