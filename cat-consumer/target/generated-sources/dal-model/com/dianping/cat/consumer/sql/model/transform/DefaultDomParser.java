package com.dianping.cat.consumer.sql.model.transform;

import static com.dianping.cat.consumer.sql.model.Constants.ELEMENT_DATABASENAME;
import static com.dianping.cat.consumer.sql.model.Constants.ELEMENT_DOMAINNAME;
import static com.dianping.cat.consumer.sql.model.Constants.ELEMENT_SQL;

import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_DATABASE;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_METHOD;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_SQL_REPORT;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_TABLE;

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
import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

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

   public SqlReport parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public SqlReport parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_SQL_REPORT);

      if (rootNode == null) {
         throw new RuntimeException(String.format("sql-report element(%s) is expected!", ENTITY_SQL_REPORT));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public SqlReport parse(IMaker<Node> maker, ILinker linker, Node node) {
      SqlReport sqlReport = maker.buildSqlReport(node);

      if (node != null) {
         SqlReport parent = sqlReport;

         for (Node child : getChildTagNodes(node, ELEMENT_DOMAINNAME)) {
            String domainName = maker.buildDomainName(child);

            parent.addDomainName(domainName);
         }

         for (Node child : getChildTagNodes(node, ELEMENT_DATABASENAME)) {
            String databaseName = maker.buildDatabaseName(child);

            parent.addDatabaseName(databaseName);
         }
         for (Node child : getChildTagNodes(node, ENTITY_DATABASE)) {
            Database database = maker.buildDatabase(child);

            if (linker.onDatabase(parent, database)) {
               parseForDatabase(maker, linker, database, child);
            }
         }
      }

      return sqlReport;
   }

   public void parseForDatabase(IMaker<Node> maker, ILinker linker, Database parent, Node node) {
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
