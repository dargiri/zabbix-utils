package name.dargiri.jolokia.monitoring;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileOutputStream;
import java.util.List;

/**
 * @author dionis on 08/07/14.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        /**
         * Generate template for jolokia monitoring.
         */
        String ipAddr = "10.0.0.1";
        String dnsUrl = toPrivateEc2DNS(ipAddr);

        String port = "28080";
        String metrixPrefix = "Message Processor Internal 01";
        String templateName = "TEMPLATE " + metrixPrefix + " TOMCAT";

        List<String> strings = IOUtils.readLines(Main.class.getClassLoader().getResourceAsStream("template.xml"));
        String joined = StringUtils.join(strings, "\n");
        joined = StringUtils.replace(joined, "##DNS_URL##", dnsUrl);
        joined = StringUtils.replace(joined, "##IP_ADDR##", ipAddr);
        joined = StringUtils.replace(joined, "##PORT##", port);
        joined = StringUtils.replace(joined, "##TEMPLATE_NAME##", templateName);
        joined = StringUtils.replace(joined, "##METRIX_PREFIX##", metrixPrefix);

        IOUtils.write(joined, new FileOutputStream("/Users/dionis/Desktop/template_generated.xml"));
    }

    private static String toPrivateEc2DNS(String ipAddr) {
        return "ip-"+StringUtils.replace(ipAddr, ".", "-")+".ec2.internal";
    }
}
