package com.dianping.cat.consumer.health.model.transform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

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

public class DefaultNativeParser implements IVisitor {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DataInputStream m_in;

   public DefaultNativeParser(InputStream in) {
      m_in = new DataInputStream(in);
   }

   public static HealthReport parse(byte[] data) {
      return parse(new ByteArrayInputStream(data));
   }

   public static HealthReport parse(InputStream in) {
      DefaultNativeParser parser = new DefaultNativeParser(in);
      HealthReport healthReport = new HealthReport();

      try {
         healthReport.accept(parser);
      } catch (RuntimeException e) {
         if (e.getCause() !=null && e.getCause() instanceof java.io.EOFException) {
            // ignore it
         } else {
            throw e;
         }
      }
      
      parser.m_linker.finish();
      return healthReport;
   }

   @Override
   public void visitBaseCacheInfo(BaseCacheInfo baseCacheInfo) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitBaseCacheInfoChildren(baseCacheInfo, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitBaseCacheInfoChildren(BaseCacheInfo baseCacheInfo, int _field, int _type) {
      switch (_field) {
         case 1:
            baseCacheInfo.setTotal(readLong());
            break;
         case 2:
            baseCacheInfo.setResponseTime(readDouble());
            break;
         case 3:
            baseCacheInfo.setHitPercent(readDouble());
            break;
      }
   }

   @Override
   public void visitBaseInfo(BaseInfo baseInfo) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitBaseInfoChildren(baseInfo, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitBaseInfoChildren(BaseInfo baseInfo, int _field, int _type) {
      switch (_field) {
         case 1:
            baseInfo.setTotal(readLong());
            break;
         case 2:
            baseInfo.setResponseTime(readDouble());
            break;
         case 3:
            baseInfo.setErrorTotal(readLong());
            break;
         case 4:
            baseInfo.setErrorPercent(readDouble());
            break;
         case 5:
            baseInfo.setSuccessPercent(readDouble());
            break;
         case 6:
            baseInfo.setTps(readDouble());
            break;
      }
   }

   @Override
   public void visitCall(Call call) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitCallChildren(call, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitCallChildren(Call call, int _field, int _type) {
      switch (_field) {
         case 33:
            BaseInfo baseInfo = new BaseInfo();

            visitBaseInfo(baseInfo);
            m_linker.onBaseInfo(call, baseInfo);
            break;
      }
   }

   @Override
   public void visitClientService(ClientService clientService) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitClientServiceChildren(clientService, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitClientServiceChildren(ClientService clientService, int _field, int _type) {
      switch (_field) {
         case 33:
            BaseInfo baseInfo = new BaseInfo();

            visitBaseInfo(baseInfo);
            m_linker.onBaseInfo(clientService, baseInfo);
            break;
      }
   }

   @Override
   public void visitHealthReport(HealthReport healthReport) {
      byte tag;

      if ((tag = readTag()) != -4) {
         throw new RuntimeException(String.format("Malformed payload, expected: %s, but was: %s!", -4, tag));
      }

      while ((tag = readTag()) != -1) {
         visitHealthReportChildren(healthReport, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitHealthReportChildren(HealthReport healthReport, int _field, int _type) {
      switch (_field) {
         case 1:
            healthReport.setDomain(readString());
            break;
         case 2:
            healthReport.setStartTime(readDate());
            break;
         case 3:
            healthReport.setEndTime(readDate());
            break;
         case 4:
            if (_type == 1) {
                  healthReport.addDomain(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  healthReport.addDomain(readString());
               }
            }
            break;
         case 33:
            ProblemInfo problemInfo = new ProblemInfo();

            visitProblemInfo(problemInfo);
            m_linker.onProblemInfo(healthReport, problemInfo);
            break;
         case 34:
            Url url = new Url();

            visitUrl(url);
            m_linker.onUrl(healthReport, url);
            break;
         case 35:
            Service service = new Service();

            visitService(service);
            m_linker.onService(healthReport, service);
            break;
         case 36:
            Call call = new Call();

            visitCall(call);
            m_linker.onCall(healthReport, call);
            break;
         case 37:
            Sql sql = new Sql();

            visitSql(sql);
            m_linker.onSql(healthReport, sql);
            break;
         case 38:
            WebCache webCache = new WebCache();

            visitWebCache(webCache);
            m_linker.onWebCache(healthReport, webCache);
            break;
         case 39:
            KvdbCache kvdbCache = new KvdbCache();

            visitKvdbCache(kvdbCache);
            m_linker.onKvdbCache(healthReport, kvdbCache);
            break;
         case 40:
            MemCache memCache = new MemCache();

            visitMemCache(memCache);
            m_linker.onMemCache(healthReport, memCache);
            break;
         case 41:
            ClientService clientService = new ClientService();

            visitClientService(clientService);
            m_linker.onClientService(healthReport, clientService);
            break;
         case 42:
            MachineInfo machineInfo = new MachineInfo();

            visitMachineInfo(machineInfo);
            m_linker.onMachineInfo(healthReport, machineInfo);
            break;
      }
   }

   @Override
   public void visitKvdbCache(KvdbCache kvdbCache) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitKvdbCacheChildren(kvdbCache, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitKvdbCacheChildren(KvdbCache kvdbCache, int _field, int _type) {
      switch (_field) {
         case 33:
            BaseCacheInfo baseCacheInfo = new BaseCacheInfo();

            visitBaseCacheInfo(baseCacheInfo);
            m_linker.onBaseCacheInfo(kvdbCache, baseCacheInfo);
            break;
      }
   }

   @Override
   public void visitMachineInfo(MachineInfo machineInfo) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitMachineInfoChildren(machineInfo, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitMachineInfoChildren(MachineInfo machineInfo, int _field, int _type) {
      switch (_field) {
         case 1:
            machineInfo.setNumbers(readInt());
            break;
         case 2:
            machineInfo.setAvgLoad(readDouble());
            break;
         case 3:
            machineInfo.setAvgMaxLoad(readDouble());
            break;
         case 4:
            machineInfo.setAvgLoadCount(readInt());
            break;
         case 5:
            machineInfo.setAvgLoadSum(readDouble());
            break;
         case 6:
            machineInfo.setAvgMaxLoadMachine(readString());
            break;
         case 7:
            machineInfo.setAvgOldgc(readDouble());
            break;
         case 8:
            machineInfo.setAvgMaxOldgc(readDouble());
            break;
         case 9:
            machineInfo.setAvgOldgcCount(readInt());
            break;
         case 10:
            machineInfo.setAvgOldgcSum(readDouble());
            break;
         case 11:
            machineInfo.setAvgMaxOldgcMachine(readString());
            break;
         case 12:
            machineInfo.setAvgHttp(readDouble());
            break;
         case 13:
            machineInfo.setAvgMaxHttp(readDouble());
            break;
         case 14:
            machineInfo.setAvgHttpCount(readInt());
            break;
         case 15:
            machineInfo.setAvgHttpSum(readDouble());
            break;
         case 16:
            machineInfo.setAvgMaxHttpMachine(readString());
            break;
         case 17:
            machineInfo.setAvgPigeon(readDouble());
            break;
         case 18:
            machineInfo.setAvgMaxPigeon(readDouble());
            break;
         case 19:
            machineInfo.setAvgPigeonCount(readInt());
            break;
         case 20:
            machineInfo.setAvgPigeonSum(readDouble());
            break;
         case 21:
            machineInfo.setAvgMaxPigeonMachine(readString());
            break;
         case 22:
            machineInfo.setAvgMemoryUsed(readDouble());
            break;
         case 23:
            machineInfo.setAvgMaxMemoryUsed(readDouble());
            break;
         case 24:
            machineInfo.setAvgMemoryUsedCount(readInt());
            break;
         case 25:
            machineInfo.setAvgMemoryUsedSum(readDouble());
            break;
         case 26:
            machineInfo.setAvgMaxMemoryUsedMachine(readString());
            break;
      }
   }

   @Override
   public void visitMemCache(MemCache memCache) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitMemCacheChildren(memCache, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitMemCacheChildren(MemCache memCache, int _field, int _type) {
      switch (_field) {
         case 33:
            BaseCacheInfo baseCacheInfo = new BaseCacheInfo();

            visitBaseCacheInfo(baseCacheInfo);
            m_linker.onBaseCacheInfo(memCache, baseCacheInfo);
            break;
      }
   }

   @Override
   public void visitProblemInfo(ProblemInfo problemInfo) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitProblemInfoChildren(problemInfo, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitProblemInfoChildren(ProblemInfo problemInfo, int _field, int _type) {
      switch (_field) {
         case 1:
            problemInfo.setExceptions(readLong());
            break;
         case 2:
            problemInfo.setLongSqls(readLong());
            break;
         case 3:
            problemInfo.setLongSqlPercent(readDouble());
            break;
         case 4:
            problemInfo.setLongUrls(readLong());
            break;
         case 5:
            problemInfo.setLongUrlPercent(readDouble());
            break;
         case 6:
            problemInfo.setLongServices(readLong());
            break;
         case 7:
            problemInfo.setLongServicePercent(readDouble());
            break;
         case 8:
            problemInfo.setLongCaches(readLong());
            break;
         case 9:
            problemInfo.setLongCachePercent(readDouble());
            break;
      }
   }

   @Override
   public void visitService(Service service) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitServiceChildren(service, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitServiceChildren(Service service, int _field, int _type) {
      switch (_field) {
         case 33:
            BaseInfo baseInfo = new BaseInfo();

            visitBaseInfo(baseInfo);
            m_linker.onBaseInfo(service, baseInfo);
            break;
      }
   }

   @Override
   public void visitSql(Sql sql) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitSqlChildren(sql, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitSqlChildren(Sql sql, int _field, int _type) {
      switch (_field) {
         case 33:
            BaseInfo baseInfo = new BaseInfo();

            visitBaseInfo(baseInfo);
            m_linker.onBaseInfo(sql, baseInfo);
            break;
      }
   }

   @Override
   public void visitUrl(Url url) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitUrlChildren(url, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitUrlChildren(Url url, int _field, int _type) {
      switch (_field) {
         case 33:
            BaseInfo baseInfo = new BaseInfo();

            visitBaseInfo(baseInfo);
            m_linker.onBaseInfo(url, baseInfo);
            break;
      }
   }

   @Override
   public void visitWebCache(WebCache webCache) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitWebCacheChildren(webCache, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitWebCacheChildren(WebCache webCache, int _field, int _type) {
      switch (_field) {
         case 33:
            BaseCacheInfo baseCacheInfo = new BaseCacheInfo();

            visitBaseCacheInfo(baseCacheInfo);
            m_linker.onBaseCacheInfo(webCache, baseCacheInfo);
            break;
      }
   }

   private java.util.Date readDate() {
      try {
         return new java.util.Date(readVarint(64));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private double readDouble() {
      try {
         return m_in.readDouble();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private int readInt() {
      try {
         return (int) readVarint(32);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private long readLong() {
      try {
         return readVarint(64);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private String readString() {
      try {
         return m_in.readUTF();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private byte readTag() {
      try {
         return m_in.readByte();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   protected long readVarint(final int length) throws IOException {
      int shift = 0;
      long result = 0;

      while (shift < length) {
         final byte b = m_in.readByte();
         result |= (long) (b & 0x7F) << shift;
         if ((b & 0x80) == 0) {
            return result;
         }
         shift += 7;
      }

      throw new RuntimeException("Malformed variable int " + length + "!");
   }
}
