package com.dianping.cat.consumer.state.model.transform;

import static com.dianping.cat.consumer.state.model.Constants.ATTR_AVGTPS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_BLOCKLOSS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_BLOCKTIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_BLOCKTOTAL;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DELAYAVG;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DELAYCOUNT;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DELAYSUM;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DUMP;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_DUMPLOSS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_MAXTPS;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_NETWORKTIMEERROR;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_PIGEONTIMEERROR;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_SIZE;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_TIME;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_TOTAL;
import static com.dianping.cat.consumer.state.model.Constants.ATTR_TOTALLOSS;

import org.xml.sax.Attributes;

import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public String buildIp(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Machine buildMachine(Attributes attributes) {
      String ip = attributes.getValue(ATTR_IP);
      String total = attributes.getValue(ATTR_TOTAL);
      String totalLoss = attributes.getValue(ATTR_TOTALLOSS);
      String maxTps = attributes.getValue(ATTR_MAXTPS);
      String avgTps = attributes.getValue(ATTR_AVGTPS);
      String blockTotal = attributes.getValue(ATTR_BLOCKTOTAL);
      String blockLoss = attributes.getValue(ATTR_BLOCKLOSS);
      String blockTime = attributes.getValue(ATTR_BLOCKTIME);
      String pigeonTimeError = attributes.getValue(ATTR_PIGEONTIMEERROR);
      String networkTimeError = attributes.getValue(ATTR_NETWORKTIMEERROR);
      String dump = attributes.getValue(ATTR_DUMP);
      String dumpLoss = attributes.getValue(ATTR_DUMPLOSS);
      String size = attributes.getValue(ATTR_SIZE);
      String delaySum = attributes.getValue(ATTR_DELAYSUM);
      String delayAvg = attributes.getValue(ATTR_DELAYAVG);
      String delayCount = attributes.getValue(ATTR_DELAYCOUNT);
      Machine machine = new Machine(ip);

      if (total != null) {
         machine.setTotal(convert(Long.class, total, 0L));
      }

      if (totalLoss != null) {
         machine.setTotalLoss(convert(Long.class, totalLoss, 0L));
      }

      if (maxTps != null) {
         machine.setMaxTps(toNumber(maxTps, "0.0", 0).doubleValue());
      }

      if (avgTps != null) {
         machine.setAvgTps(toNumber(avgTps, "0.0", 0).doubleValue());
      }

      if (blockTotal != null) {
         machine.setBlockTotal(convert(Long.class, blockTotal, 0L));
      }

      if (blockLoss != null) {
         machine.setBlockLoss(convert(Long.class, blockLoss, 0L));
      }

      if (blockTime != null) {
         machine.setBlockTime(convert(Long.class, blockTime, 0L));
      }

      if (pigeonTimeError != null) {
         machine.setPigeonTimeError(convert(Long.class, pigeonTimeError, 0L));
      }

      if (networkTimeError != null) {
         machine.setNetworkTimeError(convert(Long.class, networkTimeError, 0L));
      }

      if (dump != null) {
         machine.setDump(convert(Long.class, dump, 0L));
      }

      if (dumpLoss != null) {
         machine.setDumpLoss(convert(Long.class, dumpLoss, 0L));
      }

      if (size != null) {
         machine.setSize(toNumber(size, "0.0", 0).doubleValue());
      }

      if (delaySum != null) {
         machine.setDelaySum(toNumber(delaySum, "0.0", 0).doubleValue());
      }

      if (delayAvg != null) {
         machine.setDelayAvg(toNumber(delayAvg, "0.0", 0).doubleValue());
      }

      if (delayCount != null) {
         machine.setDelayCount(convert(Integer.class, delayCount, 0));
      }

      return machine;
   }

   @Override
   public Message buildMessage(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String time = attributes.getValue(ATTR_TIME);
      String total = attributes.getValue(ATTR_TOTAL);
      String totalLoss = attributes.getValue(ATTR_TOTALLOSS);
      String dump = attributes.getValue(ATTR_DUMP);
      String dumpLoss = attributes.getValue(ATTR_DUMPLOSS);
      String size = attributes.getValue(ATTR_SIZE);
      String delaySum = attributes.getValue(ATTR_DELAYSUM);
      String delayCount = attributes.getValue(ATTR_DELAYCOUNT);
      String pigeonTimeError = attributes.getValue(ATTR_PIGEONTIMEERROR);
      String networkTimeError = attributes.getValue(ATTR_NETWORKTIMEERROR);
      String blockTotal = attributes.getValue(ATTR_BLOCKTOTAL);
      String blockLoss = attributes.getValue(ATTR_BLOCKLOSS);
      String blockTime = attributes.getValue(ATTR_BLOCKTIME);
      Message message = new Message(id == null ? null : convert(Long.class, id, null));

      if (time != null) {
         message.setTime(toDate(time, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (total != null) {
         message.setTotal(convert(Long.class, total, 0L));
      }

      if (totalLoss != null) {
         message.setTotalLoss(convert(Long.class, totalLoss, 0L));
      }

      if (dump != null) {
         message.setDump(convert(Long.class, dump, 0L));
      }

      if (dumpLoss != null) {
         message.setDumpLoss(convert(Long.class, dumpLoss, 0L));
      }

      if (size != null) {
         message.setSize(toNumber(size, "0.0", 0).doubleValue());
      }

      if (delaySum != null) {
         message.setDelaySum(toNumber(delaySum, "0.0", 0).doubleValue());
      }

      if (delayCount != null) {
         message.setDelayCount(convert(Integer.class, delayCount, 0));
      }

      if (pigeonTimeError != null) {
         message.setPigeonTimeError(convert(Long.class, pigeonTimeError, 0L));
      }

      if (networkTimeError != null) {
         message.setNetworkTimeError(convert(Long.class, networkTimeError, 0L));
      }

      if (blockTotal != null) {
         message.setBlockTotal(convert(Long.class, blockTotal, 0L));
      }

      if (blockLoss != null) {
         message.setBlockLoss(convert(Long.class, blockLoss, 0L));
      }

      if (blockTime != null) {
         message.setBlockTime(convert(Long.class, blockTime, 0L));
      }

      return message;
   }

   @Override
   public ProcessDomain buildProcessDomain(Attributes attributes) {
      String name = attributes.getValue(ATTR_NAME);
      ProcessDomain processDomain = new ProcessDomain(name);

      return processDomain;
   }

   @Override
   public StateReport buildStateReport(Attributes attributes) {
      String domain = attributes.getValue(ATTR_DOMAIN);
      String startTime = attributes.getValue(ATTR_STARTTIME);
      String endTime = attributes.getValue(ATTR_ENDTIME);
      StateReport stateReport = new StateReport(domain);

      if (startTime != null) {
         stateReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         stateReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return stateReport;
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
