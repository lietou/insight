package com.dianping.cat.consumer.heartbeat.model.transform;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.dianping.cat.consumer.heartbeat.model.IVisitor;
import com.dianping.cat.consumer.heartbeat.model.entity.Disk;
import com.dianping.cat.consumer.heartbeat.model.entity.HeartbeatReport;
import com.dianping.cat.consumer.heartbeat.model.entity.Machine;
import com.dianping.cat.consumer.heartbeat.model.entity.Period;

public class DefaultNativeBuilder implements IVisitor {

   private DataOutputStream m_out;

   public DefaultNativeBuilder(OutputStream out) {
      m_out = new DataOutputStream(out);
   }

   public static byte[] build(HeartbeatReport heartbeatReport) {
      ByteArrayOutputStream out = new ByteArrayOutputStream(8192);

      build(heartbeatReport, out);
      return out.toByteArray();
   }

   public static void build(HeartbeatReport heartbeatReport, OutputStream out) {
      heartbeatReport.accept(new DefaultNativeBuilder(out));
   }

   @Override
   public void visitDisk(Disk disk) {
      if (disk.getPath() != null) {
         writeTag(1, 1);
         writeString(disk.getPath());
      }

      writeTag(2, 0);
      writeLong(disk.getTotal());

      writeTag(3, 0);
      writeLong(disk.getFree());

      writeTag(4, 0);
      writeLong(disk.getUsable());

      writeTag(63, 3);
   }

   @Override
   public void visitHeartbeatReport(HeartbeatReport heartbeatReport) {
      writeTag(63, 0);

      if (heartbeatReport.getDomain() != null) {
         writeTag(1, 1);
         writeString(heartbeatReport.getDomain());
      }

      if (heartbeatReport.getStartTime() != null) {
         writeTag(2, 1);
         writeDate(heartbeatReport.getStartTime());
      }

      if (heartbeatReport.getEndTime() != null) {
         writeTag(3, 1);
         writeDate(heartbeatReport.getEndTime());
      }

      if (!heartbeatReport.getDomainNames().isEmpty()) {
         writeTag(4, 2);
         writeInt(heartbeatReport.getDomainNames().size());

         for (String domainNames : heartbeatReport.getDomainNames()) {
            writeString(domainNames);
         }
      }

      if (!heartbeatReport.getIps().isEmpty()) {
         writeTag(5, 2);
         writeInt(heartbeatReport.getIps().size());

         for (String ips : heartbeatReport.getIps()) {
            writeString(ips);
         }
      }

      if (!heartbeatReport.getMachines().isEmpty()) {
         writeTag(33, 2);
         writeInt(heartbeatReport.getMachines().size());

         for (Machine machines : heartbeatReport.getMachines().values()) {
            visitMachine(machines);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitMachine(Machine machine) {
      if (machine.getIp() != null) {
         writeTag(1, 1);
         writeString(machine.getIp());
      }

      if (!machine.getPeriods().isEmpty()) {
         writeTag(33, 2);
         writeInt(machine.getPeriods().size());

         for (Period periods : machine.getPeriods()) {
            visitPeriod(periods);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitPeriod(Period period) {
      writeTag(1, 0);
      writeInt(period.getMinute());

      writeTag(2, 0);
      writeInt(period.getThreadCount());

      writeTag(3, 0);
      writeInt(period.getDaemonCount());

      writeTag(4, 0);
      writeInt(period.getTotalStartedCount());

      writeTag(5, 0);
      writeInt(period.getCatThreadCount());

      writeTag(6, 0);
      writeInt(period.getPigeonThreadCount());

      writeTag(7, 0);
      writeInt(period.getHttpThreadCount());

      writeTag(8, 0);
      writeLong(period.getNewGcCount());

      writeTag(9, 0);
      writeLong(period.getOldGcCount());

      writeTag(10, 0);
      writeLong(period.getMemoryFree());

      writeTag(11, 0);
      writeLong(period.getHeapUsage());

      writeTag(12, 0);
      writeLong(period.getNoneHeapUsage());

      writeTag(13, 0);
      writeDouble(period.getSystemLoadAverage());

      writeTag(14, 0);
      writeLong(period.getCatMessageProduced());

      writeTag(15, 0);
      writeLong(period.getCatMessageOverflow());

      writeTag(16, 0);
      writeDouble(period.getCatMessageSize());

      if (!period.getDisks().isEmpty()) {
         writeTag(33, 2);
         writeInt(period.getDisks().size());

         for (Disk disks : period.getDisks()) {
            visitDisk(disks);
         }
      }

      writeTag(63, 3);
   }

   private void writeDate(java.util.Date value) {
      try {
         writeVarint(value.getTime());
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void writeDouble(double value) {
      try {
         m_out.writeDouble(value);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void writeInt(int value) {
      try {
         writeVarint(value);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void writeLong(long value) {
      try {
         writeVarint(value);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void writeString(String value) {
      try {
         m_out.writeUTF(value);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void writeTag(int field, int type) {
      try {
         m_out.writeByte((field << 2) + type);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   protected void writeVarint(long value) throws IOException {
      while (true) {
         if ((value & ~0x7FL) == 0) {
            m_out.writeByte((byte) value);
            return;
         } else {
            m_out.writeByte(((byte) value & 0x7F) | 0x80);
            value >>>= 7;
         }
      }
   }
}
