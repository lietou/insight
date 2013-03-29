package com.dianping.cat.consumer.sql.model.transform;

import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

public interface ILinker {

   public boolean onDatabase(SqlReport parent, Database database);

   public boolean onMethod(Table parent, Method method);

   public boolean onTable(Database parent, Table table);
}
