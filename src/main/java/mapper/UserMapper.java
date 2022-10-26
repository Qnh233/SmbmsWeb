package mapper;

import org.apache.ibatis.annotations.*;
import pojo.User;

import java.util.Date;
import java.util.List;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-10-22 15:55
 */
public interface UserMapper {

    @Select("select * from smbms_user where userCode = #{userCode} and userPassword = #{Password}")
    User selectUser(@Param("userCode") String userCode, @Param("Password") String password);

    @Update("update smbms_user set userPassword=#{newpassword} where id=#{id}")
    boolean UpdatePassword(@Param("id") int id,@Param("newpassword") String newPassword);

    @Select("select * from smbms_user")
    List<User> selectUserlist();

    @Delete("DELETE FROM smbms_user WHERE id=#{id}")
    Boolean deleteUser(int id);

    @Select("select * from smbms_user where id=${id}")
    User selectUserById(int id);


//     Integer id; //id
//     String userCode; //用户编码
//     String userName; //用户名称
//     String userPassword; //用户密码
//     Integer gender;  //性别
//     Date birthday;  //出生日期
//     String phone;   //电话
//     String address; //地址
//     Integer userRole;    //用户角色
//     Integer createdBy;   //创建者
//     Date creationDate; //创建时间
//     Integer modifyBy;     //更新者
//     Date modifyDate;   //更新时间
//
//     Integer age;//年龄
//     String userRoleName;    //用户角色名称
    @Update("update smbms_user set userName=#{userName},gender=#{gender},birthday=#{birthday},phone=#{phone},address=#{address},userRole=#{userRole},modifyBy=#{modifyBy},modifyDate=#{modifyDate} where id=#{id}")
    boolean UpdateUser(User user);

    boolean addUser(User user);

    @Select("select * from smbms_user where userCode=#{userCode}")
    User checkUC(String userCode);

//分页查询逻辑

    @Select("select * from smbms_user limit #{begin} , #{size}")
    List<User> selectByPage(@Param("begin") int begin,@Param("size") int size);

    @Select("select count(*) from smbms_user")
    int selectTotalCount();

    //动态复杂sql用xml编辑
    List<User> selectByPageAndCondition(@Param("begin") int begin,@Param("size") int size,@Param("user")User user);

    int selectTotalCountByCondition(@Param("user")User user);
}
