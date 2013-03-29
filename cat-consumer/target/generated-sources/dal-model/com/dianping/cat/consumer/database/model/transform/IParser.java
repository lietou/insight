package com.dianping.cat.consumer.database.model.transform;

import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public interface IParser<T> {
   public DatabaseReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForDomain(IMaker<T> maker, ILinker linker, Domain parent, T node);

   public void parseForMethod(IMaker<T> maker, ILinker linker, Method parent, T node);

   public void parseForTable(IMaker<T> maker, ILinker linker, Table parent, T node);
}
