package com.test.sdk.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.test.sdk.dao.GameDAO;
import com.test.sdk.service.service.GameService;
import com.test.sdk.common.pojo.Game;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(timeout = 3000)
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDAO gameDAO;

    @Override
    public List<Game> getAllGame() {
        return gameDAO.getAllGame();
    }
}
