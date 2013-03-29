package com.dianping.cat.consumer.ip.model.transform;

import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ADDRESS;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_MINUTE;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.ip.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IPS;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_PERIODS;

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.ip.model.IEntity;
import com.dianping.cat.consumer.ip.model.IVisitor;
import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

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
   public void visitIp(Ip ip) {
      attributes(null, ATTR_ADDRESS, ip.getAddress(), ATTR_COUNT, ip.getCount());
   }

   @Override
   public void visitIpReport(IpReport ipReport) {
      objectBegin(null);
      attributes(null, ATTR_DOMAIN, ipReport.getDomain(), ATTR_STARTTIME, toString(ipReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(ipReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!ipReport.getDomainNames().isEmpty()) {
         arrayBegin(ELEMENT_DOMAIN_NAMES);

         for (String domain : ipReport.getDomainNames()) {
            indent();
            m_sb.append('"').append(domain).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DOMAIN_NAMES);
      }

      if (!ipReport.getPeriods().isEmpty()) {
         objectBegin(ENTITY_PERIODS);

         for (Map.Entry<Integer, Period> e : ipReport.getPeriods().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitPeriod(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_PERIODS);
      }

      objectEnd(null);
      trimComma();
   }

   @Override
   public void visitPeriod(Period period) {
      attributes(null, ATTR_MINUTE, period.getMinute());

      if (!period.getIps().isEmpty()) {
         objectBegin(ENTITY_IPS);

         for (Map.Entry<String, Ip> e : period.getIps().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitIp(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_IPS);
      }
   }
}
