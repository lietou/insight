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
import static com.dianping.cat.home.template.Constants.ENTITY_DURATION;
import static com.dianping.cat.home.template.Constants.ENTITY_PARAM;
import static com.dianping.cat.home.template.Constants.ENTITY_THRESHOLD_TEMPLATE;

import com.dianping.cat.home.template.IEntity;
import com.dianping.cat.home.template.IVisitor;
import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

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

   @Override
   public void visitConnection(Connection connection) {
      startTag(ENTITY_CONNECTION, null, ATTR_BASEURL, connection.getBaseUrl());

      if (!connection.getParams().isEmpty()) {
         for (Param param : connection.getParams().values().toArray(new Param[0])) {
            visitParam(param);
         }
      }

      endTag(ENTITY_CONNECTION);
   }

   @Override
   public void visitDuration(Duration duration) {
      startTag(ENTITY_DURATION, true, null, ATTR_ID, duration.getId(), ATTR_MIN, duration.getMin(), ATTR_MAX, duration.getMax(), ATTR_INTERVAL, duration.getInterval(), ATTR_ALARM, duration.getAlarm(), ATTR_ALARM_INTERVAL, duration.getAlarmInterval());
   }

   @Override
   public void visitParam(Param param) {
      startTag(ENTITY_PARAM, true, null, ATTR_TYPE, param.getType(), ATTR_VALUE, param.getValue());
   }

   @Override
   public void visitThresholdTemplate(ThresholdTemplate thresholdTemplate) {
      startTag(ENTITY_THRESHOLD_TEMPLATE, null);

      if (thresholdTemplate.getConnection() != null) {
         visitConnection(thresholdTemplate.getConnection());
      }

      if (!thresholdTemplate.getDurations().isEmpty()) {
         for (Duration duration : thresholdTemplate.getDurations().values().toArray(new Duration[0])) {
            visitDuration(duration);
         }
      }

      endTag(ENTITY_THRESHOLD_TEMPLATE);
   }
}
