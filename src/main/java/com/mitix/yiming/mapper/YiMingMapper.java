package com.mitix.yiming.mapper;

import com.mitix.yiming.Combox;
import com.mitix.yiming.SeriesLining;
import com.mitix.yiming.bean.Company;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Designs;
import com.mitix.yiming.bean.Linings;
import com.mitix.yiming.bean.Series;

import java.util.List;
import java.util.Map;

/**
 * Created by oldflame on 2017/6/10.
 */

public interface YiMingMapper {

    int selectCompanyExists();

    void updateCompany(Map<String, Object> param);

    void insertCompany(Map<String, Object> param);

    Company selectCompany();

    int selectSeriesExists(String seriescode);

    void insertSeries(Map<String, Object> param);

    List<Combox> selectSeriesCombox();

    int selectLiningsExists(String liningcodeTouse);

    void insertLinings(Map<String, Object> param);

    List<SeriesLining> listSeriesLining(Map<String, Object> param);

    void deleteDesigns(String liningcodeTouse);

    List<DesFiles> selectFiles(String liningcodeTouse);

    void deleteFiles(Integer id);

    void deleteLinings(String liningcodeTouse);

    int selectCountExcludes(Map<String, Object> param);

    void deleteSeries(String seriescode);

    void insertDesigns(Designs designs);

    void insertFiles(DesFiles desFiles);

    int selectDisignsExists(Map<String, Object> map);

    List<DesFiles> selectFilesByDesigns(String designcodeTouse);

    void deleteDesignsByDesigns(String designcodeTouse);

    List<Designs> listLiningsDesigns(Map<String, Object> param);

    List<Series> selectSeries();

    List<Linings> selectLinings();

    List<Designs> selectDesigns();

    List<DesFiles> selectDesFiles();

    Designs selectDesignsById(Integer desid);

    List<DesFiles>  listDesFilesByDesignCode(String designcode);

    void updateDesignsById(Map<String, Object> map);

    void updateDesFiles(DesFiles updateDesFile);

    void deleteDesFiles(DesFiles deleteDesFile);

    int updateSeries(Map<String, Object> param);

    int countLiningsBySeriesCode(String seriescodeTouse);
}
