package com.dianping.cat.consumer.ip.model.transform;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.dianping.cat.consumer.ip.model.IVisitor;
import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public class DefaultNativeBuilder implements IVisitor {

   private DataOutputStream m_out;

   public DefaultNativeBuilder(OutputStream out) {
      m_out = new DataOutputStream(out);
   }

   public static byte[] build(IpReport ipReport) {
      ByteArrayOutputStream out = new ByteArrayOutputStream(8192);

      build(ipReport, out);
      return out.toByteArray();
   }

   public static void build(IpReport ipReport, OutputStream out) {
      ipReport.accept(new DefaultNativeBuilder(out));
   }

   @Override
   public void visitIp(Ip ip) {
      if (ip.getAddress() != null) {
         writeTag(1, 1);
         writeString(ip.getAddress());
      }

      writeTag(2, 0);
      writeInt(ip.getCount());

      writeTag(63, 3);
   }

   @Override
   public void visitIpReport(IpReport ipReport) {
      writeTag(63, 0);

      if (ipReport.getDomain() != null) {
         writeTag(1, 1);
         writeString(ipReport.getDomain());
      }

      if (ipReport.getStartTime() != null) {
         writeTag(2, 1);
         writeDate(ipReport.getStartTime());
      }

      if (ipReport.getEndTime() != null) {
         writeTag(3, 1);
         writeDate(ipReport.getEndTime());
      }

      if (!ipReport.getDomainNames().isEmpty()) {
         writeTag(4, 2);
         writeInt(ipReport.getDomainNames().size());

         for (String domainNames : ipReport.getDomainNames()) {
            writeString(domainNames);
         }
      }

      if (!ipReport.getPeriods().isEmpty()) {
         writeTag(33, 2);
         writeInt(ipReport.getPeriods().size());

         for (Period periods : ipReport.getPeriods().values()) {
            visitPeriod(periods);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitPeriod(Period period) {
      if (period.getMinute() != null) {
         writeTag(1, 1);
         writeInt(period.getMinute());
      }

      if (!period.getIps().isEmpty()) {
         writeTag(33, 2);
         writeInt(period.getIps().size());

         for (Ip ips : period.getIps().values()) {
            visitIp(ips);
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
