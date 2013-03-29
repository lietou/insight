package com.dianping.cat.consumer.matrix.model.transform;

import static com.dianping.cat.consumer.matrix.model.Constants.ELEMENT_DOMAIN;//
import static com.dianping.cat.consumer.matrix.model.Constants.ELEMENT_DOMAIN_NAMES;

import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_MATRIX;
import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_MATRIX_REPORT;
import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_RATIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public class DefaultSaxParser extends DefaultHandler {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DefaultSaxMaker m_maker = new DefaultSaxMaker();

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private MatrixReport m_root;

   private StringBuilder m_text = new StringBuilder();

   public static MatrixReport parse(InputSource is) throws SAXException, IOException {
      try {
         SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
         DefaultSaxParser handler = new DefaultSaxParser();

         parser.parse(is, handler);
         return handler.getRoot();
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException("Unable to get SAX parser instance!", e);
      }
   }

   public static MatrixReport parse(InputStream in) throws SAXException, IOException {
      return parse(new InputSource(in));
   }

   public static MatrixReport parse(Reader reader) throws SAXException, IOException {
      return parse(new InputSource(reader));
   }

   public static MatrixReport parse(String xml) throws SAXException, IOException {
      return parse(new InputSource(new StringReader(xml)));
   }


   @SuppressWarnings("unchecked")
   protected <T> T convert(Class<T> type, String value, T defaultValue) {
      if (value == null || value.length() == 0) {
         return defaultValue;
      }

      if (type == Boolean.class) {
         return (T) Boolean.valueOf(value);
      } else if (type == Integer.class) {
         return (T) Integer.valueOf(value);
      } else if (type == Long.class) {
         return (T) Long.valueOf(value);
      } else if (type == Short.class) {
         return (T) Short.valueOf(value);
      } else if (type == Float.class) {
         return (T) Float.valueOf(value);
      } else if (type == Double.class) {
         return (T) Double.valueOf(value);
      } else if (type == Byte.class) {
         return (T) Byte.valueOf(value);
      } else if (type == Character.class) {
         return (T) (Character) value.charAt(0);
      } else {
         return (T) value;
      }
   }

   @Override
   public void characters(char[] ch, int start, int length) throws SAXException {
      m_text.append(ch, start, length);
   }

   @Override
   public void endDocument() throws SAXException {
      m_linker.finish();
   }

   @Override
   public void endElement(String uri, String localName, String qName) throws SAXException {
      if (uri == null || uri.length() == 0) {
         Object currentObj = m_objs.pop();
         String currentTag = m_tags.pop();

         if (currentObj instanceof MatrixReport) {
            MatrixReport matrixReport = (MatrixReport) currentObj;

            if (ELEMENT_DOMAIN.equals(currentTag)) {
               matrixReport.addDomain(getText());
            }
         }
      }

      m_text.setLength(0);
   }

   public MatrixReport getRoot() {
      return m_root;
   }

   protected String getText() {
      return m_text.toString();
   }

   public void parse(String qName, Attributes attributes) throws SAXException {
      if (ENTITY_MATRIX_REPORT.equals(qName)) {
         MatrixReport matrixReport = m_maker.buildMatrixReport(attributes);

         m_root = matrixReport;
         m_objs.push(matrixReport);
         m_tags.push(qName);
      } else {
         throw new SAXException("Root element(matrixReport) expected");
      }
   }

   public void parseForMatrix(Matrix parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_RATIO.equals(qName)) {
         Ratio ratio = m_maker.buildRatio(attributes);

         m_linker.onRatio(parentObj, ratio);
         m_objs.push(ratio);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under matrix!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForMatrixReport(MatrixReport parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_DOMAIN_NAMES.equals(qName) || ELEMENT_DOMAIN.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_MATRIX.equals(qName)) {
         Matrix matrix = m_maker.buildMatrix(attributes);

         m_linker.onMatrix(parentObj, matrix);
         m_objs.push(matrix);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under matrix-report!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForRatio(Ratio parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (uri == null || uri.length() == 0) {
         if (m_objs.isEmpty()) { // root
            parse(qName, attributes);
         } else {
            Object parent = m_objs.peek();
            String tag = m_tags.peek();

            if (parent instanceof MatrixReport) {
               parseForMatrixReport((MatrixReport) parent, tag, qName, attributes);
            } else if (parent instanceof Matrix) {
               parseForMatrix((Matrix) parent, tag, qName, attributes);
            } else if (parent instanceof Ratio) {
               parseForRatio((Ratio) parent, tag, qName, attributes);
            } else {
               throw new RuntimeException(String.format("Unknown entity(%s) under %s!", qName, parent.getClass().getName()));
            }
         }

         m_text.setLength(0);
        } else {
         throw new SAXException(String.format("Namespace(%s) is not supported by " + this.getClass().getName(), uri));
      }
   }

   protected java.util.Date toDate(String str, String format) {
      try {
         return new java.text.SimpleDateFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse date(%s) in format(%s)!", str, format), e);
      }
   }
}
