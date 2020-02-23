package when.metrics;

import com.google.gson.Gson;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: when
 * @create: 2020-02-23  15:00
 **/
public class Metrics {
    private Map<String, List<Double>> timestamps = new HashMap<>();
    private Map<String, List<Double>> responseTimes = new HashMap<>();
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public void recordTimestamp(String apiName, double timestamp) {
        timestamps.putIfAbsent(apiName, new ArrayList<>());
        timestamps.get(apiName).add(timestamp);
    }

    public void recordResponseTime(String apiName, double responseTime) {
        this.responseTimes.putIfAbsent(apiName, new ArrayList<>());
        this.responseTimes.get(apiName).add(responseTime);
    }

    public void startedRepeatedReport(long period, TimeUnit unit) {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Map<String, Map<String, Double>> stats = new HashMap<>();
                for (Map.Entry<String, List<Double>> entry : responseTimes.entrySet()) {
                    String apiName = entry.getKey();
                    List<Double> apiRespTimes = entry.getValue();
                    stats.putIfAbsent(apiName, new HashMap<>());
                    stats.get(apiName).put("max", max(apiRespTimes));
                    stats.get(apiName).put("avg", avg(apiRespTimes));
                }

                for (Map.Entry<String, List<Double>> entry : timestamps.entrySet()) {
                    String apiName = entry.getKey();
                    List<Double> apiRespTimes = entry.getValue();
                    stats.putIfAbsent(apiName, new HashMap<>());
                    stats.get(apiName).put("count", (double) (apiRespTimes.size()));
                }

                System.out.println(gson.toJson(stats));
            }
        }, 0, period, unit);
    }

    private double max(List<Double> dataset) {
        if (dataset.isEmpty()) {
            return 0.0;
        }
        Collections.sort(dataset);
        return dataset.get(dataset.size() - 1);
    }

    private double avg(List<Double> dataset) {
        double sum = 0.0;
        if (dataset.isEmpty()) {
            return sum;
        }
        for (double data : dataset) {
            sum += data;
        }
        return sum / dataset.size();
    }

}
