package com.dianping.cat.consumer.database.model.transform;

import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_DATABASENAME;//
import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_DATABASENAMES;
import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_DOMAINNAME;//
import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_DOMAINNAMES;
import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_SQL;//
import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_SQLNAMES;

import static com.dianping.cat.consumer.database.model.Constants.ENTITY_DATABASE_REPORT;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_METHOD;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_TABLE;

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
import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public class DefaultSaxParser extends DefaultHandler {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DefaultSaxMaker m_maker = new DefaultSaxMaker();

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private DatabaseReport m_root;

   private StringBuilder m_text = new StringBuilder();

   public static DatabaseReport parse(InputSource is) throws SAXException, IOException {
      try {
         SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
         DefaultSaxParser handler = new DefaultSaxParser();

         parser.parse(is, handler);
         return handler.getRoot();
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException("Unable to get SAX parser instance!", e);
      }
   }

   public static DatabaseReport parse(InputStream in) throws SAXException, IOException {
      return parse(new InputSource(in));
   }

   public static DatabaseReport parse(Reader reader) throws SAXException, IOException {
      return parse(new InputSource(reader));
   }

   public static DatabaseReport parse(String xml) throws SAXException, IOException {
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

         if (currentObj instanceof DatabaseReport) {
            DatabaseReport databaseReport = (DatabaseReport) currentObj;

            if (ELEMENT_DATABASENAME.equals(currentTag)) {
               databaseReport.addDatabaseName(getText());
            } else if (ELEMENT_DOMAINNAME.equals(currentTag)) {
               databaseReport.addDomainName(getText());
            }
         } else if (currentObj instanceof Method) {
            Method method = (Method) currentObj;

            if (ELEMENT_SQL.equals(currentTag)) {
               method.addSql(getText());
            }
         }
      }

      m_text.setLength(0);
   }

   public DatabaseReport getRoot() {
      return m_root;
   }

   protected String getText() {
      return m_text.toString();
   }

   public void parse(String qName, Attributes attributes) throws SAXException {
      if (ENTITY_DATABASE_REPORT.equals(qName)) {
         DatabaseReport databaseReport = m_maker.buildDatabaseReport(attributes);

         m_root = databaseReport;
         m_objs.push(databaseReport);
         m_tags.push(qName);
      } else {
         throw new SAXException("Root element(databaseReport) expected");
      }
   }

   public void parseForDatabaseReport(DatabaseReport parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_DATABASENAMES.equals(qName) || ELEMENT_DATABASENAME.equals(qName) || ELEMENT_DOMAINNAMES.equals(qName) || ELEMENT_DOMAINNAME.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_DOMAIN.equals(qName)) {
         Domain domain = m_maker.buildDomain(attributes);

         m_linker.onDomain(parentObj, domain);
         m_objs.push(domain);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under database-report!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForDomain(Domain parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_TABLE.equals(qName)) {
         Table table = m_maker.buildTable(attributes);

         m_linker.onTable(parentObj, table);
         m_objs.push(table);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under domain!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForMethod(Method parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_SQLNAMES.equals(qName) || ELEMENT_SQL.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under method!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForTable(Table parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_METHOD.equals(qName)) {
         Method method = m_maker.buildMethod(attributes);

         m_linker.onMethod(parentObj, method);
         m_objs.push(method);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under table!", qName));
      }

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

            if (parent instanceof DatabaseReport) {
               parseForDatabaseReport((DatabaseReport) parent, tag, qName, attributes);
            } else if (parent instanceof Domain) {
               parseForDomain((Domain) parent, tag, qName, attributes);
            } else if (parent instanceof Table) {
               parseForTable((Table) parent, tag, qName, attributes);
            } else if (parent instanceof Method) {
               parseForMethod((Method) parent, tag, qName, attributes);
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

   protected Number toNumber(String str, String format) {
      try {
         return new java.text.DecimalFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse number(%s) in format(%s)!", str, format), e);
      }
   }
}
