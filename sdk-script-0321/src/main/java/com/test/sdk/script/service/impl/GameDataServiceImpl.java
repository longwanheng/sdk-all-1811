package com.test.sdk.script.service.impl;

import com.test.sdk.data.pojo.RegistData;
import com.test.sdk.data.service.GameDataService;
import com.test.sdk.script.dao.GameDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class GameDataServiceImpl implements GameDataService {
    @Autowired
    private GameDataDAO gameDataDAO;

    @Override
    public void doStatRegistData() {
        //应该先查询出所有的游戏，然后遍历游戏获得gameid
        Integer gameId=200;
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);

        Date today=calendar.getTime();
        calendar.add(Calendar.DATE,-1);
        Date yesterday=calendar.getTime();

        List<RegistData> datas=gameDataDAO.getGameRegistData(gameId,yesterday,today);
        for (RegistData data : datas) {
            data.setDate(yesterday);
            gameDataDAO.insertGameRegistData(data);
        }
    }
}
