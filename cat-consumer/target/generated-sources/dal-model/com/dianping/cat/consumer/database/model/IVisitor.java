package com.dianping.cat.consumer.database.model;

import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public interface IVisitor {

   public void visitDatabaseReport(DatabaseReport databaseReport);

   public void visitDomain(Domain domain);

   public void visitMethod(Method method);

   public void visitTable(Table table);
}
