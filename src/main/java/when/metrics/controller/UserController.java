package when.metrics.controller;

import when.metrics.Metrics;
import when.metrics.vo.UserVo;

import java.util.concurrent.TimeUnit;

/**
 * @author: when
 * @create: 2020-02-22  19:09
 **/
public class UserController {
    private Metrics metrics = new Metrics();

    public UserController() {
        metrics.startedRepeatedReport(10, TimeUnit.SECONDS);
    }

    public void register(UserVo user) {
        long startTimestamp = System.currentTimeMillis();
        metrics.recordTimestamp("register", startTimestamp);
        // ...
        long respTime = System.currentTimeMillis() - startTimestamp;
        metrics.recordResponseTime("register", respTime);
    }

    public UserVo login(String phone, String password) {
        long startTimestamp = System.currentTimeMillis();
        metrics.recordTimestamp("login", startTimestamp);
        // ...
        long respTime = System.currentTimeMillis() - startTimestamp;
        metrics.recordResponseTime("login", respTime);
        return null;
    }
}
