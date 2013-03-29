package com.dianping.cat.consumer.health.model.transform;

import java.util.ArrayList;
import java.util.List;
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

public class DefaultLinker implements ILinker {
   @SuppressWarnings("unused")
   private boolean m_deferrable;

   private List<Runnable> m_deferedJobs = new ArrayList<Runnable>();

   public DefaultLinker(boolean deferrable) {
      m_deferrable = deferrable;
   }

   public void finish() {
      for (Runnable job : m_deferedJobs) {
         job.run();
      }
   }

   @Override
   public boolean onBaseCacheInfo(final WebCache parent, final BaseCacheInfo baseCacheInfo) {
      parent.setBaseCacheInfo(baseCacheInfo);
      return true;
   }

   @Override
   public boolean onBaseCacheInfo(final KvdbCache parent, final BaseCacheInfo baseCacheInfo) {
      parent.setBaseCacheInfo(baseCacheInfo);
      return true;
   }

   @Override
   public boolean onBaseCacheInfo(final MemCache parent, final BaseCacheInfo baseCacheInfo) {
      parent.setBaseCacheInfo(baseCacheInfo);
      return true;
   }

   @Override
   public boolean onBaseInfo(final Url parent, final BaseInfo baseInfo) {
      parent.setBaseInfo(baseInfo);
      return true;
   }

   @Override
   public boolean onBaseInfo(final Service parent, final BaseInfo baseInfo) {
      parent.setBaseInfo(baseInfo);
      return true;
   }

   @Override
   public boolean onBaseInfo(final Call parent, final BaseInfo baseInfo) {
      parent.setBaseInfo(baseInfo);
      return true;
   }

   @Override
   public boolean onBaseInfo(final Sql parent, final BaseInfo baseInfo) {
      parent.setBaseInfo(baseInfo);
      return true;
   }

   @Override
   public boolean onBaseInfo(final ClientService parent, final BaseInfo baseInfo) {
      parent.setBaseInfo(baseInfo);
      return true;
   }

   @Override
   public boolean onCall(final HealthReport parent, final Call call) {
      parent.setCall(call);
      return true;
   }

   @Override
   public boolean onClientService(final HealthReport parent, final ClientService clientService) {
      parent.setClientService(clientService);
      return true;
   }

   @Override
   public boolean onKvdbCache(final HealthReport parent, final KvdbCache kvdbCache) {
      parent.setKvdbCache(kvdbCache);
      return true;
   }

   @Override
   public boolean onMachineInfo(final HealthReport parent, final MachineInfo machineInfo) {
      parent.setMachineInfo(machineInfo);
      return true;
   }

   @Override
   public boolean onMemCache(final HealthReport parent, final MemCache memCache) {
      parent.setMemCache(memCache);
      return true;
   }

   @Override
   public boolean onProblemInfo(final HealthReport parent, final ProblemInfo problemInfo) {
      parent.setProblemInfo(problemInfo);
      return true;
   }

   @Override
   public boolean onService(final HealthReport parent, final Service service) {
      parent.setService(service);
      return true;
   }

   @Override
   public boolean onSql(final HealthReport parent, final Sql sql) {
      parent.setSql(sql);
      return true;
   }

   @Override
   public boolean onUrl(final HealthReport parent, final Url url) {
      parent.setUrl(url);
      return true;
   }

   @Override
   public boolean onWebCache(final HealthReport parent, final WebCache webCache) {
      parent.setWebCache(webCache);
      return true;
   }
}
