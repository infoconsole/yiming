package com.mitix.yiming.service.impl;

import com.mitix.yiming.FileUtil;
import com.mitix.yiming.bean.Company;
import com.mitix.yiming.bean.DesFiles;
import com.mitix.yiming.bean.Linings;
import com.mitix.yiming.mapper.YiMingMapper;
import com.mitix.yiming.service.ZpicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by oldflame-jm on 2017/6/25.
 */
@Service
public class ZpicServiceImpl implements ZpicService {
    @Autowired
    private YiMingMapper yiMingMapper;
    @Autowired
    private FilePathComponent filePathComponent;


    @Override
    public void zpic() {
        Set<String> urlSet = new HashSet<>();
        Company company = yiMingMapper.selectCompany();
        urlSet.add(company.getLogo());
        List<Linings> liningsList = yiMingMapper.selectLinings();
        if (liningsList != null && liningsList.size() > 0) {
            for (Linings linings : liningsList) {
                urlSet.add(linings.getLiningcolorurl());
            }
        }
        List<DesFiles> desFilesList = yiMingMapper.selectDesFiles();
        if (desFilesList != null|| desFilesList.size()>0) {
            for (DesFiles desFiles : desFilesList) {
                urlSet.add(desFiles.getUrl());
            }
        }

        File tmpfile = new File(filePathComponent.getLogosFolder());
        if (tmpfile.isDirectory()) {
            File[] files = tmpfile.listFiles();
            for (File childFile : files) {
                String childFileName=childFile.getName();
                String filefix = childFileName.substring(0, childFileName.lastIndexOf("."));
                if (!urlSet.contains(filefix)) {
                    try {
                        FileUtil.deleteFile(childFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
