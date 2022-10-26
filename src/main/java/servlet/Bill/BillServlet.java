package servlet.Bill;
import com.alibaba.fastjson.JSON;
import pojo.Bill;
import pojo.Provider;
import pojo.User;
import service.Bill.BillService;
import service.Bill.BillServicelmpl;
import service.Provider.ProviderService;
import service.Provider.ProviderServicelmpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private BillService BService = new BillServicelmpl();
    private ProviderService PService=new ProviderServicelmpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;
        String method = req.getParameter("method");
        if (method.equals("query")&&method!=null){
            this.selectBillList(req,resp);
        }else if(method.equals("delete"))
        {
            this.deleteBill(req,resp);
        }else if(method.equals("BackDateM")||method.equals("BackDateV"))
        {
            this.selectBillById(method,req,resp);
        }
        else if(method.equals("modify"))
        {
            this.modifyBillById(req,resp);
        }
        else if(method.equals("add"))
        {
            this.addBill(req,resp);
        }
        else if(method.equals("getproviderlist"))
        {
            this.selectProviderList(req,resp);
        }
    }

    private void selectProviderList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Provider> providers = PService.selectProviderList();
        System.out.println(providers);
        String jsonString = JSON.toJSONString(providers);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    private void deleteBill(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = Integer.valueOf(req.getParameter("id"));
        System.out.println(id);
        int B=1;
        //int totalByProid = BService.selectBillTotalByProid(id);
         B = BService.deleteBill(id);
         System.out.println(B);
         if(B>0)
         {
             resp.getWriter().write("true");
         }
         else 
         {
             resp.getWriter().write("false");
         }
    }

    private void modifyBillById(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String[]> Map = req.getParameterMap();
        Bill bill = new Bill();
        bill.setId(Integer.valueOf(Map.get("billid")[0]));
        bill.setBillCode(Map.get("billCode")[0]);
        String productName = Map.get("productName")[0];
        productName = new String(productName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        bill.setProductName(productName);
        //bill.setbillPassword(Map.get("billPassword")[0]);
        //String proDesc = new String(Map.get("proDesc")[0].getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        bill.setProductUnit(new String(Map.get("productUnit")[0].getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        bill.setProductCount((new BigDecimal((Map.get("productCount")[0]))));
        bill.setTotalPrice((new BigDecimal(Map.get("totalPrice")[0])));
        //bill.setProAddress(new String(Map.get("proAddress")[0].getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        bill.setIsPayment(Integer.valueOf(Map.get("isPayment")[0]));
        // bill.setCreatedBy(Integer.valueOf(Map.get("createdBy")[0]));
        //SimpleDateFormat scd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date mdata = scd.parse(Map.get("modifyDate")[0]);
        //bill.setCreationDate(cdata);
        Date mdata=new Date();
        User userSession = (User) req.getSession().getAttribute("userSession");
        //bill.setModifyBy(Integer.valueOf(Map.get("modifyBy")[0]));
        bill.setModifyBy(userSession.getId());
        bill.setModifyDate(mdata);

        System.out.println(bill);
        boolean b = BService.UpdateBill(bill);
        if(b)
        {
//            this.selectProviderList(req, resp);
            resp.sendRedirect(req.getContextPath()+"/BillServlet?method=query");
        }
    }

//    private void viewProviderById(HttpServletRequest req, HttpServletResponse resp) {
//
//    }



    private void addBill(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String[]> Map = req.getParameterMap();
        Bill bill = new Bill();
//        bill.setId(Integer.valueOf(Map.get("billid")[0]));
        bill.setBillCode(Map.get("billCode")[0]);
        String productName = Map.get("productName")[0];
        productName = new String(productName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        bill.setProductName(productName);
        //bill.setbillPassword(Map.get("billPassword")[0]);
//        String proDesc = new String(Map.get("proDesc")[0].getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        bill.setProductUnit(Map.get("productUnit")[0]);
        bill.setProductCount(BigDecimal.valueOf(Integer.valueOf(Map.get("productCount")[0])));
        bill.setTotalPrice(BigDecimal.valueOf(Integer.valueOf(Map.get("totalPrice")[0])));
        //  bill.setProAddress(new String(Map.get("proAddress")[0].getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        bill.setIsPayment(Integer.valueOf(Map.get("isPayment")[0]));
        // bill.setCreatedBy(Integer.valueOf(Map.get("createdBy")[0]));
        //SimpleDateFormat scd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date mdata = scd.parse(Map.get("modifyDate")[0]);
        //bill.setCreationDate(cdata);
        Date mdata=new Date();
        User userSession = (User) req.getSession().getAttribute("userSession");
        //bill.setModifyBy(Integer.valueOf(Map.get("modifyBy")[0]));
        bill.setCreatedBy(userSession.getId());
        bill.setCreationDate(mdata);
        System.out.println(bill);
        boolean b = BService.addBill(bill);
        if(b)
        {
            this.selectBillList(req, resp);
        }
    }

    private void selectBillById(String method, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.valueOf(req.getParameter("billid"));
        System.out.println(id);
        Bill bill = BService.selectBillById(id);
        //resp.setContentType("text/json;charset=utf-8");
        if(bill!=null)
        {
            req.setAttribute("bill",bill);
            if(method.equals("BackDateM")) {
                req.getRequestDispatcher("/jsp/billmodify.jsp").forward(req,resp);

            } else if(method.equals("BackDateV"))
            {
                req.getRequestDispatcher("/jsp/billview.jsp").forward(req,resp);
            }
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void selectBillList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        List<Provider> providers = PService.selectProviderList();

        Bill bill = new Bill();
        String billName = req.getParameter("queryProductName");
        String queryProviderId = req.getParameter("queryProviderId");
        String queryIsPayment = req.getParameter("queryIsPayment");
        if (billName !=null&& billName!="")
        {
            bill.setProductName(new String(billName.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8));
        }
        System.out.println(queryProviderId);
        if(queryProviderId!=null && !queryProviderId.equals("0"))
        {
            bill.setProviderId(Integer.valueOf(queryProviderId));
        }
        if(queryIsPayment!=""&&queryIsPayment !=null)
        {
            bill.setIsPayment(Integer.valueOf(queryIsPayment));
        }
        List<Bill> bills = BService.selectBillListAndCondition(bill);
        System.out.println(bills);
        //List<Bill> Bills = BService.selectBillList();
        req.setAttribute("billList",bills);
        req.setAttribute("providerList",providers);
        req.getRequestDispatcher("/jsp/billlist.jsp").forward(req,resp);
        //resp.sendRedirect(req.getContextPath()+"/billlist.jsp");
    }
}
