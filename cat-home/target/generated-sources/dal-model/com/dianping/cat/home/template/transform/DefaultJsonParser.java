package com.dianping.cat.home.template.transform;

import static com.dianping.cat.home.template.Constants.ATTR_ALARM;
import static com.dianping.cat.home.template.Constants.ATTR_ALARM_INTERVAL;
import static com.dianping.cat.home.template.Constants.ATTR_BASEURL;
import static com.dianping.cat.home.template.Constants.ATTR_ID;
import static com.dianping.cat.home.template.Constants.ATTR_INTERVAL;
import static com.dianping.cat.home.template.Constants.ATTR_MAX;
import static com.dianping.cat.home.template.Constants.ATTR_MIN;
import static com.dianping.cat.home.template.Constants.ATTR_TYPE;
import static com.dianping.cat.home.template.Constants.ATTR_VALUE;
import static com.dianping.cat.home.template.Constants.ENTITY_CONNECTION;
import static com.dianping.cat.home.template.Constants.ENTITY_DURATIONS;
import static com.dianping.cat.home.template.Constants.ENTITY_PARAMS;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private ThresholdTemplate m_root;

   public static ThresholdTemplate parse(InputStream in) throws IOException {
      return parse(new InputStreamReader(in, "utf-8"));
   }

   public static ThresholdTemplate parse(Reader reader) throws IOException {
      return new DefaultJsonParser().parse(new JsonReader(reader));
   }

   public static ThresholdTemplate parse(String json) throws IOException {
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
         m_root = new ThresholdTemplate();
         m_objs.push(m_root);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof ThresholdTemplate) {
            if (ENTITY_DURATIONS.equals(tag)) {
               m_objs.push(parent);
            } else if (ENTITY_CONNECTION.equals(tag)) {
               Connection connection = new Connection();

               m_linker.onConnection((ThresholdTemplate) parent, connection);
               m_objs.push(connection);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_DURATIONS.equals(parentTag)) {
                  Duration durations = new Duration();

                  m_linker.onDuration((ThresholdTemplate) parent, durations);
                  m_objs.push(durations);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Connection) {
            if (ENTITY_PARAMS.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_PARAMS.equals(parentTag)) {
                  Param params = new Param();

                  m_linker.onParam((Connection) parent, params);
                  m_objs.push(params);
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

      if (parent instanceof ThresholdTemplate) {
         parseForThresholdTemplate((ThresholdTemplate) parent, tag, value);
      } else if (parent instanceof Connection) {
         parseForConnection((Connection) parent, tag, value);
      } else if (parent instanceof Param) {
         parseForParam((Param) parent, tag, value);
      } else if (parent instanceof Duration) {
         parseForDuration((Duration) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private ThresholdTemplate parse(JsonReader reader) throws IOException {
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

   public void parseForConnection(Connection connection, String tag, String value) {
      if (ENTITY_PARAMS.equals(tag)) {
         // do nothing here
      } else if (ATTR_BASEURL.equals(tag)) {
         connection.setBaseUrl(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, connection, m_tags));
      }
   }

   public void parseForDuration(Duration duration, String tag, String value) {
      if (ATTR_ID.equals(tag)) {
         duration.setId(value);
      } else if (ATTR_MIN.equals(tag)) {
         duration.setMin(convert(Integer.class, value, null));
      } else if (ATTR_MAX.equals(tag)) {
         duration.setMax(convert(Integer.class, value, null));
      } else if (ATTR_INTERVAL.equals(tag)) {
         duration.setInterval(convert(Integer.class, value, null));
      } else if (ATTR_ALARM.equals(tag)) {
         duration.setAlarm(value);
      } else if (ATTR_ALARM_INTERVAL.equals(tag)) {
         duration.setAlarmInterval(convert(Integer.class, value, null));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, duration, m_tags));
      }
   }

   public void parseForParam(Param param, String tag, String value) {
      if (ATTR_TYPE.equals(tag)) {
         param.setType(value);
      } else if (ATTR_VALUE.equals(tag)) {
         param.setValue(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, param, m_tags));
      }
   }

   public void parseForThresholdTemplate(ThresholdTemplate thresholdTemplate, String tag, String value) {
      if (ENTITY_CONNECTION.equals(tag) || ENTITY_DURATIONS.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, thresholdTemplate, m_tags));
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
