package com.dianping.cat.consumer.cross.model.transform;

import static com.dianping.cat.consumer.cross.model.Constants.ELEMENT_DOMAIN;
import static com.dianping.cat.consumer.cross.model.Constants.ELEMENT_IP;

import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_CROSS_REPORT;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_LOCAL;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_NAME;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_REMOTE;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_TYPE;

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
import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

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

   public CrossReport parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public CrossReport parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_CROSS_REPORT);

      if (rootNode == null) {
         throw new RuntimeException(String.format("cross-report element(%s) is expected!", ENTITY_CROSS_REPORT));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public CrossReport parse(IMaker<Node> maker, ILinker linker, Node node) {
      CrossReport crossReport = maker.buildCrossReport(node);

      if (node != null) {
         CrossReport parent = crossReport;

         for (Node child : getChildTagNodes(node, ELEMENT_DOMAIN)) {
            String domain = maker.buildDomain(child);

            parent.addDomain(domain);
         }

         for (Node child : getChildTagNodes(node, ELEMENT_IP)) {
            String ip = maker.buildIp(child);

            parent.addIp(ip);
         }
         for (Node child : getChildTagNodes(node, ENTITY_LOCAL)) {
            Local local = maker.buildLocal(child);

            if (linker.onLocal(parent, local)) {
               parseForLocal(maker, linker, local, child);
            }
         }
      }

      return crossReport;
   }

   public void parseForLocal(IMaker<Node> maker, ILinker linker, Local parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_REMOTE)) {
         Remote remote = maker.buildRemote(child);

         if (linker.onRemote(parent, remote)) {
            parseForRemote(maker, linker, remote, child);
         }
      }
   }

   public void parseForName(IMaker<Node> maker, ILinker linker, Name parent, Node node) {
   }

   public void parseForRemote(IMaker<Node> maker, ILinker linker, Remote parent, Node node) {
      Node typeNode = getChildTagNode(node, ENTITY_TYPE);

      if (typeNode != null) {
         Type type = maker.buildType(typeNode);

         if (linker.onType(parent, type)) {
            parseForType(maker, linker, type, typeNode);
         }
      }
   }

   public void parseForType(IMaker<Node> maker, ILinker linker, Type parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_NAME)) {
         Name name = maker.buildName(child);

         if (linker.onName(parent, name)) {
            parseForName(maker, linker, name, child);
         }
      }
   }
}
