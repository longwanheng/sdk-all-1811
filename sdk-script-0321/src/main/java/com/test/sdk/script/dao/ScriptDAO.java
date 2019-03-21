package com.test.sdk.script.dao;


import com.test.sdk.data.pojo.Script;

import java.util.List;

public interface ScriptDAO {

    List<Script> getScriptList(Script script);

    void updateScript(Script script);

    void addScript(Script script);

    Script getScriptById(Integer id);
}
