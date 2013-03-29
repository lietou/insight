package com.dianping.cat.configuration.client.transform;

import static com.dianping.cat.configuration.client.Constants.ENTITY_BIND;
import static com.dianping.cat.configuration.client.Constants.ENTITY_CONFIG;
import static com.dianping.cat.configuration.client.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.configuration.client.Constants.ENTITY_PROPERTIES;
import static com.dianping.cat.configuration.client.Constants.ENTITY_SERVERS;

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
import com.dianping.cat.configuration.client.entity.Bind;
import com.dianping.cat.configuration.client.entity.ClientConfig;
import com.dianping.cat.configuration.client.entity.Domain;
import com.dianping.cat.configuration.client.entity.Property;
import com.dianping.cat.configuration.client.entity.Server;

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

   public ClientConfig parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public ClientConfig parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_CONFIG);

      if (rootNode == null) {
         throw new RuntimeException(String.format("config element(%s) is expected!", ENTITY_CONFIG));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public ClientConfig parse(IMaker<Node> maker, ILinker linker, Node node) {
      ClientConfig config = maker.buildConfig(node);

      if (node != null) {
         ClientConfig parent = config;

         for (Node child : getGrandChildTagNodes(node, ENTITY_SERVERS)) {
            Server server = maker.buildServer(child);

            if (linker.onServer(parent, server)) {
               parseForServer(maker, linker, server, child);
            }
         }

         for (Node child : getChildTagNodes(node, ENTITY_DOMAIN)) {
            Domain domain = maker.buildDomain(child);

            if (linker.onDomain(parent, domain)) {
               parseForDomain(maker, linker, domain, child);
            }
         }

         Node bindNode = getChildTagNode(node, ENTITY_BIND);

         if (bindNode != null) {
            Bind bind = maker.buildBind(bindNode);

            if (linker.onBind(parent, bind)) {
               parseForBind(maker, linker, bind, bindNode);
            }
         }

         for (Node child : getGrandChildTagNodes(node, ENTITY_PROPERTIES)) {
            Property property = maker.buildProperty(child);

            if (linker.onProperty(parent, property)) {
               parseForProperty(maker, linker, property, child);
            }
         }
      }

      return config;
   }

   public void parseForBind(IMaker<Node> maker, ILinker linker, Bind parent, Node node) {
   }

   public void parseForDomain(IMaker<Node> maker, ILinker linker, Domain parent, Node node) {
   }

   public void parseForProperty(IMaker<Node> maker, ILinker linker, Property parent, Node node) {
   }

   public void parseForServer(IMaker<Node> maker, ILinker linker, Server parent, Node node) {
   }
}
