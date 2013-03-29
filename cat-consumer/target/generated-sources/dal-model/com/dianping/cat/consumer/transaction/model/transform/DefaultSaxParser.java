package com.dianping.cat.consumer.transaction.model.transform;

import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_DOMAIN;//
import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_FAILMESSAGEURL;//
import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_IP;//
import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_IPS;
import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_SUCCESSMESSAGEURL;//

import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_DURATION;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_NAME;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_RANGE;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_TRANSACTION_REPORT;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_TYPE;

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
import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

public class DefaultSaxParser extends DefaultHandler {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DefaultSaxMaker m_maker = new DefaultSaxMaker();

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private TransactionReport m_root;

   private StringBuilder m_text = new StringBuilder();

   public static TransactionReport parse(InputSource is) throws SAXException, IOException {
      try {
         SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
         DefaultSaxParser handler = new DefaultSaxParser();

         parser.parse(is, handler);
         return handler.getRoot();
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException("Unable to get SAX parser instance!", e);
      }
   }

   public static TransactionReport parse(InputStream in) throws SAXException, IOException {
      return parse(new InputSource(in));
   }

   public static TransactionReport parse(Reader reader) throws SAXException, IOException {
      return parse(new InputSource(reader));
   }

   public static TransactionReport parse(String xml) throws SAXException, IOException {
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

         if (currentObj instanceof TransactionReport) {
            TransactionReport transactionReport = (TransactionReport) currentObj;

            if (ELEMENT_DOMAIN.equals(currentTag)) {
               transactionReport.addDomain(getText());
            } else if (ELEMENT_IP.equals(currentTag)) {
               transactionReport.addIp(getText());
            }
         } else if (currentObj instanceof TransactionType) {
            TransactionType type = (TransactionType) currentObj;

            if (ELEMENT_SUCCESSMESSAGEURL.equals(currentTag)) {
               type.setSuccessMessageUrl(getText());
            } else if (ELEMENT_FAILMESSAGEURL.equals(currentTag)) {
               type.setFailMessageUrl(getText());
            }
         } else if (currentObj instanceof TransactionName) {
            TransactionName name = (TransactionName) currentObj;

            if (ELEMENT_SUCCESSMESSAGEURL.equals(currentTag)) {
               name.setSuccessMessageUrl(getText());
            } else if (ELEMENT_FAILMESSAGEURL.equals(currentTag)) {
               name.setFailMessageUrl(getText());
            }
         }
      }

      m_text.setLength(0);
   }

   public TransactionReport getRoot() {
      return m_root;
   }

   protected String getText() {
      return m_text.toString();
   }

   public void parse(String qName, Attributes attributes) throws SAXException {
      if (ENTITY_TRANSACTION_REPORT.equals(qName)) {
         TransactionReport transactionReport = m_maker.buildTransactionReport(attributes);

         m_root = transactionReport;
         m_objs.push(transactionReport);
         m_tags.push(qName);
      } else {
         throw new SAXException("Root element(transactionReport) expected");
      }
   }

   public void parseForAllDuration(AllDuration parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForDuration(Duration parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForMachine(Machine parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_TYPE.equals(qName)) {
         TransactionType type = m_maker.buildType(attributes);

         m_linker.onType(parentObj, type);
         m_objs.push(type);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under machine!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForName(TransactionName parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_SUCCESSMESSAGEURL.equals(qName) || ELEMENT_FAILMESSAGEURL.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_RANGE.equals(qName)) {
         Range range = m_maker.buildRange(attributes);

         m_linker.onRange(parentObj, range);
         m_objs.push(range);
      } else if (ENTITY_DURATION.equals(qName)) {
         Duration duration = m_maker.buildDuration(attributes);

         m_linker.onDuration(parentObj, duration);
         m_objs.push(duration);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under name!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForRange(Range parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForRange2(Range2 parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForTransactionReport(TransactionReport parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_DOMAIN_NAMES.equals(qName) || ELEMENT_DOMAIN.equals(qName) || ELEMENT_IPS.equals(qName) || ELEMENT_IP.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_MACHINE.equals(qName)) {
         Machine machine = m_maker.buildMachine(attributes);

         m_linker.onMachine(parentObj, machine);
         m_objs.push(machine);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under transaction-report!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForType(TransactionType parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_SUCCESSMESSAGEURL.equals(qName) || ELEMENT_FAILMESSAGEURL.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_NAME.equals(qName)) {
         TransactionName name = m_maker.buildName(attributes);

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

            if (parent instanceof TransactionReport) {
               parseForTransactionReport((TransactionReport) parent, tag, qName, attributes);
            } else if (parent instanceof Machine) {
               parseForMachine((Machine) parent, tag, qName, attributes);
            } else if (parent instanceof TransactionType) {
               parseForType((TransactionType) parent, tag, qName, attributes);
            } else if (parent instanceof TransactionName) {
               parseForName((TransactionName) parent, tag, qName, attributes);
            } else if (parent instanceof Range) {
               parseForRange((Range) parent, tag, qName, attributes);
            } else if (parent instanceof Duration) {
               parseForDuration((Duration) parent, tag, qName, attributes);
            } else if (parent instanceof Range2) {
               parseForRange2((Range2) parent, tag, qName, attributes);
            } else if (parent instanceof AllDuration) {
               parseForAllDuration((AllDuration) parent, tag, qName, attributes);
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
