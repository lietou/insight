package com.dianping.cat.consumer.transaction.model.transform;

import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_AVG;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_FAILS;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_LINE95COUNT;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_LINE95SUM;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_LINE95VALUE;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_MAX;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_MIN;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_STD;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_SUM;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_SUM2;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_TPS;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_FAILMESSAGEURL;
import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_IPS;
import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_SUCCESSMESSAGEURL;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_DURATIONS;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_MACHINES;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_NAMES;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_RANGES;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_TYPES;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private TransactionReport m_root;

   private boolean m_inElements = false;

   public static TransactionReport parse(InputStream in) throws IOException {
      return parse(new InputStreamReader(in, "utf-8"));
   }

   public static TransactionReport parse(Reader reader) throws IOException {
      return new DefaultJsonParser().parse(new JsonReader(reader));
   }

   public static TransactionReport parse(String json) throws IOException {
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

      if (parent instanceof TransactionReport) {
         if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
            m_objs.push(parent);
            m_inElements = true;
         } else if (ELEMENT_IPS.equals(tag)) {
            m_objs.push(parent);
            m_inElements = true;
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else if (parent instanceof TransactionType) {
         {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else if (parent instanceof TransactionName) {
         if (ENTITY_RANGES.equals(tag)) {
            m_objs.push(parent);
         } else if (ENTITY_DURATIONS.equals(tag)) {
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

         if (parent instanceof TransactionReport) {
            if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
               ((TransactionReport) parent).addDomain(name);
            } else if (ELEMENT_IPS.equals(tag)) {
               ((TransactionReport) parent).addIp(name);
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
         m_root = new TransactionReport();
         m_objs.push(m_root);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof TransactionReport) {
            if (ENTITY_MACHINES.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_MACHINES.equals(parentTag)) {
                  Machine machines = new Machine();

                  m_linker.onMachine((TransactionReport) parent, machines);
                  m_objs.push(machines);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Machine) {
            if (ENTITY_TYPES.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_TYPES.equals(parentTag)) {
                  TransactionType types = new TransactionType();

                  m_linker.onType((Machine) parent, types);
                  m_objs.push(types);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof TransactionType) {
            if (ENTITY_NAMES.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_NAMES.equals(parentTag)) {
                  TransactionName names = new TransactionName();

                  m_linker.onName((TransactionType) parent, names);
                  m_objs.push(names);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof TransactionName) {
            if (ENTITY_RANGES.equals(tag)) {
               Range ranges = new Range();

               m_linker.onRange((TransactionName) parent, ranges);
               m_objs.push(ranges);
               m_tags.push("");
            } else if (ENTITY_DURATIONS.equals(tag)) {
               Duration durations = new Duration();

               m_linker.onDuration((TransactionName) parent, durations);
               m_objs.push(durations);
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

      if (parent instanceof TransactionReport) {
         parseForTransactionReport((TransactionReport) parent, tag, value);
      } else if (parent instanceof Machine) {
         parseForMachine((Machine) parent, tag, value);
      } else if (parent instanceof TransactionType) {
         parseForType((TransactionType) parent, tag, value);
      } else if (parent instanceof TransactionName) {
         parseForName((TransactionName) parent, tag, value);
      } else if (parent instanceof Range) {
         parseForRange((Range) parent, tag, value);
      } else if (parent instanceof Duration) {
         parseForDuration((Duration) parent, tag, value);
      } else if (parent instanceof Range2) {
         parseForRange2((Range2) parent, tag, value);
      } else if (parent instanceof AllDuration) {
         parseForAllDuration((AllDuration) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private TransactionReport parse(JsonReader reader) throws IOException {
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

   public void parseForAllDuration(AllDuration allDuration, String tag, String value) {
      if (ATTR_VALUE.equals(tag)) {
         allDuration.setValue(convert(Integer.class, value, 0));
      } else if (ATTR_COUNT.equals(tag)) {
         allDuration.setCount(convert(Integer.class, value, 0));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, allDuration, m_tags));
      }
   }

   public void parseForDuration(Duration duration, String tag, String value) {
      if (ATTR_VALUE.equals(tag)) {
         duration.setValue(convert(Integer.class, value, 0));
      } else if (ATTR_COUNT.equals(tag)) {
         duration.setCount(convert(Integer.class, value, 0));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, duration, m_tags));
      }
   }

   public void parseForMachine(Machine machine, String tag, String value) {
      if (ENTITY_TYPES.equals(tag)) {
         // do nothing here
      } else if (ATTR_IP.equals(tag)) {
         machine.setIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, machine, m_tags));
      }
   }

   public void parseForName(TransactionName name, String tag, String value) {
      if (ENTITY_RANGES.equals(tag) || ENTITY_DURATIONS.equals(tag)) {
         // do nothing here
      } else if (ATTR_ID.equals(tag)) {
         name.setId(value);
      } else if (ATTR_TOTALCOUNT.equals(tag)) {
         name.setTotalCount(convert(Long.class, value, 0L));
      } else if (ATTR_FAILCOUNT.equals(tag)) {
         name.setFailCount(convert(Long.class, value, 0L));
      } else if (ATTR_FAILPERCENT.equals(tag)) {
         name.setFailPercent(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_MIN.equals(tag)) {
         name.setMin(convert(Double.class, value, 0.0));
      } else if (ATTR_MAX.equals(tag)) {
         name.setMax(convert(Double.class, value, 0.0));
      } else if (ATTR_AVG.equals(tag)) {
         name.setAvg(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_SUM.equals(tag)) {
         name.setSum(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_SUM2.equals(tag)) {
         name.setSum2(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_STD.equals(tag)) {
         name.setStd(toNumber(value, "0.0").doubleValue());
      } else if (ELEMENT_SUCCESSMESSAGEURL.equals(tag)) {
         name.setSuccessMessageUrl(value);
      } else if (ELEMENT_FAILMESSAGEURL.equals(tag)) {
         name.setFailMessageUrl(value);
      } else if (ATTR_TPS.equals(tag)) {
         name.setTps(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_LINE95VALUE.equals(tag)) {
         name.setLine95Value(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_LINE95SUM.equals(tag)) {
         name.setLine95Sum(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_LINE95COUNT.equals(tag)) {
         name.setLine95Count(convert(Integer.class, value, 0));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, name, m_tags));
      }
   }

   public void parseForRange(Range range, String tag, String value) {
      if (ATTR_VALUE.equals(tag)) {
         range.setValue(convert(Integer.class, value, 0));
      } else if (ATTR_COUNT.equals(tag)) {
         range.setCount(convert(Integer.class, value, 0));
      } else if (ATTR_SUM.equals(tag)) {
         range.setSum(convert(Double.class, value, 0.0));
      } else if (ATTR_AVG.equals(tag)) {
         range.setAvg(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_FAILS.equals(tag)) {
         range.setFails(convert(Integer.class, value, 0));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, range, m_tags));
      }
   }

   public void parseForRange2(Range2 range2, String tag, String value) {
      if (ATTR_VALUE.equals(tag)) {
         range2.setValue(convert(Integer.class, value, 0));
      } else if (ATTR_COUNT.equals(tag)) {
         range2.setCount(convert(Integer.class, value, 0));
      } else if (ATTR_SUM.equals(tag)) {
         range2.setSum(convert(Double.class, value, 0.0));
      } else if (ATTR_AVG.equals(tag)) {
         range2.setAvg(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_FAILS.equals(tag)) {
         range2.setFails(convert(Integer.class, value, 0));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, range2, m_tags));
      }
   }

   public void parseForTransactionReport(TransactionReport transactionReport, String tag, String value) {
      if (ENTITY_MACHINES.equals(tag)) {
         // do nothing here
      } else if (ATTR_DOMAIN.equals(tag)) {
         transactionReport.setDomain(value);
      } else if (ATTR_STARTTIME.equals(tag)) {
         transactionReport.setStartTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ATTR_ENDTIME.equals(tag)) {
         transactionReport.setEndTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
         transactionReport.addDomain(value);
      } else if (ELEMENT_IPS.equals(tag)) {
         transactionReport.addIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, transactionReport, m_tags));
      }
   }

   public void parseForType(TransactionType type, String tag, String value) {
      if (ENTITY_NAMES.equals(tag)) {
         // do nothing here
      } else if (ATTR_ID.equals(tag)) {
         type.setId(value);
      } else if (ATTR_TOTALCOUNT.equals(tag)) {
         type.setTotalCount(convert(Long.class, value, 0L));
      } else if (ATTR_FAILCOUNT.equals(tag)) {
         type.setFailCount(convert(Long.class, value, 0L));
      } else if (ATTR_FAILPERCENT.equals(tag)) {
         type.setFailPercent(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_MIN.equals(tag)) {
         type.setMin(convert(Double.class, value, 0.0));
      } else if (ATTR_MAX.equals(tag)) {
         type.setMax(convert(Double.class, value, 0.0));
      } else if (ATTR_AVG.equals(tag)) {
         type.setAvg(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_SUM.equals(tag)) {
         type.setSum(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_SUM2.equals(tag)) {
         type.setSum2(toNumber(value, "0.0").doubleValue());
      } else if (ATTR_STD.equals(tag)) {
         type.setStd(toNumber(value, "0.0").doubleValue());
      } else if (ELEMENT_SUCCESSMESSAGEURL.equals(tag)) {
         type.setSuccessMessageUrl(value);
      } else if (ELEMENT_FAILMESSAGEURL.equals(tag)) {
         type.setFailMessageUrl(value);
      } else if (ATTR_TPS.equals(tag)) {
         type.setTps(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_LINE95VALUE.equals(tag)) {
         type.setLine95Value(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_LINE95SUM.equals(tag)) {
         type.setLine95Sum(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_LINE95COUNT.equals(tag)) {
         type.setLine95Count(convert(Integer.class, value, 0));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, type, m_tags));
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
