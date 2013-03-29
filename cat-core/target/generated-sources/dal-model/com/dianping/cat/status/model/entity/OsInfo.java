package com.dianping.cat.status.model.entity;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class OsInfo extends BaseEntity<OsInfo> {
   private String m_name;

   private String m_arch;

   private String m_version;

   private int m_availableProcessors;

   private double m_systemLoadAverage;

   private long m_processTime;

   private long m_totalPhysicalMemory;

   private long m_freePhysicalMemory;

   private long m_committedVirtualMemory;

   private long m_totalSwapSpace;

   private long m_freeSwapSpace;

   public OsInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitOs(this);
   }

   public String getArch() {
      return m_arch;
   }

   public int getAvailableProcessors() {
      return m_availableProcessors;
   }

   public long getCommittedVirtualMemory() {
      return m_committedVirtualMemory;
   }

   public long getFreePhysicalMemory() {
      return m_freePhysicalMemory;
   }

   public long getFreeSwapSpace() {
      return m_freeSwapSpace;
   }

   public String getName() {
      return m_name;
   }

   public long getProcessTime() {
      return m_processTime;
   }

   public double getSystemLoadAverage() {
      return m_systemLoadAverage;
   }

   public long getTotalPhysicalMemory() {
      return m_totalPhysicalMemory;
   }

   public long getTotalSwapSpace() {
      return m_totalSwapSpace;
   }

   public String getVersion() {
      return m_version;
   }

   @Override
   public void mergeAttributes(OsInfo other) {
      if (other.getName() != null) {
         m_name = other.getName();
      }

      if (other.getArch() != null) {
         m_arch = other.getArch();
      }

      if (other.getVersion() != null) {
         m_version = other.getVersion();
      }

      if (other.getAvailableProcessors() != 0) {
         m_availableProcessors = other.getAvailableProcessors();
      }

      if (other.getSystemLoadAverage() - 1e6 < 0) {
         m_systemLoadAverage = other.getSystemLoadAverage();
      }

      if (other.getProcessTime() != 0) {
         m_processTime = other.getProcessTime();
      }

      if (other.getTotalPhysicalMemory() != 0) {
         m_totalPhysicalMemory = other.getTotalPhysicalMemory();
      }

      if (other.getFreePhysicalMemory() != 0) {
         m_freePhysicalMemory = other.getFreePhysicalMemory();
      }

      if (other.getCommittedVirtualMemory() != 0) {
         m_committedVirtualMemory = other.getCommittedVirtualMemory();
      }

      if (other.getTotalSwapSpace() != 0) {
         m_totalSwapSpace = other.getTotalSwapSpace();
      }

      if (other.getFreeSwapSpace() != 0) {
         m_freeSwapSpace = other.getFreeSwapSpace();
      }
   }

   public OsInfo setArch(String arch) {
      m_arch = arch;
      return this;
   }

   public OsInfo setAvailableProcessors(int availableProcessors) {
      m_availableProcessors = availableProcessors;
      return this;
   }

   public OsInfo setCommittedVirtualMemory(long committedVirtualMemory) {
      m_committedVirtualMemory = committedVirtualMemory;
      return this;
   }

   public OsInfo setFreePhysicalMemory(long freePhysicalMemory) {
      m_freePhysicalMemory = freePhysicalMemory;
      return this;
   }

   public OsInfo setFreeSwapSpace(long freeSwapSpace) {
      m_freeSwapSpace = freeSwapSpace;
      return this;
   }

   public OsInfo setName(String name) {
      m_name = name;
      return this;
   }

   public OsInfo setProcessTime(long processTime) {
      m_processTime = processTime;
      return this;
   }

   public OsInfo setSystemLoadAverage(double systemLoadAverage) {
      m_systemLoadAverage = systemLoadAverage;
      return this;
   }

   public OsInfo setTotalPhysicalMemory(long totalPhysicalMemory) {
      m_totalPhysicalMemory = totalPhysicalMemory;
      return this;
   }

   public OsInfo setTotalSwapSpace(long totalSwapSpace) {
      m_totalSwapSpace = totalSwapSpace;
      return this;
   }

   public OsInfo setVersion(String version) {
      m_version = version;
      return this;
   }

}
