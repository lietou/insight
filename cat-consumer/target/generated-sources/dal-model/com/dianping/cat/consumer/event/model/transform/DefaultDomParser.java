package com.dianping.cat.consumer.event.model.transform;

import static com.dianping.cat.consumer.event.model.Constants.ELEMENT_DOMAIN;
import static com.dianping.cat.consumer.event.model.Constants.ELEMENT_IP;

import static com.dianping.cat.consumer.event.model.Constants.ENTITY_EVENT_REPORT;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_NAME;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_RANGE;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_TYPE;

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
import com.dianping.cat.consumer.event.model.entity.EventName;
import com.dianping.cat.consumer.event.model.entity.EventReport;
import com.dianping.cat.consumer.event.model.entity.EventType;
import com.dianping.cat.consumer.event.model.entity.Machine;
import com.dianping.cat.consumer.event.model.entity.Range;

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

   public EventReport parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public EventReport parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_EVENT_REPORT);

      if (rootNode == null) {
         throw new RuntimeException(String.format("event-report element(%s) is expected!", ENTITY_EVENT_REPORT));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public EventReport parse(IMaker<Node> maker, ILinker linker, Node node) {
      EventReport eventReport = maker.buildEventReport(node);

      if (node != null) {
         EventReport parent = eventReport;

         for (Node child : getChildTagNodes(node, ELEMENT_DOMAIN)) {
            String domain = maker.buildDomain(child);

            parent.addDomain(domain);
         }

         for (Node child : getChildTagNodes(node, ELEMENT_IP)) {
            String ip = maker.buildIp(child);

            parent.addIp(ip);
         }
         for (Node child : getChildTagNodes(node, ENTITY_MACHINE)) {
            Machine machine = maker.buildMachine(child);

            if (linker.onMachine(parent, machine)) {
               parseForMachine(maker, linker, machine, child);
            }
         }
      }

      return eventReport;
   }

   public void parseForMachine(IMaker<Node> maker, ILinker linker, Machine parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_TYPE)) {
         EventType type = maker.buildType(child);

         if (linker.onType(parent, type)) {
            parseForEventType(maker, linker, type, child);
         }
      }
   }

   public void parseForEventName(IMaker<Node> maker, ILinker linker, EventName parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_RANGE)) {
         Range range = maker.buildRange(child);

         if (linker.onRange(parent, range)) {
            parseForRange(maker, linker, range, child);
         }
      }
   }

   public void parseForRange(IMaker<Node> maker, ILinker linker, Range parent, Node node) {
   }

   public void parseForEventType(IMaker<Node> maker, ILinker linker, EventType parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_NAME)) {
         EventName name = maker.buildName(child);

         if (linker.onName(parent, name)) {
            parseForEventName(maker, linker, name, child);
         }
      }
   }
}
