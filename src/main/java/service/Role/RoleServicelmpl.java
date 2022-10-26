package service.Role;

import mapper.RoleMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.Role;
import util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-10-22 17:55
 */
public class RoleServicelmpl implements RoleService{
    SqlSessionFactory factory= SqlSessionFactoryUtil.getSqlSessionFactory();
    @Override
    public Role selectRoleById(int id) {
        SqlSession sqlSession = factory.openSession();
        RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
        Role role = mapper.selectRoleById(id);
        sqlSession.close();
        return role;
    }

    @Override
    public List<Role> selectRoleList() {
        SqlSession sqlSession = factory.openSession();

        RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
        List<Role> roles = mapper.selectRoleList();
        sqlSession.close();
        return roles;
    }
}
