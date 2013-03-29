package com.dianping.cat.consumer.heartbeat.model.transform;

import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_MESSAGE_OVERFLOW;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_MESSAGE_PRODUCED;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_MESSAGE_SIZE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_CAT_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_DAEMON_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_FREE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_HEAP_USAGE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_HTTP_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_MEMORY_FREE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_MINUTE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_NEW_GC_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_NONE_HEAP_USAGE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_OLD_GC_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_PATH;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_PIGEON_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_SYSTEM_LOAD_AVERAGE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_THREAD_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_TOTAL;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_TOTAL_STARTED_COUNT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_USABLE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ELEMENT_IPS;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_DISKS;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_MACHINES;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_PERIODS;

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.heartbeat.model.IEntity;
import com.dianping.cat.consumer.heartbeat.model.IVisitor;
import com.dianping.cat.consumer.heartbeat.model.entity.Disk;
import com.dianping.cat.consumer.heartbeat.model.entity.HeartbeatReport;
import com.dianping.cat.consumer.heartbeat.model.entity.Machine;
import com.dianping.cat.consumer.heartbeat.model.entity.Period;

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
   public void visitDisk(Disk disk) {
      attributes(null, ATTR_PATH, disk.getPath(), ATTR_TOTAL, disk.getTotal(), ATTR_FREE, disk.getFree(), ATTR_USABLE, disk.getUsable());
   }

   @Override
   public void visitHeartbeatReport(HeartbeatReport heartbeatReport) {
      objectBegin(null);
      attributes(null, ATTR_DOMAIN, heartbeatReport.getDomain(), ATTR_STARTTIME, toString(heartbeatReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(heartbeatReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!heartbeatReport.getDomainNames().isEmpty()) {
         arrayBegin(ELEMENT_DOMAIN_NAMES);

         for (String domain : heartbeatReport.getDomainNames()) {
            indent();
            m_sb.append('"').append(domain).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DOMAIN_NAMES);
      }

      if (!heartbeatReport.getIps().isEmpty()) {
         arrayBegin(ELEMENT_IPS);

         for (String ip : heartbeatReport.getIps()) {
            indent();
            m_sb.append('"').append(ip).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_IPS);
      }

      if (!heartbeatReport.getMachines().isEmpty()) {
         objectBegin(ENTITY_MACHINES);

         for (Map.Entry<String, Machine> e : heartbeatReport.getMachines().entrySet()) {
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

      if (!machine.getPeriods().isEmpty()) {
         arrayBegin(ENTITY_PERIODS);

         for (Period period : machine.getPeriods()) {
            objectBegin(null);
            visitPeriod(period);
            objectEnd(null);
         }

         arrayEnd(ENTITY_PERIODS);
      }
   }

   @Override
   public void visitPeriod(Period period) {
      attributes(null, ATTR_MINUTE, period.getMinute(), ATTR_THREAD_COUNT, period.getThreadCount(), ATTR_DAEMON_COUNT, period.getDaemonCount(), ATTR_TOTAL_STARTED_COUNT, period.getTotalStartedCount(), ATTR_CAT_THREAD_COUNT, period.getCatThreadCount(), ATTR_PIGEON_THREAD_COUNT, period.getPigeonThreadCount(), ATTR_HTTP_THREAD_COUNT, period.getHttpThreadCount(), ATTR_NEW_GC_COUNT, period.getNewGcCount(), ATTR_OLD_GC_COUNT, period.getOldGcCount(), ATTR_MEMORY_FREE, period.getMemoryFree(), ATTR_HEAP_USAGE, period.getHeapUsage(), ATTR_NONE_HEAP_USAGE, period.getNoneHeapUsage(), ATTR_SYSTEM_LOAD_AVERAGE, toString(period.getSystemLoadAverage(), "0.00"), ATTR_CAT_MESSAGE_PRODUCED, period.getCatMessageProduced(), ATTR_CAT_MESSAGE_OVERFLOW, period.getCatMessageOverflow(), ATTR_CAT_MESSAGE_SIZE, toString(period.getCatMessageSize(), "0.00"));

      if (!period.getDisks().isEmpty()) {
         arrayBegin(ENTITY_DISKS);

         for (Disk disk : period.getDisks()) {
            objectBegin(null);
            visitDisk(disk);
            objectEnd(null);
         }

         arrayEnd(ENTITY_DISKS);
      }
   }
}
