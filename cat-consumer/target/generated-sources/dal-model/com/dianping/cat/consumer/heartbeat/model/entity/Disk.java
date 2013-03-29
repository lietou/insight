package com.dianping.cat.consumer.heartbeat.model.entity;

import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_PATH;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_DISK;

import com.dianping.cat.consumer.heartbeat.model.BaseEntity;
import com.dianping.cat.consumer.heartbeat.model.IVisitor;

public class Disk extends BaseEntity<Disk> {
   private String m_path;

   private long m_total;

   private long m_free;

   private long m_usable;

   public Disk() {
   }

   public Disk(String path) {
      m_path = path;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDisk(this);
   }

   public long getFree() {
      return m_free;
   }

   public String getPath() {
      return m_path;
   }

   public long getTotal() {
      return m_total;
   }

   public long getUsable() {
      return m_usable;
   }

   @Override
   public void mergeAttributes(Disk other) {
      assertAttributeEquals(other, ENTITY_DISK, ATTR_PATH, m_path, other.getPath());

      if (other.getTotal() != 0) {
         m_total = other.getTotal();
      }

      if (other.getFree() != 0) {
         m_free = other.getFree();
      }

      if (other.getUsable() != 0) {
         m_usable = other.getUsable();
      }
   }

   public Disk setFree(long free) {
      m_free = free;
      return this;
   }

   public Disk setPath(String path) {
      m_path = path;
      return this;
   }

   public Disk setTotal(long total) {
      m_total = total;
      return this;
   }

   public Disk setUsable(long usable) {
      m_usable = usable;
      return this;
   }

}
