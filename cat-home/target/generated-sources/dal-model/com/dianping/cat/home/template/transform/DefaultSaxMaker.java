package com.dianping.cat.home.template.transform;

import static com.dianping.cat.home.template.Constants.ATTR_ALARM;
import static com.dianping.cat.home.template.Constants.ATTR_ALARM_INTERVAL;
import static com.dianping.cat.home.template.Constants.ATTR_BASEURL;
import static com.dianping.cat.home.template.Constants.ATTR_ID;
import static com.dianping.cat.home.template.Constants.ATTR_INTERVAL;
import static com.dianping.cat.home.template.Constants.ATTR_MAX;
import static com.dianping.cat.home.template.Constants.ATTR_MIN;
import static com.dianping.cat.home.template.Constants.ATTR_TYPE;
import static com.dianping.cat.home.template.Constants.ATTR_VALUE;

import org.xml.sax.Attributes;

import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public Connection buildConnection(Attributes attributes) {
      String baseUrl = attributes.getValue(ATTR_BASEURL);
      Connection connection = new Connection();

      if (baseUrl != null) {
         connection.setBaseUrl(baseUrl);
      }

      return connection;
   }

   @Override
   public Duration buildDuration(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String min = attributes.getValue(ATTR_MIN);
      String max = attributes.getValue(ATTR_MAX);
      String interval = attributes.getValue(ATTR_INTERVAL);
      String alarm = attributes.getValue(ATTR_ALARM);
      String alarmInterval = attributes.getValue(ATTR_ALARM_INTERVAL);
      Duration duration = new Duration(id);

      if (min != null) {
         duration.setMin(convert(Integer.class, min, null));
      }

      if (max != null) {
         duration.setMax(convert(Integer.class, max, null));
      }

      if (interval != null) {
         duration.setInterval(convert(Integer.class, interval, null));
      }

      if (alarm != null) {
         duration.setAlarm(alarm);
      }

      if (alarmInterval != null) {
         duration.setAlarmInterval(convert(Integer.class, alarmInterval, null));
      }

      return duration;
   }

   @Override
   public Param buildParam(Attributes attributes) {
      String type = attributes.getValue(ATTR_TYPE);
      String value = attributes.getValue(ATTR_VALUE);
      Param param = new Param(type);

      if (value != null) {
         param.setValue(value);
      }

      return param;
   }

   @Override
   public ThresholdTemplate buildThresholdTemplate(Attributes attributes) {
      ThresholdTemplate thresholdTemplate = new ThresholdTemplate();

      return thresholdTemplate;
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
}
