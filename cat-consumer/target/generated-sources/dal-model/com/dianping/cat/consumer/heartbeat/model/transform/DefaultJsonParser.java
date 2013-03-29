package com.dianping.cat.consumer.heartbeat.model.transform;

import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_MESSAGE_OVERFLOW;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_MESSAGE_PRODUCED;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_MESSAGE_SIZE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_DAEMON_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_FREE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_HEAP_USAGE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_HTTP_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_MEMORY_FREE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_MINUTE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_NEW_GC_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_NONE_HEAP_USAGE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_OLD_GC_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_PATH;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_PIGEON_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_SYSTEM_LOAD_AVERAGE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_TOTAL;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_TOTAL_STARTED_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_USABLE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ELEMENT_IPS;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_DISKS;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_MACHINES;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_PERIODS;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import com.dianping.cat.consumer.heartbeat.model.entity.Disk;
import com.dianping.cat.consumer.heartbeat.model.entity.HeartbeatReport;
import com.dianping.cat.consumer.heartbeat.model.entity.Machine;
import com.dianping.cat.consumer.heartbeat.model.entity.Period;

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private HeartbeatReport m_root;

   private boolean m_inElements = false;

   public static HeartbeatReport parse(InputStream in) throws IOException {
      return parse(new InputStreamReader(in, "utf-8"));
   }

   public static HeartbeatReport parse(Reader reader) throws IOException {
      return new DefaultJsonParser().parse(new JsonReader(reader));
   }

   public static HeartbeatReport parse(String json) throws IOException {
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

      if (parent instanceof HeartbeatReport) {
         if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
            m_objs.push(parent);
            m_inElements = true;
         } else if (ELEMENT_IPS.equals(tag)) {
            m_objs.push(parent);
            m_inElements = true;
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else if (parent instanceof Machine) {
         if (ENTITY_PERIODS.equals(tag)) {
            m_objs.push(parent);
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else if (parent instanceof Period) {
         if (ENTITY_DISKS.equals(tag)) {
            m_objs.push(parent);
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

         if (parent instanceof HeartbeatReport) {
            if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
               ((HeartbeatReport) parent).addDomain(name);
            } else if (ELEMENT_IPS.equals(tag)) {
               ((HeartbeatReport) parent).addIp(name);
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
         m_root = new HeartbeatReport();
         m_objs.push(m_root);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof HeartbeatReport) {
            if (ENTITY_MACHINES.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_MACHINES.equals(parentTag)) {
                  Machine machines = new Machine();

                  m_linker.onMachine((HeartbeatReport) parent, machines);
                  m_objs.push(machines);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Machine) {
            if (ENTITY_PERIODS.equals(tag)) {
               Period periods = new Period();

               m_linker.onPeriod((Machine) parent, periods);
               m_objs.push(periods);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Period) {
            if (ENTITY_DISKS.equals(tag)) {
               Disk disks = new Disk();

               m_linker.onDisk((Period) parent, disks);
               m_objs.push(disks);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
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

      if (parent instanceof HeartbeatReport) {
         parseForHeartbeatReport((HeartbeatReport) parent, tag, value);
      } else if (parent instanceof Machine) {
         parseForMachine((Machine) parent, tag, value);
      } else if (parent instanceof Period) {
         parseForPeriod((Period) parent, tag, value);
      } else if (parent instanceof Disk) {
         parseForDisk((Disk) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private HeartbeatReport parse(JsonReader reader) throws IOException {
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

   public void parseForDisk(Disk disk, String tag, String value) {
      if (ATTR_PATH.equals(tag)) {
         disk.setPath(value);
      } else if (ATTR_TOTAL.equals(tag)) {
         disk.setTotal(convert(Long.class, value, 0L));
      } else if (ATTR_FREE.equals(tag)) {
         disk.setFree(convert(Long.class, value, 0L));
      } else if (ATTR_USABLE.equals(tag)) {
         disk.setUsable(convert(Long.class, value, 0L));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, disk, m_tags));
      }
   }

   public void parseForHeartbeatReport(HeartbeatReport heartbeatReport, String tag, String value) {
      if (ENTITY_MACHINES.equals(tag)) {
         // do nothing here
      } else if (ATTR_DOMAIN.equals(tag)) {
         heartbeatReport.setDomain(value);
      } else if (ATTR_STARTTIME.equals(tag)) {
         heartbeatReport.setStartTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ATTR_ENDTIME.equals(tag)) {
         heartbeatReport.setEndTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
         heartbeatReport.addDomain(value);
      } else if (ELEMENT_IPS.equals(tag)) {
         heartbeatReport.addIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, heartbeatReport, m_tags));
      }
   }

   public void parseForMachine(Machine machine, String tag, String value) {
      if (ENTITY_PERIODS.equals(tag)) {
         // do nothing here
      } else if (ATTR_IP.equals(tag)) {
         machine.setIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, machine, m_tags));
      }
   }

   public void parseForPeriod(Period period, String tag, String value) {
      if (ENTITY_DISKS.equals(tag)) {
         // do nothing here
      } else if (ATTR_MINUTE.equals(tag)) {
         period.setMinute(convert(Integer.class, value, 0));
      } else if (ATTR_THREAD_COUNT.equals(tag)) {
         period.setThreadCount(convert(Integer.class, value, 0));
      } else if (ATTR_DAEMON_COUNT.equals(tag)) {
         period.setDaemonCount(convert(Integer.class, value, 0));
      } else if (ATTR_TOTAL_STARTED_COUNT.equals(tag)) {
         period.setTotalStartedCount(convert(Integer.class, value, 0));
      } else if (ATTR_CAT_THREAD_COUNT.equals(tag)) {
         period.setCatThreadCount(convert(Integer.class, value, 0));
      } else if (ATTR_PIGEON_THREAD_COUNT.equals(tag)) {
         period.setPigeonThreadCount(convert(Integer.class, value, 0));
      } else if (ATTR_HTTP_THREAD_COUNT.equals(tag)) {
         period.setHttpThreadCount(convert(Integer.class, value, 0));
      } else if (ATTR_NEW_GC_COUNT.equals(tag)) {
         period.setNewGcCount(convert(Long.class, value, 0L));
      } else if (ATTR_OLD_GC_COUNT.equals(tag)) {
         period.setOldGcCount(convert(Long.class, value, 0L));
      } else if (ATTR_MEMORY_FREE.equals(tag)) {
         period.setMemoryFree(convert(Long.class, value, 0L));
      } else if (ATTR_HEAP_USAGE.equals(tag)) {
         period.setHeapUsage(convert(Long.class, value, 0L));
      } else if (ATTR_NONE_HEAP_USAGE.equals(tag)) {
         period.setNoneHeapUsage(convert(Long.class, value, 0L));
      } else if (ATTR_SYSTEM_LOAD_AVERAGE.equals(tag)) {
         period.setSystemLoadAverage(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_CAT_MESSAGE_PRODUCED.equals(tag)) {
         period.setCatMessageProduced(convert(Long.class, value, 0L));
      } else if (ATTR_CAT_MESSAGE_OVERFLOW.equals(tag)) {
         period.setCatMessageOverflow(convert(Long.class, value, 0L));
      } else if (ATTR_CAT_MESSAGE_SIZE.equals(tag)) {
         period.setCatMessageSize(toNumber(value, "0.00").doubleValue());
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
