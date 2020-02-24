package when.metrics;

import java.util.List;
import java.util.Map;

public interface MetricsStore {
    void saveRequestInfo(RequestInfo requestInfo);

    Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis);
}
