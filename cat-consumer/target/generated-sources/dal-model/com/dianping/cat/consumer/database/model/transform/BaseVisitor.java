package com.dianping.cat.consumer.database.model.transform;

import com.dianping.cat.consumer.database.model.IVisitor;
import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitDatabaseReport(DatabaseReport databaseReport) {
      for (Domain domain : databaseReport.getDomains().values()) {
         visitDomain(domain);
      }
   }

   @Override
   public void visitDomain(Domain domain) {
      for (Table table : domain.getTables().values()) {
         visitTable(table);
      }
   }

   @Override
   public void visitMethod(Method method) {
   }

   @Override
   public void visitTable(Table table) {
      for (Method method : table.getMethods().values()) {
         visitMethod(method);
      }
   }
}
