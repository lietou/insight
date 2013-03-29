package com.dianping.cat.consumer.sql.model.transform;

import static com.dianping.cat.consumer.sql.model.Constants.ATTR_AVG;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_CONNECT_URL;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_SUM;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_TOTALPERCENT;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_TPS;
import static com.dianping.cat.consumer.sql.model.Constants.ELEMENT_DATABASENAME;
import static com.dianping.cat.consumer.sql.model.Constants.ELEMENT_DOMAINNAME;
import static com.dianping.cat.consumer.sql.model.Constants.ELEMENT_SQL;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_DATABASE;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_METHOD;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_SQL_REPORT;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_TABLE;

import com.dianping.cat.consumer.sql.model.IEntity;
import com.dianping.cat.consumer.sql.model.IVisitor;
import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

public class DefaultXmlBuilder implements IVisitor {

   private int m_level;

   private StringBuilder m_sb = new StringBuilder(4096);

   private boolean m_compact;

   public DefaultXmlBuilder() {
      this(false);
   }

   public DefaultXmlBuilder(boolean compact) {
      m_compact = compact;
   }

   public String buildXml(IEntity<?> entity) {
      m_sb.setLength(0);
      m_sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
      entity.accept(this);
      return m_sb.toString();
   }

   protected void endTag(String name) {
      m_level--;

      indent();
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected String escape(Object value) {
      return escape(value, false);
   }
   
   protected String escape(Object value, boolean text) {
      if (value == null) {
         return null;
      }

      String str = value.toString();
      int len = str.length();
      StringBuilder sb = new StringBuilder(len + 16);

      for (int i = 0; i < len; i++) {
         final char ch = str.charAt(i);

         switch (ch) {
         case '<':
            sb.append("&lt;");
            break;
         case '>':
            sb.append("&gt;");
            break;
         case '&':
            sb.append("&amp;");
            break;
         case '"':
            if (!text) {
               sb.append("&quot;");
               break;
            }
         default:
            sb.append(ch);
            break;
         }
      }

      return sb.toString();
   }

   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void startTag(String name) {
      startTag(name, false, null);
   }
   
   protected void startTag(String name, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, closed, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, false, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, Object text, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      if (dynamicAttributes != null) {
         for (java.util.Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            m_sb.append(' ').append(e.getKey()).append("=\"").append(escape(e.getValue())).append('"');
         }
      }

      if (text != null && closed) {
         m_sb.append('>');
         m_sb.append(escape(text, true));
         m_sb.append("</").append(name).append(">\r\n");
      } else {
         if (closed) {
            m_sb.append('/');
         } else {
            m_level++;
         }
   
         m_sb.append(">\r\n");
      }
   }

   private void tagWithText(String name, String text, Object... nameValues) {
      if (text == null) {
         return;
      }
      
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      m_sb.append(">");
      m_sb.append(escape(text, true));
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected String toString(java.util.Date date, String format) {
      if (date != null) {
         return new java.text.SimpleDateFormat(format).format(date);
      } else {
         return null;
      }
   }

   protected String toString(Number number, String format) {
      if (number != null) {
         return new java.text.DecimalFormat(format).format(number);
      } else {
         return null;
      }
   }

   @Override
   public void visitDatabase(Database database) {
      startTag(ENTITY_DATABASE, null, ATTR_ID, database.getId(), ATTR_CONNECT_URL, database.getConnectUrl());

      if (!database.getTables().isEmpty()) {
         for (Table table : database.getTables().values().toArray(new Table[0])) {
            visitTable(table);
         }
      }

      endTag(ENTITY_DATABASE);
   }

   @Override
   public void visitMethod(Method method) {
      startTag(ENTITY_METHOD, null, ATTR_ID, method.getId(), ATTR_TOTALCOUNT, method.getTotalCount(), ATTR_FAILCOUNT, method.getFailCount(), ATTR_FAILPERCENT, toString(method.getFailPercent(), "0.00"), ATTR_AVG, toString(method.getAvg(), "0.00"), ATTR_SUM, toString(method.getSum(), "0.00"), ATTR_TPS, toString(method.getTps(), "0.00"), ATTR_TOTALPERCENT, toString(method.getTotalPercent(), "0.00"));

      if (!method.getSqlNames().isEmpty()) {
         for (String sql : method.getSqlNames().toArray(new String[0])) {
            tagWithText(ELEMENT_SQL, sql);
         }
      }

      endTag(ENTITY_METHOD);
   }

   @Override
   public void visitSqlReport(SqlReport sqlReport) {
      startTag(ENTITY_SQL_REPORT, null, ATTR_DOMAIN, sqlReport.getDomain(), ATTR_STARTTIME, toString(sqlReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(sqlReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!sqlReport.getDomainNames().isEmpty()) {
         for (String domainName : sqlReport.getDomainNames().toArray(new String[0])) {
            tagWithText(ELEMENT_DOMAINNAME, domainName);
         }
      }

      if (!sqlReport.getDatabaseNames().isEmpty()) {
         for (String databaseName : sqlReport.getDatabaseNames().toArray(new String[0])) {
            tagWithText(ELEMENT_DATABASENAME, databaseName);
         }
      }

      if (!sqlReport.getDatabases().isEmpty()) {
         for (Database database : sqlReport.getDatabases().values().toArray(new Database[0])) {
            visitDatabase(database);
         }
      }

      endTag(ENTITY_SQL_REPORT);
   }

   @Override
   public void visitTable(Table table) {
      startTag(ENTITY_TABLE, null, ATTR_ID, table.getId(), ATTR_TOTALCOUNT, table.getTotalCount(), ATTR_FAILCOUNT, table.getFailCount(), ATTR_FAILPERCENT, toString(table.getFailPercent(), "0.00"), ATTR_AVG, toString(table.getAvg(), "0.00"), ATTR_SUM, toString(table.getSum(), "0.00"), ATTR_TPS, toString(table.getTps(), "0.00"), ATTR_TOTALPERCENT, toString(table.getTotalPercent(), "0.00"));

      if (!table.getMethods().isEmpty()) {
         for (Method method : table.getMethods().values().toArray(new Method[0])) {
            visitMethod(method);
         }
      }

      endTag(ENTITY_TABLE);
   }
}
