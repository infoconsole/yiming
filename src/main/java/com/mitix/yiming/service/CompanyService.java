package com.mitix.yiming.service;

import com.mitix.yiming.bean.Company;

/**
 * Created by oldflame on 2017/6/10.
 */
public interface CompanyService {

    void saveorupdate(String companyname, String address, String tel, String joinhands, String workmanship, String securitycode, String content, String extend1, String extend2, String filenamenew,String advantage);

    Company getcompany();
}
