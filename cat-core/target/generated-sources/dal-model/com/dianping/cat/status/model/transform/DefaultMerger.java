package com.dianping.cat.status.model.transform;

import static com.dianping.cat.status.model.Constants.ENTITY_DISK;
import static com.dianping.cat.status.model.Constants.ENTITY_DISK_VOLUME;
import static com.dianping.cat.status.model.Constants.ENTITY_GC;
import static com.dianping.cat.status.model.Constants.ENTITY_MEMORY;
import static com.dianping.cat.status.model.Constants.ENTITY_MESSAGE;
import static com.dianping.cat.status.model.Constants.ENTITY_OS;
import static com.dianping.cat.status.model.Constants.ENTITY_RUNTIME;
import static com.dianping.cat.status.model.Constants.ENTITY_THREAD;
import java.util.Stack;

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

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private StatusInfo m_status;

   public DefaultMerger(StatusInfo status) {
      m_status = status;
   }

   public StatusInfo getStatus() {
      return m_status;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeDisk(DiskInfo old, DiskInfo disk) {
      old.mergeAttributes(disk);
   }

   protected void mergeDiskVolume(DiskVolumeInfo old, DiskVolumeInfo diskVolume) {
      old.mergeAttributes(diskVolume);
   }

   protected void mergeGc(GcInfo old, GcInfo gc) {
      old.mergeAttributes(gc);
   }

   protected void mergeMemory(MemoryInfo old, MemoryInfo memory) {
      old.mergeAttributes(memory);
   }

   protected void mergeMessage(MessageInfo old, MessageInfo message) {
      old.mergeAttributes(message);
   }

   protected void mergeOs(OsInfo old, OsInfo os) {
      old.mergeAttributes(os);
   }

   protected void mergeRuntime(RuntimeInfo old, RuntimeInfo runtime) {
      old.mergeAttributes(runtime);
   }

   protected void mergeStatus(StatusInfo old, StatusInfo status) {
      old.mergeAttributes(status);
   }

   protected void mergeThread(ThreadsInfo old, ThreadsInfo thread) {
      old.mergeAttributes(thread);
   }

   @Override
   public void visitDisk(DiskInfo disk) {
      Object parent = m_objs.peek();
      DiskInfo old = null;

      if (parent instanceof StatusInfo) {
         StatusInfo status = (StatusInfo) parent;

         old = status.getDisk();

         if (old == null) {
            old = new DiskInfo();
            status.setDisk(old);
         }

         mergeDisk(old, disk);
      }

      visitDiskChildren(old, disk);
   }

   protected void visitDiskChildren(DiskInfo old, DiskInfo disk) {
      if (old != null) {
         m_objs.push(old);

         for (DiskVolumeInfo diskVolume : disk.getDiskVolumes()) {
            m_tags.push(ENTITY_DISK_VOLUME);
            visitDiskVolume(diskVolume);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitDiskVolume(DiskVolumeInfo diskVolume) {
      Object parent = m_objs.peek();
      DiskVolumeInfo old = null;

      if (parent instanceof DiskInfo) {
         DiskInfo disk = (DiskInfo) parent;

         old = disk.findDiskVolume(diskVolume.getId());

         if (old == null) {
            old = new DiskVolumeInfo(diskVolume.getId());
            disk.addDiskVolume(old);
         }

         mergeDiskVolume(old, diskVolume);
      }

      visitDiskVolumeChildren(old, diskVolume);
   }

   protected void visitDiskVolumeChildren(DiskVolumeInfo old, DiskVolumeInfo diskVolume) {
   }

   @Override
   public void visitGc(GcInfo gc) {
      Object parent = m_objs.peek();
      GcInfo old = null;

      if (parent instanceof MemoryInfo) {
         MemoryInfo memory = (MemoryInfo) parent;

         memory.addGc(gc);
      }

      visitGcChildren(old, gc);
   }

   protected void visitGcChildren(GcInfo old, GcInfo gc) {
   }

   @Override
   public void visitMemory(MemoryInfo memory) {
      Object parent = m_objs.peek();
      MemoryInfo old = null;

      if (parent instanceof StatusInfo) {
         StatusInfo status = (StatusInfo) parent;

         old = status.getMemory();

         if (old == null) {
            old = new MemoryInfo();
            status.setMemory(old);
         }

         mergeMemory(old, memory);
      }

      visitMemoryChildren(old, memory);
   }

   protected void visitMemoryChildren(MemoryInfo old, MemoryInfo memory) {
      if (old != null) {
         m_objs.push(old);

         for (GcInfo gc : memory.getGcs()) {
            m_tags.push(ENTITY_GC);
            visitGc(gc);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitMessage(MessageInfo message) {
      Object parent = m_objs.peek();
      MessageInfo old = null;

      if (parent instanceof StatusInfo) {
         StatusInfo status = (StatusInfo) parent;

         old = status.getMessage();

         if (old == null) {
            old = new MessageInfo();
            status.setMessage(old);
         }

         mergeMessage(old, message);
      }

      visitMessageChildren(old, message);
   }

   protected void visitMessageChildren(MessageInfo old, MessageInfo message) {
   }

   @Override
   public void visitOs(OsInfo os) {
      Object parent = m_objs.peek();
      OsInfo old = null;

      if (parent instanceof StatusInfo) {
         StatusInfo status = (StatusInfo) parent;

         old = status.getOs();

         if (old == null) {
            old = new OsInfo();
            status.setOs(old);
         }

         mergeOs(old, os);
      }

      visitOsChildren(old, os);
   }

   protected void visitOsChildren(OsInfo old, OsInfo os) {
   }

   @Override
   public void visitRuntime(RuntimeInfo runtime) {
      Object parent = m_objs.peek();
      RuntimeInfo old = null;

      if (parent instanceof StatusInfo) {
         StatusInfo status = (StatusInfo) parent;

         old = status.getRuntime();

         if (old == null) {
            old = new RuntimeInfo();
            status.setRuntime(old);
         }

         mergeRuntime(old, runtime);
      }

      visitRuntimeChildren(old, runtime);
   }

   protected void visitRuntimeChildren(RuntimeInfo old, RuntimeInfo runtime) {
   }

   @Override
   public void visitStatus(StatusInfo status) {
      m_status.mergeAttributes(status);
      visitStatusChildren(m_status, status);
   }

   protected void visitStatusChildren(StatusInfo old, StatusInfo status) {
      if (old != null) {
         m_objs.push(old);

         if (status.getRuntime() != null) {
            m_tags.push(ENTITY_RUNTIME);
            visitRuntime(status.getRuntime());
            m_tags.pop();
         }

         if (status.getOs() != null) {
            m_tags.push(ENTITY_OS);
            visitOs(status.getOs());
            m_tags.pop();
         }

         if (status.getDisk() != null) {
            m_tags.push(ENTITY_DISK);
            visitDisk(status.getDisk());
            m_tags.pop();
         }

         if (status.getMemory() != null) {
            m_tags.push(ENTITY_MEMORY);
            visitMemory(status.getMemory());
            m_tags.pop();
         }

         if (status.getThread() != null) {
            m_tags.push(ENTITY_THREAD);
            visitThread(status.getThread());
            m_tags.pop();
         }

         if (status.getMessage() != null) {
            m_tags.push(ENTITY_MESSAGE);
            visitMessage(status.getMessage());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitThread(ThreadsInfo thread) {
      Object parent = m_objs.peek();
      ThreadsInfo old = null;

      if (parent instanceof StatusInfo) {
         StatusInfo status = (StatusInfo) parent;

         old = status.getThread();

         if (old == null) {
            old = new ThreadsInfo();
            status.setThread(old);
         }

         mergeThread(old, thread);
      }

      visitThreadChildren(old, thread);
   }

   protected void visitThreadChildren(ThreadsInfo old, ThreadsInfo thread) {
   }
}
