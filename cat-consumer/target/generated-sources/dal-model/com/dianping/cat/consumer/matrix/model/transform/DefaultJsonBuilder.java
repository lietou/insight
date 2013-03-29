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
import static com.dianping.cat.consumer.matrix.model.Constants.ELEMENT_DOMAIN_NAMES;
import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_MATRIXS;
import static com.dianping.cat.consumer.matrix.model.Constants.ENTITY_RATIOS;

import java.util.List;
import java.util.Map;

import com.dianping.cat.consumer.matrix.model.IEntity;
import com.dianping.cat.consumer.matrix.model.IVisitor;
import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public class DefaultJsonBuilder implements IVisitor {

   private int m_level;

   private StringBuilder m_sb = new StringBuilder(2048);

   private boolean m_compact;

   public DefaultJsonBuilder() {
      this(false);
   }

   public DefaultJsonBuilder(boolean compact) {
      m_compact = compact;
   }

   protected void arrayBegin(String name) {
      indent();
      m_sb.append('"').append(name).append(m_compact ? "\":[" : "\": [\r\n");
      m_level++;
   }

   protected void arrayEnd(String name) {
      m_level--;

      trimComma();
      indent();
      m_sb.append("],").append(m_compact ? "" : "\r\n");
   }

   protected void attributes(Map<String, String> dynamicAttributes, Object... nameValues) {
      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            if (attrValue instanceof List) {
               @SuppressWarnings("unchecked")
               List<Object> list = (List<Object>) attrValue;

               if (!list.isEmpty()) {
                  indent();
                  m_sb.append('"').append(attrName).append(m_compact ? "\":[" : "\": [");

                  for (Object item : list) {
                     m_sb.append(' ');
                     toString(m_sb, item);
                     m_sb.append(',');
                  }

                  m_sb.setLength(m_sb.length() - 1);
                  m_sb.append(m_compact ? "]," : " ],\r\n");
               }
            } else {
               if (m_compact) {
                  m_sb.append('"').append(attrName).append("\":");
                  toString(m_sb, attrValue);
                  m_sb.append(",");
               } else {
                  indent();
                  m_sb.append('"').append(attrName).append("\": ");
                  toString(m_sb, attrValue);
                  m_sb.append(",\r\n");
               }
            }
         }
      }

      if (dynamicAttributes != null) {
         for (Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            if (m_compact) {
               m_sb.append('"').append(e.getKey()).append("\":");
               toString(m_sb, e.getValue());
               m_sb.append(",");
            } else {
               indent();
               m_sb.append('"').append(e.getKey()).append("\": ");
               toString(m_sb, e.getValue());
               m_sb.append(",\r\n");
            }
         }
      }
   }

   public String buildJson(IEntity<?> entity) {
      m_sb.setLength(0);
      entity.accept(this);
      return m_sb.toString();
   }

   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void objectBegin(String name) {
      indent();

      if (name == null) {
         m_sb.append("{").append(m_compact ? "" : "\r\n");
      } else {
         m_sb.append('"').append(name).append(m_compact ? "\":{" : "\": {\r\n");
      }

      m_level++;
   }

   protected void objectEnd(String name) {
      m_level--;

      trimComma();
      indent();
      m_sb.append(m_compact ? "}," : "},\r\n");
   }

   protected String toString(java.util.Date date, String format) {
      if (date != null) {
         return new java.text.SimpleDateFormat(format).format(date);
      } else {
         return null;
      }
   }

   protected void toString(StringBuilder sb, Object value) {
      if (value instanceof String) {
         sb.append('"').append(value).append('"');
      } else if (value instanceof Boolean || value instanceof Number) {
         sb.append(value);
      } else {
         sb.append('"').append(value).append('"');
      }
   }

   protected void trimComma() {
      int len = m_sb.length();

      if (m_compact) {
         if (len > 1 && m_sb.charAt(len - 1) == ',') {
            m_sb.replace(len - 1, len, "");
         }
      } else {
         if (len > 3 && m_sb.charAt(len - 3) == ',') {
            m_sb.replace(len - 3, len - 2, "");
         }
      }
   }

   @Override
   public void visitMatrix(Matrix matrix) {
      attributes(null, ATTR_TYPE, matrix.getType(), ATTR_NAME, matrix.getName(), ATTR_COUNT, matrix.getCount(), ATTR_TOTALTIME, matrix.getTotalTime(), ATTR_URL, matrix.getUrl());

      if (!matrix.getRatios().isEmpty()) {
         objectBegin(ENTITY_RATIOS);

         for (Map.Entry<String, Ratio> e : matrix.getRatios().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitRatio(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_RATIOS);
      }
   }

   @Override
   public void visitMatrixReport(MatrixReport matrixReport) {
      objectBegin(null);
      attributes(null, ATTR_DOMAIN, matrixReport.getDomain(), ATTR_STARTTIME, toString(matrixReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(matrixReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!matrixReport.getDomainNames().isEmpty()) {
         arrayBegin(ELEMENT_DOMAIN_NAMES);

         for (String domain : matrixReport.getDomainNames()) {
            indent();
            m_sb.append('"').append(domain).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_DOMAIN_NAMES);
      }

      if (!matrixReport.getMatrixs().isEmpty()) {
         objectBegin(ENTITY_MATRIXS);

         for (Map.Entry<String, Matrix> e : matrixReport.getMatrixs().entrySet()) {
            String key = String.valueOf(e.getKey());

            objectBegin(key);
            visitMatrix(e.getValue());
            objectEnd(key);
         }

         objectEnd(ENTITY_MATRIXS);
      }

      objectEnd(null);
      trimComma();
   }

   @Override
   public void visitRatio(Ratio ratio) {
      attributes(null, ATTR_TYPE, ratio.getType(), ATTR_MIN, ratio.getMin(), ATTR_MAX, ratio.getMax(), ATTR_TOTALCOUNT, ratio.getTotalCount(), ATTR_TOTALTIME, ratio.getTotalTime());
   }
}
