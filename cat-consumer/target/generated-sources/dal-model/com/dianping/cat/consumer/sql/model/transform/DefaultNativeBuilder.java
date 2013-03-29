package com.dianping.cat.consumer.sql.model.transform;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.dianping.cat.consumer.sql.model.IVisitor;
import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

public class DefaultNativeBuilder implements IVisitor {

   private DataOutputStream m_out;

   public DefaultNativeBuilder(OutputStream out) {
      m_out = new DataOutputStream(out);
   }

   public static byte[] build(SqlReport sqlReport) {
      ByteArrayOutputStream out = new ByteArrayOutputStream(8192);

      build(sqlReport, out);
      return out.toByteArray();
   }

   public static void build(SqlReport sqlReport, OutputStream out) {
      sqlReport.accept(new DefaultNativeBuilder(out));
   }

   @Override
   public void visitDatabase(Database database) {
      if (database.getId() != null) {
         writeTag(1, 1);
         writeString(database.getId());
      }

      if (database.getConnectUrl() != null) {
         writeTag(2, 1);
         writeString(database.getConnectUrl());
      }

      if (!database.getTables().isEmpty()) {
         writeTag(33, 2);
         writeInt(database.getTables().size());

         for (Table tables : database.getTables().values()) {
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
   public void visitSqlReport(SqlReport sqlReport) {
      writeTag(63, 0);

      if (sqlReport.getDomain() != null) {
         writeTag(1, 1);
         writeString(sqlReport.getDomain());
      }

      if (sqlReport.getStartTime() != null) {
         writeTag(2, 1);
         writeDate(sqlReport.getStartTime());
      }

      if (sqlReport.getEndTime() != null) {
         writeTag(3, 1);
         writeDate(sqlReport.getEndTime());
      }

      if (!sqlReport.getDomainNames().isEmpty()) {
         writeTag(4, 2);
         writeInt(sqlReport.getDomainNames().size());

         for (String domainNames : sqlReport.getDomainNames()) {
            writeString(domainNames);
         }
      }

      if (!sqlReport.getDatabaseNames().isEmpty()) {
         writeTag(5, 2);
         writeInt(sqlReport.getDatabaseNames().size());

         for (String databaseNames : sqlReport.getDatabaseNames()) {
            writeString(databaseNames);
         }
      }

      if (!sqlReport.getDatabases().isEmpty()) {
         writeTag(33, 2);
         writeInt(sqlReport.getDatabases().size());

         for (Database databases : sqlReport.getDatabases().values()) {
            visitDatabase(databases);
         }
      }

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
