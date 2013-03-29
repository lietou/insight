package com.dianping.cat.consumer.event.model.transform;

import static com.dianping.cat.consumer.event.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_FAILS;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.event.model.Constants.ATTR_VALUE;

import org.xml.sax.Attributes;

import com.dianping.cat.consumer.event.model.entity.EventName;
import com.dianping.cat.consumer.event.model.entity.EventReport;
import com.dianping.cat.consumer.event.model.entity.EventType;
import com.dianping.cat.consumer.event.model.entity.Machine;
import com.dianping.cat.consumer.event.model.entity.Range;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public String buildDomain(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public EventReport buildEventReport(Attributes attributes) {
      String domain = attributes.getValue(ATTR_DOMAIN);
      String startTime = attributes.getValue(ATTR_STARTTIME);
      String endTime = attributes.getValue(ATTR_ENDTIME);
      EventReport eventReport = new EventReport(domain);

      if (startTime != null) {
         eventReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         eventReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return eventReport;
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
   public EventName buildName(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String totalCount = attributes.getValue(ATTR_TOTALCOUNT);
      String failCount = attributes.getValue(ATTR_FAILCOUNT);
      String failPercent = attributes.getValue(ATTR_FAILPERCENT);
      EventName name = new EventName(id);

      if (totalCount != null) {
         name.setTotalCount(convert(Long.class, totalCount, 0L));
      }

      if (failCount != null) {
         name.setFailCount(convert(Long.class, failCount, 0L));
      }

      if (failPercent != null) {
         name.setFailPercent(toNumber(failPercent, "0.00", 0).doubleValue());
      }

      return name;
   }

   @Override
   public Range buildRange(Attributes attributes) {
      String value = attributes.getValue(ATTR_VALUE);
      String count = attributes.getValue(ATTR_COUNT);
      String fails = attributes.getValue(ATTR_FAILS);
      Range range = new Range(value == null ? 0 : convert(Integer.class, value, 0));

      if (count != null) {
         range.setCount(convert(Integer.class, count, 0));
      }

      if (fails != null) {
         range.setFails(convert(Integer.class, fails, 0));
      }

      return range;
   }

   @Override
   public EventType buildType(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String totalCount = attributes.getValue(ATTR_TOTALCOUNT);
      String failCount = attributes.getValue(ATTR_FAILCOUNT);
      String failPercent = attributes.getValue(ATTR_FAILPERCENT);
      EventType type = new EventType(id);

      if (totalCount != null) {
         type.setTotalCount(convert(Long.class, totalCount, 0L));
      }

      if (failCount != null) {
         type.setFailCount(convert(Long.class, failCount, 0L));
      }

      if (failPercent != null) {
         type.setFailPercent(toNumber(failPercent, "0.00", 0).doubleValue());
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
