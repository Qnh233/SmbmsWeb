package service.user;

import org.apache.ibatis.annotations.Param;
import pojo.PageBean;
import pojo.User;

import java.util.List;

public interface UserService {

    public User login(String userCode,String password);

    //根据用户ID修改密码
    public boolean updatePwd(int id, String pwd);

    public List<User> selectUserList();

    Boolean deleteUser(int id);

    User selectUserById(int id);

    boolean UpdateUser(User user);

    boolean addUser(User user);

    User checkUC(String userCode);

    int selectTotalCount();

    PageBean<User> selectByPageAndCondition(int begin, int size, User user);

    PageBean<User> selectByPage(@Param("begin") int begin,@Param("size") int size);

    int selectTotalCountByCondition(User user);
}
