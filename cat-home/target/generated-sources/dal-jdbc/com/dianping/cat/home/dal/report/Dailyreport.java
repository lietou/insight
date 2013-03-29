package com.dianping.cat.home.dal.report;

import static com.dianping.cat.home.dal.report.DailyreportEntity.CONTENT;
import static com.dianping.cat.home.dal.report.DailyreportEntity.COUNT;
import static com.dianping.cat.home.dal.report.DailyreportEntity.CREATION_DATE;
import static com.dianping.cat.home.dal.report.DailyreportEntity.DOMAIN;
import static com.dianping.cat.home.dal.report.DailyreportEntity.END_DATE;
import static com.dianping.cat.home.dal.report.DailyreportEntity.ID;
import static com.dianping.cat.home.dal.report.DailyreportEntity.IP;
import static com.dianping.cat.home.dal.report.DailyreportEntity.KEY_ID;
import static com.dianping.cat.home.dal.report.DailyreportEntity.NAME;
import static com.dianping.cat.home.dal.report.DailyreportEntity.PERIOD;
import static com.dianping.cat.home.dal.report.DailyreportEntity.START_DATE;
import static com.dianping.cat.home.dal.report.DailyreportEntity.TYPE;

import org.unidal.dal.jdbc.DataObject;

public class Dailyreport extends DataObject {
   private int m_id;

   private String m_name;

   private String m_ip;

   private String m_domain;

   private java.util.Date m_period;

   private int m_type;

   private String m_content;

   private java.util.Date m_creationDate;

   private int m_keyId;

   private int m_count;

   private java.util.Date m_startDate;

   private java.util.Date m_endDate;

   @Override
   public void afterLoad() {
      m_keyId = m_id;
      super.clearUsage();
      }

   public String getContent() {
      return m_content;
   }

   public int getCount() {
      return m_count;
   }

   public java.util.Date getCreationDate() {
      return m_creationDate;
   }

   public String getDomain() {
      return m_domain;
   }

   public java.util.Date getEndDate() {
      return m_endDate;
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

   public java.util.Date getStartDate() {
      return m_startDate;
   }

   public int getType() {
      return m_type;
   }

   public void setContent(String content) {
      setFieldUsed(CONTENT, true);
      m_content = content;
   }

   public void setCount(int count) {
      setFieldUsed(COUNT, true);
      m_count = count;
   }

   public void setCreationDate(java.util.Date creationDate) {
      setFieldUsed(CREATION_DATE, true);
      m_creationDate = creationDate;
   }

   public void setDomain(String domain) {
      setFieldUsed(DOMAIN, true);
      m_domain = domain;
   }

   public void setEndDate(java.util.Date endDate) {
      setFieldUsed(END_DATE, true);
      m_endDate = endDate;
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

   public void setStartDate(java.util.Date startDate) {
      setFieldUsed(START_DATE, true);
      m_startDate = startDate;
   }

   public void setType(int type) {
      setFieldUsed(TYPE, true);
      m_type = type;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("Dailyreport[");
      sb.append("content: ").append(m_content);
      sb.append(", count: ").append(m_count);
      sb.append(", creation-date: ").append(m_creationDate);
      sb.append(", domain: ").append(m_domain);
      sb.append(", end-date: ").append(m_endDate);
      sb.append(", id: ").append(m_id);
      sb.append(", ip: ").append(m_ip);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", name: ").append(m_name);
      sb.append(", period: ").append(m_period);
      sb.append(", start-date: ").append(m_startDate);
      sb.append(", type: ").append(m_type);
      sb.append("]");
      return sb.toString();
   }

}
