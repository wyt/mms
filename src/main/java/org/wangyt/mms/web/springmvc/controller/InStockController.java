package org.wangyt.mms.web.springmvc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wangyt.mms.domain.Bill;
import org.wangyt.mms.domain.BillDetail;
import org.wangyt.mms.domain.Material;
import org.wangyt.mms.domain.User;
import org.wangyt.mms.service.BillDetailService;
import org.wangyt.mms.service.BillService;
import org.wangyt.mms.service.DepotService;
import org.wangyt.mms.service.MaterialService;
import org.wangyt.mms.service.MeasureUnitService;
import org.wangyt.mms.service.SupplierService;
import org.wangyt.mms.service.UserService;
import org.wangyt.mms.util.ext.ExactValue;
import org.wangyt.mms.util.hibernate.QueryBuilder;
import org.wangyt.mms.web.easyui.model.DataGridPage;
import org.wangyt.mms.web.easyui.model.PageItemsJson;

/**
 * 入库单管理
 * 
 * @author 王永涛
 * 
 * @date 2012-11-14 上午11:52:01
 * 
 * @version $Rev: 142 $
 * 
 * @URL $URL: svn://localhost/mms/src/com/jnywsoft/mms/web/springmvc/controller/InStockController.java $
 * 
 * @Copyright (c) Copyright 2012 yongtao.wang, All rights reserved.
 * 
 */
@SuppressWarnings("all")
@Controller("inStockController")
public class InStockController
{
    @Autowired
    private BillService billService;
    @Autowired
    private BillDetailService billDetailService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private DepotService depotService;
    @Autowired
    private UserService userService;
    @Autowired
    private MeasureUnitService unitService;

    /**
     * 物资出库单index
     * 
     * @return
     */
    @RequestMapping(value = "stock/instock/index")
    public String index()
    {
        return "stock/instock/index";
    }

    @RequestMapping(value = "stock/instock/getInitDetails")
    @ResponseBody
    public Map getInitDetails()
    {
        List<Map<String, Object>> footer = new ArrayList<Map<String, Object>>();
        Map<String, Object> fitem = new HashMap<String, Object>();
        fitem.put("name", "合计");
        fitem.put("billCount", 0);
        fitem.put("billCost", 0);
        footer.add(fitem);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", 0);
        result.put("rows", new Object[] {});
        result.put("footer", footer);
        return result;
    }

    @RequestMapping(value = "stock/instock/getBillDetails")
    @ResponseBody
    public Map getBillDetails(String billId)
    {
        Bill bill = billService.findById(billId);

        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (BillDetail billDetail : bill.getBds())
        {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("id", billDetail.getId());
            Material material = billDetail.getMaterial();
            if (material != null)
            {
                item.put("materialNo", material.getMaterialNo());
                item.put("materialName", material.getMaterialName());
                item.put("materialSpec", material.getMaterialSpec());
                item.put("materialModel", material.getMaterialModel());
                item.put("measureUnitId", unitService.findById(material.getMeasureUnitId()).getUnitName());
            }

            item.put("billCount", billDetail.getCount());
            item.put("billPrice", billDetail.getPrice());
            item.put("billCost", ExactValue.multiply(billDetail.getCount(), billDetail.getPrice()));
            item.put("billDetailId", billDetail.getId());
            items.add(item);
        }

        List<Map<String, Object>> footer = new ArrayList<Map<String, Object>>();
        Map<String, Object> fitem = new HashMap<String, Object>();
        fitem.put("name", "合计");
        fitem.put("billCount", bill.getBillCount());
        fitem.put("billCost", bill.getBillCost());
        footer.add(fitem);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", items.size());
        result.put("rows", items);
        result.put("footer", footer);
        return result;
    }

    /**
     * 入库单数据集Json视图
     * 
     * @param dataGridPage
     * @return
     */
    @RequestMapping(value = "stock/instock/getItems")
    @ResponseBody
    public PageItemsJson getItems(DataGridPage dataGridPage)
    {
        QueryBuilder qb = new QueryBuilder(Bill.class)//
                .addOrderProperty("billNo", true);
        return billService.getPageView(qb, dataGridPage.getPage(), dataGridPage.getRows());
    }

    /**
     * 物资入库单Dialog
     * 
     * @return
     */
    @RequestMapping(value = "stock/instock/create")
    public ModelAndView create(ModelMap mm)
    {
        Bill bill = new Bill();
        bill.setBillNo("单据编号由系统自动生成");
        bill.setBillDate(new java.sql.Date(System.currentTimeMillis()));
        mm.addAttribute("editable", true);
        mm.addAttribute("bill", bill);
        mm.addAttribute("detailUrl", "/stock/instock/getInitDetails.mvc");
        return new ModelAndView("stock/instock/create");
    }

    @RequestMapping(value = "stock/instock/edit")
    public ModelAndView edit(ModelMap mm, String id)
    {
        if (id != null && !("".equals(id)))
        {
            Bill bill = billService.findById(id);
            User user = (User) userService.findById(bill.getUserId());
            mm.addAttribute("bill", bill);
            mm.addAttribute("makeBillUser", user);
            mm.addAttribute("detailUrl", "/stock/instock/getBillDetails.mvc?billId=" + bill.getId());
        }
        return new ModelAndView("stock/instock/edit");
    }

    /**
     * 保存入库单
     * 
     * @param bill
     */
    @RequestMapping(value = "stock/instock/save")
    @ResponseBody
    public Map save(HttpSession session, Bill bill, String goods)
    {
        bill.setUserId(((User) session.getAttribute("currentUser")).getId());
        bill.setBillType("RM"); // RM 入库单
        bill.setBillNo(billService.findNextBillNo("RM"));
        bill.setMakeBillDate(new Date());
        if (bill.getIntercourseId() != null && !("".equals(bill.getIntercourseId())))
        {
            bill.setIntercourseName(supplierService.findById(bill.getIntercourseId()).getShortName());
        }

        if (bill.getDepotId() != null && !("".equals(bill.getDepotId())))
        {
            bill.setDepotName(depotService.findById(bill.getDepotId()).getDepotName());
        }

        if (goods != null && !("".equals(goods)))
        {
            String[] a1 = goods.split(",");
            List<BillDetail> bds = new ArrayList<BillDetail>();
            int count = 0;
            double cost = 0;
            for (int i = 0; i < a1.length; i++)
            {
                String[] a2 = a1[i].split(":");
                System.out.println(a2[0] + "," + a2[1] + "," + a2[2]);
                BillDetail bd = new BillDetail();
                bd.setCount(Integer.parseInt(a2[1]));
                bd.setPrice(Double.parseDouble(a2[2]));
                bd.setMaterial(materialService.findById(a2[0]));
                bd.setBill(bill); // 建立好双向关联
                bds.add(bd);

                count = count + Integer.parseInt(a2[1]);
                cost = cost + ExactValue.multiply(Integer.parseInt(a2[1]), Double.parseDouble(a2[2]));
            }
            bill.setBillCount(count);
            bill.setBillCost(cost);
            bill.setBds(bds);
        }

        // billService.save(bill);
        String billId = billService.saveBill(bill);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "保存成功!");
        result.put("billId", billId);
        return result;
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
