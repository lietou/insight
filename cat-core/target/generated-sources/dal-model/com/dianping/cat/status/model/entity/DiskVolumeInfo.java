package com.dianping.cat.status.model.entity;

import static com.dianping.cat.status.model.Constants.ATTR_ID;
import static com.dianping.cat.status.model.Constants.ENTITY_DISK_VOLUME;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class DiskVolumeInfo extends BaseEntity<DiskVolumeInfo> {
   private String m_id;

   private long m_total;

   private long m_free;

   private long m_usable;

   public DiskVolumeInfo() {
   }

   public DiskVolumeInfo(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDiskVolume(this);
   }

   public long getFree() {
      return m_free;
   }

   public String getId() {
      return m_id;
   }

   public long getTotal() {
      return m_total;
   }

   public long getUsable() {
      return m_usable;
   }

   @Override
   public void mergeAttributes(DiskVolumeInfo other) {
      assertAttributeEquals(other, ENTITY_DISK_VOLUME, ATTR_ID, m_id, other.getId());

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

   public DiskVolumeInfo setFree(long free) {
      m_free = free;
      return this;
   }

   public DiskVolumeInfo setId(String id) {
      m_id = id;
      return this;
   }

   public DiskVolumeInfo setTotal(long total) {
      m_total = total;
      return this;
   }

   public DiskVolumeInfo setUsable(long usable) {
      m_usable = usable;
      return this;
   }

}
