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

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.health.model.IEntity;
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

public class DefaultJsonBuilder implements IVisitor {

   private int m_level;

   private StringBuilder m_sb = new StringBuilder(2048);

   private boolean m_compact;

   public DefaultJsonBuilder() {
      this(false);
   }

   public DefaultJsonBuilder(boolean compact) {
      m_compact = compact;
   }

   protected void arrayBegin(String name) {
      indent();
      m_sb.append('"').append(name).append(m_compact ? "\":[" : "\": [\r\n");
      m_level++;
   }

   protected void arrayEnd(String name) {
      m_level--;

      trimComma();
      indent();
      m_sb.append("],").append(m_compact ? "" : "\r\n");
   }

   protected void attributes(Map<String, String> dynamicAttributes, Object... nameValues) {
      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            if (attrValue instanceof List) {
               @SuppressWarnings("unchecked")
               List<Object> list = (List<Object>) attrValue;

               if (!list.isEmpty()) {
                  indent();
                  m_sb.append('"').append(attrName).append(m_compact ? "\":[" : "\": [");

                  for (Object item : list) {
                     m_sb.append(' ');
                     toString(m_sb, item);
                     m_sb.append(',');
                  }

                  m_sb.setLength(m_sb.length() - 1);
                  m_sb.append(m_compact ? "]," : " ],\r\n");
               }
            } else {
               if (m_compact) {
                  m_sb.append('"').append(attrName).append("\":");
                  toString(m_sb, attrValue);
                  m_sb.append(",");
               } else {
                  indent();
                  m_sb.append('"').append(attrName).append("\": ");
                  toString(m_sb, attrValue);
                  m_sb.append(",\r\n");
               }
            }
         }
      }

      if (dynamicAttributes != null) {
         for (Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            if (m_compact) {
               m_sb.append('"').append(e.getKey()).append("\":");
               toString(m_sb, e.getValue());
               m_sb.append(",");
            } else {
               indent();
               m_sb.append('"').append(e.getKey()).append("\": ");
               toString(m_sb, e.getValue());
               m_sb.append(",\r\n");
            }
         }
      }
   }

   public String buildJson(IEntity<?> entity) {
      m_sb.setLength(0);
      entity.accept(this);
      return m_sb.toString();
   }

   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void objectBegin(String name) {
      indent();

      if (name == null) {
         m_sb.append("{").append(m_compact ? "" : "\r\n");
      } else {
         m_sb.append('"').append(name).append(m_compact ? "\":{" : "\": {\r\n");
      }

      m_level++;
   }

   protected void objectEnd(String name) {
      m_level--;

      trimComma();
      indent();
      m_sb.append(m_compact ? "}," : "},\r\n");
   }

   protected String toString(java.util.Date date, String format) {
      if (date != null) {
         return new java.text.SimpleDateFormat(format).format(date);
      } else {
         return null;
      }
   }

   protected String toString(Number number, String format) {
      if (number != null) {
         return new java.text.DecimalFormat(format).format(number);
      } else {
         return null;
      }
   }

   protected void toString(StringBuilder sb, Object value) {
      if (value instanceof String) {
         sb.append('"').append(value).append('"');
      } else if (value instanceof Boolean || value instanceof Number) {
         sb.append(value);
      } else {
         sb.append('"').append(value).append('"');
      }
   }

   protected void trimComma() {
      int len = m_sb.length();

      if (m_compact) {
         if (len > 1 && m_sb.charAt(len - 1) == ',') {
            m_sb.replace(len - 1, len, "");
         }
      } else {
         if (len > 3 && m_sb.charAt(len - 3) == ',') {
            m_sb.replace(len - 3, len - 2, "");
         }
      }
   }

   @Override
   public void visitBaseCacheInfo(BaseCacheInfo baseCacheInfo) {
      attributes(null, ATTR_TOTAL, baseCacheInfo.getTotal(), ATTR_RESPONSE_TIME, toString(baseCacheInfo.getResponseTime(), "0.00"), ATTR_HIT_PERCENT, toString(baseCacheInfo.getHitPercent(), "0.000000"));
   }

   @Override
   public void visitBaseInfo(BaseInfo baseInfo) {
      attributes(null, ATTR_TOTAL, baseInfo.getTotal(), ATTR_RESPONSE_TIME, toString(baseInfo.getResponseTime(), "0.00"), ATTR_ERROR_TOTAL, baseInfo.getErrorTotal(), ATTR_ERROR_PERCENT, toString(baseInfo.getErrorPercent(), "0.000000"), ATTR_SUCCESS_PERCENT, toString(baseInfo.getSuccessPercent(), "0.000000"), ATTR_TPS, toString(baseInfo.getTps(), "0.00"));
   }

   @Override
   public void visitCall(Call call) {

      if (call.getBaseInfo() != null) {
         objectBegin(ENTITY_BASE_INFO);
         visitBaseInfo(call.getBaseInfo());
         objectEnd(ENTITY_BASE_INFO);
      }
   }

   @Override
   public void visitClientService(ClientService clientService) {

      if (clientService.getBaseInfo() != null) {
         objectBegin(ENTITY_BASE_INFO);
         visitBaseInfo(clientService.getBaseInfo());
         objectEnd(ENTITY_BASE_INFO);
      }
   }

   @Override
   public void visitHealthReport(HealthReport healthReport) {
      objectBegin(null);
      attributes(null, ATTR_DOMAIN, healthReport.getDomain(), ATTR_STARTTIME, toString(healthReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(healthReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!healthReport.getDomainNames().isEmpty()) {
         arrayBegin(ELEMENT_DOMAINNAMES);

         for (String domain : healthReport.getDomainNames()) {
            indent();
            m_sb.append('"').append(domain).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DOMAINNAMES);
      }

      if (healthReport.getProblemInfo() != null) {
         objectBegin(ENTITY_PROBLEM_INFO);
         visitProblemInfo(healthReport.getProblemInfo());
         objectEnd(ENTITY_PROBLEM_INFO);
      }

      if (healthReport.getUrl() != null) {
         objectBegin(ENTITY_URL);
         visitUrl(healthReport.getUrl());
         objectEnd(ENTITY_URL);
      }

      if (healthReport.getService() != null) {
         objectBegin(ENTITY_SERVICE);
         visitService(healthReport.getService());
         objectEnd(ENTITY_SERVICE);
      }

      if (healthReport.getCall() != null) {
         objectBegin(ENTITY_CALL);
         visitCall(healthReport.getCall());
         objectEnd(ENTITY_CALL);
      }

      if (healthReport.getSql() != null) {
         objectBegin(ENTITY_SQL);
         visitSql(healthReport.getSql());
         objectEnd(ENTITY_SQL);
      }

      if (healthReport.getWebCache() != null) {
         objectBegin(ENTITY_WEB_CACHE);
         visitWebCache(healthReport.getWebCache());
         objectEnd(ENTITY_WEB_CACHE);
      }

      if (healthReport.getKvdbCache() != null) {
         objectBegin(ENTITY_KVDB_CACHE);
         visitKvdbCache(healthReport.getKvdbCache());
         objectEnd(ENTITY_KVDB_CACHE);
      }

      if (healthReport.getMemCache() != null) {
         objectBegin(ENTITY_MEM_CACHE);
         visitMemCache(healthReport.getMemCache());
         objectEnd(ENTITY_MEM_CACHE);
      }

      if (healthReport.getClientService() != null) {
         objectBegin(ENTITY_CLIENTSERVICE);
         visitClientService(healthReport.getClientService());
         objectEnd(ENTITY_CLIENTSERVICE);
      }

      if (healthReport.getMachineInfo() != null) {
         objectBegin(ENTITY_MACHINE_INFO);
         visitMachineInfo(healthReport.getMachineInfo());
         objectEnd(ENTITY_MACHINE_INFO);
      }

      objectEnd(null);
      trimComma();
   }

   @Override
   public void visitKvdbCache(KvdbCache kvdbCache) {

      if (kvdbCache.getBaseCacheInfo() != null) {
         objectBegin(ENTITY_BASE_CACHE_INFO);
         visitBaseCacheInfo(kvdbCache.getBaseCacheInfo());
         objectEnd(ENTITY_BASE_CACHE_INFO);
      }
   }

   @Override
   public void visitMachineInfo(MachineInfo machineInfo) {
      attributes(null, ATTR_NUMBERS, machineInfo.getNumbers(), ATTR_AVG_LOAD, toString(machineInfo.getAvgLoad(), "0.000000"), ATTR_AVG_MAX_LOAD, toString(machineInfo.getAvgMaxLoad(), "0.000000"), ATTR_AVG_LOAD_COUNT, machineInfo.getAvgLoadCount(), ATTR_AVG_LOAD_SUM, toString(machineInfo.getAvgLoadSum(), "0.000000"), ATTR_AVG_MAX_LOAD_MACHINE, machineInfo.getAvgMaxLoadMachine(), ATTR_AVG_OLDGC, toString(machineInfo.getAvgOldgc(), "0.000000"), ATTR_AVG_MAX_OLDGC, toString(machineInfo.getAvgMaxOldgc(), "0.000000"), ATTR_AVG_OLDGC_COUNT, machineInfo.getAvgOldgcCount(), ATTR_AVG_OLDGC_SUM, toString(machineInfo.getAvgOldgcSum(), "0.000000"), ATTR_AVG_MAX_OLDGC_MACHINE, machineInfo.getAvgMaxOldgcMachine(), ATTR_AVG_HTTP, toString(machineInfo.getAvgHttp(), "0.000000"), ATTR_AVG_MAX_HTTP, toString(machineInfo.getAvgMaxHttp(), "0.000000"), ATTR_AVG_HTTP_COUNT, machineInfo.getAvgHttpCount(), ATTR_AVG_HTTP_SUM, toString(machineInfo.getAvgHttpSum(), "0.000000"), ATTR_AVG_MAX_HTTP_MACHINE, machineInfo.getAvgMaxHttpMachine(), ATTR_AVG_PIGEON, toString(machineInfo.getAvgPigeon(), "0.000000"), ATTR_AVG_MAX_PIGEON, toString(machineInfo.getAvgMaxPigeon(), "0.000000"), ATTR_AVG_PIGEON_COUNT, machineInfo.getAvgPigeonCount(), ATTR_AVG_PIGEON_SUM, toString(machineInfo.getAvgPigeonSum(), "0.000000"), ATTR_AVG_MAX_PIGEON_MACHINE, machineInfo.getAvgMaxPigeonMachine(), ATTR_AVG_MEMORY_USED, toString(machineInfo.getAvgMemoryUsed(), "0.000000"), ATTR_AVG_MAX_MEMORY_USED, toString(machineInfo.getAvgMaxMemoryUsed(), "0.000000"), ATTR_AVG_MEMORY_USED_COUNT, machineInfo.getAvgMemoryUsedCount(), ATTR_AVG_MEMORY_USED_SUM, toString(machineInfo.getAvgMemoryUsedSum(), "0.000000"), ATTR_AVG_MAX_MEMORY_USED_MACHINE, machineInfo.getAvgMaxMemoryUsedMachine());
   }

   @Override
   public void visitMemCache(MemCache memCache) {

      if (memCache.getBaseCacheInfo() != null) {
         objectBegin(ENTITY_BASE_CACHE_INFO);
         visitBaseCacheInfo(memCache.getBaseCacheInfo());
         objectEnd(ENTITY_BASE_CACHE_INFO);
      }
   }

   @Override
   public void visitProblemInfo(ProblemInfo problemInfo) {
      attributes(null, ATTR_EXCEPTIONS, problemInfo.getExceptions(), ATTR_LONGSQLS, problemInfo.getLongSqls(), ATTR_LONG_SQLPERCENT, toString(problemInfo.getLongSqlPercent(), "0.000000"), ATTR_LONGURLS, problemInfo.getLongUrls(), ATTR_LONG_URLPERCENT, toString(problemInfo.getLongUrlPercent(), "0.000000"), ATTR_LONGSERVICES, problemInfo.getLongServices(), ATTR_LONG_SERVICEPERCENT, toString(problemInfo.getLongServicePercent(), "0.000000"), ATTR_LONGCACHES, problemInfo.getLongCaches(), ATTR_LONG_CACHEPERCENT, toString(problemInfo.getLongCachePercent(), "0.000000"));
   }

   @Override
   public void visitService(Service service) {

      if (service.getBaseInfo() != null) {
         objectBegin(ENTITY_BASE_INFO);
         visitBaseInfo(service.getBaseInfo());
         objectEnd(ENTITY_BASE_INFO);
      }
   }

   @Override
   public void visitSql(Sql sql) {

      if (sql.getBaseInfo() != null) {
         objectBegin(ENTITY_BASE_INFO);
         visitBaseInfo(sql.getBaseInfo());
         objectEnd(ENTITY_BASE_INFO);
      }
   }

   @Override
   public void visitUrl(Url url) {

      if (url.getBaseInfo() != null) {
         objectBegin(ENTITY_BASE_INFO);
         visitBaseInfo(url.getBaseInfo());
         objectEnd(ENTITY_BASE_INFO);
      }
   }

   @Override
   public void visitWebCache(WebCache webCache) {

      if (webCache.getBaseCacheInfo() != null) {
         objectBegin(ENTITY_BASE_CACHE_INFO);
         visitBaseCacheInfo(webCache.getBaseCacheInfo());
         objectEnd(ENTITY_BASE_CACHE_INFO);
      }
   }
}
