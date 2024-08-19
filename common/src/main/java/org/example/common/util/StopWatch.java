package org.example.common.util;

public class StopWatch {
    private String name = "";
    private long startTime;

    public StopWatch() {
        startTime = System.currentTimeMillis();
    }

    public StopWatch(String name) {
        this();
        this.name = name;
    }

    /**
     * 将毫秒转换为  时分秒，用于时间间隔的计算
     *
     * @param ms 毫秒数
     * @return {@link String}
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + " 天 ");
        }
        if (hour > 0) {
            sb.append(hour + " 小时 ");
        }
        if (minute > 0) {
            sb.append(minute + " 分 ");
        }
        if (second > 0) {
            sb.append(second + " 秒 ");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond + " 毫秒");
        }
        return sb.toString();
    }

    /**
     * 停止并格式化输出
     *
     * @return {@link String}
     */
    public String stopAndPretty() {
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - this.startTime;
        String s = formatTime(spendTime);
        String res = this.name + " 用时：" + s;
        //System.out.println(res);
        return res;
    }

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch("A");
        Thread.sleep(1230);
        stopWatch.stopAndPretty();
    }
}