package com.dianping.cat.home.template.transform;

import static com.dianping.cat.home.template.Constants.ENTITY_CONNECTION;
import static com.dianping.cat.home.template.Constants.ENTITY_DURATION;
import static com.dianping.cat.home.template.Constants.ENTITY_PARAM;
import static com.dianping.cat.home.template.Constants.ENTITY_THRESHOLD_TEMPLATE;

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
import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

public class DefaultSaxParser extends DefaultHandler {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DefaultSaxMaker m_maker = new DefaultSaxMaker();

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private ThresholdTemplate m_root;

   private StringBuilder m_text = new StringBuilder();

   public static ThresholdTemplate parse(InputSource is) throws SAXException, IOException {
      try {
         SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
         DefaultSaxParser handler = new DefaultSaxParser();

         parser.parse(is, handler);
         return handler.getRoot();
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException("Unable to get SAX parser instance!", e);
      }
   }

   public static ThresholdTemplate parse(InputStream in) throws SAXException, IOException {
      return parse(new InputSource(in));
   }

   public static ThresholdTemplate parse(Reader reader) throws SAXException, IOException {
      return parse(new InputSource(reader));
   }

   public static ThresholdTemplate parse(String xml) throws SAXException, IOException {
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
         m_objs.pop();
         m_tags.pop();

      }

      m_text.setLength(0);
   }

   public ThresholdTemplate getRoot() {
      return m_root;
   }

   protected String getText() {
      return m_text.toString();
   }

   public void parse(String qName, Attributes attributes) throws SAXException {
      if (ENTITY_THRESHOLD_TEMPLATE.equals(qName)) {
         ThresholdTemplate thresholdTemplate = m_maker.buildThresholdTemplate(attributes);

         m_root = thresholdTemplate;
         m_objs.push(thresholdTemplate);
         m_tags.push(qName);
      } else {
         throw new SAXException("Root element(thresholdTemplate) expected");
      }
   }

   public void parseForConnection(Connection parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_PARAM.equals(qName)) {
         Param param = m_maker.buildParam(attributes);

         m_linker.onParam(parentObj, param);
         m_objs.push(param);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under connection!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForDuration(Duration parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForParam(Param parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForThresholdTemplate(ThresholdTemplate parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_CONNECTION.equals(qName)) {
         Connection connection = m_maker.buildConnection(attributes);

         m_linker.onConnection(parentObj, connection);
         m_objs.push(connection);
      } else if (ENTITY_DURATION.equals(qName)) {
         Duration duration = m_maker.buildDuration(attributes);

         m_linker.onDuration(parentObj, duration);
         m_objs.push(duration);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under threshold-template!", qName));
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

            if (parent instanceof ThresholdTemplate) {
               parseForThresholdTemplate((ThresholdTemplate) parent, tag, qName, attributes);
            } else if (parent instanceof Connection) {
               parseForConnection((Connection) parent, tag, qName, attributes);
            } else if (parent instanceof Param) {
               parseForParam((Param) parent, tag, qName, attributes);
            } else if (parent instanceof Duration) {
               parseForDuration((Duration) parent, tag, qName, attributes);
            } else {
               throw new RuntimeException(String.format("Unknown entity(%s) under %s!", qName, parent.getClass().getName()));
            }
         }

         m_text.setLength(0);
        } else {
         throw new SAXException(String.format("Namespace(%s) is not supported by " + this.getClass().getName(), uri));
      }
   }
}
