package com.test.sdk.dao;


import com.test.sdk.common.pojo.CP;

import java.util.List;

public interface CPDAO {
    String getCpSecret(Integer id);

    List<CP> getAllCP();
}
