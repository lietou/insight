package com.dianping.cat.consumer.top.model.transform;

import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_DOMAINS;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_SEGMENTS;

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.top.model.IEntity;
import com.dianping.cat.consumer.top.model.IVisitor;
import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

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
   public void visitDomain(Domain domain) {
      attributes(null, ATTR_NAME, domain.getName());

      if (!domain.getSegments().isEmpty()) {
         objectBegin(ENTITY_SEGMENTS);

         for (Map.Entry<Integer, Segment> e : domain.getSegments().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitSegment(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_SEGMENTS);
      }
   }

   @Override
   public void visitSegment(Segment segment) {
      attributes(null, ATTR_ID, segment.getId(), ATTR_ERROR, segment.getError(), ATTR_URL, segment.getUrl(), ATTR_URL_DURATION, toString(segment.getUrlDuration(), "0.00"), ATTR_SERVICE, segment.getService(), ATTR_SERVICE_DURATION, toString(segment.getServiceDuration(), "0.00"), ATTR_SQL, segment.getSql(), ATTR_SQL_DURATION, toString(segment.getSqlDuration(), "0.00"), ATTR_CALL, segment.getCall(), ATTR_CALL_DURATION, toString(segment.getCallDuration(), "0.00"), ATTR_CACHE, segment.getCache(), ATTR_CACHE_DURATION, toString(segment.getCacheDuration(), "0.00"), ATTR_CALL_ERROR, segment.getCallError(), ATTR_URL_SUM, toString(segment.getUrlSum(), "0.00"), ATTR_SERVICE_SUM, toString(segment.getServiceSum(), "0.00"), ATTR_SQL_SUM, toString(segment.getSqlSum(), "0.00"), ATTR_CALL_SUM, toString(segment.getCallSum(), "0.00"), ATTR_CACHE_SUM, toString(segment.getCacheSum(), "0.00"));
   }

   @Override
   public void visitTopReport(TopReport topReport) {
      objectBegin(null);
      attributes(null, ATTR_DOMAIN, topReport.getDomain(), ATTR_STARTTIME, toString(topReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(topReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!topReport.getDomains().isEmpty()) {
         objectBegin(ENTITY_DOMAINS);

         for (Map.Entry<String, Domain> e : topReport.getDomains().entrySet()) {
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
}
