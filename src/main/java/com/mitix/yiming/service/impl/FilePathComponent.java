package com.mitix.yiming.service.impl;

import com.mitix.yiming.FileUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by oldflame on 2017/6/11.
 */
@Service
public class FilePathComponent implements InitializingBean {

    @Value("${datapath}")
    private String dataPath;

    private String logosFolder;

    private static String logosPath = "pictures";

    private static String tempPath = "temps";

    private String tempFolder;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!dataPath.endsWith(File.separator)) {
            logosFolder = dataPath + File.separator + logosPath + File.separator;
            if (!FileUtil.isExist(logosFolder)) {
                FileUtil.createFolder(logosFolder);
            }
            tempFolder = dataPath + File.separator + tempPath + File.separator;
            if (FileUtil.isExist(tempFolder)) {
                FileUtil.deleteFile(tempFolder);
            }
            FileUtil.createFolder(tempFolder);
        }

    }

    public String getLogosFolder() {
        return logosFolder;
    }

    public String getTempFolder() {
        return tempFolder;
    }

    public String getDataPath() {
        return dataPath;
    }

}
