package mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.Bill;
import pojo.Provider;
import pojo.User;

import java.util.List;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-10-22 15:57
 */
public interface BillMapper {

    @Select("select * from smbms_bill")
    List<Bill> selectBilllist();

    @Select("select count(*) from smbms_bill where providerId=#{providerId}")
    int selectBillTotalByProid(int providerId);

    @Delete("DELETE FROM smbms_bill WHERE id=#{id}")
    int deleteBill(int id);

    @Update("update smbms_bill set billCode=#{billCode},productName=#{productName},productUnit=#{productUnit},productCount=#{productCount},totalPrice=#{totalPrice},isPayment=#{isPayment},modifyBy=#{modifyBy},modifyDate=#{modifyDate},providerId=#{providerId} where id=#{id}")
    boolean UpdateBill(Bill bill);


    boolean addBill(Bill bill);

    @Select("select * from smbms_bill where id=#{id}")
    Bill selectBillById(int id);

    List<Bill> selectBillListAndCondition(@Param("bill")Bill bill);
}
