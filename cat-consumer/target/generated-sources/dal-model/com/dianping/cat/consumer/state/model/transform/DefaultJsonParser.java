package com.dianping.cat.consumer.state.model.transform;

import static com.dianping.cat.consumer.state.model.Constants.ATTR_AVGTPS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_BLOCKLOSS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_BLOCKTIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_BLOCKTOTAL;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DELAYAVG;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DELAYCOUNT;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DELAYSUM;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DUMP;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DUMPLOSS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_MAXTPS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_NETWORKTIMEERROR;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_PIGEONTIMEERROR;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_SIZE;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_TIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_TOTAL;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_TOTALLOSS;
import static com.dianping.cat.consumer.state.model.Constants.ELEMENT_IPS;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MACHINES;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MESSAGES;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_PROCESSDOMAINS;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private StateReport m_root;

   private boolean m_inElements = false;

   public static StateReport parse(InputStream in) throws IOException {
      return parse(new InputStreamReader(in, "utf-8"));
   }

   public static StateReport parse(Reader reader) throws IOException {
      return new DefaultJsonParser().parse(new JsonReader(reader));
   }

   public static StateReport parse(String json) throws IOException {
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

      if (parent instanceof ProcessDomain) {
         if (ELEMENT_IPS.equals(tag)) {
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

         if (parent instanceof ProcessDomain) {
            if (ELEMENT_IPS.equals(tag)) {
               ((ProcessDomain) parent).addIp(name);
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
         m_root = new StateReport();
         m_objs.push(m_root);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof StateReport) {
            if (ENTITY_MACHINES.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_MACHINES.equals(parentTag)) {
                  Machine machines = new Machine();

                  m_linker.onMachine((StateReport) parent, machines);
                  m_objs.push(machines);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Machine) {
            if (ENTITY_PROCESSDOMAINS.equals(tag) || ENTITY_MESSAGES.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_PROCESSDOMAINS.equals(parentTag)) {
                  ProcessDomain processDomains = new ProcessDomain();

                  m_linker.onProcessDomain((Machine) parent, processDomains);
                  m_objs.push(processDomains);
               } else if (ENTITY_MESSAGES.equals(parentTag)) {
                  Message messages = new Message();

                  m_linker.onMessage((Machine) parent, messages);
                  m_objs.push(messages);
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

      if (parent instanceof StateReport) {
         parseForStateReport((StateReport) parent, tag, value);
      } else if (parent instanceof Machine) {
         parseForMachine((Machine) parent, tag, value);
      } else if (parent instanceof ProcessDomain) {
         parseForProcessDomain((ProcessDomain) parent, tag, value);
      } else if (parent instanceof Message) {
         parseForMessage((Message) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private StateReport parse(JsonReader reader) throws IOException {
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

   public void parseForMachine(Machine machine, String tag, String value) {
      if (ENTITY_PROCESSDOMAINS.equals(tag) || ENTITY_MESSAGES.equals(tag)) {
         // do nothing here
      } else if (ATTR_IP.equals(tag)) {
         machine.setIp(value);
      } else if (ATTR_TOTAL.equals(tag)) {
         machine.setTotal(convert(Long.class, value, 0L));
      } else if (ATTR_TOTALLOSS.equals(tag)) {
         machine.setTotalLoss(convert(Long.class, value, 0L));
      } else if (ATTR_MAXTPS.equals(tag)) {
         machine.setMaxTps(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_AVGTPS.equals(tag)) {
         machine.setAvgTps(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_BLOCKTOTAL.equals(tag)) {
         machine.setBlockTotal(convert(Long.class, value, 0L));
      } else if (ATTR_BLOCKLOSS.equals(tag)) {
         machine.setBlockLoss(convert(Long.class, value, 0L));
      } else if (ATTR_BLOCKTIME.equals(tag)) {
         machine.setBlockTime(convert(Long.class, value, 0L));
      } else if (ATTR_PIGEONTIMEERROR.equals(tag)) {
         machine.setPigeonTimeError(convert(Long.class, value, 0L));
      } else if (ATTR_NETWORKTIMEERROR.equals(tag)) {
         machine.setNetworkTimeError(convert(Long.class, value, 0L));
      } else if (ATTR_DUMP.equals(tag)) {
         machine.setDump(convert(Long.class, value, 0L));
      } else if (ATTR_DUMPLOSS.equals(tag)) {
         machine.setDumpLoss(convert(Long.class, value, 0L));
      } else if (ATTR_SIZE.equals(tag)) {
         machine.setSize(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_DELAYSUM.equals(tag)) {
         machine.setDelaySum(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_DELAYAVG.equals(tag)) {
         machine.setDelayAvg(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_DELAYCOUNT.equals(tag)) {
         machine.setDelayCount(convert(Integer.class, value, 0));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, machine, m_tags));
      }
   }

   public void parseForMessage(Message message, String tag, String value) {
      if (ATTR_ID.equals(tag)) {
         message.setId(convert(Long.class, value, null));
      } else if (ATTR_TIME.equals(tag)) {
         message.setTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ATTR_TOTAL.equals(tag)) {
         message.setTotal(convert(Long.class, value, 0L));
      } else if (ATTR_TOTALLOSS.equals(tag)) {
         message.setTotalLoss(convert(Long.class, value, 0L));
      } else if (ATTR_DUMP.equals(tag)) {
         message.setDump(convert(Long.class, value, 0L));
      } else if (ATTR_DUMPLOSS.equals(tag)) {
         message.setDumpLoss(convert(Long.class, value, 0L));
      } else if (ATTR_SIZE.equals(tag)) {
         message.setSize(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_DELAYSUM.equals(tag)) {
         message.setDelaySum(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_DELAYCOUNT.equals(tag)) {
         message.setDelayCount(convert(Integer.class, value, 0));
      } else if (ATTR_PIGEONTIMEERROR.equals(tag)) {
         message.setPigeonTimeError(convert(Long.class, value, 0L));
      } else if (ATTR_NETWORKTIMEERROR.equals(tag)) {
         message.setNetworkTimeError(convert(Long.class, value, 0L));
      } else if (ATTR_BLOCKTOTAL.equals(tag)) {
         message.setBlockTotal(convert(Long.class, value, 0L));
      } else if (ATTR_BLOCKLOSS.equals(tag)) {
         message.setBlockLoss(convert(Long.class, value, 0L));
      } else if (ATTR_BLOCKTIME.equals(tag)) {
         message.setBlockTime(convert(Long.class, value, 0L));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, message, m_tags));
      }
   }

   public void parseForProcessDomain(ProcessDomain processDomain, String tag, String value) {
      if (ATTR_NAME.equals(tag)) {
         processDomain.setName(value);
      } else if (ELEMENT_IPS.equals(tag)) {
         processDomain.addIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, processDomain, m_tags));
      }
   }

   public void parseForStateReport(StateReport stateReport, String tag, String value) {
      if (ENTITY_MACHINES.equals(tag)) {
         // do nothing here
      } else if (ATTR_DOMAIN.equals(tag)) {
         stateReport.setDomain(value);
      } else if (ATTR_STARTTIME.equals(tag)) {
         stateReport.setStartTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ATTR_ENDTIME.equals(tag)) {
         stateReport.setEndTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, stateReport, m_tags));
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
