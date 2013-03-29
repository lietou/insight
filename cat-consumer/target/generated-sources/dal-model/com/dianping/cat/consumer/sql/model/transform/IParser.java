package com.dianping.cat.consumer.sql.model.transform;

import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

public interface IParser<T> {
   public SqlReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForDatabase(IMaker<T> maker, ILinker linker, Database parent, T node);

   public void parseForMethod(IMaker<T> maker, ILinker linker, Method parent, T node);

   public void parseForTable(IMaker<T> maker, ILinker linker, Table parent, T node);
}
