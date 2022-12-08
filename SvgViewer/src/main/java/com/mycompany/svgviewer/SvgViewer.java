/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.svgviewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Drobles
 */
public class SvgViewer {

    public static void main(String[] args) {
       // System.out.println("Hello World!");
        SvgViewerFrame frame = new SvgViewerFrame();
        frame.setSize(1500, 1000);
        frame.setVisible(true);
        frame.setResizable(false);
        
        Graphics2D graphics = (Graphics2D)frame.getComponent(0).getGraphics();
        Scanner sc = new Scanner(System.in);
        
        
        String url = args[0];
        
        try {
            String svgString = requestSvgURL(url);
            System.out.println(svgString);
            Document svg = parseSVG(svgString);
            

            NodeList lines = svg.getElementsByTagName("line") ;

                for (int i = 0; i < lines.getLength(); i++) {
                    Node node = lines.item(i);
                    Element element = (Element) node;

                    int x1 = (int)(Float.parseFloat("0"+element.getAttribute("x1")));
                    int y1 = (int)(Float.parseFloat("0"+element.getAttribute("y1")));
                    int x2 = (int)(Float.parseFloat("0"+element.getAttribute("x2")));
                    int y2 = (int)(Float.parseFloat("0"+element.getAttribute("y2")));
                    String stroke = element.getAttribute("stroke");
                    String strokeWidth = element.getAttribute("stroke-width");

                    if (!strokeWidth.isEmpty()) {
                        graphics.setStroke(new BasicStroke(Float.parseFloat(strokeWidth)));
                    }
                    if(!stroke.isEmpty()){
                        Color color = getColor(stroke);
                        if(color != null) graphics.setColor(color);
                    }
                    graphics.drawLine(x1, y1, x2, y2);
                    graphics.setColor(Color.black);
                    graphics.setStroke(new BasicStroke(1));

                }

            
             
            NodeList circles = svg.getElementsByTagName("circle");
            
            for (int i = 0; i < circles.getLength(); i++) {
                Node node = circles.item(i);
                Element element = (Element) node;
                
                int cx = (int)(Float.parseFloat("0"+element.getAttribute("cx")));
                int cy = (int)(Float.parseFloat("0"+element.getAttribute("cy")));
                int r = (int)(Float.parseFloat("0"+element.getAttribute("r")));

                String fill = element.getAttribute("fill");
                String stroke = element.getAttribute("stroke");
                String strokeWidth = element.getAttribute("stroke-width");

                if(!strokeWidth.isEmpty()){
                    graphics.setStroke(new BasicStroke(Float.parseFloat(strokeWidth)));
                }
                if(!stroke.isEmpty()){
                    Color color = getColor(stroke);
                    if(color != null) graphics.setColor(color);
                }               

                graphics.drawOval(cx, cy, r*2, r*2);
                
                if(!fill.isEmpty()){
                    Color color = getColor(fill);
                    if(color != null) graphics.setColor(color);
                    graphics.fillOval(cx, cy, r*2, r*2);
                }
                
                graphics.setColor(Color.black);
                graphics.setStroke(new BasicStroke(1));
            }


            NodeList rectangle = svg.getElementsByTagName("rect");

            for (int i = 0; i < rectangle.getLength(); i++) {
                Node node = rectangle.item(i);
                Element element = (Element) node;

                int x = (int)(Float.parseFloat("0"+element.getAttribute("x")));
                int y = (int)(Float.parseFloat("0"+element.getAttribute("y")));
                int width = (int)(Float.parseFloat("0"+element.getAttribute("width")));
                int height = (int)(Float.parseFloat("0"+element.getAttribute("height")));
                String fill = element.getAttribute("fill");
                String stroke = element.getAttribute("stroke");
                String strokeWidth = element.getAttribute("stroke-width");

                if(!strokeWidth.isEmpty()){
                    graphics.setStroke(new BasicStroke(Float.parseFloat(strokeWidth)));
                }
                 if(!stroke.isEmpty()){
                    Color color = getColor(stroke);
                    if(color != null) graphics.setColor(color);
                }

                graphics.drawRect(x, y, (int) width,(int) height);
                graphics.setColor(Color.black);
                graphics.setStroke(new BasicStroke(1));
            }


            NodeList ellipse = svg.getElementsByTagName("ellipse");

            for (int i = 0; i < ellipse.getLength(); i++) {
                Node node = ellipse.item(i);
                Element element = (Element) node;

                int cx = (int)(Float.parseFloat("0"+element.getAttribute("cx")));
                int cy = (int)(Float.parseFloat("0"+element.getAttribute("cy")));
                int rx = (int)(Float.parseFloat("0"+element.getAttribute("rx")));
                int ry = (int)(Float.parseFloat("0"+element.getAttribute("ry")));
                String fill = element.getAttribute("fill");
                String stroke = element.getAttribute("stroke");
                String strokeWidth = element.getAttribute("stroke-width");

                if(!strokeWidth.isEmpty()){
                    graphics.setStroke(new BasicStroke(Float.parseFloat(strokeWidth)));
                }
                 if(!stroke.isEmpty()){
                    Color color = getColor(stroke);
                    if(color != null) graphics.setColor(color);
                }
                graphics.drawOval(cx, cy, rx, ry);
                
                if(!fill.isEmpty()){
                    Color color = getColor(fill);
                    if(color != null) graphics.setColor(color);
                    graphics.fillOval(cx, cy,rx, ry);
                }
                
                graphics.setColor(Color.black);
                graphics.setStroke(new BasicStroke(1));
            }


            NodeList text = svg.getElementsByTagName("text");

            for (int i = 0; i < text.getLength(); i++) {
                Node node = text.item(i);
                Element element = (Element) node;

                int x = (int)(Float.parseFloat("0"+element.getAttribute("x")));
                int y = (int)(Float.parseFloat("0"+element.getAttribute("y")));
                String fontFamily = element.getAttribute("font-family");
                String fontSize = element.getAttribute("font-size");
                String strokeWidth = element.getAttribute("stroke-width");
                String stroke = element.getAttribute("stroke");
                String nodeText = element.getTextContent();

                if(!strokeWidth.isEmpty()){
                    graphics.setStroke(new BasicStroke(Float.parseFloat(strokeWidth)));
                }
                if(!stroke.isEmpty()){
                    Color color = getColor(stroke);
                    if(color != null) graphics.setColor(color);
                }
                graphics.drawString(nodeText, x, y);
                graphics.setColor(Color.black);
                graphics.setStroke(new BasicStroke(1));
            }

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    
    static String requestSvgURL(String url) throws MalformedURLException, ProtocolException, IOException {
        URL requestUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
        con.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
    
    private static Document parseSVG(String svgString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try  
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( svgString ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }
    
    private static Color getColor(String name){
        Color color;
        try {
            if(name.charAt(0) == '#'){
                if(name.length() == 4){
                    String hexCode = name.substring(1);
                    color = Color.decode("#"+hexCode+hexCode);
                }else {
                    color = Color.decode(name);
                }
                
            }else {
                Field field = Class.forName("java.awt.Color").getField(name);
                color = (Color)field.get(null);
            }
        } catch (Exception e) {
            color = null;
        }
        return color;
    }
}
