package servlet.user;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import pojo.PageBean;
import pojo.Role;
import pojo.User;
import service.Role.RoleService;
import service.Role.RoleServicelmpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private UserService userService = new UserServiceImpl();
    private RoleService roleService = new RoleServicelmpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;
        String method = req.getParameter("method");
        if (method.equals("savepwd")&&method!=null){
            this.updatePwd(req,resp);
        }else if (method.equals("pwdmodify")){
            this.pwdModify(req, resp);
        }else if (method.equals("query"))
        {
            this.selectUserList(req, resp);
        }else if(method.equals("delete"))
        {
            this.deleteUser(req,resp);
        }else if(method.equals("BackDateM")||method.equals("BackDateV"))
        {
            this.selectUserById(method,req,resp);
        }
        else if(method.equals("modify"))
        {
            try {
                this.modifyUserById(req,resp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(method.equals("view"))
        {
            this.viewUserById(req,resp);
        }else if(method.equals("getrolelist"))
        {
            this.selectRoleList(req,resp);
        }else if(method.equals("add"))
        {
            try {
                this.addUser(req,resp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(method.equals("checkuc"))
        {
            this.checkUC(req,resp);
        }else if(method.equals("logout"))
        {
            this.logout(req,resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        session.removeAttribute("userSession");
        req.getRequestDispatcher("/login.jsp").forward(req,resp);

    }

    private void checkUC(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String userCode = req.getParameter("userCode");
        User user = userService.checkUC(userCode);
        if(user!=null)
        {
//            user.setUserCode("exist");
//             jsonString= JSON.toJSONString(user);
            resp.getWriter().write("exist");
        }
        else
        {
//            User user1 = new User();
//            user1.setUserCode("success");
//            jsonString = JSON.toJSONString("user1");
            resp.getWriter().write("success");
        }
//        String jsonString = JSON.toJSONString(user);
        //resp.setContentType("text/json;charset=utf-8");
        //resp.getWriter().write(jsonString);


    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException, ServletException {

        req.setCharacterEncoding("UTF-8");
        Map<String, String[]> Map = req.getParameterMap();
        User user = new User();
//        int id;
//        if((Integer.valueOf(Map.get("uid")[0]))!=null) {
//            user.setId(Integer.valueOf(Map.get("uid")[0]));
//        }
        user.setUserCode(Map.get("userCode")[0]);
        String userName = Map.get("userName")[0];
        String username = new String(userName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        user.setUserName(username);
        user.setUserPassword(Map.get("userPassword")[0]);
        user.setGender(Integer.valueOf(Map.get("gender")[0]));
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(Map.get("birthday")[0]);
        user.setBirthday(date);
        user.setPhone(Map.get("phone")[0]);
        user.setAddress(new String(Map.get("address")[0].getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

        Role role = roleService.selectRoleById(Integer.valueOf(Map.get("userRole")[0]));
        user.setUserRoleName(role.getRoleName());

        user.setUserRole(Integer.valueOf(Map.get("userRole")[0]));
        User userSession = (User) req.getSession().getAttribute("userSession");
        user.setCreatedBy(userSession.getId());
        Date date1 = new Date();
        user.setCreationDate(date1);
        userService.addUser(user);
        System.out.println(user);
        this.selectUserList(req,resp);

    }

    private void selectRoleList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("查询用户角色列表");
        List<Role> roles = roleService.selectRoleList();
        String jsonString = JSON.toJSONString(roles);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    private void selectUserById(String m,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int uid = Integer.valueOf(req.getParameter("uid"));
        System.out.println(uid);
        User user = userService.selectUserById(uid);
        //resp.setContentType("text/json;charset=utf-8");
        if(user!=null)
        {
            req.setAttribute("user",user);
            if(m.equals("BackDateM")) {
                req.getRequestDispatcher("/jsp/usermodify.jsp").forward(req,resp);

            } else if(m.equals("BackDateV"))
            {
                req.getRequestDispatcher("/jsp/userview.jsp").forward(req,resp);
            }
        }
    }

    private void viewUserById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    private void modifyUserById(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException {

        req.setCharacterEncoding("UTF-8");
        Map<String, String[]> Map = req.getParameterMap();
        User user = new User();
        user.setId(Integer.valueOf(Map.get("uid")[0]));

        //user.setUserCode(Map.get("userCode")[0]);
        String userName = Map.get("userName")[0];
        String username = new String(userName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        user.setUserName(username);
        //user.setUserPassword(Map.get("userPassword")[0]);
        user.setGender(Integer.valueOf(Map.get("gender")[0]));
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(Map.get("birthday")[0]);
        user.setBirthday(date);
        user.setPhone(Map.get("phone")[0]);
        user.setAddress(new String(Map.get("address")[0].getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        user.setUserRole(Integer.valueOf(Map.get("userRole")[0]));
        //获取权限用户名
        Role role = roleService.selectRoleById(Integer.valueOf(Map.get("userRole")[0]));
        user.setUserRoleName(role.getRoleName());
        // user.setCreatedBy(Integer.valueOf(Map.get("createdBy")[0]));
        SimpleDateFormat scd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date mdata = scd.parse(Map.get("modifyDate")[0]);
        //user.setCreationDate(cdata);
        Date mdata=new Date();
        User userSession = (User) req.getSession().getAttribute("userSession");
        //user.setModifyBy(Integer.valueOf(Map.get("modifyBy")[0]));
        user.setModifyBy(userSession.getId());
        user.setModifyDate(mdata);
        System.out.println(user);
        boolean b = userService.UpdateUser(user);
        if(b)
        {
//            req.getRequestDispatcher("/UserServlet?method=query").forward(req,resp);
//            this.selectUserList(req, resp);
            resp.sendRedirect(req.getContextPath()+"/UserServlet?method=query");
        }

    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//        BufferedReader reader = req.getReader();
//        String s = reader.readLine();
//        System.out.println(s);
        int uid = Integer.valueOf(req.getParameter("uid"));
        System.out.println(uid);
        Boolean aBoolean = userService.deleteUser(uid);
        //resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(""+aBoolean);



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //修改密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp){
        //从Session里面拿ID;
        Object o = req.getSession().getAttribute("userSession");

        String newpassword = req.getParameter("newpassword");

        System.out.println("UserServlet:"+newpassword);

        boolean flag = false;

        System.out.println(o!=null);
        System.out.println(StringUtils.isNullOrEmpty(newpassword));

        if (o!=null && newpassword!=null){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) o).getId(), newpassword);
            if (flag){
                req.setAttribute("message","修改密码成功，请退出，使用新密码登录");
                //密码修改成功，移除当前Session
                req.getSession().removeAttribute("userSession");
                //resp.sendRedirect(req.getContextPath()+"/login.jsp");
            }else {
                req.setAttribute("message","密码修改失败");
                //密码修改成功，移除当前Session
            }
        }else {
            req.setAttribute("message","新密码有问题");
        }

        try {
            req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //验证旧密码,session中有用户的密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp){
        //从Session里面拿ID;

        Object o = req.getSession().getAttribute("userSession");
        String oldpassword = req.getParameter("oldpassword");
        System.out.println(oldpassword);
        //万能的Map : 结果集
        Map<String, String> resultMap = new HashMap<String,String>();

        if (o==null){ //Session失效了，session过期了
            resultMap.put("result","sessionerror");
        }else if (StringUtils.isNullOrEmpty(oldpassword)){ //输入的密码为空
            resultMap.put("result","error");
        }else {
            String userPassword = ((User) o).getUserPassword(); //Session中用户的密码
            if (oldpassword.equals(userPassword)){
                resultMap.put("result","true");
            }else {
                resultMap.put("result","false");
            }
        }
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //JSONArray 阿里巴巴的JSON工具类, 转换格式
            /*
            resultMap = ["result","sessionerror","result","error"]
            Json格式 = {key：value}
             */
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void selectUserList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");

//        List<User> users = userService.selectUserList();
//        System.out.println(users);
//        req.setAttribute("userList",users);
//        System.out.println("查询用户角色列表");
        //分页属性
//        int TotalCount = userService.selectTotalCount();

        String queryUserName = req.getParameter("queryname");
        String UserRole = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");

        int queryUserRole = 0;
        int pageSize = 5; //可以把这个些到配置文件中，方便后期修改；
        int currentPageNo = 1;
        User user = new User();
        if (queryUserName ==null){
            queryUserName = "";
        }
        System.out.println(queryUserName);
        queryUserName = new String(queryUserName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        System.out.println(queryUserName);
        if (UserRole !=null && !UserRole.equals("")){
            queryUserRole = Integer.parseInt(UserRole);  //给查询赋值！0,1,2,3
        }
        if (pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }
        user.setUserName(queryUserName);
        user.setUserRole(queryUserRole);
        //获取用户的总数 (分页：  上一页，下一页的情况)
        int totalCount = userService.selectTotalCountByCondition(user);
        //总页数支持
//        PageBean<User> pageSupport = new PageBean<>();
//        pageSupport.setCurrentPageNo(currentPageNo);
//        pageSupport.(pageSize);
//        pageSupport.setTotalCount(totalCount);

        int totalPageCount = ((int)(totalCount/pageSize))+1;

        //控制首页和尾页
        //如果页面要小于1了，就显示第一页的东西
        if (currentPageNo<1){
            currentPageNo = 1;
        }else if (currentPageNo>totalPageCount){ //当前页面大于了最后一页；
            currentPageNo = totalPageCount;
        }

        //获取用户列表展示
        PageBean<User> userList = userService.selectByPageAndCondition(currentPageNo, pageSize, user);
        req.setAttribute("userList",userList.getRows());


        List<Role> roleList = roleService.selectRoleList();
        req.setAttribute("roleList",roleList);
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);

        req.getRequestDispatcher("/jsp/userlist.jsp").forward(req,resp);
        //resp.sendRedirect(req.getContextPath()+"/userlist.jsp");
    }
}
