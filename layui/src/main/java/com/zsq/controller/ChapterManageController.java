package com.zsq.controller;

import com.alibaba.fastjson.JSONObject;
import com.zsq.pojo.Chapter;
import com.zsq.service.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chapter")
public class ChapterManageController {

    @Autowired
    IChapterService chapterService;

    //到后台主页
    @RequestMapping("/toMain")
    public String toMain(){
        return "subject/main";
    }
    //到欢迎页面toWelcome
    @RequestMapping("/toWelcome")
    public String toWelcome(){
        return "welcome";
    }


    @GetMapping("/list/{course_id}")
    public String toChapterList(@PathVariable("course_id") Integer id, Model model){
        model.addAttribute("course_id", id);
        return "chapter/list";
    }

    @GetMapping("/table/addFather/{course_id}")
    public String toAddFatherChapter(@PathVariable("course_id") Integer id, Model model){
        model.addAttribute("course_id", id);
        return "chapter/addFather";
    }

    @PostMapping("/addFatherChapter")
    @ResponseBody
    public String addFatherChapter(@RequestBody Chapter chapter){

        chapterService.addFatherChapter(chapter);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code", 0);
        jsonObject.put("msg","添加成功");
        jsonObject.put("data", null);

        return jsonObject.toJSONString();
    }

    @GetMapping("/table/addSon/{section_id}/{course_id}")
    public  String toAddSonChapter(@PathVariable("section_id")Integer section_id,
                                   @PathVariable("course_id")Integer course_id,
                                   Model model){
        model.addAttribute("section_id",section_id);
        model.addAttribute("course_id",course_id);
        return "chapter/addSon";
    }

    @PostMapping("/addSonChapter")
    @ResponseBody
    public String addSonChapter(@RequestBody Chapter chapter){
        chapterService.addSonChapter(chapter);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code", 0);
        jsonObject.put("msg","添加成功");
        jsonObject.put("data", null);

        return jsonObject.toJSONString();
    }

    @GetMapping("/table/editFather")
    public String toEditFather(@RequestParam Integer section_id, Model model){
        Chapter chapter = chapterService.findById(section_id);
        model.addAttribute("chapter", chapter);
        return "chapter/editFather";
    }

    @PostMapping("/updateFather")
    @ResponseBody
    public String updateFatherChapter(@RequestBody Chapter chapter){
        chapterService.updateFather(chapter);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code", 0);
        jsonObject.put("msg", "父章节更新成功");
        jsonObject.put("data", null);

        return jsonObject.toJSONString();
    }

    @GetMapping("/table/editSon")
    public String toEditSon(@RequestParam Integer section_id, Model model){
        Chapter chapter = chapterService.findById(section_id);
        model.addAttribute("chapter", chapter);
        return "chapter/editSon";
    }

    @PostMapping("/updateSon")
    @ResponseBody
    public String updateSonChapter(@RequestBody Chapter chapter){
        chapterService.updateSon(chapter);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "子章节更新成功");
        jsonObject.put("data", null);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/getChapter/{course_id}")
    @ResponseBody
    public Map<String,Object> getChapter(@PathVariable("course_id")Integer course_id){
        HashMap<String, Object> map = new HashMap<>();
        List<Chapter> chapterList = chapterService.getChapter(course_id);
        map.put("data",chapterList);
        return map;
    }

    @GetMapping("/delFather/{id}")
    @ResponseBody
    public String deleteFahterById(@PathVariable("id") Integer id){
        chapterService.delFather(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "父章节删除成功");
        jsonObject.put("data", null);
        return jsonObject.toJSONString();
    }

    @GetMapping("/delSon/{id}")
    @ResponseBody
    public String deleteSonById(@PathVariable("id") Integer id){
        chapterService.delSon(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "子章节删除成功");
        jsonObject.put("data", null);
        return jsonObject.toJSONString();
    }
}
