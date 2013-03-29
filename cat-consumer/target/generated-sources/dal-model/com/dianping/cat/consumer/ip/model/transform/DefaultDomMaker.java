package com.dianping.cat.consumer.ip.model.transform;

import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ADDRESS;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_MINUTE;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_STARTTIME;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public String buildDomain(Node node) {
      return getText(node);
   }

   @Override
   public Ip buildIp(Node node) {
      String address = getAttribute(node, ATTR_ADDRESS);
      String count = getAttribute(node, ATTR_COUNT);

      Ip ip = new Ip(address);

      if (count != null) {
         ip.setCount(convert(Integer.class, count, 0));
      }

      return ip;
   }

   @Override
   public IpReport buildIpReport(Node node) {
      String domain = getAttribute(node, ATTR_DOMAIN);
      String startTime = getAttribute(node, ATTR_STARTTIME);
      String endTime = getAttribute(node, ATTR_ENDTIME);

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
   public Period buildPeriod(Node node) {
      String minute = getAttribute(node, ATTR_MINUTE);

      Period period = new Period(minute == null ? null : convert(Integer.class, minute, null));

      return period;
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
}
