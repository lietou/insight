package com.dianping.cat.consumer.health.model.transform;

import static com.dianping.cat.consumer.health.model.Constants.ELEMENT_DOMAIN;//
import static com.dianping.cat.consumer.health.model.Constants.ELEMENT_DOMAINNAMES;

import static com.dianping.cat.consumer.health.model.Constants.ENTITY_BASE_CACHE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_BASE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_CALL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_CLIENTSERVICE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_HEALTH_REPORT;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_KVDB_CACHE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_MACHINE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_MEM_CACHE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_PROBLEM_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_SERVICE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_SQL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_URL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_WEB_CACHE;

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
import com.dianping.cat.consumer.health.model.entity.BaseCacheInfo;
import com.dianping.cat.consumer.health.model.entity.BaseInfo;
import com.dianping.cat.consumer.health.model.entity.Call;
import com.dianping.cat.consumer.health.model.entity.ClientService;
import com.dianping.cat.consumer.health.model.entity.HealthReport;
import com.dianping.cat.consumer.health.model.entity.KvdbCache;
import com.dianping.cat.consumer.health.model.entity.MachineInfo;
import com.dianping.cat.consumer.health.model.entity.MemCache;
import com.dianping.cat.consumer.health.model.entity.ProblemInfo;
import com.dianping.cat.consumer.health.model.entity.Service;
import com.dianping.cat.consumer.health.model.entity.Sql;
import com.dianping.cat.consumer.health.model.entity.Url;
import com.dianping.cat.consumer.health.model.entity.WebCache;

public class DefaultSaxParser extends DefaultHandler {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DefaultSaxMaker m_maker = new DefaultSaxMaker();

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private HealthReport m_root;

   private StringBuilder m_text = new StringBuilder();

   public static HealthReport parse(InputSource is) throws SAXException, IOException {
      try {
         SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
         DefaultSaxParser handler = new DefaultSaxParser();

         parser.parse(is, handler);
         return handler.getRoot();
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException("Unable to get SAX parser instance!", e);
      }
   }

   public static HealthReport parse(InputStream in) throws SAXException, IOException {
      return parse(new InputSource(in));
   }

   public static HealthReport parse(Reader reader) throws SAXException, IOException {
      return parse(new InputSource(reader));
   }

   public static HealthReport parse(String xml) throws SAXException, IOException {
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

         if (currentObj instanceof HealthReport) {
            HealthReport healthReport = (HealthReport) currentObj;

            if (ELEMENT_DOMAIN.equals(currentTag)) {
               healthReport.addDomain(getText());
            }
         }
      }

      m_text.setLength(0);
   }

   public HealthReport getRoot() {
      return m_root;
   }

   protected String getText() {
      return m_text.toString();
   }

   public void parse(String qName, Attributes attributes) throws SAXException {
      if (ENTITY_HEALTH_REPORT.equals(qName)) {
         HealthReport healthReport = m_maker.buildHealthReport(attributes);

         m_root = healthReport;
         m_objs.push(healthReport);
         m_tags.push(qName);
      } else {
         throw new SAXException("Root element(healthReport) expected");
      }
   }

   public void parseForBaseCacheInfo(BaseCacheInfo parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForBaseInfo(BaseInfo parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForCall(Call parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_BASE_INFO.equals(qName)) {
         BaseInfo baseInfo = m_maker.buildBaseInfo(attributes);

         m_linker.onBaseInfo(parentObj, baseInfo);
         m_objs.push(baseInfo);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under call!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForClientService(ClientService parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_BASE_INFO.equals(qName)) {
         BaseInfo baseInfo = m_maker.buildBaseInfo(attributes);

         m_linker.onBaseInfo(parentObj, baseInfo);
         m_objs.push(baseInfo);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under clientService!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForHealthReport(HealthReport parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_DOMAINNAMES.equals(qName) || ELEMENT_DOMAIN.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_PROBLEM_INFO.equals(qName)) {
         ProblemInfo problemInfo = m_maker.buildProblemInfo(attributes);

         m_linker.onProblemInfo(parentObj, problemInfo);
         m_objs.push(problemInfo);
      } else if (ENTITY_URL.equals(qName)) {
         Url url = m_maker.buildUrl(attributes);

         m_linker.onUrl(parentObj, url);
         m_objs.push(url);
      } else if (ENTITY_SERVICE.equals(qName)) {
         Service service = m_maker.buildService(attributes);

         m_linker.onService(parentObj, service);
         m_objs.push(service);
      } else if (ENTITY_CALL.equals(qName)) {
         Call call = m_maker.buildCall(attributes);

         m_linker.onCall(parentObj, call);
         m_objs.push(call);
      } else if (ENTITY_SQL.equals(qName)) {
         Sql sql = m_maker.buildSql(attributes);

         m_linker.onSql(parentObj, sql);
         m_objs.push(sql);
      } else if (ENTITY_WEB_CACHE.equals(qName)) {
         WebCache webCache = m_maker.buildWebCache(attributes);

         m_linker.onWebCache(parentObj, webCache);
         m_objs.push(webCache);
      } else if (ENTITY_KVDB_CACHE.equals(qName)) {
         KvdbCache kvdbCache = m_maker.buildKvdbCache(attributes);

         m_linker.onKvdbCache(parentObj, kvdbCache);
         m_objs.push(kvdbCache);
      } else if (ENTITY_MEM_CACHE.equals(qName)) {
         MemCache memCache = m_maker.buildMemCache(attributes);

         m_linker.onMemCache(parentObj, memCache);
         m_objs.push(memCache);
      } else if (ENTITY_CLIENTSERVICE.equals(qName)) {
         ClientService clientService = m_maker.buildClientService(attributes);

         m_linker.onClientService(parentObj, clientService);
         m_objs.push(clientService);
      } else if (ENTITY_MACHINE_INFO.equals(qName)) {
         MachineInfo machineInfo = m_maker.buildMachineInfo(attributes);

         m_linker.onMachineInfo(parentObj, machineInfo);
         m_objs.push(machineInfo);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under health-report!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForKvdbCache(KvdbCache parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_BASE_CACHE_INFO.equals(qName)) {
         BaseCacheInfo baseCacheInfo = m_maker.buildBaseCacheInfo(attributes);

         m_linker.onBaseCacheInfo(parentObj, baseCacheInfo);
         m_objs.push(baseCacheInfo);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under kvdb-cache!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForMachineInfo(MachineInfo parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForMemCache(MemCache parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_BASE_CACHE_INFO.equals(qName)) {
         BaseCacheInfo baseCacheInfo = m_maker.buildBaseCacheInfo(attributes);

         m_linker.onBaseCacheInfo(parentObj, baseCacheInfo);
         m_objs.push(baseCacheInfo);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under mem-cache!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForProblemInfo(ProblemInfo parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   public void parseForService(Service parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_BASE_INFO.equals(qName)) {
         BaseInfo baseInfo = m_maker.buildBaseInfo(attributes);

         m_linker.onBaseInfo(parentObj, baseInfo);
         m_objs.push(baseInfo);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under service!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForSql(Sql parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_BASE_INFO.equals(qName)) {
         BaseInfo baseInfo = m_maker.buildBaseInfo(attributes);

         m_linker.onBaseInfo(parentObj, baseInfo);
         m_objs.push(baseInfo);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under sql!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForUrl(Url parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_BASE_INFO.equals(qName)) {
         BaseInfo baseInfo = m_maker.buildBaseInfo(attributes);

         m_linker.onBaseInfo(parentObj, baseInfo);
         m_objs.push(baseInfo);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under url!", qName));
      }

      m_tags.push(qName);
   }

   public void parseForWebCache(WebCache parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_BASE_CACHE_INFO.equals(qName)) {
         BaseCacheInfo baseCacheInfo = m_maker.buildBaseCacheInfo(attributes);

         m_linker.onBaseCacheInfo(parentObj, baseCacheInfo);
         m_objs.push(baseCacheInfo);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under web-cache!", qName));
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

            if (parent instanceof HealthReport) {
               parseForHealthReport((HealthReport) parent, tag, qName, attributes);
            } else if (parent instanceof ProblemInfo) {
               parseForProblemInfo((ProblemInfo) parent, tag, qName, attributes);
            } else if (parent instanceof Url) {
               parseForUrl((Url) parent, tag, qName, attributes);
            } else if (parent instanceof BaseInfo) {
               parseForBaseInfo((BaseInfo) parent, tag, qName, attributes);
            } else if (parent instanceof Service) {
               parseForService((Service) parent, tag, qName, attributes);
            } else if (parent instanceof Call) {
               parseForCall((Call) parent, tag, qName, attributes);
            } else if (parent instanceof Sql) {
               parseForSql((Sql) parent, tag, qName, attributes);
            } else if (parent instanceof WebCache) {
               parseForWebCache((WebCache) parent, tag, qName, attributes);
            } else if (parent instanceof BaseCacheInfo) {
               parseForBaseCacheInfo((BaseCacheInfo) parent, tag, qName, attributes);
            } else if (parent instanceof KvdbCache) {
               parseForKvdbCache((KvdbCache) parent, tag, qName, attributes);
            } else if (parent instanceof MemCache) {
               parseForMemCache((MemCache) parent, tag, qName, attributes);
            } else if (parent instanceof MachineInfo) {
               parseForMachineInfo((MachineInfo) parent, tag, qName, attributes);
            } else if (parent instanceof ClientService) {
               parseForClientService((ClientService) parent, tag, qName, attributes);
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
