package com.dianping.cat.status.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.status.model.BaseEntity;
import com.dianping.cat.status.model.IVisitor;

public class DiskInfo extends BaseEntity<DiskInfo> {
   private List<DiskVolumeInfo> m_diskVolumes = new ArrayList<DiskVolumeInfo>();

   public DiskInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDisk(this);
   }

   public DiskInfo addDiskVolume(DiskVolumeInfo diskVolume) {
      m_diskVolumes.add(diskVolume);
      return this;
   }

   public DiskVolumeInfo findDiskVolume(String id) {
      for (DiskVolumeInfo diskVolume : m_diskVolumes) {
         if (!diskVolume.getId().equals(id)) {
            continue;
         }

         return diskVolume;
      }

      return null;
   }

   public List<DiskVolumeInfo> getDiskVolumes() {
      return m_diskVolumes;
   }

   @Override
   public void mergeAttributes(DiskInfo other) {
   }

   public boolean removeDiskVolume(String id) {
      int len = m_diskVolumes.size();

      for (int i = 0; i < len; i++) {
         DiskVolumeInfo diskVolume = m_diskVolumes.get(i);

         if (!diskVolume.getId().equals(id)) {
            continue;
         }

         m_diskVolumes.remove(i);
         return true;
      }

      return false;
   }

}
