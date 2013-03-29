package com.dianping.cat.consumer.ip.model.transform;

import static com.dianping.cat.consumer.ip.model.Constants.ELEMENT_DOMAIN;

import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IP;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IP_REPORT;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_PERIOD;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public class DefaultDomParser implements IParser<Node> {

   protected Node getChildTagNode(Node parent, String name) {
      NodeList children = parent.getChildNodes();
      int len = children.getLength();

      for (int i = 0; i < len; i++) {
         Node child = children.item(i);

         if (child.getNodeType() == Node.ELEMENT_NODE) {
            if (child.getNodeName().equals(name)) {
               return child;
            }
         }
      }

      return null;
   }

   protected List<Node> getChildTagNodes(Node parent, String name) {
      NodeList children = parent.getChildNodes();
      int len = children.getLength();
      List<Node> nodes = new ArrayList<Node>(len);

      for (int i = 0; i < len; i++) {
         Node child = children.item(i);

         if (child.getNodeType() == Node.ELEMENT_NODE) {
            if (name == null || child.getNodeName().equals(name)) {
               nodes.add(child);
            }
         }
      }

      return nodes;
   }

   protected Node getDocument(String xml) {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

      dbf.setIgnoringElementContentWhitespace(true);
      dbf.setIgnoringComments(true);

      try {
         DocumentBuilder db = dbf.newDocumentBuilder();

         return db.parse(new InputSource(new StringReader(xml)));
      } catch (Exception x) {
         throw new RuntimeException(x);
      }
   }

   protected List<Node> getGrandChildTagNodes(Node parent, String name) {
      Node child = getChildTagNode(parent, name);
      NodeList children = child == null ? null : child.getChildNodes();
      int len = children == null ? 0 : children.getLength();
      List<Node> nodes = new ArrayList<Node>(len);

      for (int i = 0; i < len; i++) {
         Node grandChild = children.item(i);

         if (grandChild.getNodeType() == Node.ELEMENT_NODE) {
            nodes.add(grandChild);
         }
      }

      return nodes;
   }

   public IpReport parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public IpReport parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_IP_REPORT);

      if (rootNode == null) {
         throw new RuntimeException(String.format("ip-report element(%s) is expected!", ENTITY_IP_REPORT));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public IpReport parse(IMaker<Node> maker, ILinker linker, Node node) {
      IpReport ipReport = maker.buildIpReport(node);

      if (node != null) {
         IpReport parent = ipReport;

         for (Node child : getChildTagNodes(node, ELEMENT_DOMAIN)) {
            String domain = maker.buildDomain(child);

            parent.addDomain(domain);
         }
         for (Node child : getChildTagNodes(node, ENTITY_PERIOD)) {
            Period period = maker.buildPeriod(child);

            if (linker.onPeriod(parent, period)) {
               parseForPeriod(maker, linker, period, child);
            }
         }
      }

      return ipReport;
   }

   public void parseForIp(IMaker<Node> maker, ILinker linker, Ip parent, Node node) {
   }

   public void parseForPeriod(IMaker<Node> maker, ILinker linker, Period parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_IP)) {
         Ip ip = maker.buildIp(child);

         if (linker.onIp(parent, ip)) {
            parseForIp(maker, linker, ip, child);
         }
      }
   }
}
