package cn.sdutcs.sysmon.core;


import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 系统环境工具类
 * Ref. https://docs.oracle.com/javase/10/docs/api/com/sun/management/OperatingSystemMXBean.html#method.summary
 */
public class RuntimeUtils {
    private static final OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private static final Runtime runtime = Runtime.getRuntime();

    /**
     * 获取物理内存总大小
     */
    public static long getTotalPhysicalMemorySize() {
        return systemMXBean.getTotalPhysicalMemorySize();
    }

    /**
     * 获取物理内存剩余大小
     */
    public static long getFreePhysicalMemorySize() {
        return systemMXBean.getFreePhysicalMemorySize();
    }

    /**
     * 获取物理内存已使用大小
     */
    public static long getUsedPhysicalMemorySize() {
        return systemMXBean.getTotalPhysicalMemorySize() - systemMXBean.getFreePhysicalMemorySize();
    }

    /**
     * 获取 Swap 总大小
     */
    public static long getTotalSwapSpaceSize() {
        return systemMXBean.getTotalSwapSpaceSize();
    }

    /**
     * 获取 Swap 剩余大小
     */
    public static long getFreeSwapSpaceSize() {
        return systemMXBean.getFreeSwapSpaceSize();
    }

    /**
     * 获取 Swap 已使用大小
     */
    public static long getUsedSwapSpaceSize() {
        return systemMXBean.getTotalSwapSpaceSize() - systemMXBean.getFreeSwapSpaceSize();
    }

    /**
     * 获取 JVM 最大内存
     */
    public static long getJvmMaxMemory() {
        return runtime.maxMemory();
    }

    /**
     * 获取 JVM 内存总大小
     */
    public static long getJvmTotalMemory() {
        return runtime.totalMemory();
    }

    /**
     * 获取 JVM 内存剩余大小
     */
    public static long getJvmFreeMemory() {
        return runtime.freeMemory();
    }

    /**
     * 获取 JVM 内存已使用大小
     */
    public static long getJvmUsedMemory() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    /**
     * 获取JVM 内存使用率
     */
    public static String getJvmMemLoad() {
        double MemUsage = RuntimeUtils.getJvmUsedMemory() * 1.0 / RuntimeUtils.getJvmTotalMemory();
        return String.format("%.2f%%", MemUsage * 100);
    }

    /**
     * 获取 物理内存 使用率
     */
    public static String getSystemMemLoad() {
        double MemUsage = RuntimeUtils.getUsedPhysicalMemorySize() * 1.0 / RuntimeUtils.getTotalPhysicalMemorySize();
        return String.format("%.2f%%", MemUsage * 100);
    }

    /**
     * 获取系统 CPU 使用率
     */
    public static String getSystemCpuLoad() {
        return String.format("%.2f%%", systemMXBean.getSystemCpuLoad() * 100);
    }

    /**
     * 获取 JVM 进程 CPU 使用率
     */
    public static String getProcessCpuLoad() {
        return String.format("%.2f%%", systemMXBean.getProcessCpuLoad() * 100);
    }

    private static InetAddress localHost;

    static {
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static String getHostIP() {
        return localHost.getHostAddress();
    }

    public static String getHostName() {
        return localHost.getHostName();
    }

    public static String getMacAddress() {
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

    private static String getMacFromBytes(byte[] bytes) {
        StringBuilder mac = new StringBuilder();
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