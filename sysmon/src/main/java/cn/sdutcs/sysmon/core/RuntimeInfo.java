package cn.sdutcs.sysmon.core;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;

// 过时的
public class RuntimeInfo {
    public static void main(String[] args) throws UnknownHostException {
        Properties sysProperty = System.getProperties(); //系统属性
        System.out.println("Java的运行环境版本：" + sysProperty.getProperty("java.version"));
        System.out.println("Java的运行环境供应商：" + sysProperty.getProperty("java.vendor"));
        System.out.println("Java供应商的URL：" + sysProperty.getProperty("java.vendor.url"));
        System.out.println("Java的安装路径：" + sysProperty.getProperty("java.home"));
        System.out.println("Java的虚拟机规范版本：" + sysProperty.getProperty("java.vm.specification.version"));
        System.out.println("Java的虚拟机规范供应商：" + sysProperty.getProperty("java.vm.specification.vendor"));
        System.out.println("Java的虚拟机规范名称：" + sysProperty.getProperty("java.vm.specification.name"));
        System.out.println("Java的虚拟机实现版本：" + sysProperty.getProperty("java.vm.version"));
        System.out.println("Java的虚拟机实现供应商：" + sysProperty.getProperty("java.vm.vendor"));
        System.out.println("Java的虚拟机实现名称：" + sysProperty.getProperty("java.vm.name"));
        System.out.println("Java运行时环境规范版本：" + sysProperty.getProperty("java.specification.version"));
        System.out.println("Java运行时环境规范供应商：" + sysProperty.getProperty("java.specification.vender"));
        System.out.println("Java运行时环境规范名称：" + sysProperty.getProperty("java.specification.name"));
        System.out.println("Java的类格式版本号：" + sysProperty.getProperty("java.class.version"));
        System.out.println("Java的类路径：" + sysProperty.getProperty("java.class.path"));
        System.out.println("加载库时搜索的路径列表：" + sysProperty.getProperty("java.library.path"));
        System.out.println("默认的临时文件路径：" + sysProperty.getProperty("java.io.tmpdir"));
        System.out.println("一个或多个扩展目录的路径：" + sysProperty.getProperty("java.ext.dirs"));
        System.out.println("操作系统的名称：" + sysProperty.getProperty("os.name"));
        System.out.println("操作系统的构架：" + sysProperty.getProperty("os.arch"));
        System.out.println("操作系统的版本：" + sysProperty.getProperty("os.version"));
        System.out.println("文件分隔符：" + sysProperty.getProperty("file.separator"));   //在 unix 系统中是＂／＂
        System.out.println("路径分隔符：" + sysProperty.getProperty("path.separator"));   //在 unix 系统中是＂:＂
        System.out.println("行分隔符：" + sysProperty.getProperty("line.separator"));   //在 unix 系统中是＂/n＂
        System.out.println("用户的账户名称：" + sysProperty.getProperty("user.name"));
        System.out.println("用户的主目录：" + sysProperty.getProperty("user.home"));
        System.out.println("用户的当前工作目录：" + sysProperty.getProperty("user.dir"));

        RuntimeInfo syso = RuntimeInfo.getInstance();
        System.out.println("IP地址:" + getHostIP());
        System.out.println("主机名称:" + getHostName());
        System.out.println("Mac地址" + syso.getMacAddress());
    }

    private static RuntimeInfo currentSystem = null;
    private static InetAddress localHost;

    static {
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String IP;
    public String HostName;
    public Properties MoreInfo;

    public RuntimeInfo() throws UnknownHostException {
        this.IP = getHostIP();
        this.HostName = getHostName();
        this.MoreInfo = System.getProperties();
    }

    public static RuntimeInfo getInstance() throws UnknownHostException {
        if (currentSystem == null)
            currentSystem = new RuntimeInfo();
        return currentSystem;
    }

    public static String getHostIP() {
        return localHost.getHostAddress();
    }

    public static String getHostName() {
        return localHost.getHostName();
    }

    public String getMacAddress() {
        NetworkInterface byInetAddress;
        try {
            byInetAddress = NetworkInterface.getByInetAddress(localHost);
            byte[] hardwareAddress = byInetAddress.getHardwareAddress();
            return getMacFromBytes(hardwareAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMacFromBytes(byte[] bytes) {
        StringBuffer mac = new StringBuffer();
        byte currentByte;
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append("-");
            }
            currentByte = (byte) ((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte) (b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }
}
