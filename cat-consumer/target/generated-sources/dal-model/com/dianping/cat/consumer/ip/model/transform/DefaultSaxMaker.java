package com.dianping.cat.consumer.ip.model.transform;

import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ADDRESS;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_MINUTE;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_STARTTIME;

import org.xml.sax.Attributes;

import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public String buildDomain(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Ip buildIp(Attributes attributes) {
      String address = attributes.getValue(ATTR_ADDRESS);
      String count = attributes.getValue(ATTR_COUNT);
      Ip ip = new Ip(address);

      if (count != null) {
         ip.setCount(convert(Integer.class, count, 0));
      }

      return ip;
   }

   @Override
   public IpReport buildIpReport(Attributes attributes) {
      String domain = attributes.getValue(ATTR_DOMAIN);
      String startTime = attributes.getValue(ATTR_STARTTIME);
      String endTime = attributes.getValue(ATTR_ENDTIME);
      IpReport ipReport = new IpReport(domain);

      if (startTime != null) {
         ipReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         ipReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return ipReport;
   }

   @Override
   public Period buildPeriod(Attributes attributes) {
      String minute = attributes.getValue(ATTR_MINUTE);
      Period period = new Period(minute == null ? null : convert(Integer.class, minute, null));

      return period;
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
}
