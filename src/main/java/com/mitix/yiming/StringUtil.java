package com.mitix.yiming;

import java.io.UnsupportedEncodingException;

/**
 * 创建于:2014-12-5<br>
 * 版权所有(C) 2014 深圳市银之杰科技股份有限公司<br>
 * 字符串处理
 *
 * @author honglvhang
 * @version 1.0.0.0
 */
public class StringUtil {
    /**
     * 点号.
     */
    public static final String DOT = ".";
    /**
     * 空，不是null
     */
    public static final String EMPTY = "";
    /**
     * unix linux文件分隔符,斜杠字符"/"
     */
    public static final char DIR_SEPARATOR_UNIX = '/';
    /**
     * windows文件分隔符,双反斜杠字符"\\"
     */
    public static final char DIR_SEPARATOR_WINDOWS = '\\';

    /**
     * unix linux换行符(换行)
     */
    public static final String LINE_SEPARATOR_UNIX = "\n";
    /**
     * windows换行符(回车换行)
     */
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    /**
     * html占位符"&nbsp;"
     */
    public static final String HTML_NBSP = "&nbsp;";
    /**
     * html符号'"'
     */
    public static final String HTML_AMP = "&amp";
    /**
     * html符号'"'
     */
    public static final String HTML_QUOTE = "&quot;";
    /**
     * html符号'<'
     */
    public static final String HTML_LT = "&lt;";
    /**
     * html符号'>'
     */
    public static final String HTML_GT = "&gt;";

    /***
     * 字符串是否为空白 空白的定义如下： 1、为null 2、""
     *
     * @param str
     *            输入字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    /***
     * 字符串是否为空白 空白的定义如下： 1、为null 2、为不可见字符（如空格） 3、""
     *
     * @param str
     *            输入字符串
     * @return 是否为空
     */
    public static boolean isBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 去除字符串两边的空格符，如果为null返回null
     *
     * @param str 字符串
     * @return 处理后的字符串
     */
    public static String trim(String str) {
        return trim(str, null);
    }

    /**
     * 去除字符串两边的空格符，如果为null返回默认值
     *
     * @param str          字符串
     * @param defaultValue 默认值参数
     * @return 处理后的字符串
     */
    public static String trim(String str, String defaultValue) {
        return (null == str) ? defaultValue : str.trim();
    }

    /**
     * 大写首字母 例如：str = name, return Name
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String upperFirst(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 小写首字母 例如：str = Name, return name
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String lowerFirst(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 清理空白字符
     *
     * @param str 被清理的字符串
     * @return 清理后的字符串
     */
    public static String cleanBlank(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\s*", EMPTY);
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集
     * @return 编码后的字节码
     */
    public static byte[] encode(String str, String charset) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Charset [{" + charset
                    + "}] unsupported!");
        }
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集
     * @return 解码后的字符串
     * @throws UnsupportedEncodingException 异常
     */
    public static String decode(byte[] data, String charset)
            throws UnsupportedEncodingException {
        if (data == null) {
            return null;
        }
        return new String(data, charset);
    }

    /**
     * 全角字符转半角字符
     *
     * @param str 字符串
     * @return 半角字符串
     */
    public static String getDBC(String str) {
        char c[] = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);
        return returnString;
    }

    /**
     * 半角转全角
     *
     * @param str 字符串
     * @return 全角字符串
     */
    public static String getSBC(String str) {
        char c[] = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

}
