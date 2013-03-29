package com.dianping.cat.status.model.transform;

import com.dianping.cat.status.model.entity.DiskInfo;
import com.dianping.cat.status.model.entity.DiskVolumeInfo;
import com.dianping.cat.status.model.entity.GcInfo;
import com.dianping.cat.status.model.entity.MemoryInfo;
import com.dianping.cat.status.model.entity.MessageInfo;
import com.dianping.cat.status.model.entity.OsInfo;
import com.dianping.cat.status.model.entity.RuntimeInfo;
import com.dianping.cat.status.model.entity.StatusInfo;
import com.dianping.cat.status.model.entity.ThreadsInfo;

public interface ILinker {

   public boolean onDisk(StatusInfo parent, DiskInfo disk);

   public boolean onDiskVolume(DiskInfo parent, DiskVolumeInfo diskVolume);

   public boolean onGc(MemoryInfo parent, GcInfo gc);

   public boolean onMemory(StatusInfo parent, MemoryInfo memory);

   public boolean onMessage(StatusInfo parent, MessageInfo message);

   public boolean onOs(StatusInfo parent, OsInfo os);

   public boolean onRuntime(StatusInfo parent, RuntimeInfo runtime);

   public boolean onThread(StatusInfo parent, ThreadsInfo thread);
}
