package com.g4share;

import java.io.*;
import java.util.LinkedHashMap;

/**
 * User: gm
 */
public class CommonTestMethods {
    static public void createRssFile(String tFileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(tFileName, true))) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<!-- generator=\"UNIMEDIA RSS Feed\" -->");
            writer.println("<rss version=\"2.0\">");
            writer.println("    <channel>");
            writer.println("        <title>");
            writer.println("            UNIMDIIA");
            writer.println("            hello");
            writer.println("        </title>");
            writer.println("        <description>portalul de ştiri nr. 1 din Moldova</description>");
            writer.println("        <link>http://www.unimedia.md/</link>");
            writer.println("        <lastBuildDate>Tue, 01 May 2012 01:15:01 +0200</lastBuildDate>");
            writer.println("        <generator>UNIMEDIA RSS Feed</generator>");
            writer.println("        <image>");
            writer.println("            <url>http://unimedia.md/sys/img/logo_unimedia.gif</url>");
            writer.println("            <title>UNIMEDIA</title>");
            writer.println("            <link>http://www.unimedia.md/</link>");
            writer.println("            <description>portalul de ştiri nr. 1 din Moldova</description>");
            writer.println("        </image>");
            writer.println("    </channel>");
            writer.println("</rss>");
        } catch (IOException e){ throw e;}
    }

    static public void createXmlFile(String tFileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(tFileName, true))) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<!-- comments -->");
            writer.println("<node1 value1=\"node1Value1\">");
            writer.println("    <node2 value1=\"node2value1\" value2=\"node2value2\" >");
            writer.println("        <node3 value1=\"node3value1\"/>");
            writer.println("        <textNode value=\"text1\">Node vith text1</textNode>");
            writer.println("        <textNode value=\"text2\">Node vith text2</textNode>");
            writer.println("        <textNode></textNode>");
            writer.println("        <textNode/>");
            writer.println("    </node2>");
            writer.println("    <node3/>");
            writer.println("</node1>");
        } catch (IOException e){ throw e;}
    }

    static public InputStream getStream(String fileName) throws FileNotFoundException {
        return new FileInputStream(new File(fileName));
    }

    static public <T> T getByIndex(LinkedHashMap<T, ?> map, int index){
        int i = 0;
        for (T key : map.keySet()) {
            if (i++ != index) continue;
            return key;
        }
        return null;
    }
}
