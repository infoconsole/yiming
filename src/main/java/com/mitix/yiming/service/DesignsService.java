package com.mitix.yiming.service;

import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Designs;

import java.util.List;

/**
 * Created by oldflame on 2017/6/16.
 */
public interface DesignsService {
    void saveDesigns(String liningcode, String designname, List<DesFiles> sjlist/*, List<DesFiles> xglist*/);

    List<Designs> listLiningsDesigns(String lcode);

    void deleteLiningsDesigns(String designcode, String liningcode);

    Designs selectById(Integer desid);

    List<DesFiles>  listDesFilesByDesignCode(String designcode);

    void updateDesigns(Integer id, String liningcode, String designname, List<DesFiles> sjlistList);
}
