package com.mitix.yiming.controller;

import com.mitix.yiming.FileUtil;
import com.mitix.yiming.SIDUtil;
import com.mitix.yiming.SQLiteHelper;
import com.mitix.yiming.bean.Company;
import com.mitix.yiming.service.CompanyService;
import com.mitix.yiming.service.ExportService;
import com.mitix.yiming.service.impl.FilePathComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by oldflame on 2017/6/10.
 */
@RestController
public class CompanyController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private FilePathComponent filePathComponent;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ExportService exportService;

    @RequestMapping(value = "/company.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String companyupdate(
            @RequestParam(value = "companyname", required = false)
                    String companyname,
            @RequestParam(value = "address", required = false)
                    String address,
            @RequestParam(value = "tel", required = false)
                    String tel,
            @RequestParam(value = "joinhands", required = false)
                    String joinhands,
            @RequestParam(value = "workmanship", required = false)
                    String workmanship,
            @RequestParam(value = "securitycode", required = false)
                    String securitycode,
            @RequestParam(value = "content", required = false)
                    String content,
            @RequestParam(value = "extend1", required = false)
                    String extend1,
            @RequestParam(value = "extend2", required = false)
                    String extend2,
            MultipartFile file
    ) {
        String urlId;
        String filenamenew = null;
        if (file != null && file.getSize() > 0) {
            urlId = SIDUtil.getUUID16();
            String fileName = file.getOriginalFilename();
            if (fileName.lastIndexOf(".") > -1) {
                //后缀
                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                filenamenew = urlId + "." + prefix;
                File filepath = new File(filePathComponent.getLogosFolder(), filenamenew);
                try {
                    file.transferTo(filepath);
                } catch (IOException e) {
                    logger.error("文件保存失败", e);
                    return "修改失败";
                }
            } else {
                return "logo格式不正确";
            }
        }
        companyService.saveorupdate(companyname, address, tel, joinhands, workmanship, securitycode, content, extend1, extend2, filenamenew);
        return "修改成功";
    }

    @RequestMapping(value = "getcompany.do", method = RequestMethod.GET)
    public Company getcompany() {
        return companyService.getcompany();
    }

    @RequestMapping(value = "/downdb.do")
    public String downdb(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dataPath = filePathComponent.getDataPath();
        if (!dataPath.endsWith(File.separator)) {
            dataPath = dataPath + File.separator;
        }
        String dbPath = dataPath + "ym.db";
        FileUtil.deleteFile(new File(dbPath));
        SQLiteHelper helper = new SQLiteHelper(dbPath);
        helper.setExportService(exportService);
        helper.insertDb();

        OutputStream toClient = null;
        try {
            File res = new File(dbPath);
            InputStream fis = new BufferedInputStream(new FileInputStream(res));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=app.db");
            response.addHeader("Content-Length", "" + res.length());
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (toClient != null) {
                toClient.flush();
                toClient.close();
            }
        }
        return null;
    }
}
