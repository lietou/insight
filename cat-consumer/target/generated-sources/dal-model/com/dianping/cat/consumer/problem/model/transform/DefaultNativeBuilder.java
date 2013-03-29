package com.dianping.cat.consumer.problem.model.transform;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.dianping.cat.consumer.problem.model.IVisitor;
import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

public class DefaultNativeBuilder implements IVisitor {

   private DataOutputStream m_out;

   public DefaultNativeBuilder(OutputStream out) {
      m_out = new DataOutputStream(out);
   }

   public static byte[] build(ProblemReport problemReport) {
      ByteArrayOutputStream out = new ByteArrayOutputStream(8192);

      build(problemReport, out);
      return out.toByteArray();
   }

   public static void build(ProblemReport problemReport, OutputStream out) {
      problemReport.accept(new DefaultNativeBuilder(out));
   }

   @Override
   public void visitDuration(Duration duration) {
      writeTag(1, 0);
      writeInt(duration.getValue());

      writeTag(2, 0);
      writeInt(duration.getCount());

      if (!duration.getMessages().isEmpty()) {
         writeTag(3, 2);
         writeInt(duration.getMessages().size());

         for (String messages : duration.getMessages()) {
            writeString(messages);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitEntry(Entry entry) {
      if (entry.getType() != null) {
         writeTag(1, 1);
         writeString(entry.getType());
      }

      if (entry.getStatus() != null) {
         writeTag(2, 1);
         writeString(entry.getStatus());
      }

      if (!entry.getDurations().isEmpty()) {
         writeTag(33, 2);
         writeInt(entry.getDurations().size());

         for (Duration durations : entry.getDurations().values()) {
            visitDuration(durations);
         }
      }

      if (!entry.getThreads().isEmpty()) {
         writeTag(34, 2);
         writeInt(entry.getThreads().size());

         for (JavaThread threads : entry.getThreads().values()) {
            visitThread(threads);
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

      if (!machine.getEntries().isEmpty()) {
         writeTag(33, 2);
         writeInt(machine.getEntries().size());

         for (Entry entries : machine.getEntries()) {
            visitEntry(entries);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitProblemReport(ProblemReport problemReport) {
      writeTag(63, 0);

      if (problemReport.getDomain() != null) {
         writeTag(1, 1);
         writeString(problemReport.getDomain());
      }

      if (problemReport.getStartTime() != null) {
         writeTag(2, 1);
         writeDate(problemReport.getStartTime());
      }

      if (problemReport.getEndTime() != null) {
         writeTag(3, 1);
         writeDate(problemReport.getEndTime());
      }

      if (!problemReport.getDomainNames().isEmpty()) {
         writeTag(4, 2);
         writeInt(problemReport.getDomainNames().size());

         for (String domainNames : problemReport.getDomainNames()) {
            writeString(domainNames);
         }
      }

      if (!problemReport.getIps().isEmpty()) {
         writeTag(5, 2);
         writeInt(problemReport.getIps().size());

         for (String ips : problemReport.getIps()) {
            writeString(ips);
         }
      }

      if (!problemReport.getMachines().isEmpty()) {
         writeTag(33, 2);
         writeInt(problemReport.getMachines().size());

         for (Machine machines : problemReport.getMachines().values()) {
            visitMachine(machines);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitSegment(Segment segment) {
      if (segment.getId() != null) {
         writeTag(1, 1);
         writeInt(segment.getId());
      }

      writeTag(2, 0);
      writeInt(segment.getCount());

      if (!segment.getMessages().isEmpty()) {
         writeTag(3, 2);
         writeInt(segment.getMessages().size());

         for (String messages : segment.getMessages()) {
            writeString(messages);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitThread(JavaThread thread) {
      if (thread.getGroupName() != null) {
         writeTag(1, 1);
         writeString(thread.getGroupName());
      }

      if (thread.getName() != null) {
         writeTag(2, 1);
         writeString(thread.getName());
      }

      if (thread.getId() != null) {
         writeTag(3, 1);
         writeString(thread.getId());
      }

      if (!thread.getSegments().isEmpty()) {
         writeTag(33, 2);
         writeInt(thread.getSegments().size());

         for (Segment segments : thread.getSegments().values()) {
            visitSegment(segments);
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

   private void writeInt(int value) {
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
