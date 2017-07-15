package com.mitix.yiming.service.impl;

import com.mitix.yiming.ContextUtils;
import com.mitix.yiming.bean.Company;
import com.mitix.yiming.mapper.YiMingMapper;
import com.mitix.yiming.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oldflame on 2017/6/10.
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private YiMingMapper yiMingMapper;

    @Override
    public void saveorupdate(String companyname, String address, String tel, String joinhands, String workmanship, String securitycode, String content, String extend1, String extend2, String filenamenew, String advantage) {
        int count = yiMingMapper.selectCompanyExists();
        Map<String, Object> param = new HashMap<>();
        param.put("companyname", companyname);
        param.put("address", address);
        param.put("tel", tel);
        param.put("joinhands", ContextUtils.formatter(joinhands));
        param.put("workmanship", ContextUtils.formatter(workmanship));
        param.put("securitycode", securitycode);
        param.put("content", ContextUtils.formatter(content));
        param.put("extend1", extend1);
        param.put("extend2", extend2);
        param.put("logo", filenamenew);
        param.put("advantage", ContextUtils.formatter(advantage));
        if (count > 0) {
            yiMingMapper.updateCompany(param);
        } else {
            yiMingMapper.insertCompany(param);
        }
    }

    @Override
    public Company getcompany() {
        Company company = yiMingMapper.selectCompany();
        company.setJoinhands(ContextUtils.reformatter(company.getJoinhands()));
        company.setWorkmanship(ContextUtils.reformatter(company.getWorkmanship()));
        company.setContent(ContextUtils.reformatter(company.getContent()));
        company.setAdvantage(ContextUtils.reformatter(company.getAdvantage()));
        return company;
    }

}
