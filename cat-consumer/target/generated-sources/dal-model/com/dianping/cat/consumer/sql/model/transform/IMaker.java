package com.dianping.cat.consumer.sql.model.transform;

import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

public interface IMaker<T> {

   public Database buildDatabase(T node);

   public String buildDatabaseName(T node);

   public String buildDomainName(T node);

   public Method buildMethod(T node);

   public String buildSql(T node);

   public SqlReport buildSqlReport(T node);

   public Table buildTable(T node);
}
