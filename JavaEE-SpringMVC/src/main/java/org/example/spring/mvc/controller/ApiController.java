package org.example.spring.mvc.controller;
import org.example.spring.mvc.service.StudentHomeWorkJdbc;
import org.example.spring.mvc.model.Homework;
import org.example.spring.mvc.model.Student;
import org.example.spring.mvc.model.StudentHomework;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Controller
public class ApiController {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(org.example.spring.mvc.service.StudentHomeWorkJdbc.class);
    StudentHomeWorkJdbc jdbc = (StudentHomeWorkJdbc) applicationContext.getBean("jdbc");

    @RequestMapping(path = "/addhomework", method = RequestMethod.POST)
    public void addhomework(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse resp = attributes.getResponse();
        try {
            request.setCharacterEncoding("utf-8");//设置编码，以防表单提交的内容乱码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Homework homework = new Homework();
        homework.setId(Long.parseLong(request.getParameter("hid")));
        homework.setTitle(request.getParameter("title"));
        Date date = new Date();
        homework.setCreateTime(date);
        boolean result = jdbc.addhomework(homework);

        request.setAttribute("isOK", result);    //用来判断是否添加作业成功
        request.setAttribute("type","addHomework");
        try {
            request.getRequestDispatcher("result.jsp").forward(request,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/addstudent", method = RequestMethod.POST)
    public void addstudent(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        HttpServletResponse resp = attributes.getResponse();
        try {
            req.setCharacterEncoding("utf-8");//设置编码，以防表单提交的内容乱码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Student student = new Student();
        student.setId(Long.parseLong(req.getParameter("id")));
        student.setName(req.getParameter("name"));
        Date date = new Date();
        student.setCreateTime(date);
        boolean result = jdbc.addstudent(student);
        req.setAttribute("isOK", result);  //用来判断是否添加作业成功
        req.setAttribute("type","addStudent");

        try {
            req.getRequestDispatcher("result.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/addstudenthomework", method = RequestMethod.POST)
    public void addstudenthomwork(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        HttpServletResponse resp = attributes.getResponse();
        try {
            req.setCharacterEncoding("utf-8");//设置编码，以防表单提交的内容乱码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StudentHomework studentHomework = new StudentHomework();
        studentHomework.setStudentId(Long.parseLong(req.getParameter("sid")));
        studentHomework.setHomeworkId(Long.parseLong(req.getParameter("hid")));
        studentHomework.setHomeworkTitle(req.getParameter("htitle"));
        studentHomework.setHomeworkContent(req.getParameter("hcontent"));
        Date date = new Date();
        studentHomework.setCreateTime(date);
        boolean result = jdbc.addstudenthomework(studentHomework);
        req.setAttribute("isOK", result);  //用来判断是否添加作业成功
        req.setAttribute("type","addStudentHomework");

        try {
            req.getRequestDispatcher("result.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
