package when.metrics;

import java.util.List;
import java.util.Map;

/**
 * @author: when
 * @create: 2020-02-24  16:38
 **/
public abstract class ScheduledReporter {
    protected MetricsStore metricsStore;
    protected StatViewer statViewer;
    protected Aggregator aggregator;

    public ScheduledReporter(MetricsStore metricsStore, StatViewer statViewer, Aggregator aggregator) {
        this.metricsStore = metricsStore;
        this.statViewer = statViewer;
        this.aggregator = aggregator;
    }

    protected void doStatAndReport(long startTimeInMillis, long endTimeInMillis) {
        long durationInMillis = endTimeInMillis - startTimeInMillis;
        Map<String, List<RequestInfo>> requestInfos = metricsStore.getRequestInfos(startTimeInMillis, endTimeInMillis);
        Map<String, RequestStat> stats = aggregator.aggregate(requestInfos, durationInMillis);
        statViewer.output(stats, startTimeInMillis, endTimeInMillis);
    }

}
