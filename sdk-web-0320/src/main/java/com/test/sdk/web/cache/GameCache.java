package com.test.sdk.web.cache;

import com.alibaba.dubbo.config.annotation.Reference;
import com.test.sdk.common.pojo.Game;
import com.test.sdk.service.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameCache extends AbstractCache {
    @Reference
    private GameService gameDAO;

    private static Map<Integer, Game> gameMap = new ConcurrentHashMap<>();

    @Override
    public void update() {
        List<Game> games = gameDAO.getAllGame();
        Map<Integer, Game> temp = new HashMap<>();
        for (Game game : games) {
            temp.put(game.getId(), game);
        }
        if (!temp.isEmpty()) {
            gameMap = temp;
        }
    }

    public static Game getGame(Integer id) {
        return gameMap.get(id);
    }
}
