package com.mitix.yiming.controller;

import com.mitix.yiming.FileUtil;
import com.mitix.yiming.Response;
import com.mitix.yiming.ResponseObject;
import com.mitix.yiming.SIDUtil;
import com.mitix.yiming.service.ZpicService;
import com.mitix.yiming.service.impl.FilePathComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建于:2016-5-9<br>
 * 版权所有(C) 2016 杭州吉利易云科技有限公司<br>
 * Dms文件上传相关请求控制类
 *
 * @author Hong.LvHang
 * @version 1.0.0
 */
@Controller
public class SysFilesController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private FilePathComponent filePathComponent;
    @Autowired
    private ZpicService zpicService;
    /**
     * 保存
     *
     * @return
     */
    @RequestMapping(value = "/fileupload.do", method = RequestMethod.POST)
    @ResponseBody
    public Response fileUpLoad(MultipartFile upload) {
        String urlId=null;
        String filenamenew = null;
        if (upload != null && upload.getSize() > 0) {
            urlId = SIDUtil.getUUID16();
            String fileName = upload.getOriginalFilename();
            if (fileName.lastIndexOf(".") > -1) {
                //后缀
                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                filenamenew = urlId + "." + prefix;
                File filepath = new File(filePathComponent.getTempFolder(), filenamenew);
                try {
                    upload.transferTo(filepath);
                } catch (IOException e) {
                    logger.error("文件保存失败", e);
                    Response response = new Response();
                    response.setStatus(0);
                    response.setMessagecode("文件保存失败");
                    return response;
                }
                Map<String, String> ret = new HashMap<>();
                //不传输ID了 进行自增长
//                ret.put("id", id);
                ret.put("url", urlId);
                ret.put("urlfix", filenamenew);
                ResponseObject<Map<String, String>> responseObject = new ResponseObject<>();
                responseObject.setRows(ret);
                return responseObject;
            } else {
                Response response = new Response();
                response.setStatus(0);
                response.setMessagecode("图像格式不正确");
                return response;
            }
        }
        Response response = new Response();
        response.setStatus(0);
        response.setMessagecode("没有上传图像信息");
        return response;
    }

    /**
     * @param url
     * @return
     */
    @RequestMapping(value = "/deleteFile.do", method = RequestMethod.POST)
    @ResponseBody
    public Response deleteFile(String url) {
        File filepath = new File(filePathComponent.getTempFolder(), url);
        try {
            FileUtil.deleteFile(filepath);
        } catch (Exception e) {
            logger.error("删除文件失败", e);
        }
        Response response = new Response();
        return response;
    }

    @RequestMapping(value = "/zpic.do",method = RequestMethod.POST)
    @ResponseBody
    public Response zPic(){
        zpicService.zpic();
        return new Response();
    }


}
