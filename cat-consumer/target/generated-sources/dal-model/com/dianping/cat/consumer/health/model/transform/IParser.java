package com.dianping.cat.consumer.health.model.transform;

import com.dianping.cat.consumer.health.model.entity.BaseCacheInfo;
import com.dianping.cat.consumer.health.model.entity.BaseInfo;
import com.dianping.cat.consumer.health.model.entity.Call;
import com.dianping.cat.consumer.health.model.entity.ClientService;
import com.dianping.cat.consumer.health.model.entity.HealthReport;
import com.dianping.cat.consumer.health.model.entity.KvdbCache;
import com.dianping.cat.consumer.health.model.entity.MachineInfo;
import com.dianping.cat.consumer.health.model.entity.MemCache;
import com.dianping.cat.consumer.health.model.entity.ProblemInfo;
import com.dianping.cat.consumer.health.model.entity.Service;
import com.dianping.cat.consumer.health.model.entity.Sql;
import com.dianping.cat.consumer.health.model.entity.Url;
import com.dianping.cat.consumer.health.model.entity.WebCache;

public interface IParser<T> {
   public HealthReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForBaseCacheInfo(IMaker<T> maker, ILinker linker, BaseCacheInfo parent, T node);

   public void parseForBaseInfo(IMaker<T> maker, ILinker linker, BaseInfo parent, T node);

   public void parseForCall(IMaker<T> maker, ILinker linker, Call parent, T node);

   public void parseForClientService(IMaker<T> maker, ILinker linker, ClientService parent, T node);

   public void parseForKvdbCache(IMaker<T> maker, ILinker linker, KvdbCache parent, T node);

   public void parseForMachineInfo(IMaker<T> maker, ILinker linker, MachineInfo parent, T node);

   public void parseForMemCache(IMaker<T> maker, ILinker linker, MemCache parent, T node);

   public void parseForProblemInfo(IMaker<T> maker, ILinker linker, ProblemInfo parent, T node);

   public void parseForService(IMaker<T> maker, ILinker linker, Service parent, T node);

   public void parseForSql(IMaker<T> maker, ILinker linker, Sql parent, T node);

   public void parseForUrl(IMaker<T> maker, ILinker linker, Url parent, T node);

   public void parseForWebCache(IMaker<T> maker, ILinker linker, WebCache parent, T node);
}
