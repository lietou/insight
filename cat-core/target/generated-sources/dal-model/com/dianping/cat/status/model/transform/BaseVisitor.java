package com.dianping.cat.status.model.transform;

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

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitDisk(DiskInfo disk) {
      for (DiskVolumeInfo diskVolume : disk.getDiskVolumes()) {
         visitDiskVolume(diskVolume);
      }
   }

   @Override
   public void visitDiskVolume(DiskVolumeInfo diskVolume) {
   }

   @Override
   public void visitGc(GcInfo gc) {
   }

   @Override
   public void visitMemory(MemoryInfo memory) {
      for (GcInfo gc : memory.getGcs()) {
         visitGc(gc);
      }
   }

   @Override
   public void visitMessage(MessageInfo message) {
   }

   @Override
   public void visitOs(OsInfo os) {
   }

   @Override
   public void visitRuntime(RuntimeInfo runtime) {
   }

   @Override
   public void visitStatus(StatusInfo status) {
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
   }

   @Override
   public void visitThread(ThreadsInfo thread) {
   }
}
