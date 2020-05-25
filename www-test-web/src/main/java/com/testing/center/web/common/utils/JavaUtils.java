package com.testing.center.web.common.utils;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class JavaUtils {


    /**
     * 获取文件后缀名称
     *
     * @return
     */
    public static String getFileSuffixName(File file) throws IllegalArgumentException {
        if (file == null) throw new IllegalArgumentException("文件为空");
        if (!file.exists()) throw new IllegalArgumentException("文件不存在:" + file.getPath());
        if (file.getName().lastIndexOf(".") == -1) throw
                new IllegalArgumentException("不是一个文件:" + file.getName());
        return file.getName().substring(
                file.getName().lastIndexOf(".") + 1, file.getName().length());
    }

    public static boolean isDateString(String datevalue, String dateFormat) {
        if (datevalue == null || dateFormat == null) return false;
        if (datevalue.equals("") || dateFormat.equals("")) return false;
        try {
            SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
            Date dd = fmt.parse(datevalue);
            if (datevalue.equals(fmt.format(dd))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    public static void main(String[] args) {

//        String str = "ccw";
//        //一个括号是一组
//        Pattern CRLF = Pattern.compile("([c]{2})[w]");
//        Matcher m = CRLF.matcher(str);
//        System.out.println(m.matches());
//        if (m.matches()) {
//            System.out.println(m.groupCount());
//            System.out.println(m.group(1));
//        }
//
//        Pattern pattern = Pattern.compile("(\\d{4})-((\\d{2})-(\\d{2}))");
//        Matcher matcher = pattern.matcher("2017-04-25");
//        matcher.findMuid();//必须要有这句
//        System.out.println(matcher.groupCount());
//        System.out.printf("\nmatcher.group(0) value:%s", matcher.group(0));
//        System.out.printf("\nmatcher.group(1) value:%s", matcher.group(1));
//        System.out.printf("\nmatcher.group(2) value:%s", matcher.group(2));
//        System.out.printf("\nmatcher.group(3) value:%s", matcher.group(3));
//        System.out.printf("\nmatcher.group(4) value:%s", matcher.group(4));
//        System.out.println(getRandomNumbers(1, 1));
//        System.out.println(getRandomNumbers(1, 2));
//        String sss = "\\//////";
//        System.out.println(sss);
//        sss = replaceFilePathSymbol(sss);
//        System.out.println(sss);
//        System.out.println(File.separator);
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, 123);
        map.put(3, 456);
        mapKeyLaterGressionInteger(map, 5);
        System.out.println(map);

    }


    /**
     * 获取本地txt文件内容
     *
     * @param file
     * @return
     */
    public static List<String> getLocaFileTxt(File file) throws IOException {
        if (file == null) throw new NullPointerException("file为空");
        if (!file.exists()) throw new IllegalArgumentException("文件不存在:" + file.getPath());
        if (!file.getName().toLowerCase().endsWith(".txt"))
            throw new IllegalArgumentException("不是txt文件:" + file.getPath());
        List<String> list = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(new FileInputStream(file), "UTF-8")
                    );
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                list.add(msg);
            }
        } finally {
            if (bufferedReader != null) bufferedReader.close();
        }
        return list;
    }


//    /**
//     * 随机生成一个中文字符
//     *
//     * @return
//     */
//    public static String getRandomChinese() {
//        return new String(new char[]{(char) (new Random().nextInt(20902) + 19968)});
//    }

//    /**
//     * 随机生成一个中文字符
//     *
//     * @param lenth 指定长度
//     * @return
//     */
//    public static String getRandomChinese(int lenth) {
//        StringBuffer stringBuffer = new StringBuffer();
//        for (int i = 0; i < lenth; i++) {
//            stringBuffer.append(getRandomChinese());
//        }
//        return stringBuffer.toString();
//    }


    /**
     * 睡眠
     *
     * @param time
     */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepMinutes(long time) {
        sleep(time * 60 * 1000L);
    }

    public static void sleepMinutestAndSecond(long minutest, long second) {
        sleepMinutes(minutest);
        sleepSecond(second);
    }

    public static void sleepSecond(long time) {
        sleep(time * 1000L);
    }

    public static void sleep(int time) {
        sleep((long) time);
    }


    public static String currentTimeMillisString() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static long currentTimeMillisLong() {
        return System.currentTimeMillis();
    }

    /**
     * java方法
     */
    private void fangfa() {

        try {
            System.in.read();//按任意键
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * maoKey向后移位
     *
     * @param map
     * @param gression 移位number
     */
    public static <T> void mapKeyLaterGressionInteger(Map<Integer, T> map, int gression) {
        Objects.requireNonNull(map);
        Map<Integer, T> tmpMap = new HashMap<>();
        for (Map.Entry<Integer, T> set : map.entrySet()) {
            tmpMap.put(set.getKey() + gression, set.getValue());
        }
        map.clear();
        map.putAll(tmpMap);
        tmpMap.clear();
    }

    /**
     * maoKey向后移位
     *
     * @param map
     * @param gression 移位number
     */
    public static <T> void mapKeyLaterGressionLong(Map<Long, T> map, int gression) {
        Objects.requireNonNull(map);
        Map<Long, T> tmpMap = new HashMap<>();
        for (Map.Entry<Long, T> set : map.entrySet()) {
            tmpMap.put(set.getKey() + gression, set.getValue());
        }
        map.clear();
        map.putAll(tmpMap);
        tmpMap.clear();
    }

}


