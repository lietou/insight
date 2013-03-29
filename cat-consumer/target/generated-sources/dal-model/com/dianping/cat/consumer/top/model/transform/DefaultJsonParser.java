package com.dianping.cat.consumer.top.model.transform;

import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_DOMAINS;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_SEGMENTS;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private TopReport m_root;

   public static TopReport parse(InputStream in) throws IOException {
      return parse(new InputStreamReader(in, "utf-8"));
   }

   public static TopReport parse(Reader reader) throws IOException {
      return new DefaultJsonParser().parse(new JsonReader(reader));
   }

   public static TopReport parse(String json) throws IOException {
      return parse(new StringReader(json));
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

   protected void onArrayBegin() {
   }

   protected void onArrayEnd() {
      m_objs.pop();
      m_tags.pop();

   }
   protected void onName(String name) {
      m_tags.push(name);
   }

   protected void onObjectBegin() {
      if (m_objs.isEmpty()) { // root
         m_root = new TopReport();
         m_objs.push(m_root);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof TopReport) {
            if (ENTITY_DOMAINS.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_DOMAINS.equals(parentTag)) {
                  Domain domains = new Domain();

                  m_linker.onDomain((TopReport) parent, domains);
                  m_objs.push(domains);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Domain) {
            if (ENTITY_SEGMENTS.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_SEGMENTS.equals(parentTag)) {
                  Segment segments = new Segment();

                  m_linker.onSegment((Domain) parent, segments);
                  m_objs.push(segments);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      }
   }

   protected void onObjectEnd() {
      m_objs.pop();
      m_tags.pop();
   }

   protected void onValue(String value) {
      Object parent = m_objs.peek();
      String tag = m_tags.pop();

      if (parent instanceof TopReport) {
         parseForTopReport((TopReport) parent, tag, value);
      } else if (parent instanceof Domain) {
         parseForDomain((Domain) parent, tag, value);
      } else if (parent instanceof Segment) {
         parseForSegment((Segment) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private TopReport parse(JsonReader reader) throws IOException {
      try {
         reader.parse(this);
      } catch (EOFException e) {
         if (!m_objs.isEmpty()) {
            throw new EOFException(String.format("Unexpected end while parsing %s", m_tags));
         }
      }

      m_linker.finish();
      return m_root;
   }

   public void parseForDomain(Domain domain, String tag, String value) {
      if (ENTITY_SEGMENTS.equals(tag)) {
         // do nothing here
      } else if (ATTR_NAME.equals(tag)) {
         domain.setName(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, domain, m_tags));
      }
   }

   public void parseForSegment(Segment segment, String tag, String value) {
      if (ATTR_ID.equals(tag)) {
         segment.setId(convert(Integer.class, value, null));
      } else if (ATTR_ERROR.equals(tag)) {
         segment.setError(convert(Long.class, value, 0L));
      } else if (ATTR_URL.equals(tag)) {
         segment.setUrl(convert(Long.class, value, 0L));
      } else if (ATTR_URL_DURATION.equals(tag)) {
         segment.setUrlDuration(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_SERVICE.equals(tag)) {
         segment.setService(convert(Long.class, value, 0L));
      } else if (ATTR_SERVICE_DURATION.equals(tag)) {
         segment.setServiceDuration(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_SQL.equals(tag)) {
         segment.setSql(convert(Long.class, value, 0L));
      } else if (ATTR_SQL_DURATION.equals(tag)) {
         segment.setSqlDuration(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_CALL.equals(tag)) {
         segment.setCall(convert(Long.class, value, 0L));
      } else if (ATTR_CALL_DURATION.equals(tag)) {
         segment.setCallDuration(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_CACHE.equals(tag)) {
         segment.setCache(convert(Long.class, value, 0L));
      } else if (ATTR_CACHE_DURATION.equals(tag)) {
         segment.setCacheDuration(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_CALL_ERROR.equals(tag)) {
         segment.setCallError(convert(Long.class, value, 0L));
      } else if (ATTR_URL_SUM.equals(tag)) {
         segment.setUrlSum(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_SERVICE_SUM.equals(tag)) {
         segment.setServiceSum(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_SQL_SUM.equals(tag)) {
         segment.setSqlSum(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_CALL_SUM.equals(tag)) {
         segment.setCallSum(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_CACHE_SUM.equals(tag)) {
         segment.setCacheSum(toNumber(value, "0.00").doubleValue());
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, segment, m_tags));
      }
   }

   public void parseForTopReport(TopReport topReport, String tag, String value) {
      if (ENTITY_DOMAINS.equals(tag)) {
         // do nothing here
      } else if (ATTR_DOMAIN.equals(tag)) {
         topReport.setDomain(value);
      } else if (ATTR_STARTTIME.equals(tag)) {
         topReport.setStartTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ATTR_ENDTIME.equals(tag)) {
         topReport.setEndTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, topReport, m_tags));
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

   static class JsonReader {
      private Reader m_reader;

      private char[] m_buffer = new char[2048];

      private int m_size;

      private int m_index;

      public JsonReader(Reader reader) {
         m_reader = reader;
      }

      private char next() throws IOException {
         if (m_index >= m_size) {
            m_size = m_reader.read(m_buffer);
            m_index = 0;

            if (m_size == -1) {
               throw new EOFException();
            }
         }

         return m_buffer[m_index++];
      }

      public void parse(DefaultJsonParser parser) throws IOException {
         StringBuilder sb = new StringBuilder();
         boolean flag = false;

         while (true) {
            char ch = next();

            switch (ch) {
            case ' ':
            case '\t':
            case '\r':
            case '\n':
               break;
            case '{':
               parser.onObjectBegin();
               flag = false;
               break;
            case '}':
               if (flag) { // have value
                  parser.onValue(sb.toString());
                  sb.setLength(0);
               }

               parser.onObjectEnd();
               flag = false;
               break;
            case '\'':
            case '"':
               while (true) {
                  char ch2 = next();

                  if (ch2 != ch) {
                     if (ch2 == '\\') {
                        char ch3 = next();

                        sb.append(ch3);
                     } else {
                        sb.append(ch2);
                     }
                  } else {
                     if (!flag) {
                        parser.onName(sb.toString());
                     } else {
                        parser.onValue(sb.toString());
                        flag = false;
                     }

                     sb.setLength(0);
                     break;
                  }
               }

               break;
            case ':':
               if (sb.length() != 0) {
                  parser.onName(sb.toString());
                  sb.setLength(0);
               }

               flag = true;
               break;
            case ',':
               if (sb.length() != 0) {
                  if (!flag) {
                     parser.onName(sb.toString());
                  } else {
                     parser.onValue(sb.toString());
                  }

                  sb.setLength(0);
               }

               flag = false;
               break;
            case '[':
               parser.onArrayBegin();
               flag = false;
               break;
            case ']':
               parser.onArrayEnd();
               flag = false;
               break;
            default:
               sb.append(ch);
               break;
            }
         }
      }
   }
}
