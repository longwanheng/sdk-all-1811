package com.test.sdk.script.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.test.sdk.data.pojo.Script;
import com.test.sdk.data.service.ScriptService;
import com.test.sdk.script.dao.ScriptDAO;
import com.test.sdk.script.util.QuartzManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Service(timeout = 30000)
public class ScriptServiceImpl implements ScriptService, ApplicationContextAware {
    @Autowired
    private ScriptDAO scriptDAO;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    private void init() {//加载数据库中的脚本，注册到调度器中
        Script criteria = new Script();
        criteria.setStatus(1);
        List<Script> scriptList = getScriptList(criteria);//查询所有有效的脚本
        for (Script script : scriptList) {
            Object obj = applicationContext.getBean(script.getName());
            QuartzManager.addJob(script.getName(), obj.getClass(), script.getCron());
        }

    }

    @PreDestroy
    private void destroy() {
        QuartzManager.shutdownJobs();
    }

    @Override
    public List<Script> getScriptList(Script script) {
        return scriptDAO.getScriptList(script);
    }

    @Override
    public void updateScript(Script script) {
        scriptDAO.updateScript(script);

    }

    @Override
    public void doPauseScript(Integer id) {
        Script script = scriptDAO.getScriptById(id);
        script.setStatus(0);
        QuartzManager.pauseJob(script.getName());
        scriptDAO.updateScript(script);
    }

    @Override
    public void addScript(Script script) {
        scriptDAO.addScript(script);
    }

    @Override
    public void deleteScript(Integer id) {
        Script script = scriptDAO.getScriptById(id);
        script.setStatus(-1);
        QuartzManager.removeJob(script.getName());
        scriptDAO.updateScript(script);
    }

    @Override
    public Script getScriptById(Integer id) {
        return scriptDAO.getScriptById(id);
    }

    @Override
    public void doResumScriot(Integer id) {
        Script script = scriptDAO.getScriptById(id);
        int status = QuartzManager.getJobStatus(script.getName());
        System.out.println("状态：" + status);
        //暂停状态是2
        if (status == 2) {
            QuartzManager.resumeJob(script.getName());
        } else {
            Object obj = applicationContext.getBean(script.getName());
            QuartzManager.addJob(script.getName(), obj.getClass(), script.getCron());
        }
        //删除状态后是0 -7
        script.setStatus(1);
        scriptDAO.updateScript(script);
    }
}
