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

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.cross.model.IEntity;
import com.dianping.cat.consumer.cross.model.IVisitor;
import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

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
   public void visitCrossReport(CrossReport crossReport) {
      objectBegin(null);
      attributes(null, ATTR_DOMAIN, crossReport.getDomain(), ATTR_STARTTIME, toString(crossReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(crossReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!crossReport.getDomainNames().isEmpty()) {
         arrayBegin(ELEMENT_DOMAIN_NAMES);

         for (String domain : crossReport.getDomainNames()) {
            indent();
            m_sb.append('"').append(domain).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DOMAIN_NAMES);
      }

      if (!crossReport.getIps().isEmpty()) {
         arrayBegin(ELEMENT_IPS);

         for (String ip : crossReport.getIps()) {
            indent();
            m_sb.append('"').append(ip).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_IPS);
      }

      if (!crossReport.getLocals().isEmpty()) {
         objectBegin(ENTITY_LOCALS);

         for (Map.Entry<String, Local> e : crossReport.getLocals().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitLocal(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_LOCALS);
      }

      objectEnd(null);
      trimComma();
   }

   @Override
   public void visitLocal(Local local) {
      attributes(null, ATTR_ID, local.getId());

      if (!local.getRemotes().isEmpty()) {
         objectBegin(ENTITY_REMOTES);

         for (Map.Entry<String, Remote> e : local.getRemotes().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitRemote(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_REMOTES);
      }
   }

   @Override
   public void visitName(Name name) {
      attributes(null, ATTR_ID, name.getId(), ATTR_TOTALCOUNT, name.getTotalCount(), ATTR_FAILCOUNT, name.getFailCount(), ATTR_FAILPERCENT, toString(name.getFailPercent(), "0.00"), ATTR_AVG, toString(name.getAvg(), "0.00"), ATTR_SUM, toString(name.getSum(), "0.00"), ATTR_TPS, toString(name.getTps(), "0.00"));
   }

   @Override
   public void visitRemote(Remote remote) {
      attributes(null, ATTR_ID, remote.getId(), ATTR_ROLE, remote.getRole());

      if (remote.getType() != null) {
         objectBegin(ENTITY_TYPE);
         visitType(remote.getType());
         objectEnd(ENTITY_TYPE);
      }
   }

   @Override
   public void visitType(Type type) {
      attributes(null, ATTR_ID, type.getId(), ATTR_TOTALCOUNT, type.getTotalCount(), ATTR_FAILCOUNT, type.getFailCount(), ATTR_FAILPERCENT, toString(type.getFailPercent(), "0.00"), ATTR_AVG, toString(type.getAvg(), "0.00"), ATTR_SUM, toString(type.getSum(), "0.00"), ATTR_TPS, toString(type.getTps(), "0.00"));

      if (!type.getNames().isEmpty()) {
         objectBegin(ENTITY_NAMES);

         for (Map.Entry<String, Name> e : type.getNames().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitName(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_NAMES);
      }
   }
}
