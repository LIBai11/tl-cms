package com.zsq.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.pojo.Course;
import com.zsq.pojo.Subject;
import com.zsq.service.ICourseService;
import com.zsq.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/course")
public class CourseManageController {
    @Autowired
    private ICourseService iCourseService;
    @Autowired
    private ISubjectService iSubjectService;
    @GetMapping("/toCourse")
    public String toCourse(Model model){
        //获取所有专题，作为搜索条件的下拉框值
        List<Subject> subjectList=iSubjectService.findAll();
        model.addAttribute("subjectList",subjectList);

        return "course/course";
    }

    //获取专题列表集合
    @RequestMapping("/getCourseList")
    @ResponseBody
    public Object getCourseList(Integer page, Integer limit, Course course, Model model){
        //page 当前页 limit 分页单位
        PageHelper.startPage(page,limit);
        List<Course> courseList = iCourseService.getCourseList(course);
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",0);
        resultMap.put("msg","");
        resultMap.put("count",pageInfo.getTotal());
        resultMap.put("data",pageInfo.getList());
        return resultMap;
    }

    @RequestMapping("/delCourse/{course_id}")
    @ResponseBody
    public String delCourse(@PathVariable("course_id") Integer course_id){
        iCourseService.delCourse(course_id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", null);
        return jsonObject.toJSONString();
    }
    @RequestMapping("/toAddCourse")
    public String toAddCourse(Model model){
        //获取所有专题，作为搜索条件的下拉框值
        List<Subject> subjectList=iSubjectService.findAll();
        model.addAttribute("subjectList",subjectList);
        return "course/addCourse";
    }

    @PostMapping("/addCourse")
    @ResponseBody
    public String addCourse(@RequestBody Course course){
        iCourseService.addCourse(course);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", null);
        return jsonObject.toJSONString();
    }
    @RequestMapping("/toUpdateCourse/{course_id}")
    public String toUpdateCourse(@PathVariable Integer course_id,Model model){
        //获取所有专题，作为搜索条件的下拉框值
        List<Subject> subjectList=iSubjectService.findAll();
        model.addAttribute("subjectList",subjectList);
       Course course= iCourseService.findCourseById(course_id);
        String course_cover = course.getCourse_cover();
        model.addAttribute("course",course);
       return "course/updateCourse";
    }
    @RequestMapping("/updateCourse")
    @ResponseBody
    public String updateCourse(@RequestBody Course course){
        System.out.println(course);
        iCourseService.updateCourse(course);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", null);
        return jsonObject.toJSONString();
    }
    @RequestMapping("/delByGroup")
    @ResponseBody
    public String delCourseByGroup(@RequestBody List<Integer> ids){
        System.out.println(ids);
        iCourseService.delCourseByGroups(ids);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", null);
        return jsonObject.toJSONString();
    }

    //图片上传
    @ResponseBody
    @RequestMapping("/uploadFile")
    public JSON uploadFile(@RequestParam(required = false) MultipartFile course_cover_img,
                           @RequestParam(required = false) MultipartFile course_banner_img,
                           @RequestParam(required = false) MultipartFile detail_img_img) {

        JSONObject json = new JSONObject();

        if (course_cover_img!=null){
            // 文件上传的父目录
            String parentPath = "D:\\yunketang\\course_cover\\img";
            // 得到当前日期作为文件夹名称，这用随机数的类给下载的文件夹取新名字
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
            String format = simpleDateFormat.format(date);
            String dirName = format;
            // 构造文件夹对象
            File dirFile = new File(parentPath);
            //如果没有的话创建一个
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            // 根据文件原名得到新名，这用随机数的类给下载的文件取新名字
            String fileName = course_cover_img.getOriginalFilename();
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            String newName=	dirName+"@"+UUID.randomUUID().toString()+suffix;

            File desFile = new File(dirFile,newName);
            if (desFile.exists()) {
                desFile.delete();
            }
            try {
                course_cover_img.transferTo(desFile);
                String finalPath=desFile.getPath().split(":")[1];
                System.out.println(finalPath);
                json.put("code", 0);
                //将文件名放在msg中，前台提交表单时需要用到
                json.put("msg", course_cover_img.getOriginalFilename().trim());

                JSONObject json2 = new JSONObject();
                json2.put("src", finalPath);
                json2.put("title", "");
                json.put("data", json2);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                json.put("code", 1);
            }
            System.out.println(json);
            return json;
        }
        if (course_banner_img!=null){
            String parentPath=  "D:\\yunketang\\course_banner\\img";
            // 得到当前日期作为文件夹名称，这用随机数的类给下载的文件夹取新名字
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
            String format = simpleDateFormat.format(date);
            String dirName = format;
            // 构造文件夹对象
            File dirFile = new File(parentPath);
            //如果没有的话创建一个
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            // 根据文件原名得到新名，这用随机数的类给下载的文件取新名字
            String fileName = course_banner_img.getOriginalFilename();
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            String newName=	dirName+"@"+UUID.randomUUID().toString()+suffix;

            File desFile = new File(dirFile,newName);
            if (desFile.exists()) {
                desFile.delete();
            }
            try {
                course_banner_img.transferTo(desFile);
                String finalPath=desFile.getPath().split(":")[1];
                json.put("code", 0);
                //将文件名放在msg中，前台提交表单时需要用到
                json.put("msg", course_banner_img.getOriginalFilename().trim());

                JSONObject json2 = new JSONObject();
                json2.put("src", finalPath);
                json2.put("title", "");
                json.put("data", json2);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                json.put("code", 1);
            }
            System.out.println(json);
            return json;
        }
        if (detail_img_img!=null) {
            String parentPath=  "D:\\yunketang\\detail_img\\img";
            // 得到当前日期作为文件夹名称，这用随机数的类给下载的文件夹取新名字
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
            String format = simpleDateFormat.format(date);
            String dirName = format;
            // 构造文件夹对象
            File dirFile = new File(parentPath);
            //如果没有的话创建一个
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            // 根据文件原名得到新名，这用随机数的类给下载的文件取新名字
            String fileName = detail_img_img.getOriginalFilename();
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            String newName=	dirName+"@"+UUID.randomUUID().toString()+suffix;

            File desFile = new File(dirFile,newName);
            if (desFile.exists()) {
                desFile.delete();
            }
            try {
                detail_img_img.transferTo(desFile);
                String finalPath=desFile.getPath().split(":")[1];
                json.put("code", 0);
                //将文件名放在msg中，前台提交表单时需要用到
                json.put("msg", detail_img_img.getOriginalFilename().trim());
                JSONObject json2 = new JSONObject();
                json2.put("src", finalPath);
                json2.put("title", "");
                json.put("data", json2);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                json.put("code", 1);
            }
            System.out.println(json);
            return json;
        }
        json.put("code", 1);
        json.put("msg", "文件为空");
        return json;
    }

}
