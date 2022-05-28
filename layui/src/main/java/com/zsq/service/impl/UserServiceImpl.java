package com.zsq.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.zsq.mapper.UserMapper;
import com.zsq.pojo.User;
import com.zsq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean login(User user) {
        User loginUser = userMapper.login(user);
        if(loginUser!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(User user) {
        int i = userMapper.changePassword(user);
        if (i==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean sendByEmail(String email, String code) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)){
            return false;
        }
        String username = "2411903424@qq.com";
        String password = "hudtebmtfyuzebcc";
        String host = "smtp.qq.com";
        Integer port = 25; // 邮件服务器的SMTP端口，可选，默认25
        String form="2411903424@qq.com"; //邮箱发送者
        MailAccount account=new MailAccount();
        account.setHost(host);
        account.setPort(port);
        account.setAuth(true);
        account.setFrom(form);
        account.setUser(username);
        account.setPass(password);
        ArrayList<String> mailList  = CollUtil.newArrayList(email);
        try {
            MailUtil.send(account,mailList,"登陆验证码","你的验证码为："+code+" ，有效时间为2分钟，打死不要告诉别人！",false);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(email+" --> 该邮箱无法送达，请确认邮箱填写正确");
            return false;
        }
    }

    @Override
    public User selectUserByEmail(String email) {
       User user= userMapper.selectUserByEmail(email);
       return user;
    }

    @Override
    public void addUser(User newUser) {
        userMapper.addUser(newUser);
    }
}
