package com.dianping.cat.consumer.matrix.model.transform;

import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_MAX;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_MIN;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_TOTALCOUNT;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_TOTALTIME;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_TYPE;
import static com.dianping.cat.consumer.matrix.model.Constants.ATTR_URL;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public class DefaultDomMaker implements IMaker<Node> {

   @Override
   public String buildDomain(Node node) {
      return getText(node);
   }

   @Override
   public Matrix buildMatrix(Node node) {
      String type = getAttribute(node, ATTR_TYPE);
      String name = getAttribute(node, ATTR_NAME);
      String count = getAttribute(node, ATTR_COUNT);
      String totalTime = getAttribute(node, ATTR_TOTALTIME);
      String url = getAttribute(node, ATTR_URL);

      Matrix matrix = new Matrix(name);

      if (type != null) {
         matrix.setType(type);
      }

      if (count != null) {
         matrix.setCount(convert(Integer.class, count, 0));
      }

      if (totalTime != null) {
         matrix.setTotalTime(convert(Long.class, totalTime, 0L));
      }

      if (url != null) {
         matrix.setUrl(url);
      }

      return matrix;
   }

   @Override
   public MatrixReport buildMatrixReport(Node node) {
      String domain = getAttribute(node, ATTR_DOMAIN);
      String startTime = getAttribute(node, ATTR_STARTTIME);
      String endTime = getAttribute(node, ATTR_ENDTIME);

      MatrixReport matrixReport = new MatrixReport(domain);

      if (startTime != null) {
         matrixReport.setStartTime(toDate(startTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      if (endTime != null) {
         matrixReport.setEndTime(toDate(endTime, "yyyy-MM-dd HH:mm:ss", null));
      }

      return matrixReport;
   }

   @Override
   public Ratio buildRatio(Node node) {
      String type = getAttribute(node, ATTR_TYPE);
      String min = getAttribute(node, ATTR_MIN);
      String max = getAttribute(node, ATTR_MAX);
      String totalCount = getAttribute(node, ATTR_TOTALCOUNT);
      String totalTime = getAttribute(node, ATTR_TOTALTIME);

      Ratio ratio = new Ratio(type);

      if (min != null) {
         ratio.setMin(convert(Integer.class, min, 0));
      }

      if (max != null) {
         ratio.setMax(convert(Integer.class, max, 0));
      }

      if (totalCount != null) {
         ratio.setTotalCount(convert(Integer.class, totalCount, 0));
      }

      if (totalTime != null) {
         ratio.setTotalTime(convert(Long.class, totalTime, 0L));
      }

      return ratio;
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
