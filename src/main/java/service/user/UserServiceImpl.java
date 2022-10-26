package service.user;


import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.PageBean;
import pojo.User;
import util.SqlSessionFactoryUtil;

import java.util.List;


public class UserServiceImpl implements UserService{
    SqlSessionFactory factory= SqlSessionFactoryUtil.getSqlSessionFactory();

    @Override
    public User login(String userCode, String password) {
        System.out.println("查询验证");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectUser(userCode, password);
        sqlSession.close();
        return user;
//        Connection connection=null;
//        User user=null;
//        connection= BaseDao.getConnection();
//        user = userDao.getLoginUser(connection,userCode);
//        BaseDao.closeResource(connection,null,null);
//        return user;

    }

    @Override
    public boolean updatePwd(int id, String pwd) {

        System.out.println("修改密码");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.UpdatePassword(id,pwd);
        sqlSession.commit();
        sqlSession.close();
        return true;
//        Connection connection = null;
//        boolean flag = false;
//        //修改密码
//        try {
//            connection = BaseDao.getConnection();
//            if (userDao.UpdatePassWord(connection,id,pwd)>0){
//                flag = true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }  finally {
//            BaseDao.closeResource(connection,null,null);
//        }
//        return flag;
    }

    @Override
    public List<User> selectUserList() {
        System.out.println("查询所有用户");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectUserlist();
        sqlSession.close();
        return users;
    }

    @Override
    public Boolean deleteUser(int id) {
        System.out.println("删除用户");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Boolean bool = mapper.deleteUser(id);
        sqlSession.commit();
        sqlSession.close();
        return bool;
    }

    @Override
    public User selectUserById(int id) {
        System.out.println("id查询用户");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectUserById(id);
        //sqlSession.commit();
        sqlSession.close();
        return user;
    }

    @Override
    public boolean UpdateUser(User user) {
        System.out.println("修改信息");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        boolean b = mapper.UpdateUser(user);
        sqlSession.commit();
        sqlSession.close();
        return b;
    }

    @Override
    public boolean addUser(User user) {
        System.out.println("添加用户");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        boolean b = mapper.addUser(user);
        sqlSession.commit();
        sqlSession.close();
        return b;
    }

    @Override
    public User checkUC(String userCode) {
        System.out.println("用户名查询用户");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.checkUC(userCode);
        return user;
    }

    @Override
    public int selectTotalCount() {
        System.out.println("用户总数");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int TotalCount = mapper.selectTotalCount();
        sqlSession.close();
        return TotalCount;
    }

    @Override
    public PageBean<User> selectByPageAndCondition(int currentPage, int pageSize, User user) {
        System.out.println("分页条件查询");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int begin=(currentPage-1)*pageSize;
        int size=pageSize;


        //模糊表达式 搭配sql里的like 此处也检查空，为空不处理
        String userName = user.getUserName();
        if(userName!=null &&userName.length()>0)
        {
            user.setUserName("%"+userName+"%");
        }
        System.out.println(user);
        List<User> Users = mapper.selectByPageAndCondition(begin,size,user);
        int totalCount = mapper.selectTotalCountByCondition(user);

        //分页条件查询后给到封装类，数据列表加总页数。
        PageBean<User> objectPageBean = new PageBean<>();
        objectPageBean.setRows(Users);
        objectPageBean.setTotalCount(totalCount);

        sqlSession.close();
        return objectPageBean;
    }

    @Override
    public PageBean<User> selectByPage(int currentPage, int pageSize) {
        System.out.println("分页查询");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        int begin=currentPage-1;
        int size=pageSize;

        List<User> users = mapper.selectByPage(begin, size);

        int totalCount = mapper.selectTotalCount();

        PageBean<User> objectPageBean = new PageBean<>();
        objectPageBean.setRows(users);
        objectPageBean.setTotalCount(totalCount);

        return objectPageBean;
    }

    @Override
    public int selectTotalCountByCondition(User user) {
        System.out.println("条件查询得到总数");
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.selectTotalCountByCondition(user);
        return i;
    }

//    @Test
////    public void test(){
////  UserServiceImpl userService=new UserServiceImpl();
////   User user= userService.login("admin","1234567");
////        System.out.println(user.getUserPassword());
////    }

}
