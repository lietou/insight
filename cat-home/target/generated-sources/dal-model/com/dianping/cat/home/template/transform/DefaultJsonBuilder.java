package com.dianping.cat.home.template.transform;

import static com.dianping.cat.home.template.Constants.ATTR_ALARM;
import static com.dianping.cat.home.template.Constants.ATTR_ALARM_INTERVAL;
import static com.dianping.cat.home.template.Constants.ATTR_BASEURL;
import static com.dianping.cat.home.template.Constants.ATTR_ID;
import static com.dianping.cat.home.template.Constants.ATTR_INTERVAL;
import static com.dianping.cat.home.template.Constants.ATTR_MAX;
import static com.dianping.cat.home.template.Constants.ATTR_MIN;
import static com.dianping.cat.home.template.Constants.ATTR_TYPE;
import static com.dianping.cat.home.template.Constants.ATTR_VALUE;
import static com.dianping.cat.home.template.Constants.ENTITY_CONNECTION;
import static com.dianping.cat.home.template.Constants.ENTITY_DURATIONS;
import static com.dianping.cat.home.template.Constants.ENTITY_PARAMS;

import java.util.List;
import java.util.Map;

import com.dianping.cat.home.template.IEntity;
import com.dianping.cat.home.template.IVisitor;
import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

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
   public void visitConnection(Connection connection) {
      attributes(null, ATTR_BASEURL, connection.getBaseUrl());

      if (!connection.getParams().isEmpty()) {
         objectBegin(ENTITY_PARAMS);

         for (Map.Entry<String, Param> e : connection.getParams().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitParam(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_PARAMS);
      }
   }

   @Override
   public void visitDuration(Duration duration) {
      attributes(null, ATTR_ID, duration.getId(), ATTR_MIN, duration.getMin(), ATTR_MAX, duration.getMax(), ATTR_INTERVAL, duration.getInterval(), ATTR_ALARM, duration.getAlarm(), ATTR_ALARM_INTERVAL, duration.getAlarmInterval());
   }

   @Override
   public void visitParam(Param param) {
      attributes(null, ATTR_TYPE, param.getType(), ATTR_VALUE, param.getValue());
   }

   @Override
   public void visitThresholdTemplate(ThresholdTemplate thresholdTemplate) {
      objectBegin(null);

      if (thresholdTemplate.getConnection() != null) {
         objectBegin(ENTITY_CONNECTION);
         visitConnection(thresholdTemplate.getConnection());
         objectEnd(ENTITY_CONNECTION);
      }

      if (!thresholdTemplate.getDurations().isEmpty()) {
         objectBegin(ENTITY_DURATIONS);

         for (Map.Entry<String, Duration> e : thresholdTemplate.getDurations().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitDuration(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_DURATIONS);
      }

      objectEnd(null);
      trimComma();
   }
}
