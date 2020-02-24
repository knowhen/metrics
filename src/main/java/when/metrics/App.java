package when.metrics;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        MetricsStore metricsStore = new FakeMetricsStore();
        ConsoleReporter consoleReporter = new ConsoleReporter(metricsStore);
        consoleReporter.startRepeatedReport(60, 60);

        EmailReporter emailReporter = new EmailReporter(metricsStore);
        emailReporter.addToAddress("123@test.com");
        emailReporter.startDailyReport();

        MetricsCollector metricsCollector = new MetricsCollector(metricsStore);
        metricsCollector.recordRequest(new RequestInfo("register", 123, 10234));
        metricsCollector.recordRequest(new RequestInfo("register", 223, 11234));
        metricsCollector.recordRequest(new RequestInfo("register", 323, 12234));
        metricsCollector.recordRequest(new RequestInfo("login", 23, 12434));
        metricsCollector.recordRequest(new RequestInfo("login", 1223, 14234));

        Thread.sleep(100000);

    }
}
