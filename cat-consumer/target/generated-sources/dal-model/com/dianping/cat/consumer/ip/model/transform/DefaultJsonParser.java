package com.dianping.cat.consumer.ip.model.transform;

import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ADDRESS;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_MINUTE;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.ip.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IPS;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_PERIODS;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private IpReport m_root;

   private boolean m_inElements = false;

   public static IpReport parse(InputStream in) throws IOException {
      return parse(new InputStreamReader(in, "utf-8"));
   }

   public static IpReport parse(Reader reader) throws IOException {
      return new DefaultJsonParser().parse(new JsonReader(reader));
   }

   public static IpReport parse(String json) throws IOException {
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
      Object parent = m_objs.peek();
      String tag = m_tags.peek();

      if (parent instanceof IpReport) {
         if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
            m_objs.push(parent);
            m_inElements = true;
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
      }
   }

   protected void onArrayEnd() {
      m_objs.pop();
      m_tags.pop();

      m_inElements = false;

   }
   protected void onName(String name) {
      if (m_inElements) {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof IpReport) {
            if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
               ((IpReport) parent).addDomain(name);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else {
         m_tags.push(name);
      }
   }

   protected void onObjectBegin() {
      if (m_objs.isEmpty()) { // root
         m_root = new IpReport();
         m_objs.push(m_root);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof IpReport) {
            if (ENTITY_PERIODS.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_PERIODS.equals(parentTag)) {
                  Period periods = new Period();

                  m_linker.onPeriod((IpReport) parent, periods);
                  m_objs.push(periods);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Period) {
            if (ENTITY_IPS.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_IPS.equals(parentTag)) {
                  Ip ips = new Ip();

                  m_linker.onIp((Period) parent, ips);
                  m_objs.push(ips);
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

      if (parent instanceof IpReport) {
         parseForIpReport((IpReport) parent, tag, value);
      } else if (parent instanceof Period) {
         parseForPeriod((Period) parent, tag, value);
      } else if (parent instanceof Ip) {
         parseForIp((Ip) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private IpReport parse(JsonReader reader) throws IOException {
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

   public void parseForIp(Ip ip, String tag, String value) {
      if (ATTR_ADDRESS.equals(tag)) {
         ip.setAddress(value);
      } else if (ATTR_COUNT.equals(tag)) {
         ip.setCount(convert(Integer.class, value, 0));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, ip, m_tags));
      }
   }

   public void parseForIpReport(IpReport ipReport, String tag, String value) {
      if (ENTITY_PERIODS.equals(tag)) {
         // do nothing here
      } else if (ATTR_DOMAIN.equals(tag)) {
         ipReport.setDomain(value);
      } else if (ATTR_STARTTIME.equals(tag)) {
         ipReport.setStartTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ATTR_ENDTIME.equals(tag)) {
         ipReport.setEndTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
         ipReport.addDomain(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, ipReport, m_tags));
      }
   }

   public void parseForPeriod(Period period, String tag, String value) {
      if (ENTITY_IPS.equals(tag)) {
         // do nothing here
      } else if (ATTR_MINUTE.equals(tag)) {
         period.setMinute(convert(Integer.class, value, null));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, period, m_tags));
      }
   }


   protected java.util.Date toDate(String str, String format) {
      try {
         return new java.text.SimpleDateFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse date(%s) in format(%s)!", str, format), e);
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
