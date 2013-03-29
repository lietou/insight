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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public Domain buildDomain(Node node) {
      String name = getAttribute(node, ATTR_NAME);

      Domain domain = new Domain(name);

      return domain;
   }

   @Override
   public Segment buildSegment(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String error = getAttribute(node, ATTR_ERROR);
      String url = getAttribute(node, ATTR_URL);
      String urlDuration = getAttribute(node, ATTR_URL_DURATION);
      String service = getAttribute(node, ATTR_SERVICE);
      String serviceDuration = getAttribute(node, ATTR_SERVICE_DURATION);
      String sql = getAttribute(node, ATTR_SQL);
      String sqlDuration = getAttribute(node, ATTR_SQL_DURATION);
      String call = getAttribute(node, ATTR_CALL);
      String callDuration = getAttribute(node, ATTR_CALL_DURATION);
      String cache = getAttribute(node, ATTR_CACHE);
      String cacheDuration = getAttribute(node, ATTR_CACHE_DURATION);
      String callError = getAttribute(node, ATTR_CALL_ERROR);
      String urlSum = getAttribute(node, ATTR_URL_SUM);
      String serviceSum = getAttribute(node, ATTR_SERVICE_SUM);
      String sqlSum = getAttribute(node, ATTR_SQL_SUM);
      String callSum = getAttribute(node, ATTR_CALL_SUM);
      String cacheSum = getAttribute(node, ATTR_CACHE_SUM);

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
   public TopReport buildTopReport(Node node) {
      String domain = getAttribute(node, ATTR_DOMAIN);
      String startTime = getAttribute(node, ATTR_STARTTIME);
      String endTime = getAttribute(node, ATTR_ENDTIME);

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

   protected String getAttribute(Node node, String name) {
      Node attribute = node.getAttributes().getNamedItem(name);

      return attribute == null ? null : attribute.getNodeValue();
   }

   protected Node getChildTagNode(Node parent, String name) {
      NodeList children = parent.getChildNodes();
      int len = children.getLength();

      for (int i = 0; i < len; i++) {
         Node child = children.item(i);

         if (child.getNodeType() == Node.ELEMENT_NODE) {
            if (child.getNodeName().equals(name)) {
               return child;
            }
         }
      }

      return null;
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
