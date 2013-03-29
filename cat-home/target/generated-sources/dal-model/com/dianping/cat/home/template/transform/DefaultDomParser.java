package com.dianping.cat.home.template.transform;

import static com.dianping.cat.home.template.Constants.ENTITY_CONNECTION;
import static com.dianping.cat.home.template.Constants.ENTITY_DURATION;
import static com.dianping.cat.home.template.Constants.ENTITY_PARAM;
import static com.dianping.cat.home.template.Constants.ENTITY_THRESHOLD_TEMPLATE;

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
import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

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

   public ThresholdTemplate parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public ThresholdTemplate parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_THRESHOLD_TEMPLATE);

      if (rootNode == null) {
         throw new RuntimeException(String.format("threshold-template element(%s) is expected!", ENTITY_THRESHOLD_TEMPLATE));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public ThresholdTemplate parse(IMaker<Node> maker, ILinker linker, Node node) {
      ThresholdTemplate thresholdTemplate = maker.buildThresholdTemplate(node);

      if (node != null) {
         ThresholdTemplate parent = thresholdTemplate;

         Node connectionNode = getChildTagNode(node, ENTITY_CONNECTION);

         if (connectionNode != null) {
            Connection connection = maker.buildConnection(connectionNode);

            if (linker.onConnection(parent, connection)) {
               parseForConnection(maker, linker, connection, connectionNode);
            }
         }

         for (Node child : getChildTagNodes(node, ENTITY_DURATION)) {
            Duration duration = maker.buildDuration(child);

            if (linker.onDuration(parent, duration)) {
               parseForDuration(maker, linker, duration, child);
            }
         }
      }

      return thresholdTemplate;
   }

   public void parseForConnection(IMaker<Node> maker, ILinker linker, Connection parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_PARAM)) {
         Param param = maker.buildParam(child);

         if (linker.onParam(parent, param)) {
            parseForParam(maker, linker, param, child);
         }
      }
   }

   public void parseForDuration(IMaker<Node> maker, ILinker linker, Duration parent, Node node) {
   }

   public void parseForParam(IMaker<Node> maker, ILinker linker, Param parent, Node node) {
   }
}
