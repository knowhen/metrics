package when.metrics;

import java.util.Collections;
import java.util.List;

/**
 * @author: when
 * @create: 2020-02-24  09:29
 **/
public class Aggregator {
    public static RequestStat aggregate(List<RequestInfo> requestInfos, long durationInMillis) {
        double maxRespTime = Double.MAX_VALUE;
        double minRespTime = Double.MIN_VALUE;
        double sumRespTime = 0;
        double avgRespTime = -1;
        double p99RespTime = -1;
        double p999RespTime = -1;
        long count = 0;

        // 遍历请求数据，累加请求时间，统计请求次数
        for (RequestInfo requestInfo : requestInfos) {
            ++count;
            double responseTime = requestInfo.getResponseTime();
            if (responseTime > maxRespTime) {
                maxRespTime = responseTime;
            }
            if (responseTime < minRespTime) {
                minRespTime = responseTime;
            }

            sumRespTime += responseTime;
        }

        // 计算平均请求时间
        if (count != 0) {
            avgRespTime = sumRespTime / count;
        }

        // 计算tps
        long tps = count / durationInMillis * 1000;

        // 排序
        Collections.sort(requestInfos, (o1, o2) -> {
            double diff = o1.getResponseTime() - o2.getResponseTime();
            if (diff < 0.0) {
                return -1;
            } else if (diff > 0.0) {
                return 1;
            } else {
                return 0;
            }
        });

        // 计算第99和999百分位处的值
        int idx99 = (int) (count * 0.99);
        int idx999 = (int) (count * 0.999);
        if (count != 0) {
            p99RespTime = requestInfos.get(idx99).getResponseTime();
            p999RespTime = requestInfos.get(idx999).getResponseTime();
        }

        RequestStat requestStat = new RequestStat();
        requestStat.setMinResponseTime(minRespTime);
        requestStat.setMaxResponseTime(maxRespTime);
        requestStat.setAvgResponseTime(avgRespTime);
        requestStat.setP99ResponseTime(p99RespTime);
        requestStat.setP999ResponseTime(p999RespTime);
        requestStat.setCount(count);
        requestStat.setTps(tps);

        return requestStat;
    }
}
