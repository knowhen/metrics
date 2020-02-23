package when.metrics;

import when.metrics.controller.UserController;
import when.metrics.vo.UserVo;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        UserController controller = new UserController();
        for (int i = 0; i < 5; i++) {
            controller.login("1", "2");
        }

        Thread.sleep(15000);

        for (int i = 0; i < 5; i++) {
            controller.login("1", "2");
            controller.register(new UserVo());
        }
    }
}
