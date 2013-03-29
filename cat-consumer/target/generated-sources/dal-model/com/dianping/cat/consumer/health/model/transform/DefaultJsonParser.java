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
import static com.dianping.cat.consumer.health.model.Constants.ELEMENT_DOMAINNAMES;
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

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

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

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private HealthReport m_root;

   private boolean m_inElements = false;

   public static HealthReport parse(InputStream in) throws IOException {
      return parse(new InputStreamReader(in, "utf-8"));
   }

   public static HealthReport parse(Reader reader) throws IOException {
      return new DefaultJsonParser().parse(new JsonReader(reader));
   }

   public static HealthReport parse(String json) throws IOException {
      return parse(new StringReader(json));
   }

   @SuppressWarnings("unchecked")
   protected <T> T convert(Class<T> type, String value, T defaultValue) {
      if (value == null || value.length() == 0) {
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

   protected void onArrayBegin() {
      Object parent = m_objs.peek();
      String tag = m_tags.peek();

      if (parent instanceof HealthReport) {
         if (ELEMENT_DOMAINNAMES.equals(tag)) {
            m_objs.push(parent);
            m_inElements = true;
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
      }
   }

   protected void onArrayEnd() {
      m_objs.pop();
      m_tags.pop();

      m_inElements = false;

   }
   protected void onName(String name) {
      if (m_inElements) {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof HealthReport) {
            if (ELEMENT_DOMAINNAMES.equals(tag)) {
               ((HealthReport) parent).addDomain(name);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      } else {
         m_tags.push(name);
      }
   }

   protected void onObjectBegin() {
      if (m_objs.isEmpty()) { // root
         m_root = new HealthReport();
         m_objs.push(m_root);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof HealthReport) {
            if (ENTITY_PROBLEM_INFO.equals(tag)) {
               ProblemInfo problemInfo = new ProblemInfo();

               m_linker.onProblemInfo((HealthReport) parent, problemInfo);
               m_objs.push(problemInfo);
            } else if (ENTITY_URL.equals(tag)) {
               Url url = new Url();

               m_linker.onUrl((HealthReport) parent, url);
               m_objs.push(url);
            } else if (ENTITY_SERVICE.equals(tag)) {
               Service service = new Service();

               m_linker.onService((HealthReport) parent, service);
               m_objs.push(service);
            } else if (ENTITY_CALL.equals(tag)) {
               Call call = new Call();

               m_linker.onCall((HealthReport) parent, call);
               m_objs.push(call);
            } else if (ENTITY_SQL.equals(tag)) {
               Sql sql = new Sql();

               m_linker.onSql((HealthReport) parent, sql);
               m_objs.push(sql);
            } else if (ENTITY_WEB_CACHE.equals(tag)) {
               WebCache webCache = new WebCache();

               m_linker.onWebCache((HealthReport) parent, webCache);
               m_objs.push(webCache);
            } else if (ENTITY_KVDB_CACHE.equals(tag)) {
               KvdbCache kvdbCache = new KvdbCache();

               m_linker.onKvdbCache((HealthReport) parent, kvdbCache);
               m_objs.push(kvdbCache);
            } else if (ENTITY_MEM_CACHE.equals(tag)) {
               MemCache memCache = new MemCache();

               m_linker.onMemCache((HealthReport) parent, memCache);
               m_objs.push(memCache);
            } else if (ENTITY_CLIENTSERVICE.equals(tag)) {
               ClientService clientService = new ClientService();

               m_linker.onClientService((HealthReport) parent, clientService);
               m_objs.push(clientService);
            } else if (ENTITY_MACHINE_INFO.equals(tag)) {
               MachineInfo machineInfo = new MachineInfo();

               m_linker.onMachineInfo((HealthReport) parent, machineInfo);
               m_objs.push(machineInfo);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Url) {
            if (ENTITY_BASE_INFO.equals(tag)) {
               BaseInfo baseInfo = new BaseInfo();

               m_linker.onBaseInfo((Url) parent, baseInfo);
               m_objs.push(baseInfo);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Service) {
            if (ENTITY_BASE_INFO.equals(tag)) {
               BaseInfo baseInfo = new BaseInfo();

               m_linker.onBaseInfo((Service) parent, baseInfo);
               m_objs.push(baseInfo);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Call) {
            if (ENTITY_BASE_INFO.equals(tag)) {
               BaseInfo baseInfo = new BaseInfo();

               m_linker.onBaseInfo((Call) parent, baseInfo);
               m_objs.push(baseInfo);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Sql) {
            if (ENTITY_BASE_INFO.equals(tag)) {
               BaseInfo baseInfo = new BaseInfo();

               m_linker.onBaseInfo((Sql) parent, baseInfo);
               m_objs.push(baseInfo);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof WebCache) {
            if (ENTITY_BASE_CACHE_INFO.equals(tag)) {
               BaseCacheInfo baseCacheInfo = new BaseCacheInfo();

               m_linker.onBaseCacheInfo((WebCache) parent, baseCacheInfo);
               m_objs.push(baseCacheInfo);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof KvdbCache) {
            if (ENTITY_BASE_CACHE_INFO.equals(tag)) {
               BaseCacheInfo baseCacheInfo = new BaseCacheInfo();

               m_linker.onBaseCacheInfo((KvdbCache) parent, baseCacheInfo);
               m_objs.push(baseCacheInfo);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof MemCache) {
            if (ENTITY_BASE_CACHE_INFO.equals(tag)) {
               BaseCacheInfo baseCacheInfo = new BaseCacheInfo();

               m_linker.onBaseCacheInfo((MemCache) parent, baseCacheInfo);
               m_objs.push(baseCacheInfo);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof ClientService) {
            if (ENTITY_BASE_INFO.equals(tag)) {
               BaseInfo baseInfo = new BaseInfo();

               m_linker.onBaseInfo((ClientService) parent, baseInfo);
               m_objs.push(baseInfo);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      }
   }

   protected void onObjectEnd() {
      m_objs.pop();
      m_tags.pop();
   }

   protected void onValue(String value) {
      Object parent = m_objs.peek();
      String tag = m_tags.pop();

      if (parent instanceof HealthReport) {
         parseForHealthReport((HealthReport) parent, tag, value);
      } else if (parent instanceof ProblemInfo) {
         parseForProblemInfo((ProblemInfo) parent, tag, value);
      } else if (parent instanceof Url) {
         parseForUrl((Url) parent, tag, value);
      } else if (parent instanceof BaseInfo) {
         parseForBaseInfo((BaseInfo) parent, tag, value);
      } else if (parent instanceof Service) {
         parseForService((Service) parent, tag, value);
      } else if (parent instanceof Call) {
         parseForCall((Call) parent, tag, value);
      } else if (parent instanceof Sql) {
         parseForSql((Sql) parent, tag, value);
      } else if (parent instanceof WebCache) {
         parseForWebCache((WebCache) parent, tag, value);
      } else if (parent instanceof BaseCacheInfo) {
         parseForBaseCacheInfo((BaseCacheInfo) parent, tag, value);
      } else if (parent instanceof KvdbCache) {
         parseForKvdbCache((KvdbCache) parent, tag, value);
      } else if (parent instanceof MemCache) {
         parseForMemCache((MemCache) parent, tag, value);
      } else if (parent instanceof MachineInfo) {
         parseForMachineInfo((MachineInfo) parent, tag, value);
      } else if (parent instanceof ClientService) {
         parseForClientService((ClientService) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private HealthReport parse(JsonReader reader) throws IOException {
      try {
         reader.parse(this);
      } catch (EOFException e) {
         if (!m_objs.isEmpty()) {
            throw new EOFException(String.format("Unexpected end while parsing %s", m_tags));
         }
      }

      m_linker.finish();
      return m_root;
   }

   public void parseForBaseCacheInfo(BaseCacheInfo baseCacheInfo, String tag, String value) {
      if (ATTR_TOTAL.equals(tag)) {
         baseCacheInfo.setTotal(convert(Long.class, value, 0L));
      } else if (ATTR_RESPONSE_TIME.equals(tag)) {
         baseCacheInfo.setResponseTime(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_HIT_PERCENT.equals(tag)) {
         baseCacheInfo.setHitPercent(toNumber(value, "0.000000").doubleValue());
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, baseCacheInfo, m_tags));
      }
   }

   public void parseForBaseInfo(BaseInfo baseInfo, String tag, String value) {
      if (ATTR_TOTAL.equals(tag)) {
         baseInfo.setTotal(convert(Long.class, value, 0L));
      } else if (ATTR_RESPONSE_TIME.equals(tag)) {
         baseInfo.setResponseTime(toNumber(value, "0.00").doubleValue());
      } else if (ATTR_ERROR_TOTAL.equals(tag)) {
         baseInfo.setErrorTotal(convert(Long.class, value, 0L));
      } else if (ATTR_ERROR_PERCENT.equals(tag)) {
         baseInfo.setErrorPercent(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_SUCCESS_PERCENT.equals(tag)) {
         baseInfo.setSuccessPercent(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_TPS.equals(tag)) {
         baseInfo.setTps(toNumber(value, "0.00").doubleValue());
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, baseInfo, m_tags));
      }
   }

   public void parseForCall(Call call, String tag, String value) {
      if (ENTITY_BASE_INFO.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, call, m_tags));
      }
   }

   public void parseForClientService(ClientService clientService, String tag, String value) {
      if (ENTITY_BASE_INFO.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, clientService, m_tags));
      }
   }

   public void parseForHealthReport(HealthReport healthReport, String tag, String value) {
      if (ENTITY_PROBLEM_INFO.equals(tag) || ENTITY_URL.equals(tag) || ENTITY_SERVICE.equals(tag) || ENTITY_CALL.equals(tag) || ENTITY_SQL.equals(tag) || ENTITY_WEB_CACHE.equals(tag) || ENTITY_KVDB_CACHE.equals(tag) || ENTITY_MEM_CACHE.equals(tag) || ENTITY_CLIENTSERVICE.equals(tag) || ENTITY_MACHINE_INFO.equals(tag)) {
         // do nothing here
      } else if (ATTR_DOMAIN.equals(tag)) {
         healthReport.setDomain(value);
      } else if (ATTR_STARTTIME.equals(tag)) {
         healthReport.setStartTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ATTR_ENDTIME.equals(tag)) {
         healthReport.setEndTime(toDate(value, "yyyy-MM-dd HH:mm:ss"));
      } else if (ELEMENT_DOMAINNAMES.equals(tag)) {
         healthReport.addDomain(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, healthReport, m_tags));
      }
   }

   public void parseForKvdbCache(KvdbCache kvdbCache, String tag, String value) {
      if (ENTITY_BASE_CACHE_INFO.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, kvdbCache, m_tags));
      }
   }

   public void parseForMachineInfo(MachineInfo machineInfo, String tag, String value) {
      if (ATTR_NUMBERS.equals(tag)) {
         machineInfo.setNumbers(convert(Integer.class, value, 0));
      } else if (ATTR_AVG_LOAD.equals(tag)) {
         machineInfo.setAvgLoad(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MAX_LOAD.equals(tag)) {
         machineInfo.setAvgMaxLoad(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_LOAD_COUNT.equals(tag)) {
         machineInfo.setAvgLoadCount(convert(Integer.class, value, 0));
      } else if (ATTR_AVG_LOAD_SUM.equals(tag)) {
         machineInfo.setAvgLoadSum(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MAX_LOAD_MACHINE.equals(tag)) {
         machineInfo.setAvgMaxLoadMachine(value);
      } else if (ATTR_AVG_OLDGC.equals(tag)) {
         machineInfo.setAvgOldgc(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MAX_OLDGC.equals(tag)) {
         machineInfo.setAvgMaxOldgc(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_OLDGC_COUNT.equals(tag)) {
         machineInfo.setAvgOldgcCount(convert(Integer.class, value, 0));
      } else if (ATTR_AVG_OLDGC_SUM.equals(tag)) {
         machineInfo.setAvgOldgcSum(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MAX_OLDGC_MACHINE.equals(tag)) {
         machineInfo.setAvgMaxOldgcMachine(value);
      } else if (ATTR_AVG_HTTP.equals(tag)) {
         machineInfo.setAvgHttp(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MAX_HTTP.equals(tag)) {
         machineInfo.setAvgMaxHttp(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_HTTP_COUNT.equals(tag)) {
         machineInfo.setAvgHttpCount(convert(Integer.class, value, 0));
      } else if (ATTR_AVG_HTTP_SUM.equals(tag)) {
         machineInfo.setAvgHttpSum(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MAX_HTTP_MACHINE.equals(tag)) {
         machineInfo.setAvgMaxHttpMachine(value);
      } else if (ATTR_AVG_PIGEON.equals(tag)) {
         machineInfo.setAvgPigeon(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MAX_PIGEON.equals(tag)) {
         machineInfo.setAvgMaxPigeon(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_PIGEON_COUNT.equals(tag)) {
         machineInfo.setAvgPigeonCount(convert(Integer.class, value, 0));
      } else if (ATTR_AVG_PIGEON_SUM.equals(tag)) {
         machineInfo.setAvgPigeonSum(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MAX_PIGEON_MACHINE.equals(tag)) {
         machineInfo.setAvgMaxPigeonMachine(value);
      } else if (ATTR_AVG_MEMORY_USED.equals(tag)) {
         machineInfo.setAvgMemoryUsed(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MAX_MEMORY_USED.equals(tag)) {
         machineInfo.setAvgMaxMemoryUsed(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MEMORY_USED_COUNT.equals(tag)) {
         machineInfo.setAvgMemoryUsedCount(convert(Integer.class, value, 0));
      } else if (ATTR_AVG_MEMORY_USED_SUM.equals(tag)) {
         machineInfo.setAvgMemoryUsedSum(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_AVG_MAX_MEMORY_USED_MACHINE.equals(tag)) {
         machineInfo.setAvgMaxMemoryUsedMachine(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, machineInfo, m_tags));
      }
   }

   public void parseForMemCache(MemCache memCache, String tag, String value) {
      if (ENTITY_BASE_CACHE_INFO.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, memCache, m_tags));
      }
   }

   public void parseForProblemInfo(ProblemInfo problemInfo, String tag, String value) {
      if (ATTR_EXCEPTIONS.equals(tag)) {
         problemInfo.setExceptions(convert(Long.class, value, 0L));
      } else if (ATTR_LONGSQLS.equals(tag)) {
         problemInfo.setLongSqls(convert(Long.class, value, 0L));
      } else if (ATTR_LONG_SQLPERCENT.equals(tag)) {
         problemInfo.setLongSqlPercent(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_LONGURLS.equals(tag)) {
         problemInfo.setLongUrls(convert(Long.class, value, 0L));
      } else if (ATTR_LONG_URLPERCENT.equals(tag)) {
         problemInfo.setLongUrlPercent(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_LONGSERVICES.equals(tag)) {
         problemInfo.setLongServices(convert(Long.class, value, 0L));
      } else if (ATTR_LONG_SERVICEPERCENT.equals(tag)) {
         problemInfo.setLongServicePercent(toNumber(value, "0.000000").doubleValue());
      } else if (ATTR_LONGCACHES.equals(tag)) {
         problemInfo.setLongCaches(convert(Long.class, value, 0L));
      } else if (ATTR_LONG_CACHEPERCENT.equals(tag)) {
         problemInfo.setLongCachePercent(toNumber(value, "0.000000").doubleValue());
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, problemInfo, m_tags));
      }
   }

   public void parseForService(Service service, String tag, String value) {
      if (ENTITY_BASE_INFO.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, service, m_tags));
      }
   }

   public void parseForSql(Sql sql, String tag, String value) {
      if (ENTITY_BASE_INFO.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, sql, m_tags));
      }
   }

   public void parseForUrl(Url url, String tag, String value) {
      if (ENTITY_BASE_INFO.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, url, m_tags));
      }
   }

   public void parseForWebCache(WebCache webCache, String tag, String value) {
      if (ENTITY_BASE_CACHE_INFO.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, webCache, m_tags));
      }
   }


   protected java.util.Date toDate(String str, String format) {
      try {
         return new java.text.SimpleDateFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse date(%s) in format(%s)!", str, format), e);
      }
   }

   protected Number toNumber(String str, String format) {
      try {
         return new java.text.DecimalFormat(format).parse(str);
      } catch (java.text.ParseException e) {
         throw new RuntimeException(String.format("Unable to parse number(%s) in format(%s)!", str, format), e);
      }
   }

   static class JsonReader {
      private Reader m_reader;

      private char[] m_buffer = new char[2048];

      private int m_size;

      private int m_index;

      public JsonReader(Reader reader) {
         m_reader = reader;
      }

      private char next() throws IOException {
         if (m_index >= m_size) {
            m_size = m_reader.read(m_buffer);
            m_index = 0;

            if (m_size == -1) {
               throw new EOFException();
            }
         }

         return m_buffer[m_index++];
      }

      public void parse(DefaultJsonParser parser) throws IOException {
         StringBuilder sb = new StringBuilder();
         boolean flag = false;

         while (true) {
            char ch = next();

            switch (ch) {
            case ' ':
            case '\t':
            case '\r':
            case '\n':
               break;
            case '{':
               parser.onObjectBegin();
               flag = false;
               break;
            case '}':
               if (flag) { // have value
                  parser.onValue(sb.toString());
                  sb.setLength(0);
               }

               parser.onObjectEnd();
               flag = false;
               break;
            case '\'':
            case '"':
               while (true) {
                  char ch2 = next();

                  if (ch2 != ch) {
                     if (ch2 == '\\') {
                        char ch3 = next();

                        sb.append(ch3);
                     } else {
                        sb.append(ch2);
                     }
                  } else {
                     if (!flag) {
                        parser.onName(sb.toString());
                     } else {
                        parser.onValue(sb.toString());
                        flag = false;
                     }

                     sb.setLength(0);
                     break;
                  }
               }

               break;
            case ':':
               if (sb.length() != 0) {
                  parser.onName(sb.toString());
                  sb.setLength(0);
               }

               flag = true;
               break;
            case ',':
               if (sb.length() != 0) {
                  if (!flag) {
                     parser.onName(sb.toString());
                  } else {
                     parser.onValue(sb.toString());
                  }

                  sb.setLength(0);
               }

               flag = false;
               break;
            case '[':
               parser.onArrayBegin();
               flag = false;
               break;
            case ']':
               parser.onArrayEnd();
               flag = false;
               break;
            default:
               sb.append(ch);
               break;
            }
         }
      }
   }
}
