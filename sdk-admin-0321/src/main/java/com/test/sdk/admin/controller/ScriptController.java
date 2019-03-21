package com.test.sdk.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.test.sdk.admin.pojo.AdminUser;
import com.test.sdk.admin.util.AjaxMessage;
import com.test.sdk.admin.util.TableData;
import com.test.sdk.data.pojo.Script;
import com.test.sdk.data.service.ScriptService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("script.html")
public class ScriptController {
    @Reference
    private ScriptService scriptService;

    @RequestMapping
    public String page() {
        return "script";
    }

    @RequestMapping(params = "act=table")
    @ResponseBody
    public TableData table() {
        List<Script> list = scriptService.getScriptList(null);
        return new TableData(list.size(), list);
    }

    @RequestMapping(params = "act=pause")
    @ResponseBody
    public AjaxMessage pause(Integer id, Boolean run) {
        if (!run) {
            scriptService.doPauseScript(id);
        }else{
            scriptService.doResumScriot(id);
        }
        return new AjaxMessage(true,"成功");
    }
}
