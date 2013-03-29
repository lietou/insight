package com.dianping.cat.consumer.cross.model.transform;

import static com.dianping.cat.consumer.cross.model.Constants.ATTR_AVG;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ROLE;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_SUM;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.cross.model.Constants.ATTR_TPS;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public CrossReport buildCrossReport(Node node) {
      String domain = getAttribute(node, ATTR_DOMAIN);
      String startTime = getAttribute(node, ATTR_STARTTIME);
      String endTime = getAttribute(node, ATTR_ENDTIME);

      CrossReport crossReport = new CrossReport(domain);

      if (startTime != null) {
         crossReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         crossReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return crossReport;
   }

   @Override
   public String buildDomain(Node node) {
      return getText(node);
   }

   @Override
   public String buildIp(Node node) {
      return getText(node);
   }

   @Override
   public Local buildLocal(Node node) {
      String id = getAttribute(node, ATTR_ID);

      Local local = new Local(id);

      return local;
   }

   @Override
   public Name buildName(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String totalCount = getAttribute(node, ATTR_TOTALCOUNT);
      String failCount = getAttribute(node, ATTR_FAILCOUNT);
      String failPercent = getAttribute(node, ATTR_FAILPERCENT);
      String avg = getAttribute(node, ATTR_AVG);
      String sum = getAttribute(node, ATTR_SUM);
      String tps = getAttribute(node, ATTR_TPS);

      Name name = new Name(id);

      if (totalCount != null) {
         name.setTotalCount(convert(Long.class, totalCount, 0L));
      }

      if (failCount != null) {
         name.setFailCount(convert(Integer.class, failCount, 0));
      }

      if (failPercent != null) {
         name.setFailPercent(toNumber(failPercent, "0.00", 0).doubleValue());
      }

      if (avg != null) {
         name.setAvg(toNumber(avg, "0.00", 0).doubleValue());
      }

      if (sum != null) {
         name.setSum(toNumber(sum, "0.00", 0).doubleValue());
      }

      if (tps != null) {
         name.setTps(toNumber(tps, "0.00", 0).doubleValue());
      }

      return name;
   }

   @Override
   public Remote buildRemote(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String role = getAttribute(node, ATTR_ROLE);

      Remote remote = new Remote(id);

      if (role != null) {
         remote.setRole(role);
      }

      return remote;
   }

   @Override
   public Type buildType(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String totalCount = getAttribute(node, ATTR_TOTALCOUNT);
      String failCount = getAttribute(node, ATTR_FAILCOUNT);
      String failPercent = getAttribute(node, ATTR_FAILPERCENT);
      String avg = getAttribute(node, ATTR_AVG);
      String sum = getAttribute(node, ATTR_SUM);
      String tps = getAttribute(node, ATTR_TPS);

      Type type = new Type();

      if (id != null) {
         type.setId(id);
      }

      if (totalCount != null) {
         type.setTotalCount(convert(Long.class, totalCount, 0L));
      }

      if (failCount != null) {
         type.setFailCount(convert(Integer.class, failCount, 0));
      }

      if (failPercent != null) {
         type.setFailPercent(toNumber(failPercent, "0.00", 0).doubleValue());
      }

      if (avg != null) {
         type.setAvg(toNumber(avg, "0.00", 0).doubleValue());
      }

      if (sum != null) {
         type.setSum(toNumber(sum, "0.00", 0).doubleValue());
      }

      if (tps != null) {
         type.setTps(toNumber(tps, "0.00", 0).doubleValue());
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
