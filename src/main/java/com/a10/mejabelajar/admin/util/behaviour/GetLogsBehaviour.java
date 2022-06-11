package com.a10.mejabelajar.admin.util.Behaviour;

import com.a10.mejabelajar.admin.model.Log;
import com.a10.mejabelajar.auth.model.User;

import java.util.List;

public interface GetLogsBehaviour {
    List<Log> get(List<Log> logs);
}
