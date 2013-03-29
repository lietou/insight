package com.dianping.cat.consumer.database.model.transform;

import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public interface ILinker {

   public boolean onDomain(DatabaseReport parent, Domain domain);

   public boolean onMethod(Table parent, Method method);

   public boolean onTable(Domain parent, Table table);
}
