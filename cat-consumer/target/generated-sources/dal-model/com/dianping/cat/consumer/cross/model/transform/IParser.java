package com.dianping.cat.consumer.cross.model.transform;

import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

public interface IParser<T> {
   public CrossReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForLocal(IMaker<T> maker, ILinker linker, Local parent, T node);

   public void parseForName(IMaker<T> maker, ILinker linker, Name parent, T node);

   public void parseForRemote(IMaker<T> maker, ILinker linker, Remote parent, T node);

   public void parseForType(IMaker<T> maker, ILinker linker, Type parent, T node);
}
