package com.mitix.yiming.service.impl;

import com.mitix.yiming.FileUtil;
import com.mitix.yiming.SIDUtil;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Visit;
import com.mitix.yiming.mapper.YiMingMapper;
import com.mitix.yiming.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private YiMingMapper yiMingMapper;

    @Autowired
    private FilePathComponent filePathComponent;

    @Override
    public List<Visit> listVisits(String visitname) {
        String visitnameTouse = visitname.trim();
        Map<String, Object> param = new HashMap<>();
        param.put("visitname", visitnameTouse);
        List<Visit> visitList = yiMingMapper.listVisits(param);
        return visitList;
    }

    @Override
    @Transactional
    public void deleteVisit(String visitcode) {
        String visitcodeTouse = visitcode.trim();
        List<DesFiles> desFilesList = yiMingMapper.selectFilesByDesigns(visitcodeTouse);
        //删除图片
        if (desFilesList != null && desFilesList.size() > 0) {
            for (DesFiles desFiles : desFilesList) {
                Integer id = desFiles.getId();
                yiMingMapper.deleteFiles(id);
            }
        }
        //删除试试
        yiMingMapper.deleteVisit(visitcodeTouse);
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


    @Transactional
    @Override
    public void saveVisits(String visitname, List<DesFiles> visitlistList) {
        String visitnameTouse = visitname.trim();
        String visitcode = SIDUtil.getUUID16();
        Map<String, Object> param = new HashMap<>();
        param.put("visitcode", visitcode);
        param.put("visitname", visitnameTouse);
        int count = yiMingMapper.selectVisitExists(param);
        if (count > 0) {
            throw new RuntimeException("请正回访照类型");
        }
        Visit visit = new Visit();
        visit.setVisitcode(visitcode);
        visit.setVisitname(visitnameTouse);
        yiMingMapper.insertVisits(visit);

        if (visitlistList != null && visitlistList.size() > 0) {
            for (DesFiles desFiles : visitlistList) {
                File sourceFile = new File(filePathComponent.getTempFolder(), desFiles.getUrlfix());
                File destFile = new File(filePathComponent.getLogosFolder());
                try {
                    FileUtil.move(sourceFile, destFile, true);
                } catch (Exception e) {
                    throw new RuntimeException();
                }
                desFiles.setDesigncode(visitcode);
                desFiles.setType("99");
                yiMingMapper.insertFiles(desFiles);
            }
        }
    }



}
