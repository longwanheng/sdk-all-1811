package com.test.sdk.web.cache;

import com.alibaba.dubbo.config.annotation.Reference;
import com.test.sdk.common.pojo.CP;
import com.test.sdk.service.service.CPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//内存缓存
@Component
public class CPCache extends AbstractCache {


    public CPCache() {
        //super.setInterval(60*1000*20);
    }

    @Reference
    private CPService cpDAO;


//    public void setCpDao(CPDAO cpDAO) {
//        CPCache.cpDAO = cpDAO;
//    }

    private static Map<Integer, String> cpMap = new ConcurrentHashMap<>();

    @Override
    public void update() {
        List<CP> cpList = cpDAO.getAllCP();
        Map<Integer, String> temp=new HashMap<>();
        for (CP cp : cpList) {
            temp.put(cp.getId(), cp.getSecret());
        }
        if(!temp.isEmpty()){
            cpMap=temp;
        }
    }

    public static String getCpSecret(Integer id) {
        return cpMap.get(id);
    }
}
