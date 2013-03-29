package com.dianping.cat.consumer.transaction.model.transform;

import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_AVG;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_FAILS;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_LINE95COUNT;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_LINE95SUM;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_LINE95VALUE;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_MAX;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_MIN;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_STD;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_SUM;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_SUM2;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_TPS;
import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_VALUE;

import org.xml.sax.Attributes;

import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public AllDuration buildAllDuration(Attributes attributes) {
      String value = attributes.getValue(ATTR_VALUE);
      String count = attributes.getValue(ATTR_COUNT);
      AllDuration allDuration = new AllDuration(value == null ? 0 : convert(Integer.class, value, 0));

      if (count != null) {
         allDuration.setCount(convert(Integer.class, count, 0));
      }

      return allDuration;
   }

   @Override
   public String buildDomain(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Duration buildDuration(Attributes attributes) {
      String value = attributes.getValue(ATTR_VALUE);
      String count = attributes.getValue(ATTR_COUNT);
      Duration duration = new Duration(value == null ? 0 : convert(Integer.class, value, 0));

      if (count != null) {
         duration.setCount(convert(Integer.class, count, 0));
      }

      return duration;
   }

   @Override
   public String buildIp(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Machine buildMachine(Attributes attributes) {
      String ip = attributes.getValue(ATTR_IP);
      Machine machine = new Machine(ip);

      return machine;
   }

   @Override
   public TransactionName buildName(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String totalCount = attributes.getValue(ATTR_TOTALCOUNT);
      String failCount = attributes.getValue(ATTR_FAILCOUNT);
      String failPercent = attributes.getValue(ATTR_FAILPERCENT);
      String min = attributes.getValue(ATTR_MIN);
      String max = attributes.getValue(ATTR_MAX);
      String avg = attributes.getValue(ATTR_AVG);
      String sum = attributes.getValue(ATTR_SUM);
      String sum2 = attributes.getValue(ATTR_SUM2);
      String std = attributes.getValue(ATTR_STD);
      String tps = attributes.getValue(ATTR_TPS);
      String line95Value = attributes.getValue(ATTR_LINE95VALUE);
      String line95Sum = attributes.getValue(ATTR_LINE95SUM);
      String line95Count = attributes.getValue(ATTR_LINE95COUNT);
      TransactionName name = new TransactionName(id);

      if (totalCount != null) {
         name.setTotalCount(convert(Long.class, totalCount, 0L));
      }

      if (failCount != null) {
         name.setFailCount(convert(Long.class, failCount, 0L));
      }

      if (failPercent != null) {
         name.setFailPercent(toNumber(failPercent, "0.00", 0).doubleValue());
      }

      if (min != null) {
         name.setMin(convert(Double.class, min, 0.0));
      }

      if (max != null) {
         name.setMax(convert(Double.class, max, 0.0));
      }

      if (avg != null) {
         name.setAvg(toNumber(avg, "0.0", 0).doubleValue());
      }

      if (sum != null) {
         name.setSum(toNumber(sum, "0.0", 0).doubleValue());
      }

      if (sum2 != null) {
         name.setSum2(toNumber(sum2, "0.0", 0).doubleValue());
      }

      if (std != null) {
         name.setStd(toNumber(std, "0.0", 0).doubleValue());
      }

      if (tps != null) {
         name.setTps(toNumber(tps, "0.00", 0).doubleValue());
      }

      if (line95Value != null) {
         name.setLine95Value(toNumber(line95Value, "0.00", 0).doubleValue());
      }

      if (line95Sum != null) {
         name.setLine95Sum(toNumber(line95Sum, "0.00", 0).doubleValue());
      }

      if (line95Count != null) {
         name.setLine95Count(convert(Integer.class, line95Count, 0));
      }

      return name;
   }

   @Override
   public Range buildRange(Attributes attributes) {
      String value = attributes.getValue(ATTR_VALUE);
      String count = attributes.getValue(ATTR_COUNT);
      String sum = attributes.getValue(ATTR_SUM);
      String avg = attributes.getValue(ATTR_AVG);
      String fails = attributes.getValue(ATTR_FAILS);
      Range range = new Range(value == null ? 0 : convert(Integer.class, value, 0));

      if (count != null) {
         range.setCount(convert(Integer.class, count, 0));
      }

      if (sum != null) {
         range.setSum(convert(Double.class, sum, 0.0));
      }

      if (avg != null) {
         range.setAvg(toNumber(avg, "0.0", 0).doubleValue());
      }

      if (fails != null) {
         range.setFails(convert(Integer.class, fails, 0));
      }

      return range;
   }

   @Override
   public Range2 buildRange2(Attributes attributes) {
      String value = attributes.getValue(ATTR_VALUE);
      String count = attributes.getValue(ATTR_COUNT);
      String sum = attributes.getValue(ATTR_SUM);
      String avg = attributes.getValue(ATTR_AVG);
      String fails = attributes.getValue(ATTR_FAILS);
      Range2 range2 = new Range2(value == null ? 0 : convert(Integer.class, value, 0));

      if (count != null) {
         range2.setCount(convert(Integer.class, count, 0));
      }

      if (sum != null) {
         range2.setSum(convert(Double.class, sum, 0.0));
      }

      if (avg != null) {
         range2.setAvg(toNumber(avg, "0.0", 0).doubleValue());
      }

      if (fails != null) {
         range2.setFails(convert(Integer.class, fails, 0));
      }

      return range2;
   }

   @Override
   public TransactionReport buildTransactionReport(Attributes attributes) {
      String domain = attributes.getValue(ATTR_DOMAIN);
      String startTime = attributes.getValue(ATTR_STARTTIME);
      String endTime = attributes.getValue(ATTR_ENDTIME);
      TransactionReport transactionReport = new TransactionReport(domain);

      if (startTime != null) {
         transactionReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         transactionReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return transactionReport;
   }

   @Override
   public TransactionType buildType(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String totalCount = attributes.getValue(ATTR_TOTALCOUNT);
      String failCount = attributes.getValue(ATTR_FAILCOUNT);
      String failPercent = attributes.getValue(ATTR_FAILPERCENT);
      String min = attributes.getValue(ATTR_MIN);
      String max = attributes.getValue(ATTR_MAX);
      String avg = attributes.getValue(ATTR_AVG);
      String sum = attributes.getValue(ATTR_SUM);
      String sum2 = attributes.getValue(ATTR_SUM2);
      String std = attributes.getValue(ATTR_STD);
      String tps = attributes.getValue(ATTR_TPS);
      String line95Value = attributes.getValue(ATTR_LINE95VALUE);
      String line95Sum = attributes.getValue(ATTR_LINE95SUM);
      String line95Count = attributes.getValue(ATTR_LINE95COUNT);
      TransactionType type = new TransactionType(id);

      if (totalCount != null) {
         type.setTotalCount(convert(Long.class, totalCount, 0L));
      }

      if (failCount != null) {
         type.setFailCount(convert(Long.class, failCount, 0L));
      }

      if (failPercent != null) {
         type.setFailPercent(toNumber(failPercent, "0.00", 0).doubleValue());
      }

      if (min != null) {
         type.setMin(convert(Double.class, min, 0.0));
      }

      if (max != null) {
         type.setMax(convert(Double.class, max, 0.0));
      }

      if (avg != null) {
         type.setAvg(toNumber(avg, "0.0", 0).doubleValue());
      }

      if (sum != null) {
         type.setSum(toNumber(sum, "0.0", 0).doubleValue());
      }

      if (sum2 != null) {
         type.setSum2(toNumber(sum2, "0.0", 0).doubleValue());
      }

      if (std != null) {
         type.setStd(toNumber(std, "0.0", 0).doubleValue());
      }

      if (tps != null) {
         type.setTps(toNumber(tps, "0.00", 0).doubleValue());
      }

      if (line95Value != null) {
         type.setLine95Value(toNumber(line95Value, "0.00", 0).doubleValue());
      }

      if (line95Sum != null) {
         type.setLine95Sum(toNumber(line95Sum, "0.00", 0).doubleValue());
      }

      if (line95Count != null) {
         type.setLine95Count(convert(Integer.class, line95Count, 0));
      }

      return type;
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
