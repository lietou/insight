package com.dianping.cat.consumer.heartbeat.model.entity;

import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_MINUTE;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_PERIOD;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.consumer.heartbeat.model.BaseEntity;
import com.dianping.cat.consumer.heartbeat.model.IVisitor;

public class Period extends BaseEntity<Period> {
   private int m_minute;

   private int m_threadCount;

   private int m_daemonCount;

   private int m_totalStartedCount;

   private int m_catThreadCount;

   private int m_pigeonThreadCount;

   private int m_httpThreadCount;

   private long m_newGcCount;

   private long m_oldGcCount;

   private long m_memoryFree;

   private long m_heapUsage;

   private long m_noneHeapUsage;

   private double m_systemLoadAverage;

   private long m_catMessageProduced;

   private long m_catMessageOverflow;

   private double m_catMessageSize;

   private List<Disk> m_disks = new ArrayList<Disk>();

   public Period() {
   }

   public Period(int minute) {
      m_minute = minute;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitPeriod(this);
   }

   public Period addDisk(Disk disk) {
      m_disks.add(disk);
      return this;
   }

   public Disk findDisk(String path) {
      for (Disk disk : m_disks) {
         if (!disk.getPath().equals(path)) {
            continue;
         }

         return disk;
      }

      return null;
   }

   public long getCatMessageOverflow() {
      return m_catMessageOverflow;
   }

   public long getCatMessageProduced() {
      return m_catMessageProduced;
   }

   public double getCatMessageSize() {
      return m_catMessageSize;
   }

   public int getCatThreadCount() {
      return m_catThreadCount;
   }

   public int getDaemonCount() {
      return m_daemonCount;
   }

   public List<Disk> getDisks() {
      return m_disks;
   }

   public long getHeapUsage() {
      return m_heapUsage;
   }

   public int getHttpThreadCount() {
      return m_httpThreadCount;
   }

   public long getMemoryFree() {
      return m_memoryFree;
   }

   public int getMinute() {
      return m_minute;
   }

   public long getNewGcCount() {
      return m_newGcCount;
   }

   public long getNoneHeapUsage() {
      return m_noneHeapUsage;
   }

   public long getOldGcCount() {
      return m_oldGcCount;
   }

   public int getPigeonThreadCount() {
      return m_pigeonThreadCount;
   }

   public double getSystemLoadAverage() {
      return m_systemLoadAverage;
   }

   public int getThreadCount() {
      return m_threadCount;
   }

   public int getTotalStartedCount() {
      return m_totalStartedCount;
   }

   @Override
   public void mergeAttributes(Period other) {
      assertAttributeEquals(other, ENTITY_PERIOD, ATTR_MINUTE, m_minute, other.getMinute());

      if (other.getThreadCount() != 0) {
         m_threadCount = other.getThreadCount();
      }

      if (other.getDaemonCount() != 0) {
         m_daemonCount = other.getDaemonCount();
      }

      if (other.getTotalStartedCount() != 0) {
         m_totalStartedCount = other.getTotalStartedCount();
      }

      if (other.getCatThreadCount() != 0) {
         m_catThreadCount = other.getCatThreadCount();
      }

      if (other.getPigeonThreadCount() != 0) {
         m_pigeonThreadCount = other.getPigeonThreadCount();
      }

      if (other.getHttpThreadCount() != 0) {
         m_httpThreadCount = other.getHttpThreadCount();
      }

      if (other.getNewGcCount() != 0) {
         m_newGcCount = other.getNewGcCount();
      }

      if (other.getOldGcCount() != 0) {
         m_oldGcCount = other.getOldGcCount();
      }

      if (other.getMemoryFree() != 0) {
         m_memoryFree = other.getMemoryFree();
      }

      if (other.getHeapUsage() != 0) {
         m_heapUsage = other.getHeapUsage();
      }

      if (other.getNoneHeapUsage() != 0) {
         m_noneHeapUsage = other.getNoneHeapUsage();
      }

      if (other.getSystemLoadAverage() - 1e6 < 0) {
         m_systemLoadAverage = other.getSystemLoadAverage();
      }

      if (other.getCatMessageProduced() != 0) {
         m_catMessageProduced = other.getCatMessageProduced();
      }

      if (other.getCatMessageOverflow() != 0) {
         m_catMessageOverflow = other.getCatMessageOverflow();
      }

      if (other.getCatMessageSize() - 1e6 < 0) {
         m_catMessageSize = other.getCatMessageSize();
      }
   }

   public boolean removeDisk(String path) {
      int len = m_disks.size();

      for (int i = 0; i < len; i++) {
         Disk disk = m_disks.get(i);

         if (!disk.getPath().equals(path)) {
            continue;
         }

         m_disks.remove(i);
         return true;
      }

      return false;
   }

   public Period setCatMessageOverflow(long catMessageOverflow) {
      m_catMessageOverflow = catMessageOverflow;
      return this;
   }

   public Period setCatMessageProduced(long catMessageProduced) {
      m_catMessageProduced = catMessageProduced;
      return this;
   }

   public Period setCatMessageSize(double catMessageSize) {
      m_catMessageSize = catMessageSize;
      return this;
   }

   public Period setCatThreadCount(int catThreadCount) {
      m_catThreadCount = catThreadCount;
      return this;
   }

   public Period setDaemonCount(int daemonCount) {
      m_daemonCount = daemonCount;
      return this;
   }

   public Period setHeapUsage(long heapUsage) {
      m_heapUsage = heapUsage;
      return this;
   }

   public Period setHttpThreadCount(int httpThreadCount) {
      m_httpThreadCount = httpThreadCount;
      return this;
   }

   public Period setMemoryFree(long memoryFree) {
      m_memoryFree = memoryFree;
      return this;
   }

   public Period setMinute(int minute) {
      m_minute = minute;
      return this;
   }

   public Period setNewGcCount(long newGcCount) {
      m_newGcCount = newGcCount;
      return this;
   }

   public Period setNoneHeapUsage(long noneHeapUsage) {
      m_noneHeapUsage = noneHeapUsage;
      return this;
   }

   public Period setOldGcCount(long oldGcCount) {
      m_oldGcCount = oldGcCount;
      return this;
   }

   public Period setPigeonThreadCount(int pigeonThreadCount) {
      m_pigeonThreadCount = pigeonThreadCount;
      return this;
   }

   public Period setSystemLoadAverage(double systemLoadAverage) {
      m_systemLoadAverage = systemLoadAverage;
      return this;
   }

   public Period setThreadCount(int threadCount) {
      m_threadCount = threadCount;
      return this;
   }

   public Period setTotalStartedCount(int totalStartedCount) {
      m_totalStartedCount = totalStartedCount;
      return this;
   }

}
