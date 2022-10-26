package mapper;

import org.apache.ibatis.annotations.Select;
import pojo.Role;
import pojo.User;

import java.util.List;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-10-22 15:58
 */
public interface RoleMapper {

    @Select("select * from smbms_role where id=#{id}")
    Role selectRoleById(int id);

    @Select("select * from smbms_role ")
    List<Role> selectRoleList();
}
