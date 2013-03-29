package com.dianping.cat.consumer.matrix.model.transform;

import static com.dianping.cat.consumer.matrix.model.Constants.ELEMENT_DOMAIN;

import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_MATRIX;
import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_MATRIX_REPORT;
import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_RATIO;

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
import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

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

   public MatrixReport parse(Node node) {
      return parse(new DefaultDomMaker(), new DefaultLinker(false), node);
   }

   public MatrixReport parse(String xml) throws SAXException, IOException {
      Node doc = getDocument(xml);
      Node rootNode = getChildTagNode(doc, ENTITY_MATRIX_REPORT);

      if (rootNode == null) {
         throw new RuntimeException(String.format("matrix-report element(%s) is expected!", ENTITY_MATRIX_REPORT));
      }

      return parse(new DefaultDomMaker(), new DefaultLinker(false), rootNode);
   }

   public MatrixReport parse(IMaker<Node> maker, ILinker linker, Node node) {
      MatrixReport matrixReport = maker.buildMatrixReport(node);

      if (node != null) {
         MatrixReport parent = matrixReport;

         for (Node child : getChildTagNodes(node, ELEMENT_DOMAIN)) {
            String domain = maker.buildDomain(child);

            parent.addDomain(domain);
         }
         for (Node child : getChildTagNodes(node, ENTITY_MATRIX)) {
            Matrix matrix = maker.buildMatrix(child);

            if (linker.onMatrix(parent, matrix)) {
               parseForMatrix(maker, linker, matrix, child);
            }
         }
      }

      return matrixReport;
   }

   public void parseForMatrix(IMaker<Node> maker, ILinker linker, Matrix parent, Node node) {
      for (Node child : getChildTagNodes(node, ENTITY_RATIO)) {
         Ratio ratio = maker.buildRatio(child);

         if (linker.onRatio(parent, ratio)) {
            parseForRatio(maker, linker, ratio, child);
         }
      }
   }

   public void parseForRatio(IMaker<Node> maker, ILinker linker, Ratio parent, Node node) {
   }
}
