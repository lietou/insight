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

import static com.dianping.cat.consumer.event.model.Constants.ELEMENT_FAILMESSAGEURL;
import static com.dianping.cat.consumer.event.model.Constants.ELEMENT_SUCCESSMESSAGEURL;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.consumer.event.model.entity.EventName;
import com.dianping.cat.consumer.event.model.entity.EventReport;
import com.dianping.cat.consumer.event.model.entity.EventType;
import com.dianping.cat.consumer.event.model.entity.Machine;
import com.dianping.cat.consumer.event.model.entity.Range;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public String buildDomain(Node node) {
      return getText(node);
   }

   @Override
   public EventReport buildEventReport(Node node) {
      String domain = getAttribute(node, ATTR_DOMAIN);
      String startTime = getAttribute(node, ATTR_STARTTIME);
      String endTime = getAttribute(node, ATTR_ENDTIME);

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
   public EventName buildName(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String totalCount = getAttribute(node, ATTR_TOTALCOUNT);
      String failCount = getAttribute(node, ATTR_FAILCOUNT);
      String failPercent = getAttribute(node, ATTR_FAILPERCENT);
      String successMessageUrl = getText(getChildTagNode(node, ELEMENT_SUCCESSMESSAGEURL));
      String failMessageUrl = getText(getChildTagNode(node, ELEMENT_FAILMESSAGEURL));

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

      if (successMessageUrl != null) {
         name.setSuccessMessageUrl(successMessageUrl);
      }

      if (failMessageUrl != null) {
         name.setFailMessageUrl(failMessageUrl);
      }

      return name;
   }

   @Override
   public Range buildRange(Node node) {
      String value = getAttribute(node, ATTR_VALUE);
      String count = getAttribute(node, ATTR_COUNT);
      String fails = getAttribute(node, ATTR_FAILS);

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
   public EventType buildType(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String totalCount = getAttribute(node, ATTR_TOTALCOUNT);
      String failCount = getAttribute(node, ATTR_FAILCOUNT);
      String failPercent = getAttribute(node, ATTR_FAILPERCENT);
      String successMessageUrl = getText(getChildTagNode(node, ELEMENT_SUCCESSMESSAGEURL));
      String failMessageUrl = getText(getChildTagNode(node, ELEMENT_FAILMESSAGEURL));

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

      if (successMessageUrl != null) {
         type.setSuccessMessageUrl(successMessageUrl);
      }

      if (failMessageUrl != null) {
         type.setFailMessageUrl(failMessageUrl);
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
