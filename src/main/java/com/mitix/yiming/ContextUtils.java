package com.mitix.yiming;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by oldflame-jm on 2017/7/3.
 */
public abstract class ContextUtils {

    public static String formatter(String context) {
        if (context == null || context == "") {
            return context;
        }
        context = context.replaceAll("\r|\n", " ");
        String[] cts = context.split(" ");
        StringBuffer buffer = new StringBuffer();
        for (String ct : cts) {
            if (StringUtils.isNotBlank(ct)) {
                if (!ct.startsWith("<p>")) {
                    buffer.append("<p>      ").append(ct).append("</p>");
                } else {
                    buffer.append(ct);
                }
            }
        }
        return buffer.toString();
    }

    public static String reformatter(String context) {
        if (context == null || context == "") {
            return context;
        }
        context = context.replaceAll("<p>", "      ");
        context = context.replaceAll("</p>", "\r\n");
        return context;
    }
}
