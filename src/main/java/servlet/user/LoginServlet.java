package servlet.user;

import pojo.User;
import service.user.UserServiceImpl;
import service.user.UserService;
import util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    //Servlet:控制层，调用业务层代码
    private UserService userService = new UserServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("LoginServlet--start....");
        //获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //和数据库中的密码进行对比，调用业务层；
        User user = userService.login(userCode, userPassword);  //这里已经把登录的人给查出来了
        System.out.println(userCode);
        System.out.println(userPassword);
        if (user!=null){ //查有此人，可以登录
            //将用户的信息放到Session中;
            req.getSession().setAttribute("userSession",user);
            //跳转到主页
//            req.getRequestDispatcher("jsp/frame.jsp").forward(req,resp);
            resp.sendRedirect("jsp/frame.jsp");
        }else {//查无此人，无法登录
            //转发回登录页面，顺带提示它，用户名或者密码错误；
            req.setAttribute("error","用户名或者密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
