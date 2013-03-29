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

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.database.model.IEntity;
import com.dianping.cat.consumer.database.model.IVisitor;
import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

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
   public void visitDatabaseReport(DatabaseReport databaseReport) {
      objectBegin(null);
      attributes(null, ATTR_DATABASE, databaseReport.getDatabase(), ATTR_CONNECT_URL, databaseReport.getConnectUrl(), ATTR_STARTTIME, toString(databaseReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(databaseReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!databaseReport.getDatabaseNames().isEmpty()) {
         arrayBegin(ELEMENT_DATABASENAMES);

         for (String databaseName : databaseReport.getDatabaseNames()) {
            indent();
            m_sb.append('"').append(databaseName).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DATABASENAMES);
      }

      if (!databaseReport.getDomainNames().isEmpty()) {
         arrayBegin(ELEMENT_DOMAINNAMES);

         for (String domainName : databaseReport.getDomainNames()) {
            indent();
            m_sb.append('"').append(domainName).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DOMAINNAMES);
      }

      if (!databaseReport.getDomains().isEmpty()) {
         objectBegin(ENTITY_DOMAINS);

         for (Map.Entry<String, Domain> e : databaseReport.getDomains().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitDomain(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_DOMAINS);
      }

      objectEnd(null);
      trimComma();
   }

   @Override
   public void visitDomain(Domain domain) {
      attributes(null, ATTR_ID, domain.getId());

      if (!domain.getTables().isEmpty()) {
         objectBegin(ENTITY_TABLES);

         for (Map.Entry<String, Table> e : domain.getTables().entrySet()) {
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
