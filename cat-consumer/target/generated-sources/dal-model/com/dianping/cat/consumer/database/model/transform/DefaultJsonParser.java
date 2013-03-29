package com.dianping.cat.consumer.database.model.transform;

import static com.dianping.cat.consumer.database.model.Constants.ATTR_AVG;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_CONNECT_URL;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_DATABASE;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_SUM;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_TOTALPERCENT;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_TPS;
import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_DATABASENAMES;
import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_DOMAINNAMES;
import static com.dianping.cat.consumer.database.model.Constants.ELEMENT_SQLNAMES;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_DOMAINS;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_METHODS;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_TABLES;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private DatabaseReport m_root;

   private boolean m_inElements = false;

   public static DatabaseReport parse(InputStream in) throws IOException {
      return parse(new InputStreamReader(in, "utf-8"));
   }

   public static DatabaseReport parse(Reader reader) throws IOException {
      return new DefaultJsonParser().parse(new JsonReader(reader));
   }

   public static DatabaseReport parse(String json) throws IOException {
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

      if (parent instanceof DatabaseReport) {
         if (ELEMENT_DATABASENAMES.equals(tag)) {
            m_objs.push(parent);
            m_inElements = true;
         } else if (ELEMENT_DOMAINNAMES.equals(tag)) {
            m_objs.push(parent);
            m_inElements = true;
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else if (parent instanceof Method) {
         if (ELEMENT_SQLNAMES.equals(tag)) {
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

         if (parent instanceof DatabaseReport) {
            if (ELEMENT_DATABASENAMES.equals(tag)) {
               ((DatabaseReport) parent).addDatabaseName(name);
            } else if (ELEMENT_DOMAINNAMES.equals(tag)) {
               ((DatabaseReport) parent).addDomainName(name);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Method) {
            if (ELEMENT_SQLNAMES.equals(tag)) {
               ((Method) parent).addSql(name);
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
         m_root = new DatabaseReport();
         m_objs.push(m_root);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof DatabaseReport) {
            if (ENTITY_DOMAINS.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_DOMAINS.equals(parentTag)) {
                  Domain domains = new Domain();

                  m_linker.onDomain((DatabaseReport) parent, domains);
                  m_objs.push(domains);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Domain) {
            if (ENTITY_TABLES.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_TABLES.equals(parentTag)) {
                  Table tables = new Table();

                  m_linker.onTable((Domain) parent, tables);
                  m_objs.push(tables);
               } else {
                  throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
               }
            }
         } else if (parent instanceof Table) {
            if (ENTITY_METHODS.equals(tag)) {
               m_objs.push(parent);
            } else {
               String parentTag = m_tags.size() >= 2 ? m_tags.get(m_tags.size() - 2) : null;

               if (ENTITY_METHODS.equals(parentTag)) {
                  Method methods = new Method();

                  m_linker.onMethod((Table) parent, methods);
                  m_objs.push(methods);
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

      if (parent instanceof DatabaseReport) {
         parseForDatabaseReport((DatabaseReport) parent, tag, value);
      } else if (parent instanceof Domain) {
         parseForDomain((Domain) parent, tag, value);
      } else if (parent instanceof Table) {
         parseForTable((Table) parent, tag, value);
      } else if (parent instanceof Method) {
         parseForMethod((Method) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private DatabaseReport parse(JsonReader reader) throws IOException {
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

   public void parseForDatabaseReport(DatabaseReport databaseReport, String tag, String value) {
      if (ENTITY_DOMAINS.equals(tag)) {
         // do nothing here
      } else if (ATTR_DATABASE.equals(tag)) {
         databaseReport.setDatabase(value);
      } else if (ATTR_CONNECT_URL.equals(tag)) {
         databaseReport.setConnectUrl(value);
      } else if (ATTR_STARTTIME.equals(tag)) {
         databaseReport.setStartTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ATTR_ENDTIME.equals(tag)) {
         databaseReport.setEndTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ELEMENT_DATABASENAMES.equals(tag)) {
         databaseReport.addDatabaseName(value);
      } else if (ELEMENT_DOMAINNAMES.equals(tag)) {
         databaseReport.addDomainName(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, databaseReport, m_tags));
      }
   }

   public void parseForDomain(Domain domain, String tag, String value) {
      if (ENTITY_TABLES.equals(tag)) {
         // do nothing here
      } else if (ATTR_ID.equals(tag)) {
         domain.setId(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, domain, m_tags));
      }
   }

   public void parseForMethod(Method method, String tag, String value) {
      if (ATTR_ID.equals(tag)) {
         method.setId(value);
      } else if (ATTR_TOTALCOUNT.equals(tag)) {
         method.setTotalCount(convert(Integer.class, value, 0));
      } else if (ATTR_FAILCOUNT.equals(tag)) {
         method.setFailCount(convert(Integer.class, value, 0));
      } else if (ATTR_FAILPERCENT.equals(tag)) {
         method.setFailPercent(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_AVG.equals(tag)) {
         method.setAvg(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_SUM.equals(tag)) {
         method.setSum(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_TPS.equals(tag)) {
         method.setTps(toNumber(value, "0.00").doubleValue());
      } else if (ELEMENT_SQLNAMES.equals(tag)) {
         method.addSql(value);
      } else if (ATTR_TOTALPERCENT.equals(tag)) {
         method.setTotalPercent(toNumber(value, "0.00").doubleValue());
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, method, m_tags));
      }
   }

   public void parseForTable(Table table, String tag, String value) {
      if (ENTITY_METHODS.equals(tag)) {
         // do nothing here
      } else if (ATTR_ID.equals(tag)) {
         table.setId(value);
      } else if (ATTR_TOTALCOUNT.equals(tag)) {
         table.setTotalCount(convert(Integer.class, value, 0));
      } else if (ATTR_FAILCOUNT.equals(tag)) {
         table.setFailCount(convert(Integer.class, value, 0));
      } else if (ATTR_FAILPERCENT.equals(tag)) {
         table.setFailPercent(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_AVG.equals(tag)) {
         table.setAvg(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_SUM.equals(tag)) {
         table.setSum(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_TPS.equals(tag)) {
         table.setTps(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_TOTALPERCENT.equals(tag)) {
         table.setTotalPercent(toNumber(value, "0.00").doubleValue());
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, table, m_tags));
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
