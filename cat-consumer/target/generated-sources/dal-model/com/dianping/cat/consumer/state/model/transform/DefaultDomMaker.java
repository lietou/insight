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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public String buildIp(Node node) {
      return getText(node);
   }

   @Override
   public Machine buildMachine(Node node) {
      String ip = getAttribute(node, ATTR_IP);
      String total = getAttribute(node, ATTR_TOTAL);
      String totalLoss = getAttribute(node, ATTR_TOTALLOSS);
      String maxTps = getAttribute(node, ATTR_MAXTPS);
      String avgTps = getAttribute(node, ATTR_AVGTPS);
      String blockTotal = getAttribute(node, ATTR_BLOCKTOTAL);
      String blockLoss = getAttribute(node, ATTR_BLOCKLOSS);
      String blockTime = getAttribute(node, ATTR_BLOCKTIME);
      String pigeonTimeError = getAttribute(node, ATTR_PIGEONTIMEERROR);
      String networkTimeError = getAttribute(node, ATTR_NETWORKTIMEERROR);
      String dump = getAttribute(node, ATTR_DUMP);
      String dumpLoss = getAttribute(node, ATTR_DUMPLOSS);
      String size = getAttribute(node, ATTR_SIZE);
      String delaySum = getAttribute(node, ATTR_DELAYSUM);
      String delayAvg = getAttribute(node, ATTR_DELAYAVG);
      String delayCount = getAttribute(node, ATTR_DELAYCOUNT);

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
   public Message buildMessage(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String time = getAttribute(node, ATTR_TIME);
      String total = getAttribute(node, ATTR_TOTAL);
      String totalLoss = getAttribute(node, ATTR_TOTALLOSS);
      String dump = getAttribute(node, ATTR_DUMP);
      String dumpLoss = getAttribute(node, ATTR_DUMPLOSS);
      String size = getAttribute(node, ATTR_SIZE);
      String delaySum = getAttribute(node, ATTR_DELAYSUM);
      String delayCount = getAttribute(node, ATTR_DELAYCOUNT);
      String pigeonTimeError = getAttribute(node, ATTR_PIGEONTIMEERROR);
      String networkTimeError = getAttribute(node, ATTR_NETWORKTIMEERROR);
      String blockTotal = getAttribute(node, ATTR_BLOCKTOTAL);
      String blockLoss = getAttribute(node, ATTR_BLOCKLOSS);
      String blockTime = getAttribute(node, ATTR_BLOCKTIME);

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
   public ProcessDomain buildProcessDomain(Node node) {
      String name = getAttribute(node, ATTR_NAME);

      ProcessDomain processDomain = new ProcessDomain(name);

      return processDomain;
   }

   @Override
   public StateReport buildStateReport(Node node) {
      String domain = getAttribute(node, ATTR_DOMAIN);
      String startTime = getAttribute(node, ATTR_STARTTIME);
      String endTime = getAttribute(node, ATTR_ENDTIME);

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

   protected String getText(Node node) {
      if (node != null) {
         StringBuilder sb = new StringBuilder();
         NodeList children = node.getChildNodes();
         int len = children.getLength();

         for (int i = 0; i < len; i++) {
            Node child = children.item(i);

            if (child.getNodeType() == Node.TEXT_NODE || child.getNodeType() == Node.CDATA_SECTION_NODE) {
               sb.append(child.getNodeValue());
            }
         }

         if (sb.length() != 0) {
            return sb.toString();
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
