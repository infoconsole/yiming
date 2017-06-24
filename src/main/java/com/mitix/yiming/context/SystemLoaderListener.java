package com.mitix.yiming.context;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * 创建于:2016年9月23日<br>
 * 版权所有(C) 2016 杭州吉利易云科技有限公司<br>
 * 系统启动服务
 * 
 * @author Hong.LvHang
 * @version 1.0.0
 */
public class SystemLoaderListener extends ContextLoaderListener {
    
    private static Logger logger = Logger.getLogger(SystemLoaderListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        if (logger.isDebugEnabled()) {
            logger.debug("系统开始启动....");
        }
        try {
            String webInfoAbsolutePath = event.getServletContext().getRealPath("/WEB-INF");
            if (webInfoAbsolutePath == null) {
                webInfoAbsolutePath = event.getServletContext().getResource("/WEB-INF").toURI().getPath();
            }
            ContextPathUtil.setWebInfoAbsolutePath(webInfoAbsolutePath);
            super.contextInitialized(event);
            logger.debug("系统启动完毕....");
        } catch (Exception e) {
            logger.error("系统启动异常,Exception:", e);
        }
    }
}
