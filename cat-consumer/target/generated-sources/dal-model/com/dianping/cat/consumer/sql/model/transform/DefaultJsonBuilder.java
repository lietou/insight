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
import static com.dianping.cat.consumer.sql.model.Constants.ELEMENT_DATABASE_NAMES;
import static com.dianping.cat.consumer.sql.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.sql.model.Constants.ELEMENT_SQLNAMES;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_DATABASES;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_METHODS;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_TABLES;

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.sql.model.IEntity;
import com.dianping.cat.consumer.sql.model.IVisitor;
import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

public class DefaultJsonBuilder implements IVisitor {

   private int m_level;

   private StringBuilder m_sb = new StringBuilder(2048);

   private boolean m_compact;

   public DefaultJsonBuilder() {
      this(false);
   }

   public DefaultJsonBuilder(boolean compact) {
      m_compact = compact;
   }

   protected void arrayBegin(String name) {
      indent();
      m_sb.append('"').append(name).append(m_compact ? "\":[" : "\": [\r\n");
      m_level++;
   }

   protected void arrayEnd(String name) {
      m_level--;

      trimComma();
      indent();
      m_sb.append("],").append(m_compact ? "" : "\r\n");
   }

   protected void attributes(Map<String, String> dynamicAttributes, Object... nameValues) {
      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            if (attrValue instanceof List) {
               @SuppressWarnings("unchecked")
               List<Object> list = (List<Object>) attrValue;

               if (!list.isEmpty()) {
                  indent();
                  m_sb.append('"').append(attrName).append(m_compact ? "\":[" : "\": [");

                  for (Object item : list) {
                     m_sb.append(' ');
                     toString(m_sb, item);
                     m_sb.append(',');
                  }

                  m_sb.setLength(m_sb.length() - 1);
                  m_sb.append(m_compact ? "]," : " ],\r\n");
               }
            } else {
               if (m_compact) {
                  m_sb.append('"').append(attrName).append("\":");
                  toString(m_sb, attrValue);
                  m_sb.append(",");
               } else {
                  indent();
                  m_sb.append('"').append(attrName).append("\": ");
                  toString(m_sb, attrValue);
                  m_sb.append(",\r\n");
               }
            }
         }
      }

      if (dynamicAttributes != null) {
         for (Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            if (m_compact) {
               m_sb.append('"').append(e.getKey()).append("\":");
               toString(m_sb, e.getValue());
               m_sb.append(",");
            } else {
               indent();
               m_sb.append('"').append(e.getKey()).append("\": ");
               toString(m_sb, e.getValue());
               m_sb.append(",\r\n");
            }
         }
      }
   }

   public String buildJson(IEntity<?> entity) {
      m_sb.setLength(0);
      entity.accept(this);
      return m_sb.toString();
   }

   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void objectBegin(String name) {
      indent();

      if (name == null) {
         m_sb.append("{").append(m_compact ? "" : "\r\n");
      } else {
         m_sb.append('"').append(name).append(m_compact ? "\":{" : "\": {\r\n");
      }

      m_level++;
   }

   protected void objectEnd(String name) {
      m_level--;

      trimComma();
      indent();
      m_sb.append(m_compact ? "}," : "},\r\n");
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

   protected void toString(StringBuilder sb, Object value) {
      if (value instanceof String) {
         sb.append('"').append(value).append('"');
      } else if (value instanceof Boolean || value instanceof Number) {
         sb.append(value);
      } else {
         sb.append('"').append(value).append('"');
      }
   }

   protected void trimComma() {
      int len = m_sb.length();

      if (m_compact) {
         if (len > 1 && m_sb.charAt(len - 1) == ',') {
            m_sb.replace(len - 1, len, "");
         }
      } else {
         if (len > 3 && m_sb.charAt(len - 3) == ',') {
            m_sb.replace(len - 3, len - 2, "");
         }
      }
   }

   @Override
   public void visitDatabase(Database database) {
      attributes(null, ATTR_ID, database.getId(), ATTR_CONNECT_URL, database.getConnectUrl());

      if (!database.getTables().isEmpty()) {
         objectBegin(ENTITY_TABLES);

         for (Map.Entry<String, Table> e : database.getTables().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitTable(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_TABLES);
      }
   }

   @Override
   public void visitMethod(Method method) {
      attributes(null, ATTR_ID, method.getId(), ATTR_TOTALCOUNT, method.getTotalCount(), ATTR_FAILCOUNT, method.getFailCount(), ATTR_FAILPERCENT, toString(method.getFailPercent(), "0.00"), ATTR_AVG, toString(method.getAvg(), "0.00"), ATTR_SUM, toString(method.getSum(), "0.00"), ATTR_TPS, toString(method.getTps(), "0.00"), ATTR_TOTALPERCENT, toString(method.getTotalPercent(), "0.00"));

      if (!method.getSqlNames().isEmpty()) {
         arrayBegin(ELEMENT_SQLNAMES);

         for (String sql : method.getSqlNames()) {
            indent();
            m_sb.append('"').append(sql).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_SQLNAMES);
      }
   }

   @Override
   public void visitSqlReport(SqlReport sqlReport) {
      objectBegin(null);
      attributes(null, ATTR_DOMAIN, sqlReport.getDomain(), ATTR_STARTTIME, toString(sqlReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(sqlReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!sqlReport.getDomainNames().isEmpty()) {
         arrayBegin(ELEMENT_DOMAIN_NAMES);

         for (String domainName : sqlReport.getDomainNames()) {
            indent();
            m_sb.append('"').append(domainName).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DOMAIN_NAMES);
      }

      if (!sqlReport.getDatabaseNames().isEmpty()) {
         arrayBegin(ELEMENT_DATABASE_NAMES);

         for (String databaseName : sqlReport.getDatabaseNames()) {
            indent();
            m_sb.append('"').append(databaseName).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DATABASE_NAMES);
      }

      if (!sqlReport.getDatabases().isEmpty()) {
         objectBegin(ENTITY_DATABASES);

         for (Map.Entry<String, Database> e : sqlReport.getDatabases().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitDatabase(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_DATABASES);
      }

      objectEnd(null);
      trimComma();
   }

   @Override
   public void visitTable(Table table) {
      attributes(null, ATTR_ID, table.getId(), ATTR_TOTALCOUNT, table.getTotalCount(), ATTR_FAILCOUNT, table.getFailCount(), ATTR_FAILPERCENT, toString(table.getFailPercent(), "0.00"), ATTR_AVG, toString(table.getAvg(), "0.00"), ATTR_SUM, toString(table.getSum(), "0.00"), ATTR_TPS, toString(table.getTps(), "0.00"), ATTR_TOTALPERCENT, toString(table.getTotalPercent(), "0.00"));

      if (!table.getMethods().isEmpty()) {
         objectBegin(ENTITY_METHODS);

         for (Map.Entry<String, Method> e : table.getMethods().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitMethod(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_METHODS);
      }
   }
}
