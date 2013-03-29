package com.dianping.cat.configuration.server.transform;

import static com.dianping.cat.configuration.server.Constants.ENTITY_CONFIG;
import static com.dianping.cat.configuration.server.Constants.ENTITY_CONSOLE;
import static com.dianping.cat.configuration.server.Constants.ENTITY_CONSUMER;
import static com.dianping.cat.configuration.server.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.configuration.server.Constants.ENTITY_HDFS;
import static com.dianping.cat.configuration.server.Constants.ENTITY_LONG_CONFIG;
import static com.dianping.cat.configuration.server.Constants.ENTITY_PROPERTIES;
import static com.dianping.cat.configuration.server.Constants.ENTITY_STORAGE;

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
import com.dianping.cat.configuration.server.entity.ConsoleConfig;
import com.dianping.cat.configuration.server.entity.ConsumerConfig;
import com.dianping.cat.configuration.server.entity.Domain;
import com.dianping.cat.configuration.server.entity.HdfsConfig;
import com.dianping.cat.configuration.server.entity.LongConfig;
import com.dianping.cat.configuration.server.entity.Property;
import com.dianping.cat.configuration.server.entity.ServerConfig;
import com.dianping.cat.configuration.server.entity.StorageConfig;

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

   public ServerConfig parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public ServerConfig parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_CONFIG);

      if (rootNode == null) {
         throw new RuntimeException(String.format("config element(%s) is expected!", ENTITY_CONFIG));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public ServerConfig parse(IMaker<Node> maker, ILinker linker, Node node) {
      ServerConfig config = maker.buildConfig(node);

      if (node != null) {
         ServerConfig parent = config;

         Node storageNode = getChildTagNode(node, ENTITY_STORAGE);

         if (storageNode != null) {
            StorageConfig storage = maker.buildStorage(storageNode);

            if (linker.onStorage(parent, storage)) {
               parseForStorageConfig(maker, linker, storage, storageNode);
            }
         }

         Node consumerNode = getChildTagNode(node, ENTITY_CONSUMER);

         if (consumerNode != null) {
            ConsumerConfig consumer = maker.buildConsumer(consumerNode);

            if (linker.onConsumer(parent, consumer)) {
               parseForConsumerConfig(maker, linker, consumer, consumerNode);
            }
         }

         Node consoleNode = getChildTagNode(node, ENTITY_CONSOLE);

         if (consoleNode != null) {
            ConsoleConfig console = maker.buildConsole(consoleNode);

            if (linker.onConsole(parent, console)) {
               parseForConsoleConfig(maker, linker, console, consoleNode);
            }
         }
      }

      return config;
   }

   public void parseForConsoleConfig(IMaker<Node> maker, ILinker linker, ConsoleConfig parent, Node node) {
   }

   public void parseForConsumerConfig(IMaker<Node> maker, ILinker linker, ConsumerConfig parent, Node node) {
      Node longConfigNode = getChildTagNode(node, ENTITY_LONG_CONFIG);

      if (longConfigNode != null) {
         LongConfig longConfig = maker.buildLongConfig(longConfigNode);

         if (linker.onLongConfig(parent, longConfig)) {
            parseForLongConfig(maker, linker, longConfig, longConfigNode);
         }
      }
   }

   public void parseForDomain(IMaker<Node> maker, ILinker linker, Domain parent, Node node) {
   }

   public void parseForHdfsConfig(IMaker<Node> maker, ILinker linker, HdfsConfig parent, Node node) {
   }

   public void parseForLongConfig(IMaker<Node> maker, ILinker linker, LongConfig parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_DOMAIN)) {
         Domain domain = maker.buildDomain(child);

         if (linker.onDomain(parent, domain)) {
            parseForDomain(maker, linker, domain, child);
         }
      }
   }

   public void parseForProperty(IMaker<Node> maker, ILinker linker, Property parent, Node node) {
   }

   public void parseForStorageConfig(IMaker<Node> maker, ILinker linker, StorageConfig parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_HDFS)) {
         HdfsConfig hdfs = maker.buildHdfs(child);

         if (linker.onHdfs(parent, hdfs)) {
            parseForHdfsConfig(maker, linker, hdfs, child);
         }
      }

      for (Node child : getGrandChildTagNodes(node, ENTITY_PROPERTIES)) {
         Property property = maker.buildProperty(child);

         if (linker.onProperty(parent, property)) {
            parseForProperty(maker, linker, property, child);
         }
      }
   }
}
