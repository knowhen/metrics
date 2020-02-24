package when.metrics;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        MetricsStore metricsStore = new FakeMetricsStore();
        Aggregator aggregator = new Aggregator();

        ConsoleViewer consoleViewer = new ConsoleViewer();
        ConsoleReporter consoleReporter = new ConsoleReporter(metricsStore, consoleViewer, aggregator);
        consoleReporter.startRepeatedReport(10, 10);

//        EmailViewer emailViewer = new EmailViewer();
//        emailViewer.addToAddress("123@test.com");
//        EmailReporter emailReporter = new EmailReporter(metricsStore, emailViewer, aggregator);
//        emailReporter.startDailyReport();
        Thread.sleep(10000);

        MetricsCollector metricsCollector = new MetricsCollector(metricsStore);
        long timestamp = System.currentTimeMillis();
        metricsCollector.recordRequest(new RequestInfo("register", 123, timestamp));
        metricsCollector.recordRequest(new RequestInfo("register", 223, timestamp + 10000));
        metricsCollector.recordRequest(new RequestInfo("register", 323, timestamp + 20000));
        metricsCollector.recordRequest(new RequestInfo("login", 23, timestamp + 30000));
        metricsCollector.recordRequest(new RequestInfo("login", 1223, timestamp + 40000));


    }
}
