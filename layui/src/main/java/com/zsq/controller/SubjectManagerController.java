package com.zsq.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.pojo.FilePath;
import com.zsq.pojo.Subject;
import com.zsq.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/subject")
public class SubjectManagerController {

    @Autowired
    private ISubjectService subjectService;
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

    //到专题管理页面
    @RequestMapping("/list")
    public String subject(){
        return "subject/list";
    }


    /**
     * @author 张向楠
     * @param page
     * @param limit
     * @param subject
     * @return
     */
    //获取专题列表集合
    @RequestMapping("/getSubjectList")
    @ResponseBody
    public Object getSubjectList(Integer page,Integer limit,Subject subject){
        //page 当前页 limit 分页单位
        PageHelper.startPage(page,limit);
        List<Subject> slist = subjectService.getSubjectList(subject);
        PageInfo<Subject> pageInfo = new PageInfo<>(slist);
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("code",0);
        resultMap.put("msg","");
        resultMap.put("count",pageInfo.getTotal());
        resultMap.put("data",pageInfo.getList());
        return resultMap;
    }
    //前往添加页
    @GetMapping("/table/add")
    public String toAddSuj(){
        return "subject/add";
    }

    @PostMapping("/addSuj")
    @ResponseBody
    public String addSuj(@RequestBody Subject subject){
        subjectService.addSuj(subject);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", null);
        return jsonObject.toJSONString();
    }

    @GetMapping("/table/edit")
    public String toAddEdit(@RequestParam Integer id, Model model){
        System.out.println(id);
        Subject subject=subjectService.findById(id);
        model.addAttribute("subject",subject);
        return "subject/edit";
    }

    @PostMapping("/updateStu")
    @ResponseBody
    public String updateStu(@RequestBody Subject subject){
        subjectService.updateSuj(subject);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", null);
        return jsonObject.toJSONString();
    }

    /**
     * @author 郑洋龙
     * @param id
     * @return
     */
    @GetMapping("/delSuj/{id}")
    @ResponseBody
    public String deleteStuById(@PathVariable("id") Integer id){
        subjectService.delSuj(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", null);
        return jsonObject.toJSONString();
    }

    @PostMapping("/upload")
    @ResponseBody
    public JSONObject upload(@RequestBody FilePath filePath){
        System.out.println(filePath.getList()[0]);
        //目标接口地址
        String url = "http://127.0.0.1:36677/upload";
        //请求参数JOSN类型
        JSONObject postData = new JSONObject();
//        String file[] = {filePath};
//        System.out.println(file);
        postData.put("list",filePath.getList() );
        System.out.println(postData);
        RestTemplate client = new RestTemplate();
        JSONObject json = client.postForEntity(url, postData, JSONObject.class).getBody();
        return json;
    }

}
