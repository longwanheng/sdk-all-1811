package com.test.sdk.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.test.sdk.dao.CPDAO;
import com.test.sdk.service.service.CPService;
import com.test.sdk.common.pojo.CP;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(timeout = 3000)
public class CPServiceImpl implements CPService {

    @Autowired
    private CPDAO cpDAO;
    @Override
    public String getCpSecret(Integer id) {
        return cpDAO.getCpSecret(id);
    }

    @Override
    public List<CP> getAllCP() {
        return cpDAO.getAllCP();
    }
}
