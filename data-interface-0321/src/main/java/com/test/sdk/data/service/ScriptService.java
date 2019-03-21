package com.test.sdk.data.service;




import com.test.sdk.data.pojo.Script;

import java.util.List;

public interface ScriptService {
    List<Script> getScriptList(Script script);

    void updateScript(Script script);

    void addScript(Script script);

    Script getScriptById(Integer id);

    void deleteScript(Integer id);

    void doPauseScript(Integer id);

    void doResumScriot(Integer id);
}
