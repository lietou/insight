package com.dianping.cat.consumer.health.model.transform;

import static com.dianping.cat.consumer.health.model.Constants.ENTITY_BASE_CACHE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_BASE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_CALL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_CLIENTSERVICE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_KVDB_CACHE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_MACHINE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_MEM_CACHE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_PROBLEM_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_SERVICE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_SQL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_URL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_WEB_CACHE;
import java.util.Stack;

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

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private HealthReport m_healthReport;

   public DefaultMerger(HealthReport healthReport) {
      m_healthReport = healthReport;
   }

   public HealthReport getHealthReport() {
      return m_healthReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeBaseCacheInfo(BaseCacheInfo old, BaseCacheInfo baseCacheInfo) {
      old.mergeAttributes(baseCacheInfo);
   }

   protected void mergeBaseInfo(BaseInfo old, BaseInfo baseInfo) {
      old.mergeAttributes(baseInfo);
   }

   protected void mergeCall(Call old, Call call) {
      old.mergeAttributes(call);
   }

   protected void mergeClientService(ClientService old, ClientService clientService) {
      old.mergeAttributes(clientService);
   }

   protected void mergeHealthReport(HealthReport old, HealthReport healthReport) {
      old.mergeAttributes(healthReport);
   }

   protected void mergeKvdbCache(KvdbCache old, KvdbCache kvdbCache) {
      old.mergeAttributes(kvdbCache);
   }

   protected void mergeMachineInfo(MachineInfo old, MachineInfo machineInfo) {
      old.mergeAttributes(machineInfo);
   }

   protected void mergeMemCache(MemCache old, MemCache memCache) {
      old.mergeAttributes(memCache);
   }

   protected void mergeProblemInfo(ProblemInfo old, ProblemInfo problemInfo) {
      old.mergeAttributes(problemInfo);
   }

   protected void mergeService(Service old, Service service) {
      old.mergeAttributes(service);
   }

   protected void mergeSql(Sql old, Sql sql) {
      old.mergeAttributes(sql);
   }

   protected void mergeUrl(Url old, Url url) {
      old.mergeAttributes(url);
   }

   protected void mergeWebCache(WebCache old, WebCache webCache) {
      old.mergeAttributes(webCache);
   }

   @Override
   public void visitBaseCacheInfo(BaseCacheInfo baseCacheInfo) {
      Object parent = m_objs.peek();
      BaseCacheInfo old = null;

      if (parent instanceof WebCache) {
         WebCache webCache = (WebCache) parent;

         old = webCache.getBaseCacheInfo();

         if (old == null) {
            old = new BaseCacheInfo();
            webCache.setBaseCacheInfo(old);
         }

         mergeBaseCacheInfo(old, baseCacheInfo);
      } else if (parent instanceof KvdbCache) {
         KvdbCache kvdbCache = (KvdbCache) parent;

         old = kvdbCache.getBaseCacheInfo();

         if (old == null) {
            old = new BaseCacheInfo();
            kvdbCache.setBaseCacheInfo(old);
         }

         mergeBaseCacheInfo(old, baseCacheInfo);
      } else if (parent instanceof MemCache) {
         MemCache memCache = (MemCache) parent;

         old = memCache.getBaseCacheInfo();

         if (old == null) {
            old = new BaseCacheInfo();
            memCache.setBaseCacheInfo(old);
         }

         mergeBaseCacheInfo(old, baseCacheInfo);
      }

      visitBaseCacheInfoChildren(old, baseCacheInfo);
   }

   protected void visitBaseCacheInfoChildren(BaseCacheInfo old, BaseCacheInfo baseCacheInfo) {
   }

   @Override
   public void visitBaseInfo(BaseInfo baseInfo) {
      Object parent = m_objs.peek();
      BaseInfo old = null;

      if (parent instanceof Url) {
         Url url = (Url) parent;

         old = url.getBaseInfo();

         if (old == null) {
            old = new BaseInfo();
            url.setBaseInfo(old);
         }

         mergeBaseInfo(old, baseInfo);
      } else if (parent instanceof Service) {
         Service service = (Service) parent;

         old = service.getBaseInfo();

         if (old == null) {
            old = new BaseInfo();
            service.setBaseInfo(old);
         }

         mergeBaseInfo(old, baseInfo);
      } else if (parent instanceof Call) {
         Call call = (Call) parent;

         old = call.getBaseInfo();

         if (old == null) {
            old = new BaseInfo();
            call.setBaseInfo(old);
         }

         mergeBaseInfo(old, baseInfo);
      } else if (parent instanceof Sql) {
         Sql sql = (Sql) parent;

         old = sql.getBaseInfo();

         if (old == null) {
            old = new BaseInfo();
            sql.setBaseInfo(old);
         }

         mergeBaseInfo(old, baseInfo);
      } else if (parent instanceof ClientService) {
         ClientService clientService = (ClientService) parent;

         old = clientService.getBaseInfo();

         if (old == null) {
            old = new BaseInfo();
            clientService.setBaseInfo(old);
         }

         mergeBaseInfo(old, baseInfo);
      }

      visitBaseInfoChildren(old, baseInfo);
   }

   protected void visitBaseInfoChildren(BaseInfo old, BaseInfo baseInfo) {
   }

   @Override
   public void visitCall(Call call) {
      Object parent = m_objs.peek();
      Call old = null;

      if (parent instanceof HealthReport) {
         HealthReport healthReport = (HealthReport) parent;

         old = healthReport.getCall();

         if (old == null) {
            old = new Call();
            healthReport.setCall(old);
         }

         mergeCall(old, call);
      }

      visitCallChildren(old, call);
   }

   protected void visitCallChildren(Call old, Call call) {
      if (old != null) {
         m_objs.push(old);

         if (call.getBaseInfo() != null) {
            m_tags.push(ENTITY_BASE_INFO);
            visitBaseInfo(call.getBaseInfo());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitClientService(ClientService clientService) {
      Object parent = m_objs.peek();
      ClientService old = null;

      if (parent instanceof HealthReport) {
         HealthReport healthReport = (HealthReport) parent;

         old = healthReport.getClientService();

         if (old == null) {
            old = new ClientService();
            healthReport.setClientService(old);
         }

         mergeClientService(old, clientService);
      }

      visitClientServiceChildren(old, clientService);
   }

   protected void visitClientServiceChildren(ClientService old, ClientService clientService) {
      if (old != null) {
         m_objs.push(old);

         if (clientService.getBaseInfo() != null) {
            m_tags.push(ENTITY_BASE_INFO);
            visitBaseInfo(clientService.getBaseInfo());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitHealthReport(HealthReport healthReport) {
      m_healthReport.mergeAttributes(healthReport);
      visitHealthReportChildren(m_healthReport, healthReport);
   }

   protected void visitHealthReportChildren(HealthReport old, HealthReport healthReport) {
      if (old != null) {
         m_objs.push(old);

         if (healthReport.getProblemInfo() != null) {
            m_tags.push(ENTITY_PROBLEM_INFO);
            visitProblemInfo(healthReport.getProblemInfo());
            m_tags.pop();
         }

         if (healthReport.getUrl() != null) {
            m_tags.push(ENTITY_URL);
            visitUrl(healthReport.getUrl());
            m_tags.pop();
         }

         if (healthReport.getService() != null) {
            m_tags.push(ENTITY_SERVICE);
            visitService(healthReport.getService());
            m_tags.pop();
         }

         if (healthReport.getCall() != null) {
            m_tags.push(ENTITY_CALL);
            visitCall(healthReport.getCall());
            m_tags.pop();
         }

         if (healthReport.getSql() != null) {
            m_tags.push(ENTITY_SQL);
            visitSql(healthReport.getSql());
            m_tags.pop();
         }

         if (healthReport.getWebCache() != null) {
            m_tags.push(ENTITY_WEB_CACHE);
            visitWebCache(healthReport.getWebCache());
            m_tags.pop();
         }

         if (healthReport.getKvdbCache() != null) {
            m_tags.push(ENTITY_KVDB_CACHE);
            visitKvdbCache(healthReport.getKvdbCache());
            m_tags.pop();
         }

         if (healthReport.getMemCache() != null) {
            m_tags.push(ENTITY_MEM_CACHE);
            visitMemCache(healthReport.getMemCache());
            m_tags.pop();
         }

         if (healthReport.getClientService() != null) {
            m_tags.push(ENTITY_CLIENTSERVICE);
            visitClientService(healthReport.getClientService());
            m_tags.pop();
         }

         if (healthReport.getMachineInfo() != null) {
            m_tags.push(ENTITY_MACHINE_INFO);
            visitMachineInfo(healthReport.getMachineInfo());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitKvdbCache(KvdbCache kvdbCache) {
      Object parent = m_objs.peek();
      KvdbCache old = null;

      if (parent instanceof HealthReport) {
         HealthReport healthReport = (HealthReport) parent;

         old = healthReport.getKvdbCache();

         if (old == null) {
            old = new KvdbCache();
            healthReport.setKvdbCache(old);
         }

         mergeKvdbCache(old, kvdbCache);
      }

      visitKvdbCacheChildren(old, kvdbCache);
   }

   protected void visitKvdbCacheChildren(KvdbCache old, KvdbCache kvdbCache) {
      if (old != null) {
         m_objs.push(old);

         if (kvdbCache.getBaseCacheInfo() != null) {
            m_tags.push(ENTITY_BASE_CACHE_INFO);
            visitBaseCacheInfo(kvdbCache.getBaseCacheInfo());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitMachineInfo(MachineInfo machineInfo) {
      Object parent = m_objs.peek();
      MachineInfo old = null;

      if (parent instanceof HealthReport) {
         HealthReport healthReport = (HealthReport) parent;

         old = healthReport.getMachineInfo();

         if (old == null) {
            old = new MachineInfo();
            healthReport.setMachineInfo(old);
         }

         mergeMachineInfo(old, machineInfo);
      }

      visitMachineInfoChildren(old, machineInfo);
   }

   protected void visitMachineInfoChildren(MachineInfo old, MachineInfo machineInfo) {
   }

   @Override
   public void visitMemCache(MemCache memCache) {
      Object parent = m_objs.peek();
      MemCache old = null;

      if (parent instanceof HealthReport) {
         HealthReport healthReport = (HealthReport) parent;

         old = healthReport.getMemCache();

         if (old == null) {
            old = new MemCache();
            healthReport.setMemCache(old);
         }

         mergeMemCache(old, memCache);
      }

      visitMemCacheChildren(old, memCache);
   }

   protected void visitMemCacheChildren(MemCache old, MemCache memCache) {
      if (old != null) {
         m_objs.push(old);

         if (memCache.getBaseCacheInfo() != null) {
            m_tags.push(ENTITY_BASE_CACHE_INFO);
            visitBaseCacheInfo(memCache.getBaseCacheInfo());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitProblemInfo(ProblemInfo problemInfo) {
      Object parent = m_objs.peek();
      ProblemInfo old = null;

      if (parent instanceof HealthReport) {
         HealthReport healthReport = (HealthReport) parent;

         old = healthReport.getProblemInfo();

         if (old == null) {
            old = new ProblemInfo();
            healthReport.setProblemInfo(old);
         }

         mergeProblemInfo(old, problemInfo);
      }

      visitProblemInfoChildren(old, problemInfo);
   }

   protected void visitProblemInfoChildren(ProblemInfo old, ProblemInfo problemInfo) {
   }

   @Override
   public void visitService(Service service) {
      Object parent = m_objs.peek();
      Service old = null;

      if (parent instanceof HealthReport) {
         HealthReport healthReport = (HealthReport) parent;

         old = healthReport.getService();

         if (old == null) {
            old = new Service();
            healthReport.setService(old);
         }

         mergeService(old, service);
      }

      visitServiceChildren(old, service);
   }

   protected void visitServiceChildren(Service old, Service service) {
      if (old != null) {
         m_objs.push(old);

         if (service.getBaseInfo() != null) {
            m_tags.push(ENTITY_BASE_INFO);
            visitBaseInfo(service.getBaseInfo());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitSql(Sql sql) {
      Object parent = m_objs.peek();
      Sql old = null;

      if (parent instanceof HealthReport) {
         HealthReport healthReport = (HealthReport) parent;

         old = healthReport.getSql();

         if (old == null) {
            old = new Sql();
            healthReport.setSql(old);
         }

         mergeSql(old, sql);
      }

      visitSqlChildren(old, sql);
   }

   protected void visitSqlChildren(Sql old, Sql sql) {
      if (old != null) {
         m_objs.push(old);

         if (sql.getBaseInfo() != null) {
            m_tags.push(ENTITY_BASE_INFO);
            visitBaseInfo(sql.getBaseInfo());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitUrl(Url url) {
      Object parent = m_objs.peek();
      Url old = null;

      if (parent instanceof HealthReport) {
         HealthReport healthReport = (HealthReport) parent;

         old = healthReport.getUrl();

         if (old == null) {
            old = new Url();
            healthReport.setUrl(old);
         }

         mergeUrl(old, url);
      }

      visitUrlChildren(old, url);
   }

   protected void visitUrlChildren(Url old, Url url) {
      if (old != null) {
         m_objs.push(old);

         if (url.getBaseInfo() != null) {
            m_tags.push(ENTITY_BASE_INFO);
            visitBaseInfo(url.getBaseInfo());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitWebCache(WebCache webCache) {
      Object parent = m_objs.peek();
      WebCache old = null;

      if (parent instanceof HealthReport) {
         HealthReport healthReport = (HealthReport) parent;

         old = healthReport.getWebCache();

         if (old == null) {
            old = new WebCache();
            healthReport.setWebCache(old);
         }

         mergeWebCache(old, webCache);
      }

      visitWebCacheChildren(old, webCache);
   }

   protected void visitWebCacheChildren(WebCache old, WebCache webCache) {
      if (old != null) {
         m_objs.push(old);

         if (webCache.getBaseCacheInfo() != null) {
            m_tags.push(ENTITY_BASE_CACHE_INFO);
            visitBaseCacheInfo(webCache.getBaseCacheInfo());
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
