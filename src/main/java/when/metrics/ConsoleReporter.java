package when.metrics;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: when
 * @create: 2020-02-24  09:30
 **/
public class ConsoleReporter {
    private MetricsStore metricsStore;
    private ScheduledExecutorService executor;

    public ConsoleReporter(MetricsStore store) {
        this.metricsStore = store;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 根据给定的时间区间，从数据库拉取数据
                long durationInMillis = durationInSeconds * 1000;
                long endTimeInMillis = System.currentTimeMillis();
                long startTimeInMillis = endTimeInMillis - durationInMillis;

                Map<String, List<RequestInfo>> requestInfos = metricsStore.getRequestInfos(startTimeInMillis,
                        endTimeInMillis);

                Map<String, RequestStat> stats = new HashMap<>();
                for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
                    String apiName = entry.getKey();
                    List<RequestInfo> requestInfoPerApi = entry.getValue();
                    // 根据原始数据计算统计数据
                    RequestStat requestStat = Aggregator.aggregate(requestInfoPerApi, durationInMillis);
                    stats.put(apiName, requestStat);
                }

                System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMillis + "]");
                Gson gson = new Gson();
                System.out.println(gson.toJson(stats));
            }
        }, 0, periodInSeconds, TimeUnit.SECONDS);
    }

}
