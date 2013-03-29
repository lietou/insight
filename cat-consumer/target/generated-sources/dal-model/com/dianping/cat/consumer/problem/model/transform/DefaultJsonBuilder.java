package com.dianping.cat.consumer.problem.model.transform;

import static com.dianping.cat.consumer.problem.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_GROUP_NAME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_STATUS;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_TYPE;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_VALUE;
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_IPS;
import static com.dianping.cat.consumer.problem.model.Constants.ELEMENT_MESSAGES;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_DURATIONS;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_ENTRIES;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_MACHINES;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_SEGMENTS;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_THREADS;

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.problem.model.IEntity;
import com.dianping.cat.consumer.problem.model.IVisitor;
import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

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
   public void visitDuration(Duration duration) {
      attributes(null, ATTR_VALUE, duration.getValue(), ATTR_COUNT, duration.getCount());

      if (!duration.getMessages().isEmpty()) {
         arrayBegin(ELEMENT_MESSAGES);

         for (String message : duration.getMessages()) {
            indent();
            m_sb.append('"').append(message).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_MESSAGES);
      }
   }

   @Override
   public void visitEntry(Entry entry) {
      attributes(null, ATTR_TYPE, entry.getType(), ATTR_STATUS, entry.getStatus());

      if (!entry.getDurations().isEmpty()) {
         objectBegin(ENTITY_DURATIONS);

         for (Map.Entry<Integer, Duration> e : entry.getDurations().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitDuration(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_DURATIONS);
      }

      if (!entry.getThreads().isEmpty()) {
         objectBegin(ENTITY_THREADS);

         for (Map.Entry<String, JavaThread> e : entry.getThreads().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitThread(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_THREADS);
      }
   }

   @Override
   public void visitMachine(Machine machine) {
      attributes(null, ATTR_IP, machine.getIp());

      if (!machine.getEntries().isEmpty()) {
         arrayBegin(ENTITY_ENTRIES);

         for (Entry entry : machine.getEntries()) {
            objectBegin(null);
            visitEntry(entry);
            objectEnd(null);
         }

         arrayEnd(ENTITY_ENTRIES);
      }
   }

   @Override
   public void visitProblemReport(ProblemReport problemReport) {
      objectBegin(null);
      attributes(null, ATTR_DOMAIN, problemReport.getDomain(), ATTR_STARTTIME, toString(problemReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(problemReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!problemReport.getDomainNames().isEmpty()) {
         arrayBegin(ELEMENT_DOMAIN_NAMES);

         for (String domain : problemReport.getDomainNames()) {
            indent();
            m_sb.append('"').append(domain).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DOMAIN_NAMES);
      }

      if (!problemReport.getIps().isEmpty()) {
         arrayBegin(ELEMENT_IPS);

         for (String ip : problemReport.getIps()) {
            indent();
            m_sb.append('"').append(ip).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_IPS);
      }

      if (!problemReport.getMachines().isEmpty()) {
         objectBegin(ENTITY_MACHINES);

         for (Map.Entry<String, Machine> e : problemReport.getMachines().entrySet()) {
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
   public void visitSegment(Segment segment) {
      attributes(null, ATTR_ID, segment.getId(), ATTR_COUNT, segment.getCount());

      if (!segment.getMessages().isEmpty()) {
         arrayBegin(ELEMENT_MESSAGES);

         for (String message : segment.getMessages()) {
            indent();
            m_sb.append('"').append(message).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_MESSAGES);
      }
   }

   @Override
   public void visitThread(JavaThread thread) {
      attributes(null, ATTR_GROUP_NAME, thread.getGroupName(), ATTR_NAME, thread.getName(), ATTR_ID, thread.getId());

      if (!thread.getSegments().isEmpty()) {
         objectBegin(ENTITY_SEGMENTS);

         for (Map.Entry<Integer, Segment> e : thread.getSegments().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitSegment(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_SEGMENTS);
      }
   }
}
