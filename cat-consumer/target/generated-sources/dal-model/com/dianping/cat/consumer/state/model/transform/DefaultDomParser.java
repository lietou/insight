package com.dianping.cat.consumer.state.model.transform;

import static com.dianping.cat.consumer.state.model.Constants.ELEMENT_IP;

import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MESSAGE;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_PROCESSDOMAINS;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_STATE_REPORT;

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
import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

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

   public StateReport parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public StateReport parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_STATE_REPORT);

      if (rootNode == null) {
         throw new RuntimeException(String.format("state-report element(%s) is expected!", ENTITY_STATE_REPORT));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public StateReport parse(IMaker<Node> maker, ILinker linker, Node node) {
      StateReport stateReport = maker.buildStateReport(node);

      if (node != null) {
         StateReport parent = stateReport;

         for (Node child : getChildTagNodes(node, ENTITY_MACHINE)) {
            Machine machine = maker.buildMachine(child);

            if (linker.onMachine(parent, machine)) {
               parseForMachine(maker, linker, machine, child);
            }
         }
      }

      return stateReport;
   }

   public void parseForMachine(IMaker<Node> maker, ILinker linker, Machine parent, Node node) {
      for (Node child : getGrandChildTagNodes(node, ENTITY_PROCESSDOMAINS)) {
         ProcessDomain processDomain = maker.buildProcessDomain(child);

         if (linker.onProcessDomain(parent, processDomain)) {
            parseForProcessDomain(maker, linker, processDomain, child);
         }
      }

      for (Node child : getChildTagNodes(node, ENTITY_MESSAGE)) {
         Message message = maker.buildMessage(child);

         if (linker.onMessage(parent, message)) {
            parseForMessage(maker, linker, message, child);
         }
      }
   }

   public void parseForMessage(IMaker<Node> maker, ILinker linker, Message parent, Node node) {
   }

   public void parseForProcessDomain(IMaker<Node> maker, ILinker linker, ProcessDomain parent, Node node) {
      for (Node child : getChildTagNodes(node, ELEMENT_IP)) {
         String ip = maker.buildIp(child);

         parent.addIp(ip);
      }
   }
}
