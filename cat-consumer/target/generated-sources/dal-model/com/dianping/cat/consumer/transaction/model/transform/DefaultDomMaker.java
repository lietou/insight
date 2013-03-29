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

import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_FAILMESSAGEURL;
import static com.dianping.cat.consumer.transaction.model.Constants.ELEMENT_SUCCESSMESSAGEURL;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public AllDuration buildAllDuration(Node node) {
      String value = getAttribute(node, ATTR_VALUE);
      String count = getAttribute(node, ATTR_COUNT);

      AllDuration allDuration = new AllDuration(value == null ? 0 : convert(Integer.class, value, 0));

      if (count != null) {
         allDuration.setCount(convert(Integer.class, count, 0));
      }

      return allDuration;
   }

   @Override
   public String buildDomain(Node node) {
      return getText(node);
   }

   @Override
   public Duration buildDuration(Node node) {
      String value = getAttribute(node, ATTR_VALUE);
      String count = getAttribute(node, ATTR_COUNT);

      Duration duration = new Duration(value == null ? 0 : convert(Integer.class, value, 0));

      if (count != null) {
         duration.setCount(convert(Integer.class, count, 0));
      }

      return duration;
   }

   @Override
   public String buildIp(Node node) {
      return getText(node);
   }

   @Override
   public Machine buildMachine(Node node) {
      String ip = getAttribute(node, ATTR_IP);

      Machine machine = new Machine(ip);

      return machine;
   }

   @Override
   public TransactionName buildName(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String totalCount = getAttribute(node, ATTR_TOTALCOUNT);
      String failCount = getAttribute(node, ATTR_FAILCOUNT);
      String failPercent = getAttribute(node, ATTR_FAILPERCENT);
      String min = getAttribute(node, ATTR_MIN);
      String max = getAttribute(node, ATTR_MAX);
      String avg = getAttribute(node, ATTR_AVG);
      String sum = getAttribute(node, ATTR_SUM);
      String sum2 = getAttribute(node, ATTR_SUM2);
      String std = getAttribute(node, ATTR_STD);
      String successMessageUrl = getText(getChildTagNode(node, ELEMENT_SUCCESSMESSAGEURL));
      String failMessageUrl = getText(getChildTagNode(node, ELEMENT_FAILMESSAGEURL));
      String tps = getAttribute(node, ATTR_TPS);
      String line95Value = getAttribute(node, ATTR_LINE95VALUE);
      String line95Sum = getAttribute(node, ATTR_LINE95SUM);
      String line95Count = getAttribute(node, ATTR_LINE95COUNT);

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

      if (successMessageUrl != null) {
         name.setSuccessMessageUrl(successMessageUrl);
      }

      if (failMessageUrl != null) {
         name.setFailMessageUrl(failMessageUrl);
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
   public Range buildRange(Node node) {
      String value = getAttribute(node, ATTR_VALUE);
      String count = getAttribute(node, ATTR_COUNT);
      String sum = getAttribute(node, ATTR_SUM);
      String avg = getAttribute(node, ATTR_AVG);
      String fails = getAttribute(node, ATTR_FAILS);

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
   public Range2 buildRange2(Node node) {
      String value = getAttribute(node, ATTR_VALUE);
      String count = getAttribute(node, ATTR_COUNT);
      String sum = getAttribute(node, ATTR_SUM);
      String avg = getAttribute(node, ATTR_AVG);
      String fails = getAttribute(node, ATTR_FAILS);

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
   public TransactionReport buildTransactionReport(Node node) {
      String domain = getAttribute(node, ATTR_DOMAIN);
      String startTime = getAttribute(node, ATTR_STARTTIME);
      String endTime = getAttribute(node, ATTR_ENDTIME);

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
   public TransactionType buildType(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String totalCount = getAttribute(node, ATTR_TOTALCOUNT);
      String failCount = getAttribute(node, ATTR_FAILCOUNT);
      String failPercent = getAttribute(node, ATTR_FAILPERCENT);
      String min = getAttribute(node, ATTR_MIN);
      String max = getAttribute(node, ATTR_MAX);
      String avg = getAttribute(node, ATTR_AVG);
      String sum = getAttribute(node, ATTR_SUM);
      String sum2 = getAttribute(node, ATTR_SUM2);
      String std = getAttribute(node, ATTR_STD);
      String successMessageUrl = getText(getChildTagNode(node, ELEMENT_SUCCESSMESSAGEURL));
      String failMessageUrl = getText(getChildTagNode(node, ELEMENT_FAILMESSAGEURL));
      String tps = getAttribute(node, ATTR_TPS);
      String line95Value = getAttribute(node, ATTR_LINE95VALUE);
      String line95Sum = getAttribute(node, ATTR_LINE95SUM);
      String line95Count = getAttribute(node, ATTR_LINE95COUNT);

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

      if (successMessageUrl != null) {
         type.setSuccessMessageUrl(successMessageUrl);
      }

      if (failMessageUrl != null) {
         type.setFailMessageUrl(failMessageUrl);
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
