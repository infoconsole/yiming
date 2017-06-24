package com.mitix.yiming.service;

import com.mitix.yiming.Combox;
import com.mitix.yiming.SeriesLining;

import java.util.List;

/**
 * Created by oldflame on 2017/6/13.
 */
public interface SeriesService {

    void saveSeries(String seriescode, String seriesname, String seriescontent, String extend1, String extend2);

    List<Combox> selectSeriesCombox();

    void saveLinings(String seriescode, String liningcode, String liningname, String liningcolor, String filenamenew);

    List<SeriesLining> listSeriesLining(String scode, String sname);

    void deleteSeriesLinings(String seriescode, String liningcode);
}
