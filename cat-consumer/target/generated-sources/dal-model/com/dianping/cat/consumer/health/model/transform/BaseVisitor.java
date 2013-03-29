package com.dianping.cat.consumer.health.model.transform;

import com.dianping.cat.consumer.health.model.IVisitor;
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

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitBaseCacheInfo(BaseCacheInfo baseCacheInfo) {
   }

   @Override
   public void visitBaseInfo(BaseInfo baseInfo) {
   }

   @Override
   public void visitCall(Call call) {
      if (call.getBaseInfo() != null) {
         visitBaseInfo(call.getBaseInfo());
      }
   }

   @Override
   public void visitClientService(ClientService clientService) {
      if (clientService.getBaseInfo() != null) {
         visitBaseInfo(clientService.getBaseInfo());
      }
   }

   @Override
   public void visitHealthReport(HealthReport healthReport) {
      if (healthReport.getProblemInfo() != null) {
         visitProblemInfo(healthReport.getProblemInfo());
      }

      if (healthReport.getUrl() != null) {
         visitUrl(healthReport.getUrl());
      }

      if (healthReport.getService() != null) {
         visitService(healthReport.getService());
      }

      if (healthReport.getCall() != null) {
         visitCall(healthReport.getCall());
      }

      if (healthReport.getSql() != null) {
         visitSql(healthReport.getSql());
      }

      if (healthReport.getWebCache() != null) {
         visitWebCache(healthReport.getWebCache());
      }

      if (healthReport.getKvdbCache() != null) {
         visitKvdbCache(healthReport.getKvdbCache());
      }

      if (healthReport.getMemCache() != null) {
         visitMemCache(healthReport.getMemCache());
      }

      if (healthReport.getClientService() != null) {
         visitClientService(healthReport.getClientService());
      }

      if (healthReport.getMachineInfo() != null) {
         visitMachineInfo(healthReport.getMachineInfo());
      }
   }

   @Override
   public void visitKvdbCache(KvdbCache kvdbCache) {
      if (kvdbCache.getBaseCacheInfo() != null) {
         visitBaseCacheInfo(kvdbCache.getBaseCacheInfo());
      }
   }

   @Override
   public void visitMachineInfo(MachineInfo machineInfo) {
   }

   @Override
   public void visitMemCache(MemCache memCache) {
      if (memCache.getBaseCacheInfo() != null) {
         visitBaseCacheInfo(memCache.getBaseCacheInfo());
      }
   }

   @Override
   public void visitProblemInfo(ProblemInfo problemInfo) {
   }

   @Override
   public void visitService(Service service) {
      if (service.getBaseInfo() != null) {
         visitBaseInfo(service.getBaseInfo());
      }
   }

   @Override
   public void visitSql(Sql sql) {
      if (sql.getBaseInfo() != null) {
         visitBaseInfo(sql.getBaseInfo());
      }
   }

   @Override
   public void visitUrl(Url url) {
      if (url.getBaseInfo() != null) {
         visitBaseInfo(url.getBaseInfo());
      }
   }

   @Override
   public void visitWebCache(WebCache webCache) {
      if (webCache.getBaseCacheInfo() != null) {
         visitBaseCacheInfo(webCache.getBaseCacheInfo());
      }
   }
}
