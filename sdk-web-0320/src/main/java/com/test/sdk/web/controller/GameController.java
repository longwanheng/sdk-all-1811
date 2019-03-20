package com.test.sdk.web.controller;

import com.test.sdk.common.pojo.Game;
import com.test.sdk.common.util.ResponseTO;
import com.test.sdk.web.cache.GameCache;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @RequestMapping("common/init.html")
    public ResponseTO init(Integer gid){
        Game game= GameCache.getGame(gid);
        return new ResponseTO(game);
    }
}
