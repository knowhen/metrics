package when.metrics;

import java.util.List;
import java.util.Map;

/**
 * @author: when
 * @create: 2020-02-24  14:59
 **/
public class FakeMetricsStore implements MetricsStore {
    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {

    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis) {
        return null;
    }
}
