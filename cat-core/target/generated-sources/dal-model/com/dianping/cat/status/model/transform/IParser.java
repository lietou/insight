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

public interface IParser<T> {
   public StatusInfo parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForDiskInfo(IMaker<T> maker, ILinker linker, DiskInfo parent, T node);

   public void parseForDiskVolumeInfo(IMaker<T> maker, ILinker linker, DiskVolumeInfo parent, T node);

   public void parseForGcInfo(IMaker<T> maker, ILinker linker, GcInfo parent, T node);

   public void parseForMemoryInfo(IMaker<T> maker, ILinker linker, MemoryInfo parent, T node);

   public void parseForMessageInfo(IMaker<T> maker, ILinker linker, MessageInfo parent, T node);

   public void parseForOsInfo(IMaker<T> maker, ILinker linker, OsInfo parent, T node);

   public void parseForRuntimeInfo(IMaker<T> maker, ILinker linker, RuntimeInfo parent, T node);

   public void parseForThreadsInfo(IMaker<T> maker, ILinker linker, ThreadsInfo parent, T node);
}
