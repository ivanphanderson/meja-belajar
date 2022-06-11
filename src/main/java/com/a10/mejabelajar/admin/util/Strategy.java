package com.a10.mejabelajar.admin.util;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.admin.util.behaviour.GetLogsBehaviour;

import java.util.List;

public abstract class Strategy {
    GetLogsBehaviour getLogsBehaviour;

    protected Strategy() {}

    public List<Log> getLogs(List<Log> logs) {
        return getLogsBehaviour.get(logs);
    }

}
