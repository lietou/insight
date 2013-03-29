package com.dianping.cat.status.model.entity;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class GcInfo extends BaseEntity<GcInfo> {
   private String m_name;

   private long m_count;

   private long m_time;

   public GcInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitGc(this);
   }

   public long getCount() {
      return m_count;
   }

   public String getName() {
      return m_name;
   }

   public long getTime() {
      return m_time;
   }

   @Override
   public void mergeAttributes(GcInfo other) {
      if (other.getName() != null) {
         m_name = other.getName();
      }

      if (other.getCount() != 0) {
         m_count = other.getCount();
      }

      if (other.getTime() != 0) {
         m_time = other.getTime();
      }
   }

   public GcInfo setCount(long count) {
      m_count = count;
      return this;
   }

   public GcInfo setName(String name) {
      m_name = name;
      return this;
   }

   public GcInfo setTime(long time) {
      m_time = time;
      return this;
   }

}
