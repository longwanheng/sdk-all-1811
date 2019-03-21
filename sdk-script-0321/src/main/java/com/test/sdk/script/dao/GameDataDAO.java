package com.test.sdk.script.dao;

import com.test.sdk.data.pojo.RegistData;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GameDataDAO {

    List<RegistData> getGameRegistData(@Param("gameId") Integer gameId, @Param("start") Date start, @Param("end") Date end);

    void insertGameRegistData(RegistData data);
}
