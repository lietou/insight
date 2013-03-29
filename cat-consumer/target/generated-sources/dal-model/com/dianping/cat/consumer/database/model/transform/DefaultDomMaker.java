package com.dianping.cat.consumer.database.model.transform;

import static com.dianping.cat.consumer.database.model.Constants.ATTR_AVG;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_CONNECT_URL;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_DATABASE;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_FAILCOUNT;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_FAILPERCENT;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_SUM;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_TOTALPERCENT;
import static com.dianping.cat.consumer.database.model.Constants.ATTR_TPS;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.consumer.database.model.entity.DatabaseReport;
import com.dianping.cat.consumer.database.model.entity.Domain;
import com.dianping.cat.consumer.database.model.entity.Method;
import com.dianping.cat.consumer.database.model.entity.Table;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public String buildDatabaseName(Node node) {
      return getText(node);
   }

   @Override
   public DatabaseReport buildDatabaseReport(Node node) {
      String database = getAttribute(node, ATTR_DATABASE);
      String connectUrl = getAttribute(node, ATTR_CONNECT_URL);
      String startTime = getAttribute(node, ATTR_STARTTIME);
      String endTime = getAttribute(node, ATTR_ENDTIME);

      DatabaseReport databaseReport = new DatabaseReport(database);

      if (connectUrl != null) {
         databaseReport.setConnectUrl(connectUrl);
      }

      if (startTime != null) {
         databaseReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         databaseReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return databaseReport;
   }

   @Override
   public Domain buildDomain(Node node) {
      String id = getAttribute(node, ATTR_ID);

      Domain domain = new Domain(id);

      return domain;
   }

   @Override
   public String buildDomainName(Node node) {
      return getText(node);
   }

   @Override
   public Method buildMethod(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String totalCount = getAttribute(node, ATTR_TOTALCOUNT);
      String failCount = getAttribute(node, ATTR_FAILCOUNT);
      String failPercent = getAttribute(node, ATTR_FAILPERCENT);
      String avg = getAttribute(node, ATTR_AVG);
      String sum = getAttribute(node, ATTR_SUM);
      String tps = getAttribute(node, ATTR_TPS);
      String totalPercent = getAttribute(node, ATTR_TOTALPERCENT);

      Method method = new Method(id);

      if (totalCount != null) {
         method.setTotalCount(convert(Integer.class, totalCount, 0));
      }

      if (failCount != null) {
         method.setFailCount(convert(Integer.class, failCount, 0));
      }

      if (failPercent != null) {
         method.setFailPercent(toNumber(failPercent, "0.00", 0).doubleValue());
      }

      if (avg != null) {
         method.setAvg(toNumber(avg, "0.00", 0).doubleValue());
      }

      if (sum != null) {
         method.setSum(toNumber(sum, "0.00", 0).doubleValue());
      }

      if (tps != null) {
         method.setTps(toNumber(tps, "0.00", 0).doubleValue());
      }

      if (totalPercent != null) {
         method.setTotalPercent(toNumber(totalPercent, "0.00", 0).doubleValue());
      }

      return method;
   }

   @Override
   public String buildSql(Node node) {
      return getText(node);
   }

   @Override
   public Table buildTable(Node node) {
      String id = getAttribute(node, ATTR_ID);
      String totalCount = getAttribute(node, ATTR_TOTALCOUNT);
      String failCount = getAttribute(node, ATTR_FAILCOUNT);
      String failPercent = getAttribute(node, ATTR_FAILPERCENT);
      String avg = getAttribute(node, ATTR_AVG);
      String sum = getAttribute(node, ATTR_SUM);
      String tps = getAttribute(node, ATTR_TPS);
      String totalPercent = getAttribute(node, ATTR_TOTALPERCENT);

      Table table = new Table(id);

      if (totalCount != null) {
         table.setTotalCount(convert(Integer.class, totalCount, 0));
      }

      if (failCount != null) {
         table.setFailCount(convert(Integer.class, failCount, 0));
      }

      if (failPercent != null) {
         table.setFailPercent(toNumber(failPercent, "0.00", 0).doubleValue());
      }

      if (avg != null) {
         table.setAvg(toNumber(avg, "0.00", 0).doubleValue());
      }

      if (sum != null) {
         table.setSum(toNumber(sum, "0.00", 0).doubleValue());
      }

      if (tps != null) {
         table.setTps(toNumber(tps, "0.00", 0).doubleValue());
      }

      if (totalPercent != null) {
         table.setTotalPercent(toNumber(totalPercent, "0.00", 0).doubleValue());
      }

      return table;
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
