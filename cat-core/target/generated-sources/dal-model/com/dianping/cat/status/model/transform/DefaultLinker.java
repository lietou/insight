package com.dianping.cat.status.model.transform;

import java.util.ArrayList;
import java.util.List;
import com.dianping.cat.status.model.entity.DiskInfo;
import com.dianping.cat.status.model.entity.DiskVolumeInfo;
import com.dianping.cat.status.model.entity.GcInfo;
import com.dianping.cat.status.model.entity.MemoryInfo;
import com.dianping.cat.status.model.entity.MessageInfo;
import com.dianping.cat.status.model.entity.OsInfo;
import com.dianping.cat.status.model.entity.RuntimeInfo;
import com.dianping.cat.status.model.entity.StatusInfo;
import com.dianping.cat.status.model.entity.ThreadsInfo;

public class DefaultLinker implements ILinker {
   @SuppressWarnings("unused")
   private boolean m_deferrable;

   private List<Runnable> m_deferedJobs = new ArrayList<Runnable>();

   public DefaultLinker(boolean deferrable) {
      m_deferrable = deferrable;
   }

   public void finish() {
      for (Runnable job : m_deferedJobs) {
         job.run();
      }
   }

   @Override
   public boolean onDisk(final StatusInfo parent, final DiskInfo disk) {
      parent.setDisk(disk);
      return true;
   }

   @Override
   public boolean onDiskVolume(final DiskInfo parent, final DiskVolumeInfo diskVolume) {
      parent.addDiskVolume(diskVolume);
      return true;
   }

   @Override
   public boolean onGc(final MemoryInfo parent, final GcInfo gc) {
      parent.addGc(gc);
      return true;
   }

   @Override
   public boolean onMemory(final StatusInfo parent, final MemoryInfo memory) {
      parent.setMemory(memory);
      return true;
   }

   @Override
   public boolean onMessage(final StatusInfo parent, final MessageInfo message) {
      parent.setMessage(message);
      return true;
   }

   @Override
   public boolean onOs(final StatusInfo parent, final OsInfo os) {
      parent.setOs(os);
      return true;
   }

   @Override
   public boolean onRuntime(final StatusInfo parent, final RuntimeInfo runtime) {
      parent.setRuntime(runtime);
      return true;
   }

   @Override
   public boolean onThread(final StatusInfo parent, final ThreadsInfo thread) {
      parent.setThread(thread);
      return true;
   }
}
