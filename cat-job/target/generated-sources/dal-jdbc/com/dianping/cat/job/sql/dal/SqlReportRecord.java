package com.dianping.cat.job.sql.dal;

import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.AVG2_VALUE;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.CREATION_DATE;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.DOMAIN;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.DURATION_DISTRIBUTION;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.DURATION_OVER_TIME;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.FAILURE_COUNT;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.FAILURE_OVER_TIME;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.HITS_OVER_TIME;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.ID;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.KEY_ID;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.LONG_SQLS;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.MAX_VALUE;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.MIN_VALUE;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.NAME;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.SAMPLE_LINK;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.STATEMENT;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.SUM2_VALUE;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.SUM_VALUE;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.TOTAL_COUNT;
import static com.dianping.cat.job.sql.dal.SqlReportRecordEntity.TRANSACTION_DATE;

import org.unidal.dal.jdbc.DataObject;

public class SqlReportRecord extends DataObject {
   private int m_id;

   private String m_domain;

   private String m_name;

   private String m_statement;

   private int m_totalCount;

   private int m_failureCount;

   private int m_longSqls;

   private double m_minValue;

   private double m_maxValue;

   private double m_avg2Value;

   private double m_sumValue;

   private double m_sum2Value;

   private String m_sampleLink;

   private java.util.Date m_transactionDate;

   private java.util.Date m_creationDate;

   private String m_durationDistribution;

   private String m_hitsOverTime;

   private String m_durationOverTime;

   private String m_failureOverTime;

   private int m_keyId;

   @Override
   public void afterLoad() {
      m_keyId = m_id;
      super.clearUsage();
      }

   public double getAvg2Value() {
      return m_avg2Value;
   }

   public java.util.Date getCreationDate() {
      return m_creationDate;
   }

   public String getDomain() {
      return m_domain;
   }

   public String getDurationDistribution() {
      return m_durationDistribution;
   }

   public String getDurationOverTime() {
      return m_durationOverTime;
   }

   public int getFailureCount() {
      return m_failureCount;
   }

   public String getFailureOverTime() {
      return m_failureOverTime;
   }

   public String getHitsOverTime() {
      return m_hitsOverTime;
   }

   public int getId() {
      return m_id;
   }

   public int getKeyId() {
      return m_keyId;
   }

   public int getLongSqls() {
      return m_longSqls;
   }

   public double getMaxValue() {
      return m_maxValue;
   }

   public double getMinValue() {
      return m_minValue;
   }

   public String getName() {
      return m_name;
   }

   public String getSampleLink() {
      return m_sampleLink;
   }

   public String getStatement() {
      return m_statement;
   }

   public double getSum2Value() {
      return m_sum2Value;
   }

   public double getSumValue() {
      return m_sumValue;
   }

   public int getTotalCount() {
      return m_totalCount;
   }

   public java.util.Date getTransactionDate() {
      return m_transactionDate;
   }

   public void setAvg2Value(double avg2Value) {
      setFieldUsed(AVG2_VALUE, true);
      m_avg2Value = avg2Value;
   }

   public void setCreationDate(java.util.Date creationDate) {
      setFieldUsed(CREATION_DATE, true);
      m_creationDate = creationDate;
   }

   public void setDomain(String domain) {
      setFieldUsed(DOMAIN, true);
      m_domain = domain;
   }

   public void setDurationDistribution(String durationDistribution) {
      setFieldUsed(DURATION_DISTRIBUTION, true);
      m_durationDistribution = durationDistribution;
   }

   public void setDurationOverTime(String durationOverTime) {
      setFieldUsed(DURATION_OVER_TIME, true);
      m_durationOverTime = durationOverTime;
   }

   public void setFailureCount(int failureCount) {
      setFieldUsed(FAILURE_COUNT, true);
      m_failureCount = failureCount;
   }

   public void setFailureOverTime(String failureOverTime) {
      setFieldUsed(FAILURE_OVER_TIME, true);
      m_failureOverTime = failureOverTime;
   }

   public void setHitsOverTime(String hitsOverTime) {
      setFieldUsed(HITS_OVER_TIME, true);
      m_hitsOverTime = hitsOverTime;
   }

   public void setId(int id) {
      setFieldUsed(ID, true);
      m_id = id;
   }

   public void setKeyId(int keyId) {
      setFieldUsed(KEY_ID, true);
      m_keyId = keyId;
   }

   public void setLongSqls(int longSqls) {
      setFieldUsed(LONG_SQLS, true);
      m_longSqls = longSqls;
   }

   public void setMaxValue(double maxValue) {
      setFieldUsed(MAX_VALUE, true);
      m_maxValue = maxValue;
   }

   public void setMinValue(double minValue) {
      setFieldUsed(MIN_VALUE, true);
      m_minValue = minValue;
   }

   public void setName(String name) {
      setFieldUsed(NAME, true);
      m_name = name;
   }

   public void setSampleLink(String sampleLink) {
      setFieldUsed(SAMPLE_LINK, true);
      m_sampleLink = sampleLink;
   }

   public void setStatement(String statement) {
      setFieldUsed(STATEMENT, true);
      m_statement = statement;
   }

   public void setSum2Value(double sum2Value) {
      setFieldUsed(SUM2_VALUE, true);
      m_sum2Value = sum2Value;
   }

   public void setSumValue(double sumValue) {
      setFieldUsed(SUM_VALUE, true);
      m_sumValue = sumValue;
   }

   public void setTotalCount(int totalCount) {
      setFieldUsed(TOTAL_COUNT, true);
      m_totalCount = totalCount;
   }

   public void setTransactionDate(java.util.Date transactionDate) {
      setFieldUsed(TRANSACTION_DATE, true);
      m_transactionDate = transactionDate;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("SqlReportRecord[");
      sb.append("avg2-value: ").append(m_avg2Value);
      sb.append(", creation-date: ").append(m_creationDate);
      sb.append(", domain: ").append(m_domain);
      sb.append(", duration-distribution: ").append(m_durationDistribution);
      sb.append(", duration-over-time: ").append(m_durationOverTime);
      sb.append(", failure-count: ").append(m_failureCount);
      sb.append(", failure-over-time: ").append(m_failureOverTime);
      sb.append(", hits-over-time: ").append(m_hitsOverTime);
      sb.append(", id: ").append(m_id);
      sb.append(", key-id: ").append(m_keyId);
      sb.append(", long-sqls: ").append(m_longSqls);
      sb.append(", max-value: ").append(m_maxValue);
      sb.append(", min-value: ").append(m_minValue);
      sb.append(", name: ").append(m_name);
      sb.append(", sample-link: ").append(m_sampleLink);
      sb.append(", statement: ").append(m_statement);
      sb.append(", sum2-value: ").append(m_sum2Value);
      sb.append(", sum-value: ").append(m_sumValue);
      sb.append(", total-count: ").append(m_totalCount);
      sb.append(", transaction-date: ").append(m_transactionDate);
      sb.append("]");
      return sb.toString();
   }

}
