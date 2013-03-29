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

public interface IMaker<T> {

   public BaseCacheInfo buildBaseCacheInfo(T node);

   public BaseInfo buildBaseInfo(T node);

   public Call buildCall(T node);

   public ClientService buildClientService(T node);

   public String buildDomain(T node);

   public HealthReport buildHealthReport(T node);

   public KvdbCache buildKvdbCache(T node);

   public MachineInfo buildMachineInfo(T node);

   public MemCache buildMemCache(T node);

   public ProblemInfo buildProblemInfo(T node);

   public Service buildService(T node);

   public Sql buildSql(T node);

   public Url buildUrl(T node);

   public WebCache buildWebCache(T node);
}
