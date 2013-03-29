package com.dianping.cat.consumer.sql.model;

import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

public interface IVisitor {

   public void visitDatabase(Database database);

   public void visitMethod(Method method);

   public void visitSqlReport(SqlReport sqlReport);

   public void visitTable(Table table);
}
