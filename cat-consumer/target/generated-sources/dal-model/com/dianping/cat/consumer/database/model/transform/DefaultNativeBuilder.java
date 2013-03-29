package com.dianping.cat.consumer.database.model.transform;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.dianping.cat.consumer.database.model.IVisitor;
import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public class DefaultNativeBuilder implements IVisitor {

   private DataOutputStream m_out;

   public DefaultNativeBuilder(OutputStream out) {
      m_out = new DataOutputStream(out);
   }

   public static byte[] build(DatabaseReport databaseReport) {
      ByteArrayOutputStream out = new ByteArrayOutputStream(8192);

      build(databaseReport, out);
      return out.toByteArray();
   }

   public static void build(DatabaseReport databaseReport, OutputStream out) {
      databaseReport.accept(new DefaultNativeBuilder(out));
   }

   @Override
   public void visitDatabaseReport(DatabaseReport databaseReport) {
      writeTag(63, 0);

      if (databaseReport.getDatabase() != null) {
         writeTag(1, 1);
         writeString(databaseReport.getDatabase());
      }

      if (databaseReport.getConnectUrl() != null) {
         writeTag(2, 1);
         writeString(databaseReport.getConnectUrl());
      }

      if (databaseReport.getStartTime() != null) {
         writeTag(3, 1);
         writeDate(databaseReport.getStartTime());
      }

      if (databaseReport.getEndTime() != null) {
         writeTag(4, 1);
         writeDate(databaseReport.getEndTime());
      }

      if (!databaseReport.getDatabaseNames().isEmpty()) {
         writeTag(5, 2);
         writeInt(databaseReport.getDatabaseNames().size());

         for (String databaseNames : databaseReport.getDatabaseNames()) {
            writeString(databaseNames);
         }
      }

      if (!databaseReport.getDomainNames().isEmpty()) {
         writeTag(6, 2);
         writeInt(databaseReport.getDomainNames().size());

         for (String domainNames : databaseReport.getDomainNames()) {
            writeString(domainNames);
         }
      }

      if (!databaseReport.getDomains().isEmpty()) {
         writeTag(33, 2);
         writeInt(databaseReport.getDomains().size());

         for (Domain domains : databaseReport.getDomains().values()) {
            visitDomain(domains);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitDomain(Domain domain) {
      if (domain.getId() != null) {
         writeTag(1, 1);
         writeString(domain.getId());
      }

      if (!domain.getTables().isEmpty()) {
         writeTag(33, 2);
         writeInt(domain.getTables().size());

         for (Table tables : domain.getTables().values()) {
            visitTable(tables);
         }
      }

      writeTag(63, 3);
   }

   @Override
   public void visitMethod(Method method) {
      if (method.getId() != null) {
         writeTag(1, 1);
         writeString(method.getId());
      }

      writeTag(2, 0);
      writeInt(method.getTotalCount());

      writeTag(3, 0);
      writeInt(method.getFailCount());

      writeTag(4, 0);
      writeDouble(method.getFailPercent());

      writeTag(5, 0);
      writeDouble(method.getAvg());

      writeTag(6, 0);
      writeDouble(method.getSum());

      writeTag(7, 0);
      writeDouble(method.getTps());

      if (!method.getSqlNames().isEmpty()) {
         writeTag(8, 2);
         writeInt(method.getSqlNames().size());

         for (String sqlNames : method.getSqlNames()) {
            writeString(sqlNames);
         }
      }

      writeTag(9, 0);
      writeDouble(method.getTotalPercent());

      writeTag(63, 3);
   }

   @Override
   public void visitTable(Table table) {
      if (table.getId() != null) {
         writeTag(1, 1);
         writeString(table.getId());
      }

      writeTag(2, 0);
      writeInt(table.getTotalCount());

      writeTag(3, 0);
      writeInt(table.getFailCount());

      writeTag(4, 0);
      writeDouble(table.getFailPercent());

      writeTag(5, 0);
      writeDouble(table.getAvg());

      writeTag(6, 0);
      writeDouble(table.getSum());

      writeTag(7, 0);
      writeDouble(table.getTps());

      writeTag(8, 0);
      writeDouble(table.getTotalPercent());

      if (!table.getMethods().isEmpty()) {
         writeTag(33, 2);
         writeInt(table.getMethods().size());

         for (Method methods : table.getMethods().values()) {
            visitMethod(methods);
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
