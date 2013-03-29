package com.dianping.cat.consumer.event.model.transform;

import static com.dianping.cat.consumer.event.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_FAILS;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.event.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.event.model.Constants.ELEMENT_FAILMESSAGEURL;
import static com.dianping.cat.consumer.event.model.Constants.ELEMENT_IPS;
import static com.dianping.cat.consumer.event.model.Constants.ELEMENT_SUCCESSMESSAGEURL;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_MACHINES;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_NAMES;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_RANGES;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_TYPES;

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.event.model.IEntity;
import com.dianping.cat.consumer.event.model.IVisitor;
import com.dianping.cat.consumer.event.model.entity.EventName;
import com.dianping.cat.consumer.event.model.entity.EventReport;
import com.dianping.cat.consumer.event.model.entity.EventType;
import com.dianping.cat.consumer.event.model.entity.Machine;
import com.dianping.cat.consumer.event.model.entity.Range;

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
   public void visitEventReport(EventReport eventReport) {
      objectBegin(null);
      attributes(null, ATTR_DOMAIN, eventReport.getDomain(), ATTR_STARTTIME, toString(eventReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(eventReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!eventReport.getDomainNames().isEmpty()) {
         arrayBegin(ELEMENT_DOMAIN_NAMES);

         for (String domain : eventReport.getDomainNames()) {
            indent();
            m_sb.append('"').append(domain).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DOMAIN_NAMES);
      }

      if (!eventReport.getIps().isEmpty()) {
         arrayBegin(ELEMENT_IPS);

         for (String ip : eventReport.getIps()) {
            indent();
            m_sb.append('"').append(ip).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_IPS);
      }

      if (!eventReport.getMachines().isEmpty()) {
         objectBegin(ENTITY_MACHINES);

         for (Map.Entry<String, Machine> e : eventReport.getMachines().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitMachine(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_MACHINES);
      }

      objectEnd(null);
      trimComma();
   }

   @Override
   public void visitMachine(Machine machine) {
      attributes(null, ATTR_IP, machine.getIp());

      if (!machine.getTypes().isEmpty()) {
         objectBegin(ENTITY_TYPES);

         for (Map.Entry<String, EventType> e : machine.getTypes().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitType(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_TYPES);
      }
   }

   @Override
   public void visitName(EventName name) {
      attributes(null, ATTR_ID, name.getId(), ATTR_TOTALCOUNT, name.getTotalCount(), ATTR_FAILCOUNT, name.getFailCount(), ATTR_FAILPERCENT, toString(name.getFailPercent(), "0.00"), ELEMENT_SUCCESSMESSAGEURL, name.getSuccessMessageUrl(), ELEMENT_FAILMESSAGEURL, name.getFailMessageUrl());

      if (!name.getRanges().isEmpty()) {
         arrayBegin(ENTITY_RANGES);

         for (Range range : name.getRanges()) {
            objectBegin(null);
            visitRange(range);
            objectEnd(null);
         }

         arrayEnd(ENTITY_RANGES);
      }
   }

   @Override
   public void visitRange(Range range) {
      attributes(null, ATTR_VALUE, range.getValue(), ATTR_COUNT, range.getCount(), ATTR_FAILS, range.getFails());
   }

   @Override
   public void visitType(EventType type) {
      attributes(null, ATTR_ID, type.getId(), ATTR_TOTALCOUNT, type.getTotalCount(), ATTR_FAILCOUNT, type.getFailCount(), ATTR_FAILPERCENT, toString(type.getFailPercent(), "0.00"), ELEMENT_SUCCESSMESSAGEURL, type.getSuccessMessageUrl(), ELEMENT_FAILMESSAGEURL, type.getFailMessageUrl());

      if (!type.getNames().isEmpty()) {
         objectBegin(ENTITY_NAMES);

         for (Map.Entry<String, EventName> e : type.getNames().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitName(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_NAMES);
      }
   }
}
