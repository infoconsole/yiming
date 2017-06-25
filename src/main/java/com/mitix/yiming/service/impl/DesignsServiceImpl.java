package com.mitix.yiming.service.impl;

import com.mitix.yiming.FileUtil;
import com.mitix.yiming.SIDUtil;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Designs;
import com.mitix.yiming.mapper.YiMingMapper;
import com.mitix.yiming.service.DesignsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oldflame on 2017/6/16.
 */
@Service
public class DesignsServiceImpl implements DesignsService {

    @Autowired
    private YiMingMapper yiMingMapper;

    @Autowired
    private FilePathComponent filePathComponent;

    @Override
    @Transactional
    public void saveDesigns(String liningcode, String designname, List<DesFiles> sjlist, List<DesFiles> xglist) {
        String liningcodeTouse = liningcode.trim();
        String designnameTouse = designname.trim();
        int count = yiMingMapper.selectLiningsExists(liningcodeTouse);
        if (count == 0) {
            throw new RuntimeException("请正确填写布料信息");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("liningcode", liningcodeTouse);
        map.put("designname", designnameTouse);
        int count1 = yiMingMapper.selectDisignsExists(map);
        if (count1 > 0) {
            throw new RuntimeException("同一个面料下存在同款的设计");
        }

        String designcode = SIDUtil.getUUID16();
        Designs designs = new Designs();
        designs.setLiningcode(liningcodeTouse);
        designs.setDesigncode(designcode);
        designs.setDesignname(designnameTouse);
        yiMingMapper.insertDesigns(designs);

        if (sjlist != null && sjlist.size() > 0) {
            for (DesFiles desFiles : sjlist) {
                File sourceFile = new File(filePathComponent.getTempFolder(), desFiles.getUrlfix());
                File destFile = new File(filePathComponent.getLogosFolder());
                try {
                    FileUtil.move(sourceFile, destFile, true);
                } catch (Exception e) {
                    throw new RuntimeException();
                }
                desFiles.setDesigncode(designcode);
                desFiles.setType("1");
                yiMingMapper.insertFiles(desFiles);
            }
        }

        if (xglist != null && xglist.size() > 0) {
            for (DesFiles desFiles : xglist) {
                File sourceFile = new File(filePathComponent.getTempFolder(), desFiles.getUrlfix());
                File destFile = new File(filePathComponent.getLogosFolder());
                try {
                    FileUtil.move(sourceFile, destFile, true);
                } catch (Exception e) {
                    throw new RuntimeException();
                }
                desFiles.setDesigncode(designcode);
                desFiles.setType("2");
                yiMingMapper.insertFiles(desFiles);
            }
        }
    }

    @Override
    public List<Designs> listLiningsDesigns(String lcode) {
        String liningcodeToUse = lcode.trim();
        Map<String, Object> param = new HashMap<>();
        param.put("liningcode", liningcodeToUse);
        List<Designs> designsList = yiMingMapper.listLiningsDesigns(param);
        return designsList;
    }

    @Override
    @Transactional
    public void deleteLiningsDesigns(String designcode, String liningcode) {
        String liningcodeTouse = liningcode.trim();
        String designcodeTouse = designcode.trim();
        List<DesFiles> desFilesList = yiMingMapper.selectFilesByDesigns(designcodeTouse);
        //删除图片
        if (desFilesList != null && desFilesList.size() > 0) {
            for (DesFiles desFiles : desFilesList) {
                Integer id = desFiles.getId();
                yiMingMapper.deleteFiles(id);
            }
        }
        //删除设计
        yiMingMapper.deleteDesignsByDesigns(designcodeTouse);
        if (desFilesList != null && desFilesList.size() > 0) {
            for (DesFiles desFiles : desFilesList) {
                try {
                    String filenamenew = desFiles.getUrl();
                    File filepath = new File(filePathComponent.getLogosFolder(), filenamenew);
                    filepath.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
