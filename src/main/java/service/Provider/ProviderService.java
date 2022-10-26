package service.Provider;
import pojo.Provider;
import java.util.List;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-10-22 18:03
 */
public interface ProviderService {

    public List<Provider> selectProviderList();
    int deleteProvider(int id);
    boolean UpdateProvider(Provider provider);
    boolean addProvider(Provider provider);

    Provider selectProviderById(int id);

    List<Provider> selectProviderListAndCondition(Provider provider);
}
