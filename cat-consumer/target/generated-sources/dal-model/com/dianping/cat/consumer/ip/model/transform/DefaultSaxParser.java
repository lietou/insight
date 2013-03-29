package com.dianping.cat.consumer.ip.model.transform;

import static com.dianping.cat.consumer.ip.model.Constants.ELEMENT_DOMAIN;//
import static com.dianping.cat.consumer.ip.model.Constants.ELEMENT_DOMAIN_NAMES;

import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IP;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IP_REPORT;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_PERIOD;

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
import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public class DefaultSaxParser extends DefaultHandler {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DefaultSaxMaker m_maker = new DefaultSaxMaker();

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private IpReport m_root;

   private StringBuilder m_text = new StringBuilder();

   public static IpReport parse(InputSource is) throws SAXException, IOException {
      try {
         SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
         DefaultSaxParser handler = new DefaultSaxParser();

         parser.parse(is, handler);
         return handler.getRoot();
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException("Unable to get SAX parser instance!", e);
      }
   }

   public static IpReport parse(InputStream in) throws SAXException, IOException {
      return parse(new InputSource(in));
   }

   public static IpReport parse(Reader reader) throws SAXException, IOException {
      return parse(new InputSource(reader));
   }

   public static IpReport parse(String xml) throws SAXException, IOException {
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

         if (currentObj instanceof IpReport) {
            IpReport ipReport = (IpReport) currentObj;

            if (ELEMENT_DOMAIN.equals(currentTag)) {
               ipReport.addDomain(getText());
            }
         }
      }

      m_text.setLength(0);
   }

   public IpReport getRoot() {
      return m_root;
   }

   protected String getText() {
      return m_text.toString();
   }

   public void parse(String qName, Attributes attributes) throws SAXException {
      if (ENTITY_IP_REPORT.equals(qName)) {
         IpReport ipReport = m_maker.buildIpReport(attributes);

         m_root = ipReport;
         m_objs.push(ipReport);
         m_tags.push(qName);
      } else {
         throw new SAXException("Root element(ipReport) expected");
      }
   }

   public void parseForIp(Ip parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForIpReport(IpReport parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_DOMAIN_NAMES.equals(qName) || ELEMENT_DOMAIN.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_PERIOD.equals(qName)) {
         Period period = m_maker.buildPeriod(attributes);

         m_linker.onPeriod(parentObj, period);
         m_objs.push(period);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under ip-report!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForPeriod(Period parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_IP.equals(qName)) {
         Ip ip = m_maker.buildIp(attributes);

         m_linker.onIp(parentObj, ip);
         m_objs.push(ip);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under period!", qName));
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

            if (parent instanceof IpReport) {
               parseForIpReport((IpReport) parent, tag, qName, attributes);
            } else if (parent instanceof Period) {
               parseForPeriod((Period) parent, tag, qName, attributes);
            } else if (parent instanceof Ip) {
               parseForIp((Ip) parent, tag, qName, attributes);
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
