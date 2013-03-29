package com.dianping.cat.consumer.sql.model.transform;

import static com.dianping.cat.consumer.sql.model.Constants.ATTR_AVG;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_CONNECT_URL;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_SUM;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_TOTALPERCENT;
import static com.dianping.cat.consumer.sql.model.Constants.ATTR_TPS;

import org.xml.sax.Attributes;

import com.dianping.cat.consumer.sql.model.entity.Database;
import com.dianping.cat.consumer.sql.model.entity.Method;
import com.dianping.cat.consumer.sql.model.entity.SqlReport;
import com.dianping.cat.consumer.sql.model.entity.Table;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public Database buildDatabase(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String connectUrl = attributes.getValue(ATTR_CONNECT_URL);
      Database database = new Database(id);

      if (connectUrl != null) {
         database.setConnectUrl(connectUrl);
      }

      return database;
   }

   @Override
   public String buildDatabaseName(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public String buildDomainName(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Method buildMethod(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String totalCount = attributes.getValue(ATTR_TOTALCOUNT);
      String failCount = attributes.getValue(ATTR_FAILCOUNT);
      String failPercent = attributes.getValue(ATTR_FAILPERCENT);
      String avg = attributes.getValue(ATTR_AVG);
      String sum = attributes.getValue(ATTR_SUM);
      String tps = attributes.getValue(ATTR_TPS);
      String totalPercent = attributes.getValue(ATTR_TOTALPERCENT);
      Method method = new Method(id);

      if (totalCount != null) {
         method.setTotalCount(convert(Integer.class, totalCount, 0));
      }

      if (failCount != null) {
         method.setFailCount(convert(Integer.class, failCount, 0));
      }

      if (failPercent != null) {
         method.setFailPercent(toNumber(failPercent, "0.00", 0).doubleValue());
      }

      if (avg != null) {
         method.setAvg(toNumber(avg, "0.00", 0).doubleValue());
      }

      if (sum != null) {
         method.setSum(toNumber(sum, "0.00", 0).doubleValue());
      }

      if (tps != null) {
         method.setTps(toNumber(tps, "0.00", 0).doubleValue());
      }

      if (totalPercent != null) {
         method.setTotalPercent(toNumber(totalPercent, "0.00", 0).doubleValue());
      }

      return method;
   }

   @Override
   public String buildSql(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public SqlReport buildSqlReport(Attributes attributes) {
      String domain = attributes.getValue(ATTR_DOMAIN);
      String startTime = attributes.getValue(ATTR_STARTTIME);
      String endTime = attributes.getValue(ATTR_ENDTIME);
      SqlReport sqlReport = new SqlReport(domain);

      if (startTime != null) {
         sqlReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         sqlReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return sqlReport;
   }

   @Override
   public Table buildTable(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String totalCount = attributes.getValue(ATTR_TOTALCOUNT);
      String failCount = attributes.getValue(ATTR_FAILCOUNT);
      String failPercent = attributes.getValue(ATTR_FAILPERCENT);
      String avg = attributes.getValue(ATTR_AVG);
      String sum = attributes.getValue(ATTR_SUM);
      String tps = attributes.getValue(ATTR_TPS);
      String totalPercent = attributes.getValue(ATTR_TOTALPERCENT);
      Table table = new Table(id);

      if (totalCount != null) {
         table.setTotalCount(convert(Integer.class, totalCount, 0));
      }

      if (failCount != null) {
         table.setFailCount(convert(Integer.class, failCount, 0));
      }

      if (failPercent != null) {
         table.setFailPercent(toNumber(failPercent, "0.00", 0).doubleValue());
      }

      if (avg != null) {
         table.setAvg(toNumber(avg, "0.00", 0).doubleValue());
      }

      if (sum != null) {
         table.setSum(toNumber(sum, "0.00", 0).doubleValue());
      }

      if (tps != null) {
         table.setTps(toNumber(tps, "0.00", 0).doubleValue());
      }

      if (totalPercent != null) {
         table.setTotalPercent(toNumber(totalPercent, "0.00", 0).doubleValue());
      }

      return table;
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
