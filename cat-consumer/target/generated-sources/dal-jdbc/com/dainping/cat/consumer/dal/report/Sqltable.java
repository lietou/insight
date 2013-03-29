package com.dainping.cat.consumer.dal.report;

import static com.dainping.cat.consumer.dal.report.SqltableEntity.CREATION_DATE;
import static com.dainping.cat.consumer.dal.report.SqltableEntity.DOMAIN;
import static com.dainping.cat.consumer.dal.report.SqltableEntity.ID;
import static com.dainping.cat.consumer.dal.report.SqltableEntity.KEY_ID;
import static com.dainping.cat.consumer.dal.report.SqltableEntity.MODIFY_DATE;
import static com.dainping.cat.consumer.dal.report.SqltableEntity.SQL_NAME;
import static com.dainping.cat.consumer.dal.report.SqltableEntity.SQL_STATEMENT;
import static com.dainping.cat.consumer.dal.report.SqltableEntity.TABLE_NAME;

import org.unidal.dal.jdbc.DataObject;

public class Sqltable extends DataObject {
   private int m_id;

   private String m_domain;

   private String m_sqlName;

   private String m_tableName;

   private String m_sqlStatement;

   private java.util.Date m_creationDate;

   private java.util.Date m_modifyDate;

   private int m_keyId;

   @Override
   public void afterLoad() {
      m_keyId = m_id;
      super.clearUsage();
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

   public int getKeyId() {
      return m_keyId;
   }

   public java.util.Date getModifyDate() {
      return m_modifyDate;
   }

   public String getSqlName() {
      return m_sqlName;
   }

   public String getSqlStatement() {
      return m_sqlStatement;
   }

   public String getTableName() {
      return m_tableName;
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

   public void setKeyId(int keyId) {
      setFieldUsed(KEY_ID, true);
      m_keyId = keyId;
   }

   public void setModifyDate(java.util.Date modifyDate) {
      setFieldUsed(MODIFY_DATE, true);
      m_modifyDate = modifyDate;
   }

   public void setSqlName(String sqlName) {
      setFieldUsed(SQL_NAME, true);
      m_sqlName = sqlName;
   }

   public void setSqlStatement(String sqlStatement) {
      setFieldUsed(SQL_STATEMENT, true);
      m_sqlStatement = sqlStatement;
   }

   public void setTableName(String tableName) {
      setFieldUsed(TABLE_NAME, true);
      m_tableName = tableName;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("Sqltable[");
      sb.append("creation-date: ").append(m_creationDate);
      sb.append(", domain: ").append(m_domain);
      sb.append(", id: ").append(m_id);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", modify-date: ").append(m_modifyDate);
      sb.append(", sql-name: ").append(m_sqlName);
      sb.append(", sql-statement: ").append(m_sqlStatement);
      sb.append(", table-name: ").append(m_tableName);
      sb.append("]");
      return sb.toString();
   }

}
