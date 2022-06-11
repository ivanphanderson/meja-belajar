package com.a10.mejabelajar.admin.util.Behaviour;

import com.a10.mejabelajar.admin.model.Log;
import java.util.List;

public class GetLogsAdmin implements GetLogsBehaviour {
    @Override
    public List<Log> get(List<Log> logs) {
        return logs;
    }
}
