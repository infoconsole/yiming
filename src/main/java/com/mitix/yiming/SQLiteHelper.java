package com.mitix.yiming;

import com.mitix.yiming.bean.Company;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Designs;
import com.mitix.yiming.bean.Linings;
import com.mitix.yiming.bean.Series;
import com.mitix.yiming.service.ExportService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SQLiteHelper {

    private Connection conn;

    private List<String> tableSql = new ArrayList<String>();

    private Statement stat;

    private ExportService exportService;

    {
        tableSql.add(" CREATE TABLE \"company\" (\n" +
                "  \"ID\"           INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  \"COMPANYNAME\"  TEXT,\n" +
                "  \"LOGO\"         TEXT,\n" +
                "  \"ADDRESS\"      TEXT,\n" +
                "  \"TEL\"          TEXT,\n" +
                "  \"JOINHANDS\"    TEXT,\n" +
                "  \"SECURITYCODE\" TEXT,\n" +
                "  \"CONTENT\"      TEXT,\n" +
                "  \"EXTEND1\"      TEXT,\n" +
                "  \"EXTEND2\"      TEXT,\n" +
                "  \"WORKMANSHIP\"  TEXT\n" +
                ")");
        tableSql.add(" CREATE TABLE \"designs\" (\n" +
                "  \"ID\"         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  \"LININGCODE\" TEXT,\n" +
                "  \"DESIGNCODE\" TEXT,\n" +
                "  \"DESIGNNAME\" TEXT,\n" +
                "  \"EXTEND\"     TEXT\n" +
                ")");
        tableSql.add(" CREATE TABLE \"files\" (\n" +
                "  \"ID\"         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  \"DESIGNCODE\" TEXT,\n" +
                "  \"TYPE\"       TEXT,\n" +
                "  \"URL\"        TEXT,\n" +
                "  \"CONTENT\"    TEXT\n" +
                ")");
        tableSql.add(" CREATE TABLE \"linings\" (\n" +
                "  \"ID\"             INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  \"SERIESCODE\"     TEXT,\n" +
                "  \"LININGCODE\"     TEXT,\n" +
                "  \"LININGNAME\"     TEXT,\n" +
                "  \"LININGCOLOR\"    INTEGER,\n" +
                "  \"LININGCOLORURL\" TEXT,\n" +
                "  \"EXTEND1\"        TEXT,\n" +
                "  \"EXTEND2\"        TEXT\n" +
                ")");
        tableSql.add(" CREATE TABLE \"series\" (\n" +
                "  \"ID\"            INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  \"SERIESCODE\"    TEXT,\n" +
                "  \"SERIESNAME\"    TEXT,\n" +
                "  \"SERIESCONTENT\" TEXT,\n" +
                "  \"EXTEND1\"       TEXT,\n" +
                "  \"EXTEND2\"       TEXT\n" +
                ")");
        tableSql.add("CREATE TABLE if not exists 'android_metadata' ('locale' TEXT DEFAULT 'zh_CN')");
        tableSql.add("INSERT INTO 'android_metadata' VALUES ('zh_CN')");
    }


    public SQLiteHelper(String path) {
        try {
            Class.forName("org.sqlite.JDBC");

            Properties p = new Properties();
            p.setProperty("useUnicode", "true");
            p.setProperty("characterEncoding", "utf-8");
            // 建立一个数据库名app.db的连接，如果不存在就在当前目录下创建之
            conn = DriverManager.getConnection("jdbc:sqlite:" + path, p);

            stat = conn.createStatement();

            for (String sql : tableSql) {
                stat.executeUpdate(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
            destroy();
        }

    }


    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
    }

    public void insertDb() {
        try {
            insertCompany();
            insertSeries();
            insertLinings();
            insertDesigns();
            insertFiles();
            updateSqliteSeq();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出数据错误", e);
        } finally {
            destroy();
        }

    }

    private void updateSqliteSeq() {
    }

    private void insertFiles() throws SQLException {
        List<DesFiles> desFilesList = exportService.selectDesFiles();
        if (desFilesList != null && desFilesList.size() > 0) {
            for (DesFiles desFiles : desFilesList) {
                StringBuffer sql = new StringBuffer(" INSERT INTO files(DESIGNCODE, TYPE, URL, CONTENT) VALUES (");
                sql.append("'").append(desFiles.getDesigncode() == null ? "" : desFiles.getDesigncode()).append("',");
                sql.append("'").append(desFiles.getType() == null ? "" : desFiles.getType()).append("',");
                sql.append("'").append(desFiles.getUrl() == null ? "" : desFiles.getUrl()).append("',");
                sql.append("'").append(desFiles.getContent() == null ? "" : desFiles.getContent()).append("')");
                stat.executeUpdate(sql.toString());
            }
        }

    }

    private void insertDesigns() throws SQLException {
        List<Designs> designsList = exportService.selectDesigns();
        if (designsList != null && designsList.size() > 0) {
            for (Designs designs : designsList) {
                StringBuffer sql = new StringBuffer(" INSERT INTO designs(LININGCODE, DESIGNCODE, DESIGNNAME, EXTEND) VALUES (");
                sql.append("'").append(designs.getLiningcode() == null ? "" : designs.getLiningcode()).append("',");
                sql.append("'").append(designs.getDesigncode() == null ? "" : designs.getDesigncode()).append("',");
                sql.append("'").append(designs.getDesignname() == null ? "" : designs.getDesignname()).append("',");
                sql.append("'").append(designs.getExtend1() == null ? "" : designs.getExtend1()).append("')");
                stat.executeUpdate(sql.toString());
            }
        }
    }

    private void insertLinings() throws SQLException {
        List<Linings> liningsList = exportService.selectLinings();
        if (liningsList != null && liningsList.size() > 0) {
            for (Linings linings : liningsList) {
                StringBuffer sql = new StringBuffer(" INSERT INTO linings (SERIESCODE, LININGCODE, LININGNAME, LININGCOLOR, LININGCOLORURL, EXTEND1, EXTEND2) VALUES (");
                sql.append("'").append(linings.getSeriescode() == null ? "" : linings.getSeriescode()).append("',");
                sql.append("'").append(linings.getLiningcode() == null ? "" : linings.getLiningcode()).append("',");
                sql.append("'").append(linings.getLiningname() == null ? "" : linings.getLiningname()).append("',");
                sql.append("'").append(linings.getLiningcolor() == null ? "" : linings.getLiningcolor()).append("',");
                sql.append("'").append(linings.getLiningcolorurl() == null ? "" : linings.getLiningcolorurl()).append("',");
                sql.append("'").append(linings.getExtend1() == null ? "" : linings.getExtend1()).append("',");
                sql.append("'").append(linings.getExtend2() == null ? "" : linings.getExtend2()).append("')");
                stat.executeUpdate(sql.toString());
            }
        }

    }

    private void insertSeries() throws SQLException {
        List<Series> seriesList = exportService.selectSeries();
        if (seriesList != null && seriesList.size() > 0) {
            for (Series series : seriesList) {
                StringBuffer sql = new StringBuffer(" INSERT INTO series(SERIESCODE, SERIESNAME, SERIESCONTENT, EXTEND1, EXTEND2) VALUES (");
                sql.append("'").append(series.getSeriescode() == null ? "" : series.getSeriescode()).append("',");
                sql.append("'").append(series.getSeriesname() == null ? "" : series.getSeriesname()).append("',");
                sql.append("'").append(series.getSeriescontent() == null ? "" : series.getSeriescontent()).append("',");
                sql.append("'").append(series.getExtend1() == null ? "" : series.getExtend1()).append("',");
                sql.append("'").append(series.getExtend2() == null ? "" : series.getExtend2()).append("')");
                stat.executeUpdate(sql.toString());
            }
        }
    }

    private void insertCompany() throws SQLException {
        Company company = exportService.selectCompany();
        StringBuffer sql = new StringBuffer(" INSERT INTO company(COMPANYNAME, LOGO, ADDRESS, TEL, JOINHANDS, WORKMANSHIP, SECURITYCODE, CONTENT, EXTEND1, EXTEND2) VALUES (");
        sql.append("'").append(company.getCompanyname() == null ? "" : company.getCompanyname()).append("',");
        sql.append("'").append(company.getLogo() == null ? "" : company.getLogo()).append("',");
        sql.append("'").append(company.getAddress() == null ? "" : company.getAddress()).append("',");
        sql.append("'").append(company.getTel() == null ? "" : company.getTel()).append("',");
        sql.append("'").append(company.getJoinhands() == null ? "" : company.getJoinhands()).append("',");
        sql.append("'").append(company.getWorkmanship() == null ? "" : company.getWorkmanship()).append("',");
        sql.append("'").append(company.getSecuritycode() == null ? "" : company.getSecuritycode()).append("',");
        sql.append("'").append(company.getContent() == null ? "" : company.getContent()).append("',");
        sql.append("'").append(company.getExtend1() == null ? "" : company.getExtend1()).append("',");
        sql.append("'").append(company.getExtend2() == null ? "" : company.getExtend2()).append("')");
        stat.executeUpdate(sql.toString());
    }


    public void destroy() {
        try {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
