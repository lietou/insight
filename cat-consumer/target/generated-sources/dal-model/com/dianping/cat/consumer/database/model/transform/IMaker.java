package com.dianping.cat.consumer.database.model.transform;

import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public interface IMaker<T> {

   public String buildDatabaseName(T node);

   public DatabaseReport buildDatabaseReport(T node);

   public Domain buildDomain(T node);

   public String buildDomainName(T node);

   public Method buildMethod(T node);

   public String buildSql(T node);

   public Table buildTable(T node);
}
