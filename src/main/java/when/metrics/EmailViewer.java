package when.metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: when
 * @create: 2020-02-24  16:01
 **/
public class EmailViewer implements StatViewer {
    private EmailSender emailSender;
    private List<String> emailAddresses = new ArrayList<>();

    public EmailViewer() {
        this.emailSender = new EmailSender();
    }

    public EmailViewer(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void addToAddress(String address) {
        emailAddresses.add(address);
    }

    @Override
    public void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMillis) {
        // 格式化为html格式，发送邮件。
    }
}
