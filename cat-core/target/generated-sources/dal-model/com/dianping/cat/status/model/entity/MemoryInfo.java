package com.dianping.cat.status.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class MemoryInfo extends BaseEntity<MemoryInfo> {
   private long m_max;

   private long m_total;

   private long m_free;

   private long m_heapUsage;

   private long m_nonHeapUsage;

   private List<GcInfo> m_gcs = new ArrayList<GcInfo>();

   public MemoryInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMemory(this);
   }

   public MemoryInfo addGc(GcInfo gc) {
      m_gcs.add(gc);
      return this;
   }

   public long getFree() {
      return m_free;
   }

   public List<GcInfo> getGcs() {
      return m_gcs;
   }

   public long getHeapUsage() {
      return m_heapUsage;
   }

   public long getMax() {
      return m_max;
   }

   public long getNonHeapUsage() {
      return m_nonHeapUsage;
   }

   public long getTotal() {
      return m_total;
   }

   @Override
   public void mergeAttributes(MemoryInfo other) {
      if (other.getMax() != 0) {
         m_max = other.getMax();
      }

      if (other.getTotal() != 0) {
         m_total = other.getTotal();
      }

      if (other.getFree() != 0) {
         m_free = other.getFree();
      }

      if (other.getHeapUsage() != 0) {
         m_heapUsage = other.getHeapUsage();
      }

      if (other.getNonHeapUsage() != 0) {
         m_nonHeapUsage = other.getNonHeapUsage();
      }
   }

   public MemoryInfo setFree(long free) {
      m_free = free;
      return this;
   }

   public MemoryInfo setHeapUsage(long heapUsage) {
      m_heapUsage = heapUsage;
      return this;
   }

   public MemoryInfo setMax(long max) {
      m_max = max;
      return this;
   }

   public MemoryInfo setNonHeapUsage(long nonHeapUsage) {
      m_nonHeapUsage = nonHeapUsage;
      return this;
   }

   public MemoryInfo setTotal(long total) {
      m_total = total;
      return this;
   }

}
