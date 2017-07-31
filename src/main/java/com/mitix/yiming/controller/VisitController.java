package com.mitix.yiming.controller;

import com.mitix.yiming.JSONUtils;
import com.mitix.yiming.Response;
import com.mitix.yiming.ResponseObject;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Visit;
import com.mitix.yiming.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class VisitController {
    private static final Logger logger = LoggerFactory.getLogger(VisitController.class);


    @Autowired
    private VisitService visitService;

    @RequestMapping(value = "/showvisitadd.do")
    public String showDesignsAdd() {
        return "visitadd";
    }

    @RequestMapping(value = "/showvisitupdate.do")
    public String showDesignsUpdate(Visit visit, ModelMap map) {
        map.put("Visit", visit);
        return "visitupdate";
    }

    @RequestMapping(value = "/queryvisits.do")
    @ResponseBody
    public Response queryVisit(
            @RequestParam(value = "visitname", required = false)
                    String visitname) {
        List<Visit> visitList = visitService.listVisits(visitname);
        ResponseObject<List<Visit>> responseObject = new ResponseObject<>();
        responseObject.setRows(visitList);
        return responseObject;

    }

    @RequestMapping(value = "/deletevisit.do")
    @ResponseBody
    public Response deleteVisit(
            @RequestParam(value = "visitcode", required = false)
                    String visitcode) {
        visitService.deleteVisit(visitcode);
        Response response = new Response();
        response.setMessagecode("成功");
        return response;
    }

    @RequestMapping(value = "/savevisits.do")
    @ResponseBody
    public Response saveVisits(
            @RequestParam(value = "visitname")
                    String visitname,
            @RequestParam(value = "visitlist")
                    String visitlist) {
        try {
            List<DesFiles> visitlistList = JSONUtils.toBeanList(visitlist, DesFiles.class);
            visitService.saveVisits(visitname, visitlistList);
            return new Response();
        } catch (RuntimeException e) {
            logger.error("出错1", e);
            Response response = new Response();
            response.setStatus(0);
            response.setMessagecode(e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("出错2", e);
            Response response = new Response();
            response.setStatus(0);
            response.setMessagecode("保存图片出错");
            return response;
        }

    }
}
