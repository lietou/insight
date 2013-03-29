package com.dianping.cat.consumer.problem.model.transform;

import static com.dianping.cat.consumer.problem.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_GROUP_NAME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_STATUS;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_TYPE;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_IPS;
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_MESSAGES;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_DURATIONS;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_ENTRIES;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_MACHINES;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_SEGMENTS;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_THREADS;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private ProblemReport m_root;

   private boolean m_inElements = false;

   public static ProblemReport parse(InputStream in) throws IOException {
      return parse(new InputStreamReader(in, "utf-8"));
   }

   public static ProblemReport parse(Reader reader) throws IOException {
      return new DefaultJsonParser().parse(new JsonReader(reader));
   }

   public static ProblemReport parse(String json) throws IOException {
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

      if (parent instanceof ProblemReport) {
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
         if (ENTITY_ENTRIES.equals(tag)) {
            m_objs.push(parent);
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else if (parent instanceof Segment) {
         if (ELEMENT_MESSAGES.equals(tag)) {
            m_objs.push(parent);
            m_inElements = true;
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else if (parent instanceof Duration) {
         if (ELEMENT_MESSAGES.equals(tag)) {
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

         if (parent instanceof ProblemReport) {
            if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
               ((ProblemReport) parent).addDomain(name);
            } else if (ELEMENT_IPS.equals(tag)) {
               ((ProblemReport) parent).addIp(name);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Segment) {
            if (ELEMENT_MESSAGES.equals(tag)) {
               ((Segment) parent).addMessage(name);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Duration) {
            if (ELEMENT_MESSAGES.equals(tag)) {
               ((Duration) parent).addMessage(name);
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
         m_root = new ProblemReport();
         m_objs.push(m_root);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof ProblemReport) {
            if (ENTITY_MACHINES.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_MACHINES.equals(parentTag)) {
                  Machine machines = new Machine();

                  m_linker.onMachine((ProblemReport) parent, machines);
                  m_objs.push(machines);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Machine) {
            if (ENTITY_ENTRIES.equals(tag)) {
               Entry entries = new Entry();

               m_linker.onEntry((Machine) parent, entries);
               m_objs.push(entries);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Entry) {
            if (ENTITY_DURATIONS.equals(tag) || ENTITY_THREADS.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_DURATIONS.equals(parentTag)) {
                  Duration durations = new Duration();

                  m_linker.onDuration((Entry) parent, durations);
                  m_objs.push(durations);
               } else if (ENTITY_THREADS.equals(parentTag)) {
                  JavaThread threads = new JavaThread();

                  m_linker.onThread((Entry) parent, threads);
                  m_objs.push(threads);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof JavaThread) {
            if (ENTITY_SEGMENTS.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_SEGMENTS.equals(parentTag)) {
                  Segment segments = new Segment();

                  m_linker.onSegment((JavaThread) parent, segments);
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

      if (parent instanceof ProblemReport) {
         parseForProblemReport((ProblemReport) parent, tag, value);
      } else if (parent instanceof Machine) {
         parseForMachine((Machine) parent, tag, value);
      } else if (parent instanceof Entry) {
         parseForEntry((Entry) parent, tag, value);
      } else if (parent instanceof JavaThread) {
         parseForThread((JavaThread) parent, tag, value);
      } else if (parent instanceof Segment) {
         parseForSegment((Segment) parent, tag, value);
      } else if (parent instanceof Duration) {
         parseForDuration((Duration) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private ProblemReport parse(JsonReader reader) throws IOException {
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

   public void parseForDuration(Duration duration, String tag, String value) {
      if (ATTR_VALUE.equals(tag)) {
         duration.setValue(convert(Integer.class, value, 0));
      } else if (ATTR_COUNT.equals(tag)) {
         duration.setCount(convert(Integer.class, value, 0));
      } else if (ELEMENT_MESSAGES.equals(tag)) {
         duration.addMessage(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, duration, m_tags));
      }
   }

   public void parseForEntry(Entry entry, String tag, String value) {
      if (ENTITY_DURATIONS.equals(tag) || ENTITY_THREADS.equals(tag)) {
         // do nothing here
      } else if (ATTR_TYPE.equals(tag)) {
         entry.setType(value);
      } else if (ATTR_STATUS.equals(tag)) {
         entry.setStatus(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, entry, m_tags));
      }
   }

   public void parseForMachine(Machine machine, String tag, String value) {
      if (ENTITY_ENTRIES.equals(tag)) {
         // do nothing here
      } else if (ATTR_IP.equals(tag)) {
         machine.setIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, machine, m_tags));
      }
   }

   public void parseForProblemReport(ProblemReport problemReport, String tag, String value) {
      if (ENTITY_MACHINES.equals(tag)) {
         // do nothing here
      } else if (ATTR_DOMAIN.equals(tag)) {
         problemReport.setDomain(value);
      } else if (ATTR_STARTTIME.equals(tag)) {
         problemReport.setStartTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ATTR_ENDTIME.equals(tag)) {
         problemReport.setEndTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
         problemReport.addDomain(value);
      } else if (ELEMENT_IPS.equals(tag)) {
         problemReport.addIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, problemReport, m_tags));
      }
   }

   public void parseForSegment(Segment segment, String tag, String value) {
      if (ATTR_ID.equals(tag)) {
         segment.setId(convert(Integer.class, value, null));
      } else if (ATTR_COUNT.equals(tag)) {
         segment.setCount(convert(Integer.class, value, 0));
      } else if (ELEMENT_MESSAGES.equals(tag)) {
         segment.addMessage(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, segment, m_tags));
      }
   }

   public void parseForThread(JavaThread thread, String tag, String value) {
      if (ENTITY_SEGMENTS.equals(tag)) {
         // do nothing here
      } else if (ATTR_GROUP_NAME.equals(tag)) {
         thread.setGroupName(value);
      } else if (ATTR_NAME.equals(tag)) {
         thread.setName(value);
      } else if (ATTR_ID.equals(tag)) {
         thread.setId(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, thread, m_tags));
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
