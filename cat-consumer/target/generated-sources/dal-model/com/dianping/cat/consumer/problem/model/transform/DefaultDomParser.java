package com.dianping.cat.consumer.problem.model.transform;

import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_DOMAIN;
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_IP;
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_MESSAGE;

import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_DURATION;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_ENTRY;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_PROBLEM_REPORT;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_SEGMENT;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_THREAD;

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
import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

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

   public ProblemReport parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public ProblemReport parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_PROBLEM_REPORT);

      if (rootNode == null) {
         throw new RuntimeException(String.format("problem-report element(%s) is expected!", ENTITY_PROBLEM_REPORT));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public ProblemReport parse(IMaker<Node> maker, ILinker linker, Node node) {
      ProblemReport problemReport = maker.buildProblemReport(node);

      if (node != null) {
         ProblemReport parent = problemReport;

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

      return problemReport;
   }

   public void parseForDuration(IMaker<Node> maker, ILinker linker, Duration parent, Node node) {
      for (Node child : getChildTagNodes(node, ELEMENT_MESSAGE)) {
         String message = maker.buildMessage(child);

         parent.addMessage(message);
      }
   }

   public void parseForEntry(IMaker<Node> maker, ILinker linker, Entry parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_DURATION)) {
         Duration duration = maker.buildDuration(child);

         if (linker.onDuration(parent, duration)) {
            parseForDuration(maker, linker, duration, child);
         }
      }

      for (Node child : getChildTagNodes(node, ENTITY_THREAD)) {
         JavaThread thread = maker.buildThread(child);

         if (linker.onThread(parent, thread)) {
            parseForJavaThread(maker, linker, thread, child);
         }
      }
   }

   public void parseForMachine(IMaker<Node> maker, ILinker linker, Machine parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_ENTRY)) {
         Entry entry = maker.buildEntry(child);

         if (linker.onEntry(parent, entry)) {
            parseForEntry(maker, linker, entry, child);
         }
      }
   }

   public void parseForSegment(IMaker<Node> maker, ILinker linker, Segment parent, Node node) {
      for (Node child : getChildTagNodes(node, ELEMENT_MESSAGE)) {
         String message = maker.buildMessage(child);

         parent.addMessage(message);
      }
   }

   public void parseForJavaThread(IMaker<Node> maker, ILinker linker, JavaThread parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_SEGMENT)) {
         Segment segment = maker.buildSegment(child);

         if (linker.onSegment(parent, segment)) {
            parseForSegment(maker, linker, segment, child);
         }
      }
   }
}
