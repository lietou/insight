package com.dianping.cat.status.model.entity;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class ThreadsInfo extends BaseEntity<ThreadsInfo> {
   private int m_count;

   private int m_daemonCount;

   private int m_peekCount;

   private int m_totalStartedCount;

   private int m_catThreadCount;

   private int m_pigeonThreadCount;

   private int m_httpThreadCount;

   private String m_dump;

   public ThreadsInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitThread(this);
   }

   public int getCatThreadCount() {
      return m_catThreadCount;
   }

   public int getCount() {
      return m_count;
   }

   public int getDaemonCount() {
      return m_daemonCount;
   }

   public String getDump() {
      return m_dump;
   }

   public int getHttpThreadCount() {
      return m_httpThreadCount;
   }

   public int getPeekCount() {
      return m_peekCount;
   }

   public int getPigeonThreadCount() {
      return m_pigeonThreadCount;
   }

   public int getTotalStartedCount() {
      return m_totalStartedCount;
   }

   @Override
   public void mergeAttributes(ThreadsInfo other) {
      if (other.getCount() != 0) {
         m_count = other.getCount();
      }

      if (other.getDaemonCount() != 0) {
         m_daemonCount = other.getDaemonCount();
      }

      if (other.getPeekCount() != 0) {
         m_peekCount = other.getPeekCount();
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
   }

   public ThreadsInfo setCatThreadCount(int catThreadCount) {
      m_catThreadCount = catThreadCount;
      return this;
   }

   public ThreadsInfo setCount(int count) {
      m_count = count;
      return this;
   }

   public ThreadsInfo setDaemonCount(int daemonCount) {
      m_daemonCount = daemonCount;
      return this;
   }

   public ThreadsInfo setDump(String dump) {
      m_dump = dump;
      return this;
   }

   public ThreadsInfo setHttpThreadCount(int httpThreadCount) {
      m_httpThreadCount = httpThreadCount;
      return this;
   }

   public ThreadsInfo setPeekCount(int peekCount) {
      m_peekCount = peekCount;
      return this;
   }

   public ThreadsInfo setPigeonThreadCount(int pigeonThreadCount) {
      m_pigeonThreadCount = pigeonThreadCount;
      return this;
   }

   public ThreadsInfo setTotalStartedCount(int totalStartedCount) {
      m_totalStartedCount = totalStartedCount;
      return this;
   }

}
