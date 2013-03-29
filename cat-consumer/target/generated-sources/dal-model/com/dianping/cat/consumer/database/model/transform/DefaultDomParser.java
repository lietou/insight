package com.dianping.cat.consumer.database.model.transform;

import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_DATABASENAME;
import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_DOMAINNAME;
import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_SQL;

import static com.dianping.cat.consumer.database.model.Constants.ENTITY_DATABASE_REPORT;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_METHOD;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_TABLE;

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
import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

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

   public DatabaseReport parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public DatabaseReport parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_DATABASE_REPORT);

      if (rootNode == null) {
         throw new RuntimeException(String.format("database-report element(%s) is expected!", ENTITY_DATABASE_REPORT));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public DatabaseReport parse(IMaker<Node> maker, ILinker linker, Node node) {
      DatabaseReport databaseReport = maker.buildDatabaseReport(node);

      if (node != null) {
         DatabaseReport parent = databaseReport;

         for (Node child : getChildTagNodes(node, ELEMENT_DATABASENAME)) {
            String databaseName = maker.buildDatabaseName(child);

            parent.addDatabaseName(databaseName);
         }

         for (Node child : getChildTagNodes(node, ELEMENT_DOMAINNAME)) {
            String domainName = maker.buildDomainName(child);

            parent.addDomainName(domainName);
         }
         for (Node child : getChildTagNodes(node, ENTITY_DOMAIN)) {
            Domain domain = maker.buildDomain(child);

            if (linker.onDomain(parent, domain)) {
               parseForDomain(maker, linker, domain, child);
            }
         }
      }

      return databaseReport;
   }

   public void parseForDomain(IMaker<Node> maker, ILinker linker, Domain parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_TABLE)) {
         Table table = maker.buildTable(child);

         if (linker.onTable(parent, table)) {
            parseForTable(maker, linker, table, child);
         }
      }
   }

   public void parseForMethod(IMaker<Node> maker, ILinker linker, Method parent, Node node) {
      for (Node child : getChildTagNodes(node, ELEMENT_SQL)) {
         String sql = maker.buildSql(child);

         parent.addSql(sql);
      }
   }

   public void parseForTable(IMaker<Node> maker, ILinker linker, Table parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_METHOD)) {
         Method method = maker.buildMethod(child);

         if (linker.onMethod(parent, method)) {
            parseForMethod(maker, linker, method, child);
         }
      }
   }
}
