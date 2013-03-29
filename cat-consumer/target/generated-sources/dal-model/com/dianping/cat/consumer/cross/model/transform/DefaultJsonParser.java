package com.dianping.cat.consumer.cross.model.transform;

import static com.dianping.cat.consumer.cross.model.Constants.ATTR_AVG;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ROLE;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_SUM;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_TPS;
import static com.dianping.cat.consumer.cross.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.cross.model.Constants.ELEMENT_IPS;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_LOCALS;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_NAMES;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_REMOTES;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_TYPE;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private CrossReport m_root;

   private boolean m_inElements = false;

   public static CrossReport parse(InputStream in) throws IOException {
      return parse(new InputStreamReader(in, "utf-8"));
   }

   public static CrossReport parse(Reader reader) throws IOException {
      return new DefaultJsonParser().parse(new JsonReader(reader));
   }

   public static CrossReport parse(String json) throws IOException {
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

      if (parent instanceof CrossReport) {
         if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
            m_objs.push(parent);
            m_inElements = true;
         } else if (ELEMENT_IPS.equals(tag)) {
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

         if (parent instanceof CrossReport) {
            if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
               ((CrossReport) parent).addDomain(name);
            } else if (ELEMENT_IPS.equals(tag)) {
               ((CrossReport) parent).addIp(name);
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
         m_root = new CrossReport();
         m_objs.push(m_root);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof CrossReport) {
            if (ENTITY_LOCALS.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_LOCALS.equals(parentTag)) {
                  Local locals = new Local();

                  m_linker.onLocal((CrossReport) parent, locals);
                  m_objs.push(locals);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Local) {
            if (ENTITY_REMOTES.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_REMOTES.equals(parentTag)) {
                  Remote remotes = new Remote();

                  m_linker.onRemote((Local) parent, remotes);
                  m_objs.push(remotes);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Remote) {
            if (ENTITY_TYPE.equals(tag)) {
               Type type = new Type();

               m_linker.onType((Remote) parent, type);
               m_objs.push(type);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Type) {
            if (ENTITY_NAMES.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_NAMES.equals(parentTag)) {
                  Name names = new Name();

                  m_linker.onName((Type) parent, names);
                  m_objs.push(names);
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

      if (parent instanceof CrossReport) {
         parseForCrossReport((CrossReport) parent, tag, value);
      } else if (parent instanceof Local) {
         parseForLocal((Local) parent, tag, value);
      } else if (parent instanceof Remote) {
         parseForRemote((Remote) parent, tag, value);
      } else if (parent instanceof Type) {
         parseForType((Type) parent, tag, value);
      } else if (parent instanceof Name) {
         parseForName((Name) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private CrossReport parse(JsonReader reader) throws IOException {
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

   public void parseForCrossReport(CrossReport crossReport, String tag, String value) {
      if (ENTITY_LOCALS.equals(tag)) {
         // do nothing here
      } else if (ATTR_DOMAIN.equals(tag)) {
         crossReport.setDomain(value);
      } else if (ATTR_STARTTIME.equals(tag)) {
         crossReport.setStartTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ATTR_ENDTIME.equals(tag)) {
         crossReport.setEndTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ELEMENT_DOMAIN_NAMES.equals(tag)) {
         crossReport.addDomain(value);
      } else if (ELEMENT_IPS.equals(tag)) {
         crossReport.addIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, crossReport, m_tags));
      }
   }

   public void parseForLocal(Local local, String tag, String value) {
      if (ENTITY_REMOTES.equals(tag)) {
         // do nothing here
      } else if (ATTR_ID.equals(tag)) {
         local.setId(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, local, m_tags));
      }
   }

   public void parseForName(Name name, String tag, String value) {
      if (ATTR_ID.equals(tag)) {
         name.setId(value);
      } else if (ATTR_TOTALCOUNT.equals(tag)) {
         name.setTotalCount(convert(Long.class, value, 0L));
      } else if (ATTR_FAILCOUNT.equals(tag)) {
         name.setFailCount(convert(Integer.class, value, 0));
      } else if (ATTR_FAILPERCENT.equals(tag)) {
         name.setFailPercent(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_AVG.equals(tag)) {
         name.setAvg(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_SUM.equals(tag)) {
         name.setSum(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_TPS.equals(tag)) {
         name.setTps(toNumber(value, "0.00").doubleValue());
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, name, m_tags));
      }
   }

   public void parseForRemote(Remote remote, String tag, String value) {
      if (ENTITY_TYPE.equals(tag)) {
         // do nothing here
      } else if (ATTR_ID.equals(tag)) {
         remote.setId(value);
      } else if (ATTR_ROLE.equals(tag)) {
         remote.setRole(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, remote, m_tags));
      }
   }

   public void parseForType(Type type, String tag, String value) {
      if (ENTITY_NAMES.equals(tag)) {
         // do nothing here
      } else if (ATTR_ID.equals(tag)) {
         type.setId(value);
      } else if (ATTR_TOTALCOUNT.equals(tag)) {
         type.setTotalCount(convert(Long.class, value, 0L));
      } else if (ATTR_FAILCOUNT.equals(tag)) {
         type.setFailCount(convert(Integer.class, value, 0));
      } else if (ATTR_FAILPERCENT.equals(tag)) {
         type.setFailPercent(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_AVG.equals(tag)) {
         type.setAvg(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_SUM.equals(tag)) {
         type.setSum(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_TPS.equals(tag)) {
         type.setTps(toNumber(value, "0.00").doubleValue());
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
