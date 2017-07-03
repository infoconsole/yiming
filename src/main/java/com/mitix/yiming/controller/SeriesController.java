package com.mitix.yiming.controller;

import com.mitix.yiming.Combox;
import com.mitix.yiming.ContextUtils;
import com.mitix.yiming.ImageCompressUtil;
import com.mitix.yiming.Response;
import com.mitix.yiming.ResponseObject;
import com.mitix.yiming.SIDUtil;
import com.mitix.yiming.SeriesLining;
import com.mitix.yiming.service.SeriesService;
import com.mitix.yiming.service.impl.FilePathComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by oldflame on 2017/6/11.
 */
@Controller
public class SeriesController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private FilePathComponent filePathComponent;
    @Autowired
    private SeriesService seriesService;

    @RequestMapping(value = "/showseriesadd.do")
    public String showSeriesAdd(ModelMap model) {
        return "seriesadd";
    }


    @RequestMapping(value = "/showliningsadd.do")
    public String showLiningsAdd(ModelMap model) {
        return "liningadd";

    }

    @RequestMapping(value = "/addseries.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public
    @ResponseBody
    String addseries(
            @RequestParam(value = "seriescode", required = false)
                    String seriescode,
            @RequestParam(value = "seriesname", required = false)
                    String seriesname,
            @RequestParam(value = "seriescontent", required = false)
                    String seriescontent,
            @RequestParam(value = "extend1", required = false)
                    String extend1,
            @RequestParam(value = "extend2", required = false)
                    String extend2) {
        try {
            seriesService.saveSeries(seriescode, seriesname, seriescontent, extend1, extend2);
            return "success";
        } catch (Exception e) {
            logger.error("保存系列失败", e);
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/getseriescombox.do", method = RequestMethod.POST)
    @ResponseBody
    public Response getSeriesCombox() {
        List<Combox> comboxList = seriesService.selectSeriesCombox();
        ResponseObject<List<Combox>> responseObject = new ResponseObject<>();
        responseObject.setRows(comboxList);
        return responseObject;
    }

    @RequestMapping(value = "/addlining.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public
    @ResponseBody
    String addlining(
            @RequestParam(value = "seriescode", required = false)
                    String seriescode,
            @RequestParam(value = "liningcode", required = false)
                    String liningcode,
            @RequestParam(value = "liningname", required = false)
                    String liningname,
            @RequestParam(value = "liningcolor", required = false)
                    String liningcolor,
            MultipartFile file) {
        try {
            String urlId = null;
            String filenamenew = null;
            if (file != null && file.getSize() > 0) {
                urlId = SIDUtil.getUUID16();
                String fileName = file.getOriginalFilename();
                if (fileName.lastIndexOf(".") > -1) {
                    //后缀
                    String prefix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    filenamenew = urlId + "." + prefix;
                    try {
                        InputStream inputStream = file.getInputStream();
                        String fileStr = filePathComponent.getTempFolder() + filenamenew;
                        ImageCompressUtil.zipImageFile(inputStream, fileStr, 156, 90, 1f);
                    } catch (IOException e) {
                        logger.error("文件保存失败", e);
                        throw new Exception("图片保存失败");
                    }
                } else {
                    return "色卡格式不正确";
                }
            }
            seriesService.saveLinings(seriescode, liningcode, liningname, liningcolor, urlId);
            return "success";
        } catch (Exception e) {
            logger.error("保存系列失败", e);
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/queryserieslinings.do", method = RequestMethod.POST)
    @ResponseBody
    public Response queryserieslinings(
            @RequestParam(value = "scode", required = false)
                    String scode,
            @RequestParam(value = "sname", required = false)
                    String sname) {
        List<SeriesLining> seriesLiningList = seriesService.listSeriesLining(scode, sname);
        for (SeriesLining seriesLining : seriesLiningList) {
            seriesLining.setSeriescontent(ContextUtils.reformatter(seriesLining.getSeriescontent()));
        }
        ResponseObject<List<SeriesLining>> responseObject = new ResponseObject<>();
        responseObject.setRows(seriesLiningList);
        return responseObject;
    }

    @RequestMapping(value = "/deleteserieslinings.do", method = RequestMethod.POST)
    @ResponseBody
    public Response deleteSeriesLinings(
            @RequestParam(value = "seriescode", required = false)
                    String seriescode,
            @RequestParam(value = "liningcode", required = false)
                    String liningcode) {
        seriesService.deleteSeriesLinings(seriescode, liningcode);
        Response response = new Response();
        response.setMessagecode("成功");
        return response;
    }

}
