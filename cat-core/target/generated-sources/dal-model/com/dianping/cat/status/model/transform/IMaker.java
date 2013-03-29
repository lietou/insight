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

public interface IMaker<T> {

   public DiskInfo buildDisk(T node);

   public DiskVolumeInfo buildDiskVolume(T node);

   public GcInfo buildGc(T node);

   public MemoryInfo buildMemory(T node);

   public MessageInfo buildMessage(T node);

   public OsInfo buildOs(T node);

   public RuntimeInfo buildRuntime(T node);

   public StatusInfo buildStatus(T node);

   public ThreadsInfo buildThread(T node);
}
