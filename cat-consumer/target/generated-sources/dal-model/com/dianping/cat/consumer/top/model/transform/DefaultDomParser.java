package com.dianping.cat.consumer.top.model.transform;

import static com.dianping.cat.consumer.top.model.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_SEGMENT;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_TOP_REPORT;

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
import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

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

   public TopReport parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public TopReport parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_TOP_REPORT);

      if (rootNode == null) {
         throw new RuntimeException(String.format("top-report element(%s) is expected!", ENTITY_TOP_REPORT));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public TopReport parse(IMaker<Node> maker, ILinker linker, Node node) {
      TopReport topReport = maker.buildTopReport(node);

      if (node != null) {
         TopReport parent = topReport;

         for (Node child : getChildTagNodes(node, ENTITY_DOMAIN)) {
            Domain domain = maker.buildDomain(child);

            if (linker.onDomain(parent, domain)) {
               parseForDomain(maker, linker, domain, child);
            }
         }
      }

      return topReport;
   }

   public void parseForDomain(IMaker<Node> maker, ILinker linker, Domain parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_SEGMENT)) {
         Segment segment = maker.buildSegment(child);

         if (linker.onSegment(parent, segment)) {
            parseForSegment(maker, linker, segment, child);
         }
      }
   }

   public void parseForSegment(IMaker<Node> maker, ILinker linker, Segment parent, Node node) {
   }
}
