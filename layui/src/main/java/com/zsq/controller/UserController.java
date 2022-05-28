package com.zsq.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zsq.pojo.User;
import com.zsq.service.UserService;
import com.zsq.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody User user, HttpSession session){
        boolean flag = userService.login(user);
        JSONObject jsonObject = new JSONObject();
        if(flag){
            session.setAttribute("user",user);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "登陆成功");
            jsonObject.put("data",flag);
        }else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            jsonObject.put("data",flag);
        }
        return jsonObject.toJSONString();
    }

    //到后台主页
    @RequestMapping("/toMain")
    public String toMain(){
        return "main";
    }

    //到欢迎页面toWelcome
    @RequestMapping("/toWelcome")
    public String toWelcome(){
        return "welcome";
    }

    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(){
        return "user/updatePassword";
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public String changePassword(@RequestBody User user,HttpSession session){
        boolean flag= userService.changePassword(user);
        JSONObject jsonObject = new JSONObject();
        if (flag){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改成功");
            jsonObject.put("data",flag);
        }else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改失败");
            jsonObject.put("data",flag);
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }
    @RequestMapping("/toEmailLogin")
    public String toEmailLogin(){
        return "emailLogin";
    }
    @RequestMapping("/getCode")
    @ResponseBody
    public JSON getCode(@RequestParam("email") String email){
        JSONObject jsonObject = new JSONObject();
        //检查redis中是否含有验证码信息
        String code = (String) redisTemplate.opsForValue().get(email);
        //如果存在 直接返回不需要再次发送
        if (!StringUtils.isEmpty(code)){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "验证码还在有效期内");
            jsonObject.put("data",null);
            return jsonObject;
        }
        //如果获取不到，则发送
        //1、生成验证码
        code = RandomUtil.getSixBitRandom();
        //2、调用service 发送短信验证码
        boolean isSend = userService.sendByEmail(email,code);
        //3、生成验证码放入redis中
        if (isSend){
            redisTemplate.opsForValue().set(email,code,2, TimeUnit.MINUTES);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "发送成功");
            jsonObject.put("data",null);
            return jsonObject;
        }else {
            jsonObject.put("code", 2);
            jsonObject.put("msg", "发送邮箱失败");
            jsonObject.put("data",null);
            return jsonObject;
        }
    }
    @RequestMapping("/emailLogin")
    @ResponseBody
    public String emailLogin(@RequestBody Map<String,Object> loginVo,HttpSession session){
        JSONObject jsonObject = new JSONObject();
        String email = (String) loginVo.get("email");
        //判断是否第一次登录，是则注册 不是则获取对象数据
        User user = userService.selectUserByEmail(email);
        User newUser=new User();
        if (user==null){
            String username = email.split("@")[0];
            newUser.setPassword(username);//密码默认为邮箱前段
            newUser.setUsername(username);//密码默认为邮箱前段
            newUser.setEmail(email);
            userService.addUser(newUser);
        }
        String code = (String) loginVo.get("code");
        String confirm_code = (String) redisTemplate.opsForValue().get(email);
        if (confirm_code==null){
            jsonObject.put("code", 2);
            jsonObject.put("msg", "验证码失效");
            jsonObject.put("data",null);
            return jsonObject.toJSONString();
        }
        if (confirm_code.equals(code)){
            if (newUser.getId()==null){
                session.setAttribute("user",user);
            }else{
                session.setAttribute("user",newUser);
            }
            System.out.println(session.getAttribute("user"));
            jsonObject.put("code", 0);
            jsonObject.put("msg", "登录成功");
            jsonObject.put("data",null);
            return jsonObject.toJSONString();
        }else{
            jsonObject.put("code", 1);
            jsonObject.put("msg", "验证码错误");
            jsonObject.put("data",null);
            return jsonObject.toJSONString();
        }
    }
}
