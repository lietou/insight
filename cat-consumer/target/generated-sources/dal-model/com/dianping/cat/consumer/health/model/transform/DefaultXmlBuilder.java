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
import static com.dianping.cat.consumer.health.model.Constants.ELEMENT_DOMAIN;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_BASE_CACHE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_BASE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_CALL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_CLIENTSERVICE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_HEALTH_REPORT;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_KVDB_CACHE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_MACHINE_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_MEM_CACHE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_PROBLEM_INFO;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_SERVICE;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_SQL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_URL;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_WEB_CACHE;

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

public class DefaultXmlBuilder implements IVisitor {

   private int m_level;

   private StringBuilder m_sb = new StringBuilder(4096);

   private boolean m_compact;

   public DefaultXmlBuilder() {
      this(false);
   }

   public DefaultXmlBuilder(boolean compact) {
      m_compact = compact;
   }

   public String buildXml(IEntity<?> entity) {
      m_sb.setLength(0);
      m_sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
      entity.accept(this);
      return m_sb.toString();
   }

   protected void endTag(String name) {
      m_level--;

      indent();
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected String escape(Object value) {
      return escape(value, false);
   }
   
   protected String escape(Object value, boolean text) {
      if (value == null) {
         return null;
      }

      String str = value.toString();
      int len = str.length();
      StringBuilder sb = new StringBuilder(len + 16);

      for (int i = 0; i < len; i++) {
         final char ch = str.charAt(i);

         switch (ch) {
         case '<':
            sb.append("&lt;");
            break;
         case '>':
            sb.append("&gt;");
            break;
         case '&':
            sb.append("&amp;");
            break;
         case '"':
            if (!text) {
               sb.append("&quot;");
               break;
            }
         default:
            sb.append(ch);
            break;
         }
      }

      return sb.toString();
   }

   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void startTag(String name) {
      startTag(name, false, null);
   }
   
   protected void startTag(String name, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, closed, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, false, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, Object text, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      if (dynamicAttributes != null) {
         for (java.util.Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            m_sb.append(' ').append(e.getKey()).append("=\"").append(escape(e.getValue())).append('"');
         }
      }

      if (text != null && closed) {
         m_sb.append('>');
         m_sb.append(escape(text, true));
         m_sb.append("</").append(name).append(">\r\n");
      } else {
         if (closed) {
            m_sb.append('/');
         } else {
            m_level++;
         }
   
         m_sb.append(">\r\n");
      }
   }

   private void tagWithText(String name, String text, Object... nameValues) {
      if (text == null) {
         return;
      }
      
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      m_sb.append(">");
      m_sb.append(escape(text, true));
      m_sb.append("</").append(name).append(">\r\n");
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

   @Override
   public void visitBaseCacheInfo(BaseCacheInfo baseCacheInfo) {
      startTag(ENTITY_BASE_CACHE_INFO, true, null, ATTR_TOTAL, baseCacheInfo.getTotal(), ATTR_RESPONSE_TIME, toString(baseCacheInfo.getResponseTime(), "0.00"), ATTR_HIT_PERCENT, toString(baseCacheInfo.getHitPercent(), "0.000000"));
   }

   @Override
   public void visitBaseInfo(BaseInfo baseInfo) {
      startTag(ENTITY_BASE_INFO, true, null, ATTR_TOTAL, baseInfo.getTotal(), ATTR_RESPONSE_TIME, toString(baseInfo.getResponseTime(), "0.00"), ATTR_ERROR_TOTAL, baseInfo.getErrorTotal(), ATTR_ERROR_PERCENT, toString(baseInfo.getErrorPercent(), "0.000000"), ATTR_SUCCESS_PERCENT, toString(baseInfo.getSuccessPercent(), "0.000000"), ATTR_TPS, toString(baseInfo.getTps(), "0.00"));
   }

   @Override
   public void visitCall(Call call) {
      startTag(ENTITY_CALL, null);

      if (call.getBaseInfo() != null) {
         visitBaseInfo(call.getBaseInfo());
      }

      endTag(ENTITY_CALL);
   }

   @Override
   public void visitClientService(ClientService clientService) {
      startTag(ENTITY_CLIENTSERVICE, null);

      if (clientService.getBaseInfo() != null) {
         visitBaseInfo(clientService.getBaseInfo());
      }

      endTag(ENTITY_CLIENTSERVICE);
   }

   @Override
   public void visitHealthReport(HealthReport healthReport) {
      startTag(ENTITY_HEALTH_REPORT, null, ATTR_DOMAIN, healthReport.getDomain(), ATTR_STARTTIME, toString(healthReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(healthReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!healthReport.getDomainNames().isEmpty()) {
         for (String domain : healthReport.getDomainNames().toArray(new String[0])) {
            tagWithText(ELEMENT_DOMAIN, domain);
         }
      }

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

      endTag(ENTITY_HEALTH_REPORT);
   }

   @Override
   public void visitKvdbCache(KvdbCache kvdbCache) {
      startTag(ENTITY_KVDB_CACHE, null);

      if (kvdbCache.getBaseCacheInfo() != null) {
         visitBaseCacheInfo(kvdbCache.getBaseCacheInfo());
      }

      endTag(ENTITY_KVDB_CACHE);
   }

   @Override
   public void visitMachineInfo(MachineInfo machineInfo) {
      startTag(ENTITY_MACHINE_INFO, true, null, ATTR_NUMBERS, machineInfo.getNumbers(), ATTR_AVG_LOAD, toString(machineInfo.getAvgLoad(), "0.000000"), ATTR_AVG_MAX_LOAD, toString(machineInfo.getAvgMaxLoad(), "0.000000"), ATTR_AVG_LOAD_COUNT, machineInfo.getAvgLoadCount(), ATTR_AVG_LOAD_SUM, toString(machineInfo.getAvgLoadSum(), "0.000000"), ATTR_AVG_MAX_LOAD_MACHINE, machineInfo.getAvgMaxLoadMachine(), ATTR_AVG_OLDGC, toString(machineInfo.getAvgOldgc(), "0.000000"), ATTR_AVG_MAX_OLDGC, toString(machineInfo.getAvgMaxOldgc(), "0.000000"), ATTR_AVG_OLDGC_COUNT, machineInfo.getAvgOldgcCount(), ATTR_AVG_OLDGC_SUM, toString(machineInfo.getAvgOldgcSum(), "0.000000"), ATTR_AVG_MAX_OLDGC_MACHINE, machineInfo.getAvgMaxOldgcMachine(), ATTR_AVG_HTTP, toString(machineInfo.getAvgHttp(), "0.000000"), ATTR_AVG_MAX_HTTP, toString(machineInfo.getAvgMaxHttp(), "0.000000"), ATTR_AVG_HTTP_COUNT, machineInfo.getAvgHttpCount(), ATTR_AVG_HTTP_SUM, toString(machineInfo.getAvgHttpSum(), "0.000000"), ATTR_AVG_MAX_HTTP_MACHINE, machineInfo.getAvgMaxHttpMachine(), ATTR_AVG_PIGEON, toString(machineInfo.getAvgPigeon(), "0.000000"), ATTR_AVG_MAX_PIGEON, toString(machineInfo.getAvgMaxPigeon(), "0.000000"), ATTR_AVG_PIGEON_COUNT, machineInfo.getAvgPigeonCount(), ATTR_AVG_PIGEON_SUM, toString(machineInfo.getAvgPigeonSum(), "0.000000"), ATTR_AVG_MAX_PIGEON_MACHINE, machineInfo.getAvgMaxPigeonMachine(), ATTR_AVG_MEMORY_USED, toString(machineInfo.getAvgMemoryUsed(), "0.000000"), ATTR_AVG_MAX_MEMORY_USED, toString(machineInfo.getAvgMaxMemoryUsed(), "0.000000"), ATTR_AVG_MEMORY_USED_COUNT, machineInfo.getAvgMemoryUsedCount(), ATTR_AVG_MEMORY_USED_SUM, toString(machineInfo.getAvgMemoryUsedSum(), "0.000000"), ATTR_AVG_MAX_MEMORY_USED_MACHINE, machineInfo.getAvgMaxMemoryUsedMachine());
   }

   @Override
   public void visitMemCache(MemCache memCache) {
      startTag(ENTITY_MEM_CACHE, null);

      if (memCache.getBaseCacheInfo() != null) {
         visitBaseCacheInfo(memCache.getBaseCacheInfo());
      }

      endTag(ENTITY_MEM_CACHE);
   }

   @Override
   public void visitProblemInfo(ProblemInfo problemInfo) {
      startTag(ENTITY_PROBLEM_INFO, true, null, ATTR_EXCEPTIONS, problemInfo.getExceptions(), ATTR_LONGSQLS, problemInfo.getLongSqls(), ATTR_LONG_SQLPERCENT, toString(problemInfo.getLongSqlPercent(), "0.000000"), ATTR_LONGURLS, problemInfo.getLongUrls(), ATTR_LONG_URLPERCENT, toString(problemInfo.getLongUrlPercent(), "0.000000"), ATTR_LONGSERVICES, problemInfo.getLongServices(), ATTR_LONG_SERVICEPERCENT, toString(problemInfo.getLongServicePercent(), "0.000000"), ATTR_LONGCACHES, problemInfo.getLongCaches(), ATTR_LONG_CACHEPERCENT, toString(problemInfo.getLongCachePercent(), "0.000000"));
   }

   @Override
   public void visitService(Service service) {
      startTag(ENTITY_SERVICE, null);

      if (service.getBaseInfo() != null) {
         visitBaseInfo(service.getBaseInfo());
      }

      endTag(ENTITY_SERVICE);
   }

   @Override
   public void visitSql(Sql sql) {
      startTag(ENTITY_SQL, null);

      if (sql.getBaseInfo() != null) {
         visitBaseInfo(sql.getBaseInfo());
      }

      endTag(ENTITY_SQL);
   }

   @Override
   public void visitUrl(Url url) {
      startTag(ENTITY_URL, null);

      if (url.getBaseInfo() != null) {
         visitBaseInfo(url.getBaseInfo());
      }

      endTag(ENTITY_URL);
   }

   @Override
   public void visitWebCache(WebCache webCache) {
      startTag(ENTITY_WEB_CACHE, null);

      if (webCache.getBaseCacheInfo() != null) {
         visitBaseCacheInfo(webCache.getBaseCacheInfo());
      }

      endTag(ENTITY_WEB_CACHE);
   }
}
