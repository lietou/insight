package com.dianping.cat.consumer.ip.model.transform;

import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ADDRESS;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_COUNT;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ENDTIME;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_MINUTE;
import static com.dianping.cat.consumer.ip.model.Constants.ATTR_STARTTIME;
import static com.dianping.cat.consumer.ip.model.Constants.ELEMENT_DOMAIN;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IP;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IP_REPORT;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_PERIOD;

import com.dianping.cat.consumer.ip.model.IEntity;
import com.dianping.cat.consumer.ip.model.IVisitor;
import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public class DefaultXmlBuilder implements IVisitor {

   private int m_level;

   private StringBuilder m_sb = new StringBuilder(4096);

   private boolean m_compact;

   public DefaultXmlBuilder() {
      this(false);
   }

   public DefaultXmlBuilder(boolean compact) {
      m_compact = compact;
   }

   public String buildXml(IEntity<?> entity) {
      m_sb.setLength(0);
      m_sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
      entity.accept(this);
      return m_sb.toString();
   }

   protected void endTag(String name) {
      m_level--;

      indent();
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected String escape(Object value) {
      return escape(value, false);
   }
   
   protected String escape(Object value, boolean text) {
      if (value == null) {
         return null;
      }

      String str = value.toString();
      int len = str.length();
      StringBuilder sb = new StringBuilder(len + 16);

      for (int i = 0; i < len; i++) {
         final char ch = str.charAt(i);

         switch (ch) {
         case '<':
            sb.append("&lt;");
            break;
         case '>':
            sb.append("&gt;");
            break;
         case '&':
            sb.append("&amp;");
            break;
         case '"':
            if (!text) {
               sb.append("&quot;");
               break;
            }
         default:
            sb.append(ch);
            break;
         }
      }

      return sb.toString();
   }

   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void startTag(String name) {
      startTag(name, false, null);
   }
   
   protected void startTag(String name, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, closed, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, false, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, Object text, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      if (dynamicAttributes != null) {
         for (java.util.Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            m_sb.append(' ').append(e.getKey()).append("=\"").append(escape(e.getValue())).append('"');
         }
      }

      if (text != null && closed) {
         m_sb.append('>');
         m_sb.append(escape(text, true));
         m_sb.append("</").append(name).append(">\r\n");
      } else {
         if (closed) {
            m_sb.append('/');
         } else {
            m_level++;
         }
   
         m_sb.append(">\r\n");
      }
   }

   private void tagWithText(String name, String text, Object... nameValues) {
      if (text == null) {
         return;
      }
      
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      m_sb.append(">");
      m_sb.append(escape(text, true));
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected String toString(java.util.Date date, String format) {
      if (date != null) {
         return new java.text.SimpleDateFormat(format).format(date);
      } else {
         return null;
      }
   }

   @Override
   public void visitIp(Ip ip) {
      startTag(ENTITY_IP, true, null, ATTR_ADDRESS, ip.getAddress(), ATTR_COUNT, ip.getCount());
   }

   @Override
   public void visitIpReport(IpReport ipReport) {
      startTag(ENTITY_IP_REPORT, null, ATTR_DOMAIN, ipReport.getDomain(), ATTR_STARTTIME, toString(ipReport.getStartTime(), "yyyy-MM-dd HH:mm:ss"), ATTR_ENDTIME, toString(ipReport.getEndTime(), "yyyy-MM-dd HH:mm:ss"));

      if (!ipReport.getDomainNames().isEmpty()) {
         for (String domain : ipReport.getDomainNames().toArray(new String[0])) {
            tagWithText(ELEMENT_DOMAIN, domain);
         }
      }

      if (!ipReport.getPeriods().isEmpty()) {
         for (Period period : ipReport.getPeriods().values().toArray(new Period[0])) {
            visitPeriod(period);
         }
      }

      endTag(ENTITY_IP_REPORT);
   }

   @Override
   public void visitPeriod(Period period) {
      startTag(ENTITY_PERIOD, null, ATTR_MINUTE, period.getMinute());

      if (!period.getIps().isEmpty()) {
         for (Ip ip : period.getIps().values().toArray(new Ip[0])) {
            visitIp(ip);
         }
      }

      endTag(ENTITY_PERIOD);
   }
}
