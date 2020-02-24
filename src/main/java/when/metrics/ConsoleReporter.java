package when.metrics;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: when
 * @create: 2020-02-24  09:30
 **/
public class ConsoleReporter extends ScheduledReporter {
    private ScheduledExecutorService executor;

    public ConsoleReporter(MetricsStore store, StatViewer statViewer, Aggregator aggregator) {
        super(store, statViewer, aggregator);
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
        executor.scheduleAtFixedRate(() -> {
            // 根据给定的时间区间，从数据库拉取数据
            long durationInMillis = durationInSeconds * 1000;
            long endTimeInMillis = System.currentTimeMillis();
            long startTimeInMillis = endTimeInMillis - durationInMillis;

            doStatAndReport(startTimeInMillis, endTimeInMillis);
        }, 0, periodInSeconds, TimeUnit.SECONDS);
    }

}
