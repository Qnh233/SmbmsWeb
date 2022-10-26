package mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.Provider;
import pojo.User;

import java.util.List;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-10-22 15:58
 */
public interface ProviderMapper {
    @Select("select * from smbms_provider")
    List<Provider> selectProviderList();

    @Delete("DELETE FROM smbms_provider WHERE id=#{id}")
    int deleteProvider(int id);

    @Update("update smbms_provider set proName=#{proName},proDesc=#{proDesc},proContact=#{proContact},proPhone=#{proPhone},proAddress=#{proAddress},proFax=#{proFax},modifyBy=#{modifyBy},modifyDate=#{modifyDate} where id=#{id}")
    boolean UpdateProvider(Provider provider);


    boolean addProvider(Provider provider);

    @Select("select * from smbms_provider where id=#{id}")
    Provider selectProviderById(int id);

    List<Provider> selectProviderListAndCondition(@Param("provider")Provider provider);

}
