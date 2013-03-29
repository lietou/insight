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
import static com.dianping.cat.status.model.Constants.ENTITY_DISK;
import static com.dianping.cat.status.model.Constants.ENTITY_DISK_VOLUME;
import static com.dianping.cat.status.model.Constants.ENTITY_GC;
import static com.dianping.cat.status.model.Constants.ENTITY_MEMORY;
import static com.dianping.cat.status.model.Constants.ENTITY_MESSAGE;
import static com.dianping.cat.status.model.Constants.ENTITY_OS;
import static com.dianping.cat.status.model.Constants.ENTITY_RUNTIME;
import static com.dianping.cat.status.model.Constants.ENTITY_STATUS;
import static com.dianping.cat.status.model.Constants.ENTITY_THREAD;

import com.dianping.cat.status.model.IEntity;
import com.dianping.cat.status.model.IVisitor;
import com.dianping.cat.status.model.entity.DiskInfo;
import com.dianping.cat.status.model.entity.DiskVolumeInfo;
import com.dianping.cat.status.model.entity.GcInfo;
import com.dianping.cat.status.model.entity.MemoryInfo;
import com.dianping.cat.status.model.entity.MessageInfo;
import com.dianping.cat.status.model.entity.OsInfo;
import com.dianping.cat.status.model.entity.RuntimeInfo;
import com.dianping.cat.status.model.entity.StatusInfo;
import com.dianping.cat.status.model.entity.ThreadsInfo;

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

   @Override
   public void visitDisk(DiskInfo disk) {
      startTag(ENTITY_DISK, null);

      if (!disk.getDiskVolumes().isEmpty()) {
         for (DiskVolumeInfo diskVolume : disk.getDiskVolumes().toArray(new DiskVolumeInfo[0])) {
            visitDiskVolume(diskVolume);
         }
      }

      endTag(ENTITY_DISK);
   }

   @Override
   public void visitDiskVolume(DiskVolumeInfo diskVolume) {
      startTag(ENTITY_DISK_VOLUME, true, null, ATTR_ID, diskVolume.getId(), ATTR_TOTAL, diskVolume.getTotal(), ATTR_FREE, diskVolume.getFree(), ATTR_USABLE, diskVolume.getUsable());
   }

   @Override
   public void visitGc(GcInfo gc) {
      startTag(ENTITY_GC, true, null, ATTR_NAME, gc.getName(), ATTR_COUNT, gc.getCount(), ATTR_TIME, gc.getTime());
   }

   @Override
   public void visitMemory(MemoryInfo memory) {
      startTag(ENTITY_MEMORY, null, ATTR_MAX, memory.getMax(), ATTR_TOTAL, memory.getTotal(), ATTR_FREE, memory.getFree(), ATTR_HEAP_USAGE, memory.getHeapUsage(), ATTR_NON_HEAP_USAGE, memory.getNonHeapUsage());

      if (!memory.getGcs().isEmpty()) {
         for (GcInfo gc : memory.getGcs().toArray(new GcInfo[0])) {
            visitGc(gc);
         }
      }

      endTag(ENTITY_MEMORY);
   }

   @Override
   public void visitMessage(MessageInfo message) {
      startTag(ENTITY_MESSAGE, true, null, ATTR_PRODUCED, message.getProduced(), ATTR_OVERFLOWED, message.getOverflowed(), ATTR_BYTES, message.getBytes());
   }

   @Override
   public void visitOs(OsInfo os) {
      startTag(ENTITY_OS, true, null, ATTR_NAME, os.getName(), ATTR_ARCH, os.getArch(), ATTR_VERSION, os.getVersion(), ATTR_AVAILABLE_PROCESSORS, os.getAvailableProcessors(), ATTR_SYSTEM_LOAD_AVERAGE, os.getSystemLoadAverage(), ATTR_PROCESS_TIME, os.getProcessTime(), ATTR_TOTAL_PHYSICAL_MEMORY, os.getTotalPhysicalMemory(), ATTR_FREE_PHYSICAL_MEMORY, os.getFreePhysicalMemory(), ATTR_COMMITTED_VIRTUAL_MEMORY, os.getCommittedVirtualMemory(), ATTR_TOTAL_SWAP_SPACE, os.getTotalSwapSpace(), ATTR_FREE_SWAP_SPACE, os.getFreeSwapSpace());
   }

   @Override
   public void visitRuntime(RuntimeInfo runtime) {
      startTag(ENTITY_RUNTIME, null, ATTR_START_TIME, runtime.getStartTime(), ATTR_UP_TIME, runtime.getUpTime(), ATTR_JAVA_VERSION, runtime.getJavaVersion(), ATTR_USER_NAME, runtime.getUserName());

      tagWithText(ELEMENT_USER_DIR, runtime.getUserDir());

      tagWithText(ELEMENT_JAVA_CLASSPATH, runtime.getJavaClasspath());

      endTag(ENTITY_RUNTIME);
   }

   @Override
   public void visitStatus(StatusInfo status) {
      startTag(ENTITY_STATUS, status.getDynamicAttributes(), ATTR_TIMESTAMP, toString(status.getTimestamp(), "yyyy-MM-dd HH:mm:ss.SSS"));

      if (status.getRuntime() != null) {
         visitRuntime(status.getRuntime());
      }

      if (status.getOs() != null) {
         visitOs(status.getOs());
      }

      if (status.getDisk() != null) {
         visitDisk(status.getDisk());
      }

      if (status.getMemory() != null) {
         visitMemory(status.getMemory());
      }

      if (status.getThread() != null) {
         visitThread(status.getThread());
      }

      if (status.getMessage() != null) {
         visitMessage(status.getMessage());
      }

      endTag(ENTITY_STATUS);
   }

   @Override
   public void visitThread(ThreadsInfo thread) {
      startTag(ENTITY_THREAD, null, ATTR_COUNT, thread.getCount(), ATTR_DAEMON_COUNT, thread.getDaemonCount(), ATTR_PEEK_COUNT, thread.getPeekCount(), ATTR_TOTAL_STARTED_COUNT, thread.getTotalStartedCount(), ATTR_CAT_THREAD_COUNT, thread.getCatThreadCount(), ATTR_PIGEON_THREAD_COUNT, thread.getPigeonThreadCount(), ATTR_HTTP_THREAD_COUNT, thread.getHttpThreadCount());

      tagWithText(ELEMENT_DUMP, thread.getDump());

      endTag(ENTITY_THREAD);
   }
}
