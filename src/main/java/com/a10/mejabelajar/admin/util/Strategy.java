package com.a10.mejabelajar.admin.util;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.admin.util.Behaviour.GetLogsBehaviour;
import java.util.List;

public abstract class Strategy {
    GetLogsBehaviour getLogsBehaviour;

    public Strategy() {}

    public List<Log> getLogs(List<Log> logs) {
        return getLogsBehaviour.get(logs);
    }

}
