package when.metrics;

import java.util.*;

/**
 * @author: when
 * @create: 2020-02-24  14:50
 **/
public class EmailReporter extends ScheduledReporter {
    private static final Long DAY_HOURS_IN_SECONDS = 86400L;

    private MetricsStore metricsStore;
    private StatViewer statViewer;
    private Aggregator aggregator;

    public EmailReporter(MetricsStore metricsStore, StatViewer statViewer, Aggregator aggregator) {
        super(metricsStore, statViewer, aggregator);
    }

    public void startDailyReport() {
        Date now = new Date();
        Date firstTime = trimTimeToZeroOfNextDay(now);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long durationInMillis = DAY_HOURS_IN_SECONDS * 1000;
                long endTimeMillis = System.currentTimeMillis();
                long startTimeMillis = endTimeMillis - durationInMillis;

                doStatAndReport(startTimeMillis, endTimeMillis);
            }
        }, firstTime, DAY_HOURS_IN_SECONDS * 1000);
    }

    /**
     * 获取当前时间下一天的 0 点时间
     *
     * @param date
     * @return
     */
    private Date trimTimeToZeroOfNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
