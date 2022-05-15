package com.a10.mejabelajar.admin.util;

import com.a10.mejabelajar.admin.util.Behaviour.GetLogsAdmin;

public class AdminStrategy extends Strategy {
    public AdminStrategy() {
        getLogsBehaviour = new GetLogsAdmin();
    }
}
