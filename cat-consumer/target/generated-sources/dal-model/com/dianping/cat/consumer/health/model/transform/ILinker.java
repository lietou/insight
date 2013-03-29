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

public interface ILinker {

   public boolean onBaseCacheInfo(WebCache parent, BaseCacheInfo baseCacheInfo);

   public boolean onBaseCacheInfo(KvdbCache parent, BaseCacheInfo baseCacheInfo);

   public boolean onBaseCacheInfo(MemCache parent, BaseCacheInfo baseCacheInfo);

   public boolean onBaseInfo(Url parent, BaseInfo baseInfo);

   public boolean onBaseInfo(Service parent, BaseInfo baseInfo);

   public boolean onBaseInfo(Call parent, BaseInfo baseInfo);

   public boolean onBaseInfo(Sql parent, BaseInfo baseInfo);

   public boolean onBaseInfo(ClientService parent, BaseInfo baseInfo);

   public boolean onCall(HealthReport parent, Call call);

   public boolean onClientService(HealthReport parent, ClientService clientService);

   public boolean onKvdbCache(HealthReport parent, KvdbCache kvdbCache);

   public boolean onMachineInfo(HealthReport parent, MachineInfo machineInfo);

   public boolean onMemCache(HealthReport parent, MemCache memCache);

   public boolean onProblemInfo(HealthReport parent, ProblemInfo problemInfo);

   public boolean onService(HealthReport parent, Service service);

   public boolean onSql(HealthReport parent, Sql sql);

   public boolean onUrl(HealthReport parent, Url url);

   public boolean onWebCache(HealthReport parent, WebCache webCache);
}
