package com.dianping.cat.consumer.problem.model.transform;

import static com.dianping.cat.consumer.problem.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_GROUP_NAME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_STATUS;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_TYPE;
import static com.dianping.cat.consumer.problem.model.Constants.ATTR_VALUE;

import org.xml.sax.Attributes;

import com.dianping.cat.consumer.problem.model.entity.Duration;
import com.dianping.cat.consumer.problem.model.entity.Entry;
import com.dianping.cat.consumer.problem.model.entity.JavaThread;
import com.dianping.cat.consumer.problem.model.entity.Machine;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.consumer.problem.model.entity.Segment;

public class DefaultSaxMaker implements IMaker<Attributes> {

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
   public Entry buildEntry(Attributes attributes) {
      String type = attributes.getValue(ATTR_TYPE);
      String status = attributes.getValue(ATTR_STATUS);
      Entry entry = new Entry();

      if (type != null) {
         entry.setType(type);
      }

      if (status != null) {
         entry.setStatus(status);
      }

      return entry;
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
   public String buildMessage(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public ProblemReport buildProblemReport(Attributes attributes) {
      String domain = attributes.getValue(ATTR_DOMAIN);
      String startTime = attributes.getValue(ATTR_STARTTIME);
      String endTime = attributes.getValue(ATTR_ENDTIME);
      ProblemReport problemReport = new ProblemReport(domain);

      if (startTime != null) {
         problemReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         problemReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return problemReport;
   }

   @Override
   public Segment buildSegment(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String count = attributes.getValue(ATTR_COUNT);
      Segment segment = new Segment(id == null ? null : convert(Integer.class, id, null));

      if (count != null) {
         segment.setCount(convert(Integer.class, count, 0));
      }

      return segment;
   }

   @Override
   public JavaThread buildThread(Attributes attributes) {
      String groupName = attributes.getValue(ATTR_GROUP_NAME);
      String name = attributes.getValue(ATTR_NAME);
      String id = attributes.getValue(ATTR_ID);
      JavaThread thread = new JavaThread(id);

      if (groupName != null) {
         thread.setGroupName(groupName);
      }

      if (name != null) {
         thread.setName(name);
      }

      return thread;
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
