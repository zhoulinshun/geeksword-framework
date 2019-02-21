package org.geeksword.common;

import java.util.Objects;

/**
 * @Author zhoulinshun
 * @Description:
 * @Date: Created in 2018/6/13.
 */
public class RunnableUtils {

    public static Runnable runnable(Runnable before, Runnable after) {
        Objects.requireNonNull(before, "前置任务不能为null");
        Objects.requireNonNull(after, "后置任务不能为null");
        return () -> {
            try {
                before.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            after.run();
        };
    }

}
