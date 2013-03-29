package com.dianping.cat.home.dal.alarm;

import static com.dianping.cat.home.dal.alarm.MailRecordEntity.CONTENT;
import static com.dianping.cat.home.dal.alarm.MailRecordEntity.CREATION_DATE;
import static com.dianping.cat.home.dal.alarm.MailRecordEntity.ID;
import static com.dianping.cat.home.dal.alarm.MailRecordEntity.KEY_ID;
import static com.dianping.cat.home.dal.alarm.MailRecordEntity.RECEIVERS;
import static com.dianping.cat.home.dal.alarm.MailRecordEntity.RULE_ID;
import static com.dianping.cat.home.dal.alarm.MailRecordEntity.RULE_IDS;
import static com.dianping.cat.home.dal.alarm.MailRecordEntity.STATUS;
import static com.dianping.cat.home.dal.alarm.MailRecordEntity.TITLE;
import static com.dianping.cat.home.dal.alarm.MailRecordEntity.TYPE;

import org.unidal.dal.jdbc.DataObject;

public class MailRecord extends DataObject {
   private int m_id;

   private int m_type;

   private int m_ruleId;

   private String m_receivers;

   private String m_title;

   private String m_content;

   private int m_status;

   private java.util.Date m_creationDate;

   private int m_keyId;

   private int[] m_ruleIds;

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

   public int getId() {
      return m_id;
   }

   public int getKeyId() {
      return m_keyId;
   }

   public String getReceivers() {
      return m_receivers;
   }

   public int getRuleId() {
      return m_ruleId;
   }

   public int[] getRuleIds() {
      return m_ruleIds;
   }

   public int getStatus() {
      return m_status;
   }

   public String getTitle() {
      return m_title;
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

   public void setId(int id) {
      setFieldUsed(ID, true);
      m_id = id;
   }

   public void setKeyId(int keyId) {
      setFieldUsed(KEY_ID, true);
      m_keyId = keyId;
   }

   public void setReceivers(String receivers) {
      setFieldUsed(RECEIVERS, true);
      m_receivers = receivers;
   }

   public void setRuleId(int ruleId) {
      setFieldUsed(RULE_ID, true);
      m_ruleId = ruleId;
   }

   public void setRuleIds(int[] ruleIds) {
      setFieldUsed(RULE_IDS, true);
      m_ruleIds = ruleIds;
   }

   public void setStatus(int status) {
      setFieldUsed(STATUS, true);
      m_status = status;
   }

   public void setTitle(String title) {
      setFieldUsed(TITLE, true);
      m_title = title;
   }

   public void setType(int type) {
      setFieldUsed(TYPE, true);
      m_type = type;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("MailRecord[");
      sb.append("content: ").append(m_content);
      sb.append(", creation-date: ").append(m_creationDate);
      sb.append(", id: ").append(m_id);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", receivers: ").append(m_receivers);
      sb.append(", rule-id: ").append(m_ruleId);
      sb.append(", rule-ids: ").append(m_ruleIds == null ? null : java.util.Arrays.asList(m_ruleIds));
      sb.append(", status: ").append(m_status);
      sb.append(", title: ").append(m_title);
      sb.append(", type: ").append(m_type);
      sb.append("]");
      return sb.toString();
   }

}
