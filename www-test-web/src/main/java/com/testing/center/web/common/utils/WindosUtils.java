package com.testing.center.web.common.utils;

import com.testing.center.web.dao.cxb.test_auto_case.impl.TestAutoCaseRunDaoMapperImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class WindosUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WindosUtils.class);

    /**
     * 打开文件
     *
     * @param file
     * @throws IOException
     */
    public static void openFile(String file) {
        File fe = new File(file);
        String err = file;
        if (!fe.exists() && (file = getPuth(file)) == null) {
            throw new RuntimeException(err + "没有找到");
        }
        try {
            cmd("cmd /c explorer " + file);
            System.out.println(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("打开" + file + "失败，请联系管理员");
        }
    }

    /***
     * 测试主机Host的port端口是否被使用
     * @param host
     * @param port
     * @throws UnknownHostException
     */
    public static boolean isPortUsing(String host, int port) throws UnknownHostException {
        InetAddress Address = InetAddress.getByName(host);
        //建立一个Socket连接
        try (Socket socket = new Socket(Address, port);) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 查询使用端口的PID
     *
     * @param netstat
     * @return PID
     */
    public static int selectNetstatPid(int netstat) {
        if (!String.valueOf(netstat).matches("\\d+")) throw new IllegalArgumentException(
                "参数错误，不是整数:" + netstat);
        try {
            List<String> cmd = cmd("netstat -ano ");
            String result = null;
            for (String c : cmd) {
                if (c.contains("127.0.0.1:" + netstat + " ")) {
                    result = c;
                    break;
                }
            }
            if (result == null) return -1;
            result = result.substring(result.lastIndexOf(" "), result.length()).trim();
            return Integer.parseInt(result);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

    }


    /**
     * 复制文件
     */
    public static boolean copyFile(File savePath, File copyPath) {

        if (savePath == null || copyPath == null)
            throw new IllegalArgumentException("复制或保存地址为空");
        //文件如果存在，重新名称
        if (savePath.exists()) {
            int filePathIndex = savePath.getPath().lastIndexOf(".");
            StringBuffer sb = new StringBuffer(savePath.getPath());
            if (filePathIndex != -1) {
                sb.insert(filePathIndex, WindosUtils.getDate(" MM-dd-HH-mm-ss"));
            } else {
                sb.append(WindosUtils.getDate(" MM-dd-HH-mm-ss"));
            }
            savePath = new File(sb.toString());
        }
        InputStream ips = null;
        OutputStream ops = null;
        try {
            ips = new FileInputStream(copyPath);
            byte[] ipsBuffer = new byte[ips.available()];
            ips.read(ipsBuffer);
            ops = new FileOutputStream(savePath);
            ops.write(ipsBuffer);
            ops.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ops != null) {
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ips != null) {
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 复制文件
     */
    public static boolean copyFile(String savePath, File copyPath) {
        if (savePath == null || copyPath == null)
            throw new IllegalArgumentException("复制或保存地址为空");
        InputStream ips = null;
        OutputStream ops = null;
        try {
            ips = new FileInputStream(copyPath);
            byte[] ipsBuffer = new byte[ips.available()];
            ips.read(ipsBuffer);
            ops = new FileOutputStream(savePath);
            ops.write(ipsBuffer);
            ops.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ops != null) {
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ips != null) {
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    /**
     * 选中文件
     *
     * @param File
     * @throws IOException
     */
    public static void selectFile(String File) throws IOException {
        cmd("cmd /c explorer  /select, " + File);
    }

    /**
     * 执行windows系统dos命令
     *
     * @param code
     * @return
     */
    public static List<String> cmd(String code) throws IOException {
        Process pro;
        BufferedReader br = null;
        List<String> list = new ArrayList<>();
        try {
            pro = Runtime.getRuntime().exec(code);
            br = new BufferedReader(new InputStreamReader(pro.getInputStream(), Charset.forName("GBK")));
            String str = null;
            while ((str = br.readLine()) != null) {
                list.add(new String(str.getBytes(), "GBK"));

            }
            return list;
        } finally {
            if (br != null) {
                br.close();
            }
        }

    }

    /**
     * 执行windows系统dos命令
     *
     * @param code
     * @return
     */
    public static void cmd(String code, List<String> list) throws IOException, InterruptedException {
        Process pro = null;
        BufferedReader br = null;
        try {
            LOGGER.info("cmd:" + code);
            pro = Runtime.getRuntime().exec(code);
            br = new BufferedReader(new InputStreamReader(pro.getInputStream(), Charset.forName("GBK")));
            String str = null;
            while ((str = br.readLine()) != null) {
                Thread.sleep(0);
                list.add(str);
                System.out.println(str);
            }
            pro.waitFor();
        } finally {
            if (pro != null) pro.destroyForcibly();
            if (br != null) {
                br.close();
            }
        }

    }


    public static void main(String[] args) throws IOException {
//        cmd(
//                " mvn.cmd package -f  E:\\server-testng\\pom.xml " +
//                        " test -Dsurefire.suiteXmlFiles=" +
//                        "E:\\server-testng\\testNgXml\\thumbupCancel.xml");
//        cmd(
//                " mvn.cmd package -f  E:\\server-testng\\pom.xml " +
//                        " test -D=ThumbupCancel");
        System.out.println(WindosUtils.isPortUsing("127.0.0.1", 1099));
    }

    /**
     * 获取本机IP地址
     *
     * @return
     */
    public static String getLocalIP() {
        String ip = "";
        try {
            List<String> str = cmd("ipconfig");
            String title = "";
            String index = "";
            for (String s : str) {
                if ((s.contains(":") && !s.contains(".")) || s.contains("适配器")) {
                    title = s;
                }
                if (s.contains("IPv4") || s.toUpperCase().contains("IPv4".toUpperCase())) {
                    if (!title.equals(index)) {
                        ip += title + "\n";
                        index = title;
                    }
                    ip += s + "\n";
                    ip += "\n";
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ip;
    }

    /**
     * 自动搜索文件地址
     *
     * @return
     */
    public static String getPuth(String fileName) {
        String msg = null;
        String name = fileName;
        String file = fileName.substring(fileName.lastIndexOf(File.separator) + 1
                , fileName.length());
        fileName = fileName.substring(0, fileName.lastIndexOf(File.separator) + 1);
        file = "*" + file + "*";
        fileName += file;
        try {
            Process pro = Runtime.getRuntime().exec("cmd /c dir/s/a/b " + fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream(), Charset.forName("GBK")));
            while ((msg = br.readLine()) != null) {
                if (msg.contains(name)) {
                    return msg;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定目录下的文件或目录
     *
     * @param path
     * @return
     */
    public static File[] getDirectoryFilesName(String path) {
        if (path == null) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        if (!file.isDirectory()) {
            return null;
        }
        return file.listFiles();
    }


    public static boolean removeDir(File dir) {
        if (dir == null) throw new NullPointerException();
        if (!dir.exists()) throw new IllegalArgumentException("文件地址不存在:" + dir);
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                removeDir(file);
            } else {
                if (!file.delete()) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    /**
     * 获取指定PID名称
     *
     * @return
     */
    public static String getPIDName(int pid) {
        if (!String.valueOf(pid).matches("\\d+")) throw new IllegalArgumentException("参数不合法:" + pid);
        try {
            List<String> request = cmd("tasklist");
            String r;
            for (String re : request) {
                r = re;
                if (r.equals("")) continue;
                r = r.trim();
                r = r.substring(r.indexOf(" "), r.length()).trim();
                if (r.startsWith(String.valueOf(pid))) {
                    r = re.trim();
                    return r.substring(0, r.indexOf(" "));
                }
            }
        } catch (IOException | StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 运行本地exe程序
     *
     * @param code
     * @return
     */
    public static String[] runLocalhostExe(String code, JTextArea jTextArea) {
        if (jTextArea == null) throw new IllegalArgumentException("jTextArea为空");
        String[] str = null;
        try {
            Process pro = Runtime.getRuntime().exec(code);
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(pro.getErrorStream(), StandardCharsets.UTF_8));
            String msg;
            int index = 0;
            while ((msg = br.readLine()) != null) {
                if (index == 0) str = new String[0];
                str = Arrays.copyOf(str, str.length + 1);
                str[str.length - 1] = msg;
                index++;
                jTextArea.append(msg + "\n");
                jTextArea.selectAll();
                if (jTextArea.getSelectedText() != null && jTextArea != null) {
                    jTextArea.setCaretPosition(jTextArea.getSelectedText().length());
                    jTextArea.requestFocus();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            jTextArea.append(e.toString() + "\n");
        }
        return str;
    }


    /**
     * 获取系统时间
     *
     * @param format 指定的格式
     * @return
     */
    public static String getDate(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取系统时间，默认时间格式:yyyy-MM-dd HH:mm:ss
     *
     * @return date
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd HH:mm:ss");
    }


}
