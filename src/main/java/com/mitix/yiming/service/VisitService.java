package com.mitix.yiming.service;

import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Visit;

import java.util.List;

public interface VisitService {
    List<Visit> listVisits(String visitname);

    void deleteVisit(String visitcode);

    void saveVisits(String visitname, List<DesFiles> sjlistList);

    Visit selectById(Integer visitid);

    List<DesFiles> listDesFilesByVisitCode(String visitcode);

    void updateVisitsDesigns(Integer id,String visitcode, String visitname, List<DesFiles> visitlistList);
}
