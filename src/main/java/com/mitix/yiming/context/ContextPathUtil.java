package com.mitix.yiming.context;

import org.apache.log4j.Logger;

import java.io.File;

/**
 * 创建于:2016年9月23日<br>
 * 版权所有(C) 2016 杭州吉利易云科技有限公司<br>
 * 存储WEB服务的AbsolutePath
 * 
 * @author Hong.LvHang
 * @version 1.0.0
 */
public class ContextPathUtil {
    private static Logger logger = Logger.getLogger(ContextPathUtil.class);
    private static String webInfAbsolutePath;

    /**
     * 设置WEB-INF路径 容器启动时设置
     * 
     * @param webInfoAbsolutePath
     */
    public static void setWebInfoAbsolutePath(String webInfoAbsolutePath) {
        webInfAbsolutePath = webInfoAbsolutePath;
        logger.info("WEB-INF路径被设置为:" + webInfoAbsolutePath);
    }

    /**
     * 获取文件的绝对路径 1.如果传入路径为绝对路径则返回传入的路径 2.如果传入的为相对路径会判断和WEB-INF的相对路径
     * 
     * @param filePath
     * @return 如果文件不存在就返回null
     */
    public static String getAbsolutePath(String filePath) {
        File file = new File(filePath).getAbsoluteFile();
        if (file.exists()) {
            logger.debug(filePath + "路径new File直接存在,返回");
            return file.getAbsolutePath();
        }
        if ((webInfAbsolutePath != null) && (!webInfAbsolutePath.equals(""))) {
            String fileAbsolutePath = webInfAbsolutePath + File.separator + filePath;
            file = new File(fileAbsolutePath);
            logger.debug("web环境获取到的路径:" + fileAbsolutePath);
            if (file.exists()) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * 获取文件的绝对路径 1.如果传入路径为绝对路径则返回传入的路径 2.如果传入的为相对路径会判断和WEB-INF的相对路径
     * 
     * @param filePath
     * @return 如果文件不存在就返回null
     */
    public static File getAbsoluteFile(String filePath) {
        File file = new File(filePath).getAbsoluteFile();
        if (file.exists()) {
            logger.debug(filePath + "路径new File直接存在,返回");
            return file;
        }
        if ((webInfAbsolutePath != null) && (!webInfAbsolutePath.equals(""))) {
            String fileAbsolutePath = webInfAbsolutePath + File.separator + filePath;
            file = new File(fileAbsolutePath);
            logger.debug("web环境获取到的路径:" + fileAbsolutePath);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }
}
