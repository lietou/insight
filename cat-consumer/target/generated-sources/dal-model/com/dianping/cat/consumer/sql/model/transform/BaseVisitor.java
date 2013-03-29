package com.dianping.cat.consumer.sql.model.transform;

import com.dianping.cat.consumer.sql.model.IVisitor;
import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitDatabase(Database database) {
      for (Table table : database.getTables().values()) {
         visitTable(table);
      }
   }

   @Override
   public void visitMethod(Method method) {
   }

   @Override
   public void visitSqlReport(SqlReport sqlReport) {
      for (Database database : sqlReport.getDatabases().values()) {
         visitDatabase(database);
      }
   }

   @Override
   public void visitTable(Table table) {
      for (Method method : table.getMethods().values()) {
         visitMethod(method);
      }
   }
}
