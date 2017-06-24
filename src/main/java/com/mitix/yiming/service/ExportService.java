package com.mitix.yiming.service;

import com.mitix.yiming.bean.Company;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Designs;
import com.mitix.yiming.bean.Linings;
import com.mitix.yiming.bean.Series;

import java.util.List;

/**
 * Created by oldflame on 2017/6/20.
 */
public interface ExportService {
    Company selectCompany();

    List<Series> selectSeries();

    List<Linings> selectLinings();

    List<Designs> selectDesigns();

    List<DesFiles> selectDesFiles();
}
