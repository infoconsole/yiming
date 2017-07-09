package com.mitix.yiming.controller;

import com.mitix.yiming.JSONUtils;
import com.mitix.yiming.Response;
import com.mitix.yiming.ResponseObject;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Designs;
import com.mitix.yiming.service.DesignsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by oldflame on 2017/6/16.
 */
@Controller
public class DesignsController {
    private static final Logger logger = LoggerFactory.getLogger(DesignsController.class);


    @Autowired
    private DesignsService designsService;

    @RequestMapping(value = "/showdesignsadd.do")
    public String showDesignsAdd() {
        return "designsadd";
    }

    @RequestMapping(value = "/showdesignsupdate.do")
    public String showDesignsUpdate(Designs designs, ModelMap map) {
        map.put("Designs", designs);
        return "designsupdate";
    }

    @RequestMapping(value = "/savedesigns.do", method = RequestMethod.POST)
    @ResponseBody
    public Response saveDesigns(
            @RequestParam(value = "liningcode")
                    String liningcode,
            @RequestParam(value = "designname", required = false)
                    String designname,
            @RequestParam(value = "sjlist", required = false)
                    String sjlist) {
        try {
            List<DesFiles> sjlistList = JSONUtils.toBeanList(sjlist, DesFiles.class);
//            List<DesFiles> xglistList = JSONUtils.toBeanList(xglist, DesFiles.class);
            designsService.saveDesigns(liningcode, designname, sjlistList);
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
            response.setMessagecode("保存设计出错");
            return response;
        }
    }

    @RequestMapping(value = "/updatedesigns.do", method = RequestMethod.POST)
    @ResponseBody
    public Response updateDesigns(
            @RequestParam(value = "id")
            Integer id,
            @RequestParam(value = "liningcode")
                    String liningcode,
            @RequestParam(value = "designname", required = false)
                    String designname,
            @RequestParam(value = "sjlist", required = false)
                    String sjlist) {
        try {
            List<DesFiles> sjlistList = JSONUtils.toBeanList(sjlist, DesFiles.class);
            designsService.updateDesigns(id,liningcode, designname, sjlistList);
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
            response.setMessagecode("保存设计出错");
            return response;
        }
    }


    @RequestMapping(value = "/queryliningsdesigns.do", method = RequestMethod.POST)
    @ResponseBody
    public Response queryLiningsDesigns(
            @RequestParam(value = "lcode", required = false)
                    String lcode) {
        List<Designs> designsList = designsService.listLiningsDesigns(lcode);
        ResponseObject<List<Designs>> responseObject = new ResponseObject<>();
        responseObject.setRows(designsList);
        return responseObject;
    }

    @RequestMapping(value = "/deleteliningsdesigns.do", method = RequestMethod.POST)
    @ResponseBody
    public Response deleteLiningsDesigns(
            @RequestParam(value = "designcode", required = false)
                    String designcode,
            @RequestParam(value = "liningcode", required = false)
                    String liningcode) {
        designsService.deleteLiningsDesigns(designcode, liningcode);
        Response response = new Response();
        response.setMessagecode("成功");
        return response;
    }

    @RequestMapping(value = "/querydesignsfiles.do", method = RequestMethod.POST)
    @ResponseBody
    public Response querydesignsfiles(
            @RequestParam(value = "desid")
            Integer desid) {
        Designs designs = designsService.selectById(desid);
        String designcode = designs.getDesigncode();
        List<DesFiles> desFilesList = designsService.listDesFilesByDesignCode(designcode);
        ResponseObject<List<DesFiles>> responseObject = new ResponseObject<>();
        responseObject.setRows(desFilesList);
        return responseObject;
    }
}
