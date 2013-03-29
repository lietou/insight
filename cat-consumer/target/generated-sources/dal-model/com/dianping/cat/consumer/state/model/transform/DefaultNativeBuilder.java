package com.dianping.cat.consumer.state.model.transform;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.dianping.cat.consumer.state.model.IVisitor;
import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public class DefaultNativeBuilder implements IVisitor {

   private DataOutputStream m_out;

   public DefaultNativeBuilder(OutputStream out) {
      m_out = new DataOutputStream(out);
   }

   public static byte[] build(StateReport stateReport) {
      ByteArrayOutputStream out = new ByteArrayOutputStream(8192);

      build(stateReport, out);
      return out.toByteArray();
   }

   public static void build(StateReport stateReport, OutputStream out) {
      stateReport.accept(new DefaultNativeBuilder(out));
   }

   @Override
   public void visitMachine(Machine machine) {
      if (machine.getIp() != null) {
         writeTag(1, 1);
         writeString(machine.getIp());
      }

      writeTag(2, 0);
      writeLong(machine.getTotal());

      writeTag(3, 0);
      writeLong(machine.getTotalLoss());

      writeTag(4, 0);
      writeDouble(machine.getMaxTps());

      writeTag(5, 0);
      writeDouble(machine.getAvgTps());

      writeTag(6, 0);
      writeLong(machine.getBlockTotal());

      writeTag(7, 0);
      writeLong(machine.getBlockLoss());

      writeTag(8, 0);
      writeLong(machine.getBlockTime());

      writeTag(9, 0);
      writeLong(machine.getPigeonTimeError());

      writeTag(10, 0);
      writeLong(machine.getNetworkTimeError());

      writeTag(11, 0);
      writeLong(machine.getDump());

      writeTag(12, 0);
      writeLong(machine.getDumpLoss());

      writeTag(13, 0);
      writeDouble(machine.getSize());

      writeTag(14, 0);
      writeDouble(machine.getDelaySum());

      writeTag(15, 0);
      writeDouble(machine.getDelayAvg());

      writeTag(16, 0);
      writeInt(machine.getDelayCount());

      if (!machine.getProcessDomains().isEmpty()) {
         writeTag(33, 2);
         writeInt(machine.getProcessDomains().size());

         for (ProcessDomain processDomains : machine.getProcessDomains().values()) {
            visitProcessDomain(processDomains);
         }
      }

      if (!machine.getMessages().isEmpty()) {
         writeTag(34, 2);
         writeInt(machine.getMessages().size());

         for (Message messages : machine.getMessages().values()) {
            visitMessage(messages);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitMessage(Message message) {
      if (message.getId() != null) {
         writeTag(1, 1);
         writeLong(message.getId());
      }

      if (message.getTime() != null) {
         writeTag(2, 1);
         writeDate(message.getTime());
      }

      writeTag(3, 0);
      writeLong(message.getTotal());

      writeTag(4, 0);
      writeLong(message.getTotalLoss());

      writeTag(5, 0);
      writeLong(message.getDump());

      writeTag(6, 0);
      writeLong(message.getDumpLoss());

      writeTag(7, 0);
      writeDouble(message.getSize());

      writeTag(8, 0);
      writeDouble(message.getDelaySum());

      writeTag(9, 0);
      writeInt(message.getDelayCount());

      writeTag(10, 0);
      writeLong(message.getPigeonTimeError());

      writeTag(11, 0);
      writeLong(message.getNetworkTimeError());

      writeTag(12, 0);
      writeLong(message.getBlockTotal());

      writeTag(13, 0);
      writeLong(message.getBlockLoss());

      writeTag(14, 0);
      writeLong(message.getBlockTime());

      writeTag(63, 3);
   }

   @Override
   public void visitProcessDomain(ProcessDomain processDomain) {
      if (processDomain.getName() != null) {
         writeTag(1, 1);
         writeString(processDomain.getName());
      }

      if (!processDomain.getIps().isEmpty()) {
         writeTag(2, 2);
         writeInt(processDomain.getIps().size());

         for (String ips : processDomain.getIps()) {
            writeString(ips);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitStateReport(StateReport stateReport) {
      writeTag(63, 0);

      if (stateReport.getDomain() != null) {
         writeTag(1, 1);
         writeString(stateReport.getDomain());
      }

      if (stateReport.getStartTime() != null) {
         writeTag(2, 1);
         writeDate(stateReport.getStartTime());
      }

      if (stateReport.getEndTime() != null) {
         writeTag(3, 1);
         writeDate(stateReport.getEndTime());
      }

      if (!stateReport.getMachines().isEmpty()) {
         writeTag(33, 2);
         writeInt(stateReport.getMachines().size());

         for (Machine machines : stateReport.getMachines().values()) {
            visitMachine(machines);
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
