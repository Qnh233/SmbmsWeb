package service.Bill;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.Bill;

import java.util.List;

public interface BillService {


    public List<Bill> selectBillList();

    int selectBillTotalByProid(int providerId);


    int deleteBill(int id);

    boolean UpdateBill(Bill bill);


    boolean addBill(Bill bill);

    Bill selectBillById(int id);

    List<Bill> selectBillListAndCondition(@Param("bill")Bill bill);

}
