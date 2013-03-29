package com.dianping.cat.consumer.health.model.transform;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

public class DefaultNativeBuilder implements IVisitor {

   private DataOutputStream m_out;

   public DefaultNativeBuilder(OutputStream out) {
      m_out = new DataOutputStream(out);
   }

   public static byte[] build(HealthReport healthReport) {
      ByteArrayOutputStream out = new ByteArrayOutputStream(8192);

      build(healthReport, out);
      return out.toByteArray();
   }

   public static void build(HealthReport healthReport, OutputStream out) {
      healthReport.accept(new DefaultNativeBuilder(out));
   }

   @Override
   public void visitBaseCacheInfo(BaseCacheInfo baseCacheInfo) {
      writeTag(1, 0);
      writeLong(baseCacheInfo.getTotal());

      writeTag(2, 0);
      writeDouble(baseCacheInfo.getResponseTime());

      writeTag(3, 0);
      writeDouble(baseCacheInfo.getHitPercent());

      writeTag(63, 3);
   }

   @Override
   public void visitBaseInfo(BaseInfo baseInfo) {
      writeTag(1, 0);
      writeLong(baseInfo.getTotal());

      writeTag(2, 0);
      writeDouble(baseInfo.getResponseTime());

      writeTag(3, 0);
      writeLong(baseInfo.getErrorTotal());

      writeTag(4, 0);
      writeDouble(baseInfo.getErrorPercent());

      writeTag(5, 0);
      writeDouble(baseInfo.getSuccessPercent());

      writeTag(6, 0);
      writeDouble(baseInfo.getTps());

      writeTag(63, 3);
   }

   @Override
   public void visitCall(Call call) {
      if (call.getBaseInfo() != null) {
         writeTag(33, 1);
         visitBaseInfo(call.getBaseInfo());
      }

      writeTag(63, 3);
   }

   @Override
   public void visitClientService(ClientService clientService) {
      if (clientService.getBaseInfo() != null) {
         writeTag(33, 1);
         visitBaseInfo(clientService.getBaseInfo());
      }

      writeTag(63, 3);
   }

   @Override
   public void visitHealthReport(HealthReport healthReport) {
      writeTag(63, 0);

      if (healthReport.getDomain() != null) {
         writeTag(1, 1);
         writeString(healthReport.getDomain());
      }

      if (healthReport.getStartTime() != null) {
         writeTag(2, 1);
         writeDate(healthReport.getStartTime());
      }

      if (healthReport.getEndTime() != null) {
         writeTag(3, 1);
         writeDate(healthReport.getEndTime());
      }

      if (!healthReport.getDomainNames().isEmpty()) {
         writeTag(4, 2);
         writeInt(healthReport.getDomainNames().size());

         for (String domainNames : healthReport.getDomainNames()) {
            writeString(domainNames);
         }
      }

      if (healthReport.getProblemInfo() != null) {
         writeTag(33, 1);
         visitProblemInfo(healthReport.getProblemInfo());
      }

      if (healthReport.getUrl() != null) {
         writeTag(34, 1);
         visitUrl(healthReport.getUrl());
      }

      if (healthReport.getService() != null) {
         writeTag(35, 1);
         visitService(healthReport.getService());
      }

      if (healthReport.getCall() != null) {
         writeTag(36, 1);
         visitCall(healthReport.getCall());
      }

      if (healthReport.getSql() != null) {
         writeTag(37, 1);
         visitSql(healthReport.getSql());
      }

      if (healthReport.getWebCache() != null) {
         writeTag(38, 1);
         visitWebCache(healthReport.getWebCache());
      }

      if (healthReport.getKvdbCache() != null) {
         writeTag(39, 1);
         visitKvdbCache(healthReport.getKvdbCache());
      }

      if (healthReport.getMemCache() != null) {
         writeTag(40, 1);
         visitMemCache(healthReport.getMemCache());
      }

      if (healthReport.getClientService() != null) {
         writeTag(41, 1);
         visitClientService(healthReport.getClientService());
      }

      if (healthReport.getMachineInfo() != null) {
         writeTag(42, 1);
         visitMachineInfo(healthReport.getMachineInfo());
      }

      writeTag(63, 3);
   }

   @Override
   public void visitKvdbCache(KvdbCache kvdbCache) {
      if (kvdbCache.getBaseCacheInfo() != null) {
         writeTag(33, 1);
         visitBaseCacheInfo(kvdbCache.getBaseCacheInfo());
      }

      writeTag(63, 3);
   }

   @Override
   public void visitMachineInfo(MachineInfo machineInfo) {
      writeTag(1, 0);
      writeInt(machineInfo.getNumbers());

      writeTag(2, 0);
      writeDouble(machineInfo.getAvgLoad());

      writeTag(3, 0);
      writeDouble(machineInfo.getAvgMaxLoad());

      writeTag(4, 0);
      writeInt(machineInfo.getAvgLoadCount());

      writeTag(5, 0);
      writeDouble(machineInfo.getAvgLoadSum());

      if (machineInfo.getAvgMaxLoadMachine() != null) {
         writeTag(6, 1);
         writeString(machineInfo.getAvgMaxLoadMachine());
      }

      writeTag(7, 0);
      writeDouble(machineInfo.getAvgOldgc());

      writeTag(8, 0);
      writeDouble(machineInfo.getAvgMaxOldgc());

      writeTag(9, 0);
      writeInt(machineInfo.getAvgOldgcCount());

      writeTag(10, 0);
      writeDouble(machineInfo.getAvgOldgcSum());

      if (machineInfo.getAvgMaxOldgcMachine() != null) {
         writeTag(11, 1);
         writeString(machineInfo.getAvgMaxOldgcMachine());
      }

      writeTag(12, 0);
      writeDouble(machineInfo.getAvgHttp());

      writeTag(13, 0);
      writeDouble(machineInfo.getAvgMaxHttp());

      writeTag(14, 0);
      writeInt(machineInfo.getAvgHttpCount());

      writeTag(15, 0);
      writeDouble(machineInfo.getAvgHttpSum());

      if (machineInfo.getAvgMaxHttpMachine() != null) {
         writeTag(16, 1);
         writeString(machineInfo.getAvgMaxHttpMachine());
      }

      writeTag(17, 0);
      writeDouble(machineInfo.getAvgPigeon());

      writeTag(18, 0);
      writeDouble(machineInfo.getAvgMaxPigeon());

      writeTag(19, 0);
      writeInt(machineInfo.getAvgPigeonCount());

      writeTag(20, 0);
      writeDouble(machineInfo.getAvgPigeonSum());

      if (machineInfo.getAvgMaxPigeonMachine() != null) {
         writeTag(21, 1);
         writeString(machineInfo.getAvgMaxPigeonMachine());
      }

      writeTag(22, 0);
      writeDouble(machineInfo.getAvgMemoryUsed());

      writeTag(23, 0);
      writeDouble(machineInfo.getAvgMaxMemoryUsed());

      writeTag(24, 0);
      writeInt(machineInfo.getAvgMemoryUsedCount());

      writeTag(25, 0);
      writeDouble(machineInfo.getAvgMemoryUsedSum());

      if (machineInfo.getAvgMaxMemoryUsedMachine() != null) {
         writeTag(26, 1);
         writeString(machineInfo.getAvgMaxMemoryUsedMachine());
      }

      writeTag(63, 3);
   }

   @Override
   public void visitMemCache(MemCache memCache) {
      if (memCache.getBaseCacheInfo() != null) {
         writeTag(33, 1);
         visitBaseCacheInfo(memCache.getBaseCacheInfo());
      }

      writeTag(63, 3);
   }

   @Override
   public void visitProblemInfo(ProblemInfo problemInfo) {
      writeTag(1, 0);
      writeLong(problemInfo.getExceptions());

      writeTag(2, 0);
      writeLong(problemInfo.getLongSqls());

      writeTag(3, 0);
      writeDouble(problemInfo.getLongSqlPercent());

      writeTag(4, 0);
      writeLong(problemInfo.getLongUrls());

      writeTag(5, 0);
      writeDouble(problemInfo.getLongUrlPercent());

      writeTag(6, 0);
      writeLong(problemInfo.getLongServices());

      writeTag(7, 0);
      writeDouble(problemInfo.getLongServicePercent());

      writeTag(8, 0);
      writeLong(problemInfo.getLongCaches());

      writeTag(9, 0);
      writeDouble(problemInfo.getLongCachePercent());

      writeTag(63, 3);
   }

   @Override
   public void visitService(Service service) {
      if (service.getBaseInfo() != null) {
         writeTag(33, 1);
         visitBaseInfo(service.getBaseInfo());
      }

      writeTag(63, 3);
   }

   @Override
   public void visitSql(Sql sql) {
      if (sql.getBaseInfo() != null) {
         writeTag(33, 1);
         visitBaseInfo(sql.getBaseInfo());
      }

      writeTag(63, 3);
   }

   @Override
   public void visitUrl(Url url) {
      if (url.getBaseInfo() != null) {
         writeTag(33, 1);
         visitBaseInfo(url.getBaseInfo());
      }

      writeTag(63, 3);
   }

   @Override
   public void visitWebCache(WebCache webCache) {
      if (webCache.getBaseCacheInfo() != null) {
         writeTag(33, 1);
         visitBaseCacheInfo(webCache.getBaseCacheInfo());
      }

      writeTag(63, 3);
   }

   private void writeDate(java.util.Date value) {
      try {
         writeVarint(value.getTime());
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void writeDouble(double value) {
      try {
         m_out.writeDouble(value);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void writeInt(int value) {
      try {
         writeVarint(value);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void writeLong(long value) {
      try {
         writeVarint(value);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void writeString(String value) {
      try {
         m_out.writeUTF(value);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void writeTag(int field, int type) {
      try {
         m_out.writeByte((field << 2) + type);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   protected void writeVarint(long value) throws IOException {
      while (true) {
         if ((value & ~0x7FL) == 0) {
            m_out.writeByte((byte) value);
            return;
         } else {
            m_out.writeByte(((byte) value & 0x7F) | 0x80);
            value >>>= 7;
         }
      }
   }
}
