package when.metrics;

import java.util.*;

/**
 * @author: when
 * @create: 2020-02-24  14:50
 **/
public class EmailReporter {
    private static final Long DAY_HOURS_IN_SECONDS = 86400L;

    private MetricsStore metricsStore;
    private EmailSender emailSender;
    private List<String> emailAddresses = new ArrayList<>();

    public EmailReporter(MetricsStore metricsStore) {
        this.metricsStore = metricsStore;
        this.emailSender = new EmailSender();
    }

    public void addToAddress(String address) {
        emailAddresses.add(address);
    }

    public void startDailyReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date firstTime = calendar.getTime();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long durationInMillis = DAY_HOURS_IN_SECONDS * 1000;
                long endTimeMillis = System.currentTimeMillis();
                long startTimeMillis = endTimeMillis - durationInMillis;

                Map<String, List<RequestInfo>> requestInfos = metricsStore.getRequestInfos(startTimeMillis,
                        endTimeMillis);
                Map<String, RequestStat> stats = new HashMap<>();
                for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
                    String apiName = entry.getKey();
                    List<RequestInfo> requestInfoPerApi = entry.getValue();
                    // 根据原始数据计算统计数据
                    RequestStat requestStat = Aggregator.aggregate(requestInfoPerApi, durationInMillis);
                    stats.put(apiName, requestStat);
                }

                // 格式化为html格式，发送邮件。
            }
        }, firstTime, DAY_HOURS_IN_SECONDS * 1000);
    }
}
