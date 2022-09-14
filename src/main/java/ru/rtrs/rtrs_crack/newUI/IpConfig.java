package ru.rtrs.rtrs_crack.newUI;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class IpConfig {
    public static void changeIp() throws IOException {
        InputStream input = new FileInputStream("src/main/resources/config.properties");
        Properties prop = new Properties();
        prop.load(input);
        String ip = prop.getProperty("ip");
        String mask = prop.getProperty("mask");
        String interfaceName = prop.getProperty("interface");
        String[] command1 = {"netsh", "interface", "ip", "set", "address",
                "name=", interfaceName ,"source=static", "addr=",ip,
                "mask=", mask};
        Process process1 = Runtime.getRuntime().exec(command1);
        printResults(process1);
    }
    public static void printResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
    public static void addAlias(List<String> alias) throws IOException {
        InputStream input = new FileInputStream("src/main/resources/config.properties");
        Properties prop = new Properties();
        prop.load(input);
        String ip = prop.getProperty("ip");
        String mask = prop.getProperty("mask");
        String interfaceName = prop.getProperty("interface");
        for (String aliasIp:alias
             ) {
            Process process = Runtime.getRuntime().exec(new String[]{"netsh", "interface", "ip", "add", "address",
                    interfaceName, aliasIp, mask});
        }

    }
}
