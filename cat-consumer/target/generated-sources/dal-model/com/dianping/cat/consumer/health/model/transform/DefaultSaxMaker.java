package com.dianping.cat.consumer.health.model.transform;

import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_HTTP;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_HTTP_COUNT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_HTTP_SUM;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_LOAD;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_LOAD_COUNT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_LOAD_SUM;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MAX_HTTP;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MAX_HTTP_MACHINE;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MAX_LOAD;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MAX_LOAD_MACHINE;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MAX_MEMORY_USED;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MAX_MEMORY_USED_MACHINE;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MAX_OLDGC;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MAX_OLDGC_MACHINE;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MAX_PIGEON;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MAX_PIGEON_MACHINE;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MEMORY_USED;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MEMORY_USED_COUNT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_MEMORY_USED_SUM;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_OLDGC;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_OLDGC_COUNT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_OLDGC_SUM;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_PIGEON;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_PIGEON_COUNT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_AVG_PIGEON_SUM;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_ERROR_PERCENT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_ERROR_TOTAL;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_EXCEPTIONS;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_HIT_PERCENT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_LONG_CACHEPERCENT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_LONG_SERVICEPERCENT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_LONG_SQLPERCENT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_LONG_URLPERCENT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_LONGCACHES;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_LONGSERVICES;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_LONGSQLS;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_LONGURLS;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_NUMBERS;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_RESPONSE_TIME;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_SUCCESS_PERCENT;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_TOTAL;
import static com.dianping.cat.consumer.health.model.Constants.ATTR_TPS;

import org.xml.sax.Attributes;

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

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public BaseCacheInfo buildBaseCacheInfo(Attributes attributes) {
      String total = attributes.getValue(ATTR_TOTAL);
      String responseTime = attributes.getValue(ATTR_RESPONSE_TIME);
      String hitPercent = attributes.getValue(ATTR_HIT_PERCENT);
      BaseCacheInfo baseCacheInfo = new BaseCacheInfo();

      if (total != null) {
         baseCacheInfo.setTotal(convert(Long.class, total, 0L));
      }

      if (responseTime != null) {
         baseCacheInfo.setResponseTime(toNumber(responseTime, "0.00", 0).doubleValue());
      }

      if (hitPercent != null) {
         baseCacheInfo.setHitPercent(toNumber(hitPercent, "0.000000", 0).doubleValue());
      }

      return baseCacheInfo;
   }

   @Override
   public BaseInfo buildBaseInfo(Attributes attributes) {
      String total = attributes.getValue(ATTR_TOTAL);
      String responseTime = attributes.getValue(ATTR_RESPONSE_TIME);
      String errorTotal = attributes.getValue(ATTR_ERROR_TOTAL);
      String errorPercent = attributes.getValue(ATTR_ERROR_PERCENT);
      String successPercent = attributes.getValue(ATTR_SUCCESS_PERCENT);
      String tps = attributes.getValue(ATTR_TPS);
      BaseInfo baseInfo = new BaseInfo();

      if (total != null) {
         baseInfo.setTotal(convert(Long.class, total, 0L));
      }

      if (responseTime != null) {
         baseInfo.setResponseTime(toNumber(responseTime, "0.00", 0).doubleValue());
      }

      if (errorTotal != null) {
         baseInfo.setErrorTotal(convert(Long.class, errorTotal, 0L));
      }

      if (errorPercent != null) {
         baseInfo.setErrorPercent(toNumber(errorPercent, "0.000000", 0).doubleValue());
      }

      if (successPercent != null) {
         baseInfo.setSuccessPercent(toNumber(successPercent, "0.000000", 0).doubleValue());
      }

      if (tps != null) {
         baseInfo.setTps(toNumber(tps, "0.00", 0).doubleValue());
      }

      return baseInfo;
   }

   @Override
   public Call buildCall(Attributes attributes) {
      Call call = new Call();

      return call;
   }

   @Override
   public ClientService buildClientService(Attributes attributes) {
      ClientService clientService = new ClientService();

      return clientService;
   }

   @Override
   public String buildDomain(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public HealthReport buildHealthReport(Attributes attributes) {
      String domain = attributes.getValue(ATTR_DOMAIN);
      String startTime = attributes.getValue(ATTR_STARTTIME);
      String endTime = attributes.getValue(ATTR_ENDTIME);
      HealthReport healthReport = new HealthReport(domain);

      if (startTime != null) {
         healthReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         healthReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return healthReport;
   }

   @Override
   public KvdbCache buildKvdbCache(Attributes attributes) {
      KvdbCache kvdbCache = new KvdbCache();

      return kvdbCache;
   }

   @Override
   public MachineInfo buildMachineInfo(Attributes attributes) {
      String numbers = attributes.getValue(ATTR_NUMBERS);
      String avgLoad = attributes.getValue(ATTR_AVG_LOAD);
      String avgMaxLoad = attributes.getValue(ATTR_AVG_MAX_LOAD);
      String avgLoadCount = attributes.getValue(ATTR_AVG_LOAD_COUNT);
      String avgLoadSum = attributes.getValue(ATTR_AVG_LOAD_SUM);
      String avgMaxLoadMachine = attributes.getValue(ATTR_AVG_MAX_LOAD_MACHINE);
      String avgOldgc = attributes.getValue(ATTR_AVG_OLDGC);
      String avgMaxOldgc = attributes.getValue(ATTR_AVG_MAX_OLDGC);
      String avgOldgcCount = attributes.getValue(ATTR_AVG_OLDGC_COUNT);
      String avgOldgcSum = attributes.getValue(ATTR_AVG_OLDGC_SUM);
      String avgMaxOldgcMachine = attributes.getValue(ATTR_AVG_MAX_OLDGC_MACHINE);
      String avgHttp = attributes.getValue(ATTR_AVG_HTTP);
      String avgMaxHttp = attributes.getValue(ATTR_AVG_MAX_HTTP);
      String avgHttpCount = attributes.getValue(ATTR_AVG_HTTP_COUNT);
      String avgHttpSum = attributes.getValue(ATTR_AVG_HTTP_SUM);
      String avgMaxHttpMachine = attributes.getValue(ATTR_AVG_MAX_HTTP_MACHINE);
      String avgPigeon = attributes.getValue(ATTR_AVG_PIGEON);
      String avgMaxPigeon = attributes.getValue(ATTR_AVG_MAX_PIGEON);
      String avgPigeonCount = attributes.getValue(ATTR_AVG_PIGEON_COUNT);
      String avgPigeonSum = attributes.getValue(ATTR_AVG_PIGEON_SUM);
      String avgMaxPigeonMachine = attributes.getValue(ATTR_AVG_MAX_PIGEON_MACHINE);
      String avgMemoryUsed = attributes.getValue(ATTR_AVG_MEMORY_USED);
      String avgMaxMemoryUsed = attributes.getValue(ATTR_AVG_MAX_MEMORY_USED);
      String avgMemoryUsedCount = attributes.getValue(ATTR_AVG_MEMORY_USED_COUNT);
      String avgMemoryUsedSum = attributes.getValue(ATTR_AVG_MEMORY_USED_SUM);
      String avgMaxMemoryUsedMachine = attributes.getValue(ATTR_AVG_MAX_MEMORY_USED_MACHINE);
      MachineInfo machineInfo = new MachineInfo();

      if (numbers != null) {
         machineInfo.setNumbers(convert(Integer.class, numbers, 0));
      }

      if (avgLoad != null) {
         machineInfo.setAvgLoad(toNumber(avgLoad, "0.000000", 0).doubleValue());
      }

      if (avgMaxLoad != null) {
         machineInfo.setAvgMaxLoad(toNumber(avgMaxLoad, "0.000000", 0).doubleValue());
      }

      if (avgLoadCount != null) {
         machineInfo.setAvgLoadCount(convert(Integer.class, avgLoadCount, 0));
      }

      if (avgLoadSum != null) {
         machineInfo.setAvgLoadSum(toNumber(avgLoadSum, "0.000000", 0).doubleValue());
      }

      if (avgMaxLoadMachine != null) {
         machineInfo.setAvgMaxLoadMachine(avgMaxLoadMachine);
      }

      if (avgOldgc != null) {
         machineInfo.setAvgOldgc(toNumber(avgOldgc, "0.000000", 0).doubleValue());
      }

      if (avgMaxOldgc != null) {
         machineInfo.setAvgMaxOldgc(toNumber(avgMaxOldgc, "0.000000", 0).doubleValue());
      }

      if (avgOldgcCount != null) {
         machineInfo.setAvgOldgcCount(convert(Integer.class, avgOldgcCount, 0));
      }

      if (avgOldgcSum != null) {
         machineInfo.setAvgOldgcSum(toNumber(avgOldgcSum, "0.000000", 0).doubleValue());
      }

      if (avgMaxOldgcMachine != null) {
         machineInfo.setAvgMaxOldgcMachine(avgMaxOldgcMachine);
      }

      if (avgHttp != null) {
         machineInfo.setAvgHttp(toNumber(avgHttp, "0.000000", 0).doubleValue());
      }

      if (avgMaxHttp != null) {
         machineInfo.setAvgMaxHttp(toNumber(avgMaxHttp, "0.000000", 0).doubleValue());
      }

      if (avgHttpCount != null) {
         machineInfo.setAvgHttpCount(convert(Integer.class, avgHttpCount, 0));
      }

      if (avgHttpSum != null) {
         machineInfo.setAvgHttpSum(toNumber(avgHttpSum, "0.000000", 0).doubleValue());
      }

      if (avgMaxHttpMachine != null) {
         machineInfo.setAvgMaxHttpMachine(avgMaxHttpMachine);
      }

      if (avgPigeon != null) {
         machineInfo.setAvgPigeon(toNumber(avgPigeon, "0.000000", 0).doubleValue());
      }

      if (avgMaxPigeon != null) {
         machineInfo.setAvgMaxPigeon(toNumber(avgMaxPigeon, "0.000000", 0).doubleValue());
      }

      if (avgPigeonCount != null) {
         machineInfo.setAvgPigeonCount(convert(Integer.class, avgPigeonCount, 0));
      }

      if (avgPigeonSum != null) {
         machineInfo.setAvgPigeonSum(toNumber(avgPigeonSum, "0.000000", 0).doubleValue());
      }

      if (avgMaxPigeonMachine != null) {
         machineInfo.setAvgMaxPigeonMachine(avgMaxPigeonMachine);
      }

      if (avgMemoryUsed != null) {
         machineInfo.setAvgMemoryUsed(toNumber(avgMemoryUsed, "0.000000", 0).doubleValue());
      }

      if (avgMaxMemoryUsed != null) {
         machineInfo.setAvgMaxMemoryUsed(toNumber(avgMaxMemoryUsed, "0.000000", 0).doubleValue());
      }

      if (avgMemoryUsedCount != null) {
         machineInfo.setAvgMemoryUsedCount(convert(Integer.class, avgMemoryUsedCount, 0));
      }

      if (avgMemoryUsedSum != null) {
         machineInfo.setAvgMemoryUsedSum(toNumber(avgMemoryUsedSum, "0.000000", 0).doubleValue());
      }

      if (avgMaxMemoryUsedMachine != null) {
         machineInfo.setAvgMaxMemoryUsedMachine(avgMaxMemoryUsedMachine);
      }

      return machineInfo;
   }

   @Override
   public MemCache buildMemCache(Attributes attributes) {
      MemCache memCache = new MemCache();

      return memCache;
   }

   @Override
   public ProblemInfo buildProblemInfo(Attributes attributes) {
      String exceptions = attributes.getValue(ATTR_EXCEPTIONS);
      String longSqls = attributes.getValue(ATTR_LONGSQLS);
      String longSqlPercent = attributes.getValue(ATTR_LONG_SQLPERCENT);
      String longUrls = attributes.getValue(ATTR_LONGURLS);
      String longUrlPercent = attributes.getValue(ATTR_LONG_URLPERCENT);
      String longServices = attributes.getValue(ATTR_LONGSERVICES);
      String longServicePercent = attributes.getValue(ATTR_LONG_SERVICEPERCENT);
      String longCaches = attributes.getValue(ATTR_LONGCACHES);
      String longCachePercent = attributes.getValue(ATTR_LONG_CACHEPERCENT);
      ProblemInfo problemInfo = new ProblemInfo();

      if (exceptions != null) {
         problemInfo.setExceptions(convert(Long.class, exceptions, 0L));
      }

      if (longSqls != null) {
         problemInfo.setLongSqls(convert(Long.class, longSqls, 0L));
      }

      if (longSqlPercent != null) {
         problemInfo.setLongSqlPercent(toNumber(longSqlPercent, "0.000000", 0).doubleValue());
      }

      if (longUrls != null) {
         problemInfo.setLongUrls(convert(Long.class, longUrls, 0L));
      }

      if (longUrlPercent != null) {
         problemInfo.setLongUrlPercent(toNumber(longUrlPercent, "0.000000", 0).doubleValue());
      }

      if (longServices != null) {
         problemInfo.setLongServices(convert(Long.class, longServices, 0L));
      }

      if (longServicePercent != null) {
         problemInfo.setLongServicePercent(toNumber(longServicePercent, "0.000000", 0).doubleValue());
      }

      if (longCaches != null) {
         problemInfo.setLongCaches(convert(Long.class, longCaches, 0L));
      }

      if (longCachePercent != null) {
         problemInfo.setLongCachePercent(toNumber(longCachePercent, "0.000000", 0).doubleValue());
      }

      return problemInfo;
   }

   @Override
   public Service buildService(Attributes attributes) {
      Service service = new Service();

      return service;
   }

   @Override
   public Sql buildSql(Attributes attributes) {
      Sql sql = new Sql();

      return sql;
   }

   @Override
   public Url buildUrl(Attributes attributes) {
      Url url = new Url();

      return url;
   }

   @Override
   public WebCache buildWebCache(Attributes attributes) {
      WebCache webCache = new WebCache();

      return webCache;
   }

   @SuppressWarnings("unchecked")
   protected <T> T convert(Class<T> type, String value, T defaultValue) {
      if (value == null) {
         return defaultValue;
      }

      if (type == Boolean.class) {
         return (T) Boolean.valueOf(value);
      } else if (type == Integer.class) {
         return (T) Integer.valueOf(value);
      } else if (type == Long.class) {
         return (T) Long.valueOf(value);
      } else if (type == Short.class) {
         return (T) Short.valueOf(value);
      } else if (type == Float.class) {
         return (T) Float.valueOf(value);
      } else if (type == Double.class) {
         return (T) Double.valueOf(value);
      } else if (type == Byte.class) {
         return (T) Byte.valueOf(value);
      } else if (type == Character.class) {
         return (T) (Character) value.charAt(0);
      } else {
         return (T) value;
      }
   }

   protected java.util.Date toDate(String str, String format, java.util.Date defaultValue) {
      if (str == null || str.length() == 0) {
         return defaultValue;
      }

      try {
         return new java.text.SimpleDateFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse date(%s) in format(%s)!", str, format), e);
      }
   }

   protected Number toNumber(String str, String format, Number defaultValue) {
      if (str == null || str.length() == 0) {
         return defaultValue;
      }

      try {
         return new java.text.DecimalFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse number(%s) in format(%s)!", str, format), e);
      }
   }
}
