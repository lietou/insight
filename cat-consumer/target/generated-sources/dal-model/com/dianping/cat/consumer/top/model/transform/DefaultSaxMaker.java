package com.dianping.cat.consumer.top.model.transform;

import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CACHE_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_CALL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ERROR;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SERVICE_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_SQL_SUM;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL_DURATION;
import static com.dianping.cat.consumer.top.model.Constants.ATTR_URL_SUM;

import org.xml.sax.Attributes;

import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public Domain buildDomain(Attributes attributes) {
      String name = attributes.getValue(ATTR_NAME);
      Domain domain = new Domain(name);

      return domain;
   }

   @Override
   public Segment buildSegment(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String error = attributes.getValue(ATTR_ERROR);
      String url = attributes.getValue(ATTR_URL);
      String urlDuration = attributes.getValue(ATTR_URL_DURATION);
      String service = attributes.getValue(ATTR_SERVICE);
      String serviceDuration = attributes.getValue(ATTR_SERVICE_DURATION);
      String sql = attributes.getValue(ATTR_SQL);
      String sqlDuration = attributes.getValue(ATTR_SQL_DURATION);
      String call = attributes.getValue(ATTR_CALL);
      String callDuration = attributes.getValue(ATTR_CALL_DURATION);
      String cache = attributes.getValue(ATTR_CACHE);
      String cacheDuration = attributes.getValue(ATTR_CACHE_DURATION);
      String callError = attributes.getValue(ATTR_CALL_ERROR);
      String urlSum = attributes.getValue(ATTR_URL_SUM);
      String serviceSum = attributes.getValue(ATTR_SERVICE_SUM);
      String sqlSum = attributes.getValue(ATTR_SQL_SUM);
      String callSum = attributes.getValue(ATTR_CALL_SUM);
      String cacheSum = attributes.getValue(ATTR_CACHE_SUM);
      Segment segment = new Segment(id == null ? null : convert(Integer.class, id, null));

      if (error != null) {
         segment.setError(convert(Long.class, error, 0L));
      }

      if (url != null) {
         segment.setUrl(convert(Long.class, url, 0L));
      }

      if (urlDuration != null) {
         segment.setUrlDuration(toNumber(urlDuration, "0.00", 0).doubleValue());
      }

      if (service != null) {
         segment.setService(convert(Long.class, service, 0L));
      }

      if (serviceDuration != null) {
         segment.setServiceDuration(toNumber(serviceDuration, "0.00", 0).doubleValue());
      }

      if (sql != null) {
         segment.setSql(convert(Long.class, sql, 0L));
      }

      if (sqlDuration != null) {
         segment.setSqlDuration(toNumber(sqlDuration, "0.00", 0).doubleValue());
      }

      if (call != null) {
         segment.setCall(convert(Long.class, call, 0L));
      }

      if (callDuration != null) {
         segment.setCallDuration(toNumber(callDuration, "0.00", 0).doubleValue());
      }

      if (cache != null) {
         segment.setCache(convert(Long.class, cache, 0L));
      }

      if (cacheDuration != null) {
         segment.setCacheDuration(toNumber(cacheDuration, "0.00", 0).doubleValue());
      }

      if (callError != null) {
         segment.setCallError(convert(Long.class, callError, 0L));
      }

      if (urlSum != null) {
         segment.setUrlSum(toNumber(urlSum, "0.00", 0).doubleValue());
      }

      if (serviceSum != null) {
         segment.setServiceSum(toNumber(serviceSum, "0.00", 0).doubleValue());
      }

      if (sqlSum != null) {
         segment.setSqlSum(toNumber(sqlSum, "0.00", 0).doubleValue());
      }

      if (callSum != null) {
         segment.setCallSum(toNumber(callSum, "0.00", 0).doubleValue());
      }

      if (cacheSum != null) {
         segment.setCacheSum(toNumber(cacheSum, "0.00", 0).doubleValue());
      }

      return segment;
   }

   @Override
   public TopReport buildTopReport(Attributes attributes) {
      String domain = attributes.getValue(ATTR_DOMAIN);
      String startTime = attributes.getValue(ATTR_STARTTIME);
      String endTime = attributes.getValue(ATTR_ENDTIME);
      TopReport topReport = new TopReport(domain);

      if (startTime != null) {
         topReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         topReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return topReport;
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
