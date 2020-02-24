package when.metrics;

import com.google.gson.Gson;

import java.util.Map;

/**
 * @author: when
 * @create: 2020-02-24  16:00
 **/
public class ConsoleViewer implements StatViewer {
    @Override
    public void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMillis) {
        System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMillis + "]");
        Gson gson = new Gson();
        System.out.println(gson.toJson(requestStats));
    }
}
