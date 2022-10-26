package service.Provider;

import mapper.ProviderMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.Provider;
import util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-10-22 18:03
 */
public class ProviderServicelmpl implements ProviderService{
    SqlSessionFactory factory= SqlSessionFactoryUtil.getSqlSessionFactory();
    @Override
    public List<Provider> selectProviderList() {
        SqlSession sqlSession = factory.openSession();
        ProviderMapper mapper = sqlSession.getMapper(ProviderMapper.class);
        List<Provider> providers = mapper.selectProviderList();
        sqlSession.close();
        return providers;
    }

    @Override
    public int deleteProvider(int id) {
        SqlSession sqlSession = factory.openSession();
        ProviderMapper mapper = sqlSession.getMapper(ProviderMapper.class);
        int i = mapper.deleteProvider(id);
        sqlSession.commit();
        sqlSession.close();
        return i;
    }

    @Override
    public boolean UpdateProvider(Provider provider) {
        SqlSession sqlSession = factory.openSession();
        ProviderMapper mapper = sqlSession.getMapper(ProviderMapper.class);
        boolean b = mapper.UpdateProvider(provider);
        sqlSession.commit();
        sqlSession.close();
        return b;
    }

    @Override
    public boolean addProvider(Provider provider) {
        SqlSession sqlSession = factory.openSession();
        ProviderMapper mapper = sqlSession.getMapper(ProviderMapper.class);
        boolean b = mapper.addProvider(provider);
        sqlSession.commit();
        sqlSession.close();
        return b;
    }

    @Override
    public Provider selectProviderById(int id) {
        SqlSession sqlSession = factory.openSession();
        ProviderMapper mapper = sqlSession.getMapper(ProviderMapper.class);
        Provider provider = mapper.selectProviderById(id);
        sqlSession.close();
        return provider;
    }

    @Override
    public List<Provider> selectProviderListAndCondition(Provider provider) {
        SqlSession sqlSession = factory.openSession();
        ProviderMapper mapper = sqlSession.getMapper(ProviderMapper.class);

        //模糊表达式 搭配sql里的like 此处也检查空，为空不处理
        String proName = provider.getProName();
        String proCode = provider.getProCode();
        if(proName !=null && proName.length()>0)
        {
            provider.setProName("%"+ proName +"%");
        }
        if(proCode !=null && proCode.length()>0)
        {
            provider.setProCode("%"+ proCode +"%");
        }
        System.out.println(provider);
        List<Provider> pros = mapper.selectProviderListAndCondition(provider);
        sqlSession.close();
        return pros;
    }
}
