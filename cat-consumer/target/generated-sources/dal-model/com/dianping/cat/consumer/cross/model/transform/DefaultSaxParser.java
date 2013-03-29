package com.dianping.cat.consumer.cross.model.transform;

import static com.dianping.cat.consumer.cross.model.Constants.ELEMENT_DOMAIN;//
import static com.dianping.cat.consumer.cross.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.cross.model.Constants.ELEMENT_IP;//
import static com.dianping.cat.consumer.cross.model.Constants.ELEMENT_IPS;

import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_CROSS_REPORT;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_LOCAL;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_NAME;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_REMOTE;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_TYPE;

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
import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

public class DefaultSaxParser extends DefaultHandler {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DefaultSaxMaker m_maker = new DefaultSaxMaker();

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private CrossReport m_root;

   private StringBuilder m_text = new StringBuilder();

   public static CrossReport parse(InputSource is) throws SAXException, IOException {
      try {
         SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
         DefaultSaxParser handler = new DefaultSaxParser();

         parser.parse(is, handler);
         return handler.getRoot();
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException("Unable to get SAX parser instance!", e);
      }
   }

   public static CrossReport parse(InputStream in) throws SAXException, IOException {
      return parse(new InputSource(in));
   }

   public static CrossReport parse(Reader reader) throws SAXException, IOException {
      return parse(new InputSource(reader));
   }

   public static CrossReport parse(String xml) throws SAXException, IOException {
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

         if (currentObj instanceof CrossReport) {
            CrossReport crossReport = (CrossReport) currentObj;

            if (ELEMENT_DOMAIN.equals(currentTag)) {
               crossReport.addDomain(getText());
            } else if (ELEMENT_IP.equals(currentTag)) {
               crossReport.addIp(getText());
            }
         }
      }

      m_text.setLength(0);
   }

   public CrossReport getRoot() {
      return m_root;
   }

   protected String getText() {
      return m_text.toString();
   }

   public void parse(String qName, Attributes attributes) throws SAXException {
      if (ENTITY_CROSS_REPORT.equals(qName)) {
         CrossReport crossReport = m_maker.buildCrossReport(attributes);

         m_root = crossReport;
         m_objs.push(crossReport);
         m_tags.push(qName);
      } else {
         throw new SAXException("Root element(crossReport) expected");
      }
   }

   public void parseForCrossReport(CrossReport parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_DOMAIN_NAMES.equals(qName) || ELEMENT_DOMAIN.equals(qName) || ELEMENT_IPS.equals(qName) || ELEMENT_IP.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_LOCAL.equals(qName)) {
         Local local = m_maker.buildLocal(attributes);

         m_linker.onLocal(parentObj, local);
         m_objs.push(local);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under cross-report!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForLocal(Local parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_REMOTE.equals(qName)) {
         Remote remote = m_maker.buildRemote(attributes);

         m_linker.onRemote(parentObj, remote);
         m_objs.push(remote);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under local!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForName(Name parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForRemote(Remote parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_TYPE.equals(qName)) {
         Type type = m_maker.buildType(attributes);

         m_linker.onType(parentObj, type);
         m_objs.push(type);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under remote!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForType(Type parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_NAME.equals(qName)) {
         Name name = m_maker.buildName(attributes);

         m_linker.onName(parentObj, name);
         m_objs.push(name);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under type!", qName));
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

            if (parent instanceof CrossReport) {
               parseForCrossReport((CrossReport) parent, tag, qName, attributes);
            } else if (parent instanceof Local) {
               parseForLocal((Local) parent, tag, qName, attributes);
            } else if (parent instanceof Remote) {
               parseForRemote((Remote) parent, tag, qName, attributes);
            } else if (parent instanceof Type) {
               parseForType((Type) parent, tag, qName, attributes);
            } else if (parent instanceof Name) {
               parseForName((Name) parent, tag, qName, attributes);
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
