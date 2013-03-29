package com.dianping.cat.status.model.transform;

import static com.dianping.cat.status.model.Constants.ATTR_ARCH;
import static com.dianping.cat.status.model.Constants.ATTR_AVAILABLE_PROCESSORS;
import static com.dianping.cat.status.model.Constants.ATTR_BYTES;
import static com.dianping.cat.status.model.Constants.ATTR_CAT_THREAD_COUNT;
import static com.dianping.cat.status.model.Constants.ATTR_COMMITTED_VIRTUAL_MEMORY;
import static com.dianping.cat.status.model.Constants.ATTR_COUNT;
import static com.dianping.cat.status.model.Constants.ATTR_DAEMON_COUNT;
import static com.dianping.cat.status.model.Constants.ATTR_FREE;
import static com.dianping.cat.status.model.Constants.ATTR_FREE_PHYSICAL_MEMORY;
import static com.dianping.cat.status.model.Constants.ATTR_FREE_SWAP_SPACE;
import static com.dianping.cat.status.model.Constants.ATTR_HEAP_USAGE;
import static com.dianping.cat.status.model.Constants.ATTR_HTTP_THREAD_COUNT;
import static com.dianping.cat.status.model.Constants.ATTR_ID;
import static com.dianping.cat.status.model.Constants.ATTR_JAVA_VERSION;
import static com.dianping.cat.status.model.Constants.ATTR_MAX;
import static com.dianping.cat.status.model.Constants.ATTR_NAME;
import static com.dianping.cat.status.model.Constants.ATTR_NON_HEAP_USAGE;
import static com.dianping.cat.status.model.Constants.ATTR_OVERFLOWED;
import static com.dianping.cat.status.model.Constants.ATTR_PEEK_COUNT;
import static com.dianping.cat.status.model.Constants.ATTR_PIGEON_THREAD_COUNT;
import static com.dianping.cat.status.model.Constants.ATTR_PROCESS_TIME;
import static com.dianping.cat.status.model.Constants.ATTR_PRODUCED;
import static com.dianping.cat.status.model.Constants.ATTR_START_TIME;
import static com.dianping.cat.status.model.Constants.ATTR_SYSTEM_LOAD_AVERAGE;
import static com.dianping.cat.status.model.Constants.ATTR_TIME;
import static com.dianping.cat.status.model.Constants.ATTR_TIMESTAMP;
import static com.dianping.cat.status.model.Constants.ATTR_TOTAL;
import static com.dianping.cat.status.model.Constants.ATTR_TOTAL_PHYSICAL_MEMORY;
import static com.dianping.cat.status.model.Constants.ATTR_TOTAL_STARTED_COUNT;
import static com.dianping.cat.status.model.Constants.ATTR_TOTAL_SWAP_SPACE;
import static com.dianping.cat.status.model.Constants.ATTR_UP_TIME;
import static com.dianping.cat.status.model.Constants.ATTR_USABLE;
import static com.dianping.cat.status.model.Constants.ATTR_USER_NAME;
import static com.dianping.cat.status.model.Constants.ATTR_VERSION;

import static com.dianping.cat.status.model.Constants.ELEMENT_DUMP;
import static com.dianping.cat.status.model.Constants.ELEMENT_JAVA_CLASSPATH;
import static com.dianping.cat.status.model.Constants.ELEMENT_USER_DIR;

import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.status.model.entity.DiskInfo;
import com.dianping.cat.status.model.entity.DiskVolumeInfo;
import com.dianping.cat.status.model.entity.GcInfo;
import com.dianping.cat.status.model.entity.MemoryInfo;
import com.dianping.cat.status.model.entity.MessageInfo;
import com.dianping.cat.status.model.entity.OsInfo;
import com.dianping.cat.status.model.entity.RuntimeInfo;
import com.dianping.cat.status.model.entity.StatusInfo;
import com.dianping.cat.status.model.entity.ThreadsInfo;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public DiskInfo buildDisk(Node node) {
      DiskInfo disk = new DiskInfo();

      return disk;
   }

   @Override
   public DiskVolumeInfo buildDiskVolume(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String total = getAttribute(node, ATTR_TOTAL);
      String free = getAttribute(node, ATTR_FREE);
      String usable = getAttribute(node, ATTR_USABLE);

      DiskVolumeInfo diskVolume = new DiskVolumeInfo(id);

      if (total != null) {
         diskVolume.setTotal(convert(Long.class, total, 0L));
      }

      if (free != null) {
         diskVolume.setFree(convert(Long.class, free, 0L));
      }

      if (usable != null) {
         diskVolume.setUsable(convert(Long.class, usable, 0L));
      }

      return diskVolume;
   }

   @Override
   public GcInfo buildGc(Node node) {
      String name = getAttribute(node, ATTR_NAME);
      String count = getAttribute(node, ATTR_COUNT);
      String time = getAttribute(node, ATTR_TIME);

      GcInfo gc = new GcInfo();

      if (name != null) {
         gc.setName(name);
      }

      if (count != null) {
         gc.setCount(convert(Long.class, count, 0L));
      }

      if (time != null) {
         gc.setTime(convert(Long.class, time, 0L));
      }

      return gc;
   }

   @Override
   public MemoryInfo buildMemory(Node node) {
      String max = getAttribute(node, ATTR_MAX);
      String total = getAttribute(node, ATTR_TOTAL);
      String free = getAttribute(node, ATTR_FREE);
      String heapUsage = getAttribute(node, ATTR_HEAP_USAGE);
      String nonHeapUsage = getAttribute(node, ATTR_NON_HEAP_USAGE);

      MemoryInfo memory = new MemoryInfo();

      if (max != null) {
         memory.setMax(convert(Long.class, max, 0L));
      }

      if (total != null) {
         memory.setTotal(convert(Long.class, total, 0L));
      }

      if (free != null) {
         memory.setFree(convert(Long.class, free, 0L));
      }

      if (heapUsage != null) {
         memory.setHeapUsage(convert(Long.class, heapUsage, 0L));
      }

      if (nonHeapUsage != null) {
         memory.setNonHeapUsage(convert(Long.class, nonHeapUsage, 0L));
      }

      return memory;
   }

   @Override
   public MessageInfo buildMessage(Node node) {
      String produced = getAttribute(node, ATTR_PRODUCED);
      String overflowed = getAttribute(node, ATTR_OVERFLOWED);
      String bytes = getAttribute(node, ATTR_BYTES);

      MessageInfo message = new MessageInfo();

      if (produced != null) {
         message.setProduced(convert(Long.class, produced, 0L));
      }

      if (overflowed != null) {
         message.setOverflowed(convert(Long.class, overflowed, 0L));
      }

      if (bytes != null) {
         message.setBytes(convert(Long.class, bytes, 0L));
      }

      return message;
   }

   @Override
   public OsInfo buildOs(Node node) {
      String name = getAttribute(node, ATTR_NAME);
      String arch = getAttribute(node, ATTR_ARCH);
      String version = getAttribute(node, ATTR_VERSION);
      String availableProcessors = getAttribute(node, ATTR_AVAILABLE_PROCESSORS);
      String systemLoadAverage = getAttribute(node, ATTR_SYSTEM_LOAD_AVERAGE);
      String processTime = getAttribute(node, ATTR_PROCESS_TIME);
      String totalPhysicalMemory = getAttribute(node, ATTR_TOTAL_PHYSICAL_MEMORY);
      String freePhysicalMemory = getAttribute(node, ATTR_FREE_PHYSICAL_MEMORY);
      String committedVirtualMemory = getAttribute(node, ATTR_COMMITTED_VIRTUAL_MEMORY);
      String totalSwapSpace = getAttribute(node, ATTR_TOTAL_SWAP_SPACE);
      String freeSwapSpace = getAttribute(node, ATTR_FREE_SWAP_SPACE);

      OsInfo os = new OsInfo();

      if (name != null) {
         os.setName(name);
      }

      if (arch != null) {
         os.setArch(arch);
      }

      if (version != null) {
         os.setVersion(version);
      }

      if (availableProcessors != null) {
         os.setAvailableProcessors(convert(Integer.class, availableProcessors, 0));
      }

      if (systemLoadAverage != null) {
         os.setSystemLoadAverage(convert(Double.class, systemLoadAverage, 0.0));
      }

      if (processTime != null) {
         os.setProcessTime(convert(Long.class, processTime, 0L));
      }

      if (totalPhysicalMemory != null) {
         os.setTotalPhysicalMemory(convert(Long.class, totalPhysicalMemory, 0L));
      }

      if (freePhysicalMemory != null) {
         os.setFreePhysicalMemory(convert(Long.class, freePhysicalMemory, 0L));
      }

      if (committedVirtualMemory != null) {
         os.setCommittedVirtualMemory(convert(Long.class, committedVirtualMemory, 0L));
      }

      if (totalSwapSpace != null) {
         os.setTotalSwapSpace(convert(Long.class, totalSwapSpace, 0L));
      }

      if (freeSwapSpace != null) {
         os.setFreeSwapSpace(convert(Long.class, freeSwapSpace, 0L));
      }

      return os;
   }

   @Override
   public RuntimeInfo buildRuntime(Node node) {
      String startTime = getAttribute(node, ATTR_START_TIME);
      String upTime = getAttribute(node, ATTR_UP_TIME);
      String javaVersion = getAttribute(node, ATTR_JAVA_VERSION);
      String userName = getAttribute(node, ATTR_USER_NAME);
      String userDir = getText(getChildTagNode(node, ELEMENT_USER_DIR));
      String javaClasspath = getText(getChildTagNode(node, ELEMENT_JAVA_CLASSPATH));

      RuntimeInfo runtime = new RuntimeInfo();

      if (startTime != null) {
         runtime.setStartTime(convert(Long.class, startTime, 0L));
      }

      if (upTime != null) {
         runtime.setUpTime(convert(Long.class, upTime, 0L));
      }

      if (javaVersion != null) {
         runtime.setJavaVersion(javaVersion);
      }

      if (userName != null) {
         runtime.setUserName(userName);
      }

      if (userDir != null) {
         runtime.setUserDir(userDir);
      }

      if (javaClasspath != null) {
         runtime.setJavaClasspath(javaClasspath);
      }

      return runtime;
   }

   @Override
   public StatusInfo buildStatus(Node node) {
      String timestamp = getAttribute(node, ATTR_TIMESTAMP);

      StatusInfo status = new StatusInfo();

      if (timestamp != null) {
         status.setTimestamp(toDate(timestamp, "yyyy-MM-dd HH:mm:ss.SSS", null));
      }

      Map<String, String> dynamicAttributes = status.getDynamicAttributes();
      NamedNodeMap attributes = node.getAttributes();
      int length = attributes == null ? 0 : attributes.getLength();

      for (int i = 0; i < length; i++) {
         Node item = attributes.item(i);

         dynamicAttributes.put(item.getNodeName(), item.getNodeValue());
      }

      dynamicAttributes.remove(ATTR_TIMESTAMP);

      return status;
   }

   @Override
   public ThreadsInfo buildThread(Node node) {
      String count = getAttribute(node, ATTR_COUNT);
      String daemonCount = getAttribute(node, ATTR_DAEMON_COUNT);
      String peekCount = getAttribute(node, ATTR_PEEK_COUNT);
      String totalStartedCount = getAttribute(node, ATTR_TOTAL_STARTED_COUNT);
      String catThreadCount = getAttribute(node, ATTR_CAT_THREAD_COUNT);
      String pigeonThreadCount = getAttribute(node, ATTR_PIGEON_THREAD_COUNT);
      String httpThreadCount = getAttribute(node, ATTR_HTTP_THREAD_COUNT);
      String dump = getText(getChildTagNode(node, ELEMENT_DUMP));

      ThreadsInfo thread = new ThreadsInfo();

      if (count != null) {
         thread.setCount(convert(Integer.class, count, 0));
      }

      if (daemonCount != null) {
         thread.setDaemonCount(convert(Integer.class, daemonCount, 0));
      }

      if (peekCount != null) {
         thread.setPeekCount(convert(Integer.class, peekCount, 0));
      }

      if (totalStartedCount != null) {
         thread.setTotalStartedCount(convert(Integer.class, totalStartedCount, 0));
      }

      if (catThreadCount != null) {
         thread.setCatThreadCount(convert(Integer.class, catThreadCount, 0));
      }

      if (pigeonThreadCount != null) {
         thread.setPigeonThreadCount(convert(Integer.class, pigeonThreadCount, 0));
      }

      if (httpThreadCount != null) {
         thread.setHttpThreadCount(convert(Integer.class, httpThreadCount, 0));
      }

      if (dump != null) {
         thread.setDump(dump);
      }

      return thread;
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
}
