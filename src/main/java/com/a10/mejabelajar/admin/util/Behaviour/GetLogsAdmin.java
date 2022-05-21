package com.a10.mejabelajar.admin.util.Behaviour;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.admin.repository.LogRepository;
import com.a10.mejabelajar.admin.service.LogService;
import com.a10.mejabelajar.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GetLogsAdmin implements GetLogsBehaviour{
    @Override
    public List<Log> get(List<Log> logs) {
        return logs;
    }
}
