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
import static com.dianping.cat.consumer.heartbeat.model.Constants.ELEMENT_DOMAIN;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ELEMENT_IP;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_DISK;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_HEARTBEAT_REPORT;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_PERIOD;

import com.dianping.cat.consumer.heartbeat.model.IEntity;
import com.dianping.cat.consumer.heartbeat.model.IVisitor;
import com.dianping.cat.consumer.heartbeat.model.entity.Disk;
import com.dianping.cat.consumer.heartbeat.model.entity.HeartbeatReport;
import com.dianping.cat.consumer.heartbeat.model.entity.Machine;
import com.dianping.cat.consumer.heartbeat.model.entity.Period;

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

   private void tagWithText(String name, String text, Object... nameValues) {
      if (text == null) {
         return;
      }
      
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

      m_sb.append(">");
      m_sb.append(escape(text, true));
      m_sb.append("</").append(name).append(">\r\n");
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

   @Override
   public void visitDisk(Disk disk) {
      startTag(ENTITY_DISK, true, null, ATTR_PATH, disk.getPath(), ATTR_TOTAL, disk.getTotal(), ATTR_FREE, disk.getFree(), ATTR_USABLE, disk.getUsable());
   }

   @Override
   public void visitHeartbeatReport(HeartbeatReport heartbeatReport) {
      startTag(ENTITY_HEARTBEAT_REPORT, null, ATTR_DOMAIN, heartbeatReport.getDomain(), ATTR_STARTTIME, toString(heartbeatReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(heartbeatReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!heartbeatReport.getDomainNames().isEmpty()) {
         for (String domain : heartbeatReport.getDomainNames().toArray(new String[0])) {
            tagWithText(ELEMENT_DOMAIN, domain);
         }
      }

      if (!heartbeatReport.getIps().isEmpty()) {
         for (String ip : heartbeatReport.getIps().toArray(new String[0])) {
            tagWithText(ELEMENT_IP, ip);
         }
      }

      if (!heartbeatReport.getMachines().isEmpty()) {
         for (Machine machine : heartbeatReport.getMachines().values().toArray(new Machine[0])) {
            visitMachine(machine);
         }
      }

      endTag(ENTITY_HEARTBEAT_REPORT);
   }

   @Override
   public void visitMachine(Machine machine) {
      startTag(ENTITY_MACHINE, null, ATTR_IP, machine.getIp());

      if (!machine.getPeriods().isEmpty()) {
         for (Period period : machine.getPeriods().toArray(new Period[0])) {
            visitPeriod(period);
         }
      }

      endTag(ENTITY_MACHINE);
   }

   @Override
   public void visitPeriod(Period period) {
      startTag(ENTITY_PERIOD, null, ATTR_MINUTE, period.getMinute(), ATTR_THREAD_COUNT, period.getThreadCount(), ATTR_DAEMON_COUNT, period.getDaemonCount(), ATTR_TOTAL_STARTED_COUNT, period.getTotalStartedCount(), ATTR_CAT_THREAD_COUNT, period.getCatThreadCount(), ATTR_PIGEON_THREAD_COUNT, period.getPigeonThreadCount(), ATTR_HTTP_THREAD_COUNT, period.getHttpThreadCount(), ATTR_NEW_GC_COUNT, period.getNewGcCount(), ATTR_OLD_GC_COUNT, period.getOldGcCount(), ATTR_MEMORY_FREE, period.getMemoryFree(), ATTR_HEAP_USAGE, period.getHeapUsage(), ATTR_NONE_HEAP_USAGE, period.getNoneHeapUsage(), ATTR_SYSTEM_LOAD_AVERAGE, toString(period.getSystemLoadAverage(), "0.00"), ATTR_CAT_MESSAGE_PRODUCED, period.getCatMessageProduced(), ATTR_CAT_MESSAGE_OVERFLOW, period.getCatMessageOverflow(), ATTR_CAT_MESSAGE_SIZE, toString(period.getCatMessageSize(), "0.00"));

      if (!period.getDisks().isEmpty()) {
         for (Disk disk : period.getDisks().toArray(new Disk[0])) {
            visitDisk(disk);
         }
      }

      endTag(ENTITY_PERIOD);
   }
}
