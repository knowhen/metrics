package when.metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: when
 * @create: 2020-02-24  14:59
 **/
public class FakeMetricsStore implements MetricsStore {
    private Map<String, List<RequestInfo>> requestInfos = new HashMap<>();

    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {
        String apiName = requestInfo.getApiName();
        requestInfos.putIfAbsent(apiName, new ArrayList<>());
        requestInfos.get(apiName).add(requestInfo);
    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis) {
        Map<String, List<RequestInfo>> result = new HashMap<>();
        for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
            String apiName = entry.getKey();
            List<RequestInfo> apiRequestInfo = entry.getValue();
            List<RequestInfo> requestInfoRequired = new ArrayList<>();
            for (RequestInfo requestInfo : apiRequestInfo) {
                long timestamp = requestInfo.getTimestamp();
                if (timestamp >= startTimeInMillis && timestamp <= endTimeInMillis) {
                    requestInfoRequired.add(requestInfo);
                }
            }
            result.put(apiName, requestInfoRequired);
        }
        return result;
    }
}
