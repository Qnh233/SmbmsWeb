package service.Role;

import pojo.Role;

import java.util.List;

public interface RoleService {


    public Role selectRoleById(int id);

    public List<Role> selectRoleList();

}
