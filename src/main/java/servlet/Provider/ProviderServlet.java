package servlet.Provider;
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
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/ProviderServlet")
public class ProviderServlet extends HttpServlet {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private ProviderService PService = new ProviderServicelmpl();
    private BillService BService = new BillServicelmpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;
        String method = req.getParameter("method");
        if (method.equals("query")&&method!=null){
            this.selectProviderList(req,resp);
        }else if(method.equals("delete"))
        {
            this.deleteProvider(req,resp);
        }else if(method.equals("BackDateM")||method.equals("BackDateV"))
        {
            this.selectProviderById(method,req,resp);
        }
        else if(method.equals("modify"))
        {
            this.modifyProviderById(req,resp);
        }
        else if(method.equals("add"))
        {
            this.addProvider(req,resp);
        }
    }

    private void deleteProvider(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int proid = Integer.valueOf(req.getParameter("proid"));
        System.out.println(proid);
        int B=1;
        int totalByProid = BService.selectBillTotalByProid(proid);
        if(totalByProid!=0)
        {
            resp.getWriter().write(""+totalByProid);
             B = PService.deleteProvider(proid);
        }
        else
        {
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
        //resp.setContentType("text/json;charset=utf-8");
    }

    private void modifyProviderById(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String[]> Map = req.getParameterMap();
        Provider provider = new Provider();
        provider.setId(Integer.valueOf(Map.get("proid")[0]));
        provider.setProCode(Map.get("proCode")[0]);
        String providerName = Map.get("proName")[0];
        String providername = new String(providerName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        provider.setProName(providername);
        //provider.setproviderPassword(Map.get("providerPassword")[0]);
        String proDesc = new String(Map.get("proDesc")[0].getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        provider.setProDesc(proDesc);
        provider.setProContact(Map.get("proContact")[0]);
        provider.setProPhone(Map.get("proPhone")[0]);
        provider.setProAddress(new String(Map.get("proAddress")[0].getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        provider.setProFax(Map.get("proFax")[0]);
        // Provider.setCreatedBy(Integer.valueOf(Map.get("createdBy")[0]));
        //SimpleDateFormat scd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date mdata = scd.parse(Map.get("modifyDate")[0]);
        //Provider.setCreationDate(cdata);
        Date mdata=new Date();
        User userSession = (User) req.getSession().getAttribute("userSession");
        //Provider.setModifyBy(Integer.valueOf(Map.get("modifyBy")[0]));
        provider.setModifyBy(userSession.getId());
        provider.setModifyDate(mdata);
        System.out.println(provider);
        boolean b = PService.UpdateProvider(provider);
        if(b)
        {
//            this.selectProviderList(req, resp);
            resp.sendRedirect(req.getContextPath()+"/ProviderServlet?method=query");
        }
    }

//    private void viewProviderById(HttpServletRequest req, HttpServletResponse resp) {
//
//    }



    private void addProvider(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String[]> Map = req.getParameterMap();
        Provider provider = new Provider();
//        provider.setId(Integer.valueOf(Map.get("proid")[0]));

        //Provider.setProviderCode(Map.get("ProviderCode")[0]);
        String providerName = Map.get("proName")[0];
        String providername = new String(providerName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        provider.setProName(providername);
        //provider.setproviderPassword(Map.get("providerPassword")[0]);
        provider.setProDesc(Map.get("proDesc")[0]);
        provider.setProContact(Map.get("proContact")[0]);
        provider.setProPhone(Map.get("proPhone")[0]);
        provider.setProAddress(new String(Map.get("proAddress")[0].getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        provider.setProFax(Map.get("proFax")[0]);
        User userSession = (User) req.getSession().getAttribute("userSession");
        provider.setCreatedBy(userSession.getId());
        //SimpleDateFormat scd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date mdata = scd.parse(Map.get("modifyDate")[0]);
        Date mdata=new Date();
        provider.setCreationDate(mdata);
        //Provider.setModifyBy(Integer.valueOf(Map.get("modifyBy")[0]));
//        provider.setModifyBy(userSession.getId());
//        provider.setModifyDate(mdata);
        System.out.println(provider);
        boolean b = PService.addProvider(provider);
        if(b)
        {
            this.selectProviderList(req, resp);
        }
    }

    private void selectProviderById(String method, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int proid = Integer.valueOf(req.getParameter("proid"));
        System.out.println(proid);
        Provider provider = PService.selectProviderById(proid);
        //resp.setContentType("text/json;charset=utf-8");
        if(provider!=null)
        {
            req.setAttribute("provider",provider);
            if(method.equals("BackDateM")) {
                req.getRequestDispatcher("/jsp/providermodify.jsp").forward(req,resp);

            } else if(method.equals("BackDateV"))
            {
                req.getRequestDispatcher("/jsp/providerview.jsp").forward(req,resp);
            }
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void selectProviderList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {


        Provider provider = new Provider();
        String proName = req.getParameter("queryProName");
        String proCode = req.getParameter("queryProCode");
        if (proCode!=null)
        {
            provider.setProCode(proCode);
        }
        if(proName!=null)
        {

            provider.setProName(new String(proName.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8));
        }
        List<Provider> providers = PService.selectProviderListAndCondition(provider);
        System.out.println(providers);
        req.setAttribute("providerList",providers);
        req.getRequestDispatcher("/jsp/providerlist.jsp").forward(req,resp);
        //resp.sendRedirect(req.getContextPath()+"/Providerlist.jsp");
    }


}
