package com.dianping.cat.home.dal.report;

import static com.dianping.cat.home.dal.report.WeeklyreportEntity.CONTENT;
import static com.dianping.cat.home.dal.report.WeeklyreportEntity.CREATION_DATE;
import static com.dianping.cat.home.dal.report.WeeklyreportEntity.DOMAIN;
import static com.dianping.cat.home.dal.report.WeeklyreportEntity.ID;
import static com.dianping.cat.home.dal.report.WeeklyreportEntity.IP;
import static com.dianping.cat.home.dal.report.WeeklyreportEntity.KEY_ID;
import static com.dianping.cat.home.dal.report.WeeklyreportEntity.NAME;
import static com.dianping.cat.home.dal.report.WeeklyreportEntity.PERIOD;
import static com.dianping.cat.home.dal.report.WeeklyreportEntity.TYPE;

import org.unidal.dal.jdbc.DataObject;

public class Weeklyreport extends DataObject {
   private int m_id;

   private String m_name;

   private String m_ip;

   private String m_domain;

   private java.util.Date m_period;

   private int m_type;

   private String m_content;

   private java.util.Date m_creationDate;

   private int m_keyId;

   @Override
   public void afterLoad() {
      m_keyId = m_id;
      super.clearUsage();
      }

   public String getContent() {
      return m_content;
   }

   public java.util.Date getCreationDate() {
      return m_creationDate;
   }

   public String getDomain() {
      return m_domain;
   }

   public int getId() {
      return m_id;
   }

   public String getIp() {
      return m_ip;
   }

   public int getKeyId() {
      return m_keyId;
   }

   public String getName() {
      return m_name;
   }

   public java.util.Date getPeriod() {
      return m_period;
   }

   public int getType() {
      return m_type;
   }

   public void setContent(String content) {
      setFieldUsed(CONTENT, true);
      m_content = content;
   }

   public void setCreationDate(java.util.Date creationDate) {
      setFieldUsed(CREATION_DATE, true);
      m_creationDate = creationDate;
   }

   public void setDomain(String domain) {
      setFieldUsed(DOMAIN, true);
      m_domain = domain;
   }

   public void setId(int id) {
      setFieldUsed(ID, true);
      m_id = id;
   }

   public void setIp(String ip) {
      setFieldUsed(IP, true);
      m_ip = ip;
   }

   public void setKeyId(int keyId) {
      setFieldUsed(KEY_ID, true);
      m_keyId = keyId;
   }

   public void setName(String name) {
      setFieldUsed(NAME, true);
      m_name = name;
   }

   public void setPeriod(java.util.Date period) {
      setFieldUsed(PERIOD, true);
      m_period = period;
   }

   public void setType(int type) {
      setFieldUsed(TYPE, true);
      m_type = type;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("Weeklyreport[");
      sb.append("content: ").append(m_content);
      sb.append(", creation-date: ").append(m_creationDate);
      sb.append(", domain: ").append(m_domain);
      sb.append(", id: ").append(m_id);
      sb.append(", ip: ").append(m_ip);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", name: ").append(m_name);
      sb.append(", period: ").append(m_period);
      sb.append(", type: ").append(m_type);
      sb.append("]");
      return sb.toString();
   }

}
