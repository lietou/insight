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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.consumer.heartbeat.model.entity.Disk;
import com.dianping.cat.consumer.heartbeat.model.entity.HeartbeatReport;
import com.dianping.cat.consumer.heartbeat.model.entity.Machine;
import com.dianping.cat.consumer.heartbeat.model.entity.Period;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public Disk buildDisk(Node node) {
      String path = getAttribute(node, ATTR_PATH);
      String total = getAttribute(node, ATTR_TOTAL);
      String free = getAttribute(node, ATTR_FREE);
      String usable = getAttribute(node, ATTR_USABLE);

      Disk disk = new Disk(path);

      if (total != null) {
         disk.setTotal(convert(Long.class, total, 0L));
      }

      if (free != null) {
         disk.setFree(convert(Long.class, free, 0L));
      }

      if (usable != null) {
         disk.setUsable(convert(Long.class, usable, 0L));
      }

      return disk;
   }

   @Override
   public String buildDomain(Node node) {
      return getText(node);
   }

   @Override
   public HeartbeatReport buildHeartbeatReport(Node node) {
      String domain = getAttribute(node, ATTR_DOMAIN);
      String startTime = getAttribute(node, ATTR_STARTTIME);
      String endTime = getAttribute(node, ATTR_ENDTIME);

      HeartbeatReport heartbeatReport = new HeartbeatReport(domain);

      if (startTime != null) {
         heartbeatReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         heartbeatReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return heartbeatReport;
   }

   @Override
   public String buildIp(Node node) {
      return getText(node);
   }

   @Override
   public Machine buildMachine(Node node) {
      String ip = getAttribute(node, ATTR_IP);

      Machine machine = new Machine(ip);

      return machine;
   }

   @Override
   public Period buildPeriod(Node node) {
      String minute = getAttribute(node, ATTR_MINUTE);
      String threadCount = getAttribute(node, ATTR_THREAD_COUNT);
      String daemonCount = getAttribute(node, ATTR_DAEMON_COUNT);
      String totalStartedCount = getAttribute(node, ATTR_TOTAL_STARTED_COUNT);
      String catThreadCount = getAttribute(node, ATTR_CAT_THREAD_COUNT);
      String pigeonThreadCount = getAttribute(node, ATTR_PIGEON_THREAD_COUNT);
      String httpThreadCount = getAttribute(node, ATTR_HTTP_THREAD_COUNT);
      String newGcCount = getAttribute(node, ATTR_NEW_GC_COUNT);
      String oldGcCount = getAttribute(node, ATTR_OLD_GC_COUNT);
      String memoryFree = getAttribute(node, ATTR_MEMORY_FREE);
      String heapUsage = getAttribute(node, ATTR_HEAP_USAGE);
      String noneHeapUsage = getAttribute(node, ATTR_NONE_HEAP_USAGE);
      String systemLoadAverage = getAttribute(node, ATTR_SYSTEM_LOAD_AVERAGE);
      String catMessageProduced = getAttribute(node, ATTR_CAT_MESSAGE_PRODUCED);
      String catMessageOverflow = getAttribute(node, ATTR_CAT_MESSAGE_OVERFLOW);
      String catMessageSize = getAttribute(node, ATTR_CAT_MESSAGE_SIZE);

      Period period = new Period(minute == null ? 0 : convert(Integer.class, minute, 0));

      if (threadCount != null) {
         period.setThreadCount(convert(Integer.class, threadCount, 0));
      }

      if (daemonCount != null) {
         period.setDaemonCount(convert(Integer.class, daemonCount, 0));
      }

      if (totalStartedCount != null) {
         period.setTotalStartedCount(convert(Integer.class, totalStartedCount, 0));
      }

      if (catThreadCount != null) {
         period.setCatThreadCount(convert(Integer.class, catThreadCount, 0));
      }

      if (pigeonThreadCount != null) {
         period.setPigeonThreadCount(convert(Integer.class, pigeonThreadCount, 0));
      }

      if (httpThreadCount != null) {
         period.setHttpThreadCount(convert(Integer.class, httpThreadCount, 0));
      }

      if (newGcCount != null) {
         period.setNewGcCount(convert(Long.class, newGcCount, 0L));
      }

      if (oldGcCount != null) {
         period.setOldGcCount(convert(Long.class, oldGcCount, 0L));
      }

      if (memoryFree != null) {
         period.setMemoryFree(convert(Long.class, memoryFree, 0L));
      }

      if (heapUsage != null) {
         period.setHeapUsage(convert(Long.class, heapUsage, 0L));
      }

      if (noneHeapUsage != null) {
         period.setNoneHeapUsage(convert(Long.class, noneHeapUsage, 0L));
      }

      if (systemLoadAverage != null) {
         period.setSystemLoadAverage(toNumber(systemLoadAverage, "0.00", 0).doubleValue());
      }

      if (catMessageProduced != null) {
         period.setCatMessageProduced(convert(Long.class, catMessageProduced, 0L));
      }

      if (catMessageOverflow != null) {
         period.setCatMessageOverflow(convert(Long.class, catMessageOverflow, 0L));
      }

      if (catMessageSize != null) {
         period.setCatMessageSize(toNumber(catMessageSize, "0.00", 0).doubleValue());
      }

      return period;
   }

   @SuppressWarnings("unchecked")
   protected <T> T convert(Class<T> type, String value, T defaultValue) {
      if (value == null || value.length() == 0) {
         return defaultValue;
      }

      if (type == Boolean.class) {
         return (T) Boolean.valueOf(value);
      } else if (type == Integer.class) {
         return (T) Integer.valueOf(value);
      } else if (type == Long.class) {
         return (T) Long.valueOf(value);
      } else if (type == Short.class) {
         return (T) Short.valueOf(value);
      } else if (type == Float.class) {
         return (T) Float.valueOf(value);
      } else if (type == Double.class) {
         return (T) Double.valueOf(value);
      } else if (type == Byte.class) {
         return (T) Byte.valueOf(value);
      } else if (type == Character.class) {
         return (T) (Character) value.charAt(0);
      } else {
         return (T) value;
      }
   }

   protected String getAttribute(Node node, String name) {
      Node attribute = node.getAttributes().getNamedItem(name);

      return attribute == null ? null : attribute.getNodeValue();
   }

   protected Node getChildTagNode(Node parent, String name) {
      NodeList children = parent.getChildNodes();
      int len = children.getLength();

      for (int i = 0; i < len; i++) {
         Node child = children.item(i);

         if (child.getNodeType() == Node.ELEMENT_NODE) {
            if (child.getNodeName().equals(name)) {
               return child;
            }
         }
      }

      return null;
   }

   protected String getText(Node node) {
      if (node != null) {
         StringBuilder sb = new StringBuilder();
         NodeList children = node.getChildNodes();
         int len = children.getLength();

         for (int i = 0; i < len; i++) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.TEXT_NODE || child.getNodeType() == Node.CDATA_SECTION_NODE) {
               sb.append(child.getNodeValue());
            }
         }

         if (sb.length() != 0) {
            return sb.toString();
         }
      }

      return null;
   }

   protected java.util.Date toDate(String str, String format, java.util.Date defaultValue) {
      if (str == null || str.length() == 0) {
         return defaultValue;
      }

      try {
         return new java.text.SimpleDateFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse date(%s) in format(%s)!", str, format), e);
      }
   }

   protected Number toNumber(String str, String format, Number defaultValue) {
      if (str == null || str.length() == 0) {
         return defaultValue;
      }

      try {
         return new java.text.DecimalFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse number(%s) in format(%s)!", str, format), e);
      }
   }
}
