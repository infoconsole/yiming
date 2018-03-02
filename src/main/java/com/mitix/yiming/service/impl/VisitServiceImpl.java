package com.mitix.yiming.service.impl;

import com.mitix.yiming.FileUtil;
import com.mitix.yiming.SIDUtil;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Designs;
import com.mitix.yiming.bean.Visit;
import com.mitix.yiming.mapper.YiMingMapper;
import com.mitix.yiming.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
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
            throw new RuntimeException("请正确填写回访照名称");
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

    @Override
    public Visit selectById(Integer visitid) {
        return yiMingMapper.selectVisitsById(visitid);
    }

    @Override
    public List<DesFiles> listDesFilesByVisitCode(String visitcode) {
        List<DesFiles> desFiles = yiMingMapper.listDesFilesByDesignCode(visitcode);
//        if (desFiles != null && desFiles.size() > 0) {
//            for (DesFiles desFile : desFiles) {
//                desFile.setType();
//            }
//        }
        return desFiles;
    }

    @Transactional
    @Override
    public void updateVisitsDesigns(Integer id, String visitcode, String visitname, List<DesFiles> visitlistList) {
        String visitcodeTouse = visitcode.trim();
        String visitnameTouse = visitname.trim();
        Visit visit = yiMingMapper.selectVisitsById(id);
        if (visit == null) {
            throw new RuntimeException("未找到回访照代码，不能更新");
        }
        Map<String, Object> param1 = new HashMap<>();
        param1.put("visitcode", visitcode);
        param1.put("visitname", visitnameTouse);
        int count1 = yiMingMapper.selectVisitWithOutExists(param1);
        if (count1 > 0) {
            throw new RuntimeException("请正确填写回访照名称");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("visitname", visitnameTouse);
        yiMingMapper.updateVisitById(map);
        List<DesFiles> inFilesList = yiMingMapper.listDesFilesByDesignCode(visitcodeTouse);
        List<DesFiles> insertDesFiles = new ArrayList<>();
        List<DesFiles> updateDesFiles = new ArrayList<>();
        List<DesFiles> deleteDesFiles = new ArrayList<>();
        List<Integer> updateIdList = new ArrayList<>();
        for (DesFiles desFiles : visitlistList) {
            if (desFiles.getId() == null) {
                insertDesFiles.add(desFiles);
            } else {
                updateDesFiles.add(desFiles);
                updateIdList.add(desFiles.getId());
            }
        }
        for (DesFiles desFiles : inFilesList) {
            if (!updateIdList.contains(desFiles.getId())) {
                deleteDesFiles.add(desFiles);
            }
        }
        if (insertDesFiles != null && insertDesFiles.size() > 0) {
            for (DesFiles insertDesFile : insertDesFiles) {
                File sourceFile = new File(filePathComponent.getTempFolder(), insertDesFile.getUrlfix());
                File destFile = new File(filePathComponent.getLogosFolder());
                try {
                    FileUtil.move(sourceFile, destFile, true);
                } catch (Exception e) {
                    throw new RuntimeException();
                }
                insertDesFile.setDesigncode(visitcodeTouse);
                insertDesFile.setType("99");
                yiMingMapper.insertFiles(insertDesFile);
            }
        }
        if (deleteDesFiles != null && deleteDesFiles.size() > 0) {
            for (DesFiles deleteDesFile : deleteDesFiles) {
                yiMingMapper.deleteDesFiles(deleteDesFile);
            }
        }
    }


}
