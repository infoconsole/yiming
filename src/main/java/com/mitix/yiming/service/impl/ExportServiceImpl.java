package com.mitix.yiming.service.impl;

import com.mitix.yiming.bean.Company;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Designs;
import com.mitix.yiming.bean.Linings;
import com.mitix.yiming.bean.Series;
import com.mitix.yiming.bean.Visit;
import com.mitix.yiming.mapper.YiMingMapper;
import com.mitix.yiming.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oldflame on 2017/6/20.
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private YiMingMapper yiMingMapper;

    @Override
    public Company selectCompany() {
        return yiMingMapper.selectCompany();
    }

    @Override
    public List<Series> selectSeries() {
        return yiMingMapper.selectSeries();
    }

    @Override
    public List<Linings> selectLinings() {
        return yiMingMapper.selectLinings();
    }

    @Override
    public List<Designs> selectDesigns() {
        return yiMingMapper.selectDesigns();
    }

    @Override
    public List<DesFiles> selectDesFiles() {
        return yiMingMapper.selectDesFiles();
    }

    @Override
    public List<Visit> selectVisit() {
        Map<String, Object> param = new HashMap<>();
        param.put("visitname", null);
        return yiMingMapper.listVisits(param);
    }
}
