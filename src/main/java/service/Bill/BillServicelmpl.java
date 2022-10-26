package service.Bill;

import mapper.BillMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.Bill;
import util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-10-22 17:56
 */
public class BillServicelmpl implements BillService {
    SqlSessionFactory factory= SqlSessionFactoryUtil.getSqlSessionFactory();
    @Override
    public List<Bill> selectBillList() {
        System.out.println("查询订单");
        SqlSession sqlSession = factory.openSession();
        BillMapper mapper = sqlSession.getMapper(BillMapper.class);
        List<Bill> bills = mapper.selectBilllist();
        sqlSession.close();
        return bills;
    }

    @Override
    public int selectBillTotalByProid(int providerId) {

        System.out.println("查询订单总数");

        SqlSession sqlSession = factory.openSession();
        BillMapper mapper = sqlSession.getMapper(BillMapper.class);
        int TotalBill = mapper.selectBillTotalByProid(providerId);
        System.out.println(TotalBill);
        sqlSession.close();
        return TotalBill;
    }

    @Override
    public int deleteBill(int id) {
        SqlSession sqlSession = factory.openSession();
        BillMapper mapper = sqlSession.getMapper(BillMapper.class);
        int b = mapper.deleteBill(id);
        sqlSession.commit();
        sqlSession.close();
        return b;
    }

    @Override
    public boolean UpdateBill(Bill bill) {
        SqlSession sqlSession = factory.openSession();
        BillMapper mapper = sqlSession.getMapper(BillMapper.class);
        boolean b = mapper.UpdateBill(bill);
        sqlSession.commit();
        sqlSession.close();
        return b;
    }

    @Override
    public boolean addBill(Bill bill) {
        SqlSession sqlSession = factory.openSession();
        BillMapper mapper = sqlSession.getMapper(BillMapper.class);
        boolean b = mapper.addBill(bill);
        sqlSession.commit();
        sqlSession.close();
        return b;
    }

    @Override
    public Bill selectBillById(int id) {
        SqlSession sqlSession = factory.openSession();
        BillMapper mapper = sqlSession.getMapper(BillMapper.class);
        Bill bill = mapper.selectBillById(id);
        sqlSession.commit();
        sqlSession.close();
        return bill;
    }

    @Override
    public List<Bill> selectBillListAndCondition(Bill bill) {
        SqlSession sqlSession = factory.openSession();
        BillMapper mapper = sqlSession.getMapper(BillMapper.class);
        //模糊表达式 搭配sql里的like 此处也检查空，为空不处理
        String billName = bill.getProductName();
        int proid=0;
        int ispay=0;
        if(bill.getProviderId()!=null)
        {
            proid= bill.getProviderId();
        }
        if(bill.getIsPayment()!=null)
        {
            ispay = bill.getIsPayment();
        }
        if(billName!=null &&billName.length()>0)
        {
            bill.setProductName("%"+billName+"%");
        }
        if(proid>0)
        {
            bill.setProviderId(proid);
        }
        if(ispay>=0)
        {
            bill.setIsPayment(ispay);
        }

        System.out.println(bill);
        List<Bill> bills = mapper.selectBillListAndCondition(bill);
//        sqlSession.commit();
        sqlSession.close();
        return bills;
    }
}
