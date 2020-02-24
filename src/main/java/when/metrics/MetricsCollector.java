package when.metrics;

import when.metrics.utils.StringUtils;

/**
 * @author: when
 * @create: 2020-02-24  09:29
 **/
public class MetricsCollector {
    private MetricsStore metricsStore;

    public MetricsCollector(MetricsStore metricsStore) {
        this.metricsStore = metricsStore;
    }

    public void recordRequest(RequestInfo requestInfo) {
        if(requestInfo == null || StringUtils.isBlank(requestInfo.getApiName())) {
            return;
        }
        metricsStore.saveRequestInfo(requestInfo);
    }
}
