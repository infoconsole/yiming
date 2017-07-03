package com.mitix.yiming.service.impl;

import com.mitix.yiming.Combox;
import com.mitix.yiming.ContextUtils;
import com.mitix.yiming.SeriesLining;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.mapper.YiMingMapper;
import com.mitix.yiming.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oldflame on 2017/6/13.
 */
@Service
public class SeriesServiceImpl implements SeriesService {
    @Autowired
    private YiMingMapper yiMingMapper;
    @Autowired
    private FilePathComponent filePathComponent;

    @Override
    @Transactional
    public void saveSeries(String seriescode, String seriesname, String seriescontent, String extend1, String extend2) {
        String seriescodeTouse = seriescode.trim();
        int count = yiMingMapper.selectSeriesExists(seriescodeTouse);
        if (count > 0) {
            throw new RuntimeException("系列信息已经存在");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("seriescode", seriescodeTouse);
        param.put("seriesname", seriesname);
        param.put("seriescontent", ContextUtils.formatter(seriescontent));
        yiMingMapper.insertSeries(param);
    }

    @Override
    @Transactional
    public void saveLinings(String seriescode, String liningcode, String liningname, String liningcolor, String liningcolorurl) {
        String liningcodeTouse = liningcode.trim();
        String seriescodeTouse = seriescode.trim();
        int count = yiMingMapper.selectLiningsExists(liningcodeTouse);
        if (count > 0) {
            throw new RuntimeException("布料信息已经存在");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("seriescode", seriescodeTouse);
        param.put("liningcode", liningcodeTouse);
        param.put("liningname", liningname);
        param.put("liningcolor", liningcolor);
        param.put("liningcolorurl", liningcolorurl);
        yiMingMapper.insertLinings(param);
    }

    @Override
    public List<SeriesLining> listSeriesLining(String scode, String sname) {
        Map<String, Object> param = new HashMap<>();
        param.put("seriescode", scode);
        param.put("seriesname", sname);
        return yiMingMapper.listSeriesLining(param);
    }

    @Override
    @Transactional
    public void deleteSeriesLinings(String seriescode, String liningcode) {
        String liningcodeTouse = liningcode.trim();
        String seriescodeTouse = seriescode.trim();
        List<DesFiles> desFilesList = yiMingMapper.selectFiles(liningcodeTouse);
        if (desFilesList != null && desFilesList.size() > 0) {
            for (DesFiles desFiles : desFilesList) {
                Integer id = desFiles.getId();
                yiMingMapper.deleteFiles(id);
            }
        }
        yiMingMapper.deleteDesigns(liningcodeTouse);
        yiMingMapper.deleteLinings(liningcodeTouse);
//        Map<String, Object> param = new HashMap<>();
//        param.put("seriescode", seriescodeTouse);
//        param.put("liningcode", liningcodeTouse);
//        int count = yiMingMapper.selectCountExcludes(param);
//        if (count == 0) {
//            yiMingMapper.deleteSeries(seriescode);
//        }
        if (desFilesList != null && desFilesList.size() > 0) {
            for (DesFiles desFiles : desFilesList) {
                try {
                    String filenamenew = desFiles.getUrlfix();
                    File filepath = new File(filePathComponent.getLogosFolder(), filenamenew);
                    filepath.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Combox> selectSeriesCombox() {
        return yiMingMapper.selectSeriesCombox();
    }


}
