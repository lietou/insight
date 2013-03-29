package com.dianping.cat.consumer.health.model;

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

public interface IVisitor {

   public void visitBaseCacheInfo(BaseCacheInfo baseCacheInfo);

   public void visitBaseInfo(BaseInfo baseInfo);

   public void visitCall(Call call);

   public void visitClientService(ClientService clientService);

   public void visitHealthReport(HealthReport healthReport);

   public void visitKvdbCache(KvdbCache kvdbCache);

   public void visitMachineInfo(MachineInfo machineInfo);

   public void visitMemCache(MemCache memCache);

   public void visitProblemInfo(ProblemInfo problemInfo);

   public void visitService(Service service);

   public void visitSql(Sql sql);

   public void visitUrl(Url url);

   public void visitWebCache(WebCache webCache);
}
