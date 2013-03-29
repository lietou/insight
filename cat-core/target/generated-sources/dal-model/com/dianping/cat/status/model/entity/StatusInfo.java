package com.dianping.cat.status.model.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class StatusInfo extends BaseEntity<StatusInfo> {
   private java.util.Date m_timestamp;

   private RuntimeInfo m_runtime;

   private OsInfo m_os;

   private DiskInfo m_disk;

   private MemoryInfo m_memory;

   private ThreadsInfo m_thread;

   private MessageInfo m_message;

   private Map<String, String> m_dynamicAttributes = new LinkedHashMap<String, String>();

   public StatusInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitStatus(this);
   }

   public String getDynamicAttribute(String name) {
      return m_dynamicAttributes.get(name);
   }

   public Map<String, String> getDynamicAttributes() {
      return m_dynamicAttributes;
   }

   public DiskInfo getDisk() {
      return m_disk;
   }

   public MemoryInfo getMemory() {
      return m_memory;
   }

   public MessageInfo getMessage() {
      return m_message;
   }

   public OsInfo getOs() {
      return m_os;
   }

   public RuntimeInfo getRuntime() {
      return m_runtime;
   }

   public ThreadsInfo getThread() {
      return m_thread;
   }

   public java.util.Date getTimestamp() {
      return m_timestamp;
   }

   @Override
   public void mergeAttributes(StatusInfo other) {
      for (Map.Entry<String, String> e : other.getDynamicAttributes().entrySet()) {
         m_dynamicAttributes.put(e.getKey(), e.getValue());
      }

      if (other.getTimestamp() != null) {
         m_timestamp = other.getTimestamp();
      }
   }

   public void setDynamicAttribute(String name, String value) {
      m_dynamicAttributes.put(name, value);
   }

   public StatusInfo setDisk(DiskInfo disk) {
      m_disk = disk;
      return this;
   }

   public StatusInfo setMemory(MemoryInfo memory) {
      m_memory = memory;
      return this;
   }

   public StatusInfo setMessage(MessageInfo message) {
      m_message = message;
      return this;
   }

   public StatusInfo setOs(OsInfo os) {
      m_os = os;
      return this;
   }

   public StatusInfo setRuntime(RuntimeInfo runtime) {
      m_runtime = runtime;
      return this;
   }

   public StatusInfo setThread(ThreadsInfo thread) {
      m_thread = thread;
      return this;
   }

   public StatusInfo setTimestamp(java.util.Date timestamp) {
      m_timestamp = timestamp;
      return this;
   }

}
