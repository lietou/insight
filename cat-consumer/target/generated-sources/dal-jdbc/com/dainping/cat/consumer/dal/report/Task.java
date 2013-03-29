package com.dainping.cat.consumer.dal.report;

import static com.dainping.cat.consumer.dal.report.TaskEntity.CONSUMER;
import static com.dainping.cat.consumer.dal.report.TaskEntity.COUNT;
import static com.dainping.cat.consumer.dal.report.TaskEntity.CREATION_DATE;
import static com.dainping.cat.consumer.dal.report.TaskEntity.END_DATE;
import static com.dainping.cat.consumer.dal.report.TaskEntity.END_LIMIT;
import static com.dainping.cat.consumer.dal.report.TaskEntity.FAILURE_COUNT;
import static com.dainping.cat.consumer.dal.report.TaskEntity.ID;
import static com.dainping.cat.consumer.dal.report.TaskEntity.KEY_ID;
import static com.dainping.cat.consumer.dal.report.TaskEntity.PRODUCER;
import static com.dainping.cat.consumer.dal.report.TaskEntity.REPORT_DOMAIN;
import static com.dainping.cat.consumer.dal.report.TaskEntity.REPORT_NAME;
import static com.dainping.cat.consumer.dal.report.TaskEntity.REPORT_PERIOD;
import static com.dainping.cat.consumer.dal.report.TaskEntity.START_DATE;
import static com.dainping.cat.consumer.dal.report.TaskEntity.START_LIMIT;
import static com.dainping.cat.consumer.dal.report.TaskEntity.STATUS;
import static com.dainping.cat.consumer.dal.report.TaskEntity.TASK_TYPE;

import org.unidal.dal.jdbc.DataObject;

public class Task extends DataObject {
   private int m_id;

   private String m_producer;

   private String m_consumer;

   private int m_failureCount;

   private String m_reportName;

   private String m_reportDomain;

   private java.util.Date m_reportPeriod;

   private int m_status;

   private java.util.Date m_creationDate;

   private java.util.Date m_startDate;

   private java.util.Date m_endDate;

   private int m_taskType;

   private int m_keyId;

   private int m_count;

   private int m_startLimit;

   private int m_endLimit;

   @Override
   public void afterLoad() {
      m_keyId = m_id;
      super.clearUsage();
      }

   public String getConsumer() {
      return m_consumer;
   }

   public int getCount() {
      return m_count;
   }

   public java.util.Date getCreationDate() {
      return m_creationDate;
   }

   public java.util.Date getEndDate() {
      return m_endDate;
   }

   public int getEndLimit() {
      return m_endLimit;
   }

   public int getFailureCount() {
      return m_failureCount;
   }

   public int getId() {
      return m_id;
   }

   public int getKeyId() {
      return m_keyId;
   }

   public String getProducer() {
      return m_producer;
   }

   public String getReportDomain() {
      return m_reportDomain;
   }

   public String getReportName() {
      return m_reportName;
   }

   public java.util.Date getReportPeriod() {
      return m_reportPeriod;
   }

   public java.util.Date getStartDate() {
      return m_startDate;
   }

   public int getStartLimit() {
      return m_startLimit;
   }

   public int getStatus() {
      return m_status;
   }

   public int getTaskType() {
      return m_taskType;
   }

   public void setConsumer(String consumer) {
      setFieldUsed(CONSUMER, true);
      m_consumer = consumer;
   }

   public void setCount(int count) {
      setFieldUsed(COUNT, true);
      m_count = count;
   }

   public void setCreationDate(java.util.Date creationDate) {
      setFieldUsed(CREATION_DATE, true);
      m_creationDate = creationDate;
   }

   public void setEndDate(java.util.Date endDate) {
      setFieldUsed(END_DATE, true);
      m_endDate = endDate;
   }

   public void setEndLimit(int endLimit) {
      setFieldUsed(END_LIMIT, true);
      m_endLimit = endLimit;
   }

   public void setFailureCount(int failureCount) {
      setFieldUsed(FAILURE_COUNT, true);
      m_failureCount = failureCount;
   }

   public void setId(int id) {
      setFieldUsed(ID, true);
      m_id = id;
   }

   public void setKeyId(int keyId) {
      setFieldUsed(KEY_ID, true);
      m_keyId = keyId;
   }

   public void setProducer(String producer) {
      setFieldUsed(PRODUCER, true);
      m_producer = producer;
   }

   public void setReportDomain(String reportDomain) {
      setFieldUsed(REPORT_DOMAIN, true);
      m_reportDomain = reportDomain;
   }

   public void setReportName(String reportName) {
      setFieldUsed(REPORT_NAME, true);
      m_reportName = reportName;
   }

   public void setReportPeriod(java.util.Date reportPeriod) {
      setFieldUsed(REPORT_PERIOD, true);
      m_reportPeriod = reportPeriod;
   }

   public void setStartDate(java.util.Date startDate) {
      setFieldUsed(START_DATE, true);
      m_startDate = startDate;
   }

   public void setStartLimit(int startLimit) {
      setFieldUsed(START_LIMIT, true);
      m_startLimit = startLimit;
   }

   public void setStatus(int status) {
      setFieldUsed(STATUS, true);
      m_status = status;
   }

   public void setTaskType(int taskType) {
      setFieldUsed(TASK_TYPE, true);
      m_taskType = taskType;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("Task[");
      sb.append("consumer: ").append(m_consumer);
      sb.append(", count: ").append(m_count);
      sb.append(", creation-date: ").append(m_creationDate);
      sb.append(", end-date: ").append(m_endDate);
      sb.append(", end-limit: ").append(m_endLimit);
      sb.append(", failure-count: ").append(m_failureCount);
      sb.append(", id: ").append(m_id);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", producer: ").append(m_producer);
      sb.append(", report-domain: ").append(m_reportDomain);
      sb.append(", report-name: ").append(m_reportName);
      sb.append(", report-period: ").append(m_reportPeriod);
      sb.append(", start-date: ").append(m_startDate);
      sb.append(", start-limit: ").append(m_startLimit);
      sb.append(", status: ").append(m_status);
      sb.append(", task-type: ").append(m_taskType);
      sb.append("]");
      return sb.toString();
   }

}
