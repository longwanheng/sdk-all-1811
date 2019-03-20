package com.test.sdk.service.service;

import com.test.sdk.common.pojo.CP;

import java.util.List;

public interface CPService {
    String getCpSecret(Integer id);

    List<CP> getAllCP();
}
