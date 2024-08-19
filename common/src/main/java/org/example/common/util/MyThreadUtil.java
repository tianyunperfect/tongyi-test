package org.example.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * 多线程工具类
 * Map<String, Object> results = executeConcurrently(createTaskMap(
 * new Task("task1", () -> func1()),
 * new Task("task2", () -> func2()),
 * new Task("task3", () -> func3()))
 * );
 *
 * @author apple
 * @date 2023/09/29
 */
public class MyThreadUtil {
    public static void main(String[] args) throws Exception {
        // 并发执行函数并获取结果
        Map<String, Object> results = executeConcurrently(
                new MyTask("task1", () -> func1()),
                new MyTask("task2", () -> func2()),
                new MyTask("task3", () -> func3())
        );

        // 打印结果
        results.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    // 并发执行函数并获取结果
    public static Map<String, Object> executeConcurrently(MyTask... tasks) throws Exception {
        Map<String, Object> results = new ConcurrentHashMap<>();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (MyTask task : tasks) {
            futures.add(
                    CompletableFuture.supplyAsync(task.getSupplier(), executor)
                            .thenAccept(result -> {
                                if (result != null) {
                                    results.put(task.getName(), result);
                                }
                            })
            );
        }
        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

        return results;
    }

    private static final Executor executor = Executors.newFixedThreadPool(8);

    //异步执行
    public static Map<String, CompletableFuture<Object>> executeAsync(MyTask... tasks) {
        Map<String, CompletableFuture<Object>> taskMap = new HashMap<>();
        for (MyTask task : tasks) {
            System.out.println("执行异步任务开始: "+ task.getName());
            CompletableFuture<Object> future = CompletableFuture.supplyAsync(task.getSupplier(), executor);
            taskMap.put(task.getName(), future);
        }
        return taskMap;
    }

    public static void executeAsync2(Runnable... runAbles) {
        for (Runnable runAble : runAbles) {
            System.out.println("执行异步任务开始");
            executor.execute(runAble);
            System.out.println("执行异步任务结束");

        }
    }

    public static class MyTask {
        private String name;
        private Supplier<Object> supplier;

        public MyTask(String name, Supplier<Object> supplier) {
            this.name = name;
            this.supplier = supplier;
        }

        public String getName() {
            return name;
        }

        public Supplier<Object> getSupplier() {
            return supplier;
        }
    }

    public static Map<String, Supplier<Object>> createTaskMap(MyTask... tasks) {
        Map<String, Supplier<Object>> taskMap = new HashMap<>();
        for (MyTask task : tasks) {
            taskMap.put(task.getName(), task.getSupplier());
        }
        return taskMap;
    }

    // 这里是你的函数
    public static Object func1() {
        // 这里写你的函数逻辑
        return "result1";
    }

    public static Object func2() {
        // 这里写你的函数逻辑
        return "result2";
    }

    public static Object func3() {
        // 这里写你的函数逻辑
        return "result3";
    }
}
