package when.metrics;

import java.util.*;

/**
 * @author: when
 * @create: 2020-02-24  09:29
 **/
public class Aggregator {
    public Map<String, RequestStat> aggregate(Map<String, List<RequestInfo>> requestInfos, long durationInMillis) {
        Map<String, RequestStat> requestStats = new HashMap<>();
        for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
            String apiName = entry.getKey();
            List<RequestInfo> requestInfosPerApi = entry.getValue();
            RequestStat requestStat = doAggregate(requestInfosPerApi, durationInMillis);
            requestStats.put(apiName, requestStat);
        }
        return requestStats;
    }

    public RequestStat doAggregate(List<RequestInfo> requestInfos, long durationInMillis) {
        List<Double> responseTimes = new ArrayList<>();
        for (RequestInfo requestInfo : requestInfos) {
            double responseTime = requestInfo.getResponseTime();
            responseTimes.add(responseTime);
        }

        RequestStat requestStat = new RequestStat();
        requestStat.setMinResponseTime(min(responseTimes));
        requestStat.setMaxResponseTime(max(responseTimes));
        requestStat.setAvgResponseTime(avg(responseTimes));
        requestStat.setP99ResponseTime(percentile99(responseTimes));
        requestStat.setP999ResponseTime(percentile999(responseTimes));
        requestStat.setCount(responseTimes.size());
        requestStat.setTps((long) tps(responseTimes.size(), durationInMillis / 1000));

        return requestStat;
    }

    private double min(List<Double> dataset) {
        Collections.sort(dataset);
        return dataset.get(0);
    }

    private double max(List<Double> dataset) {
        Collections.sort(dataset);
        return dataset.get(dataset.size() - 1);
    }

    private double avg(List<Double> dataset) {
        if (dataset.size() == 0) return 0;

        double sumRespTime = 0;
        for (double responseTime : dataset) {
            sumRespTime += responseTime;
        }
        return sumRespTime / dataset.size();
    }

    private double tps(int count, double duration) {
        return count / duration * 1000;
    }

    private double percentile99(List<Double> dataset) {
        double idx99 = (dataset.size() * 0.99);
        return percentile(dataset, idx99);
    }

    private double percentile999(List<Double> dataset) {
        double idx999 = (dataset.size() * 0.999);
        return percentile(dataset, idx999);
    }

    private double percentile(List<Double> dataset, double ratio) {
        return dataset.get((int) ratio);
    }
}
