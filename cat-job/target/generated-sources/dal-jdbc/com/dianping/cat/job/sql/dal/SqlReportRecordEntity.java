package com.dianping.cat.job.sql.dal;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "sqlreport", physicalName = "SqlReport", alias = "s")
public class SqlReportRecordEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "domain", nullable = false)
   public static final DataField DOMAIN = new DataField("domain");

   @Attribute(field = "name", nullable = false)
   public static final DataField NAME = new DataField("name");

   @Attribute(field = "statement", nullable = false)
   public static final DataField STATEMENT = new DataField("statement");

   @Attribute(field = "total_count", nullable = false)
   public static final DataField TOTAL_COUNT = new DataField("total-count");

   @Attribute(field = "failure_count", nullable = false)
   public static final DataField FAILURE_COUNT = new DataField("failure-count");

   @Attribute(field = "long_sqls", nullable = false)
   public static final DataField LONG_SQLS = new DataField("long-sqls");

   @Attribute(field = "min_value", nullable = false)
   public static final DataField MIN_VALUE = new DataField("min-value");

   @Attribute(field = "max_value", nullable = false)
   public static final DataField MAX_VALUE = new DataField("max-value");

   @Attribute(field = "avg2_value", nullable = false)
   public static final DataField AVG2_VALUE = new DataField("avg2-value");

   @Attribute(field = "sum_value", nullable = false)
   public static final DataField SUM_VALUE = new DataField("sum-value");

   @Attribute(field = "sum2_value", nullable = false)
   public static final DataField SUM2_VALUE = new DataField("sum2-value");

   @Attribute(field = "sample_link", nullable = false)
   public static final DataField SAMPLE_LINK = new DataField("sample-link");

   @Attribute(field = "transaction_date", nullable = false)
   public static final DataField TRANSACTION_DATE = new DataField("transaction-date");

   @Attribute(field = "creation_date", nullable = false, insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Attribute(field = "duration_distribution", nullable = false)
   public static final DataField DURATION_DISTRIBUTION = new DataField("duration-distribution");

   @Attribute(field = "hits_over_time", nullable = false)
   public static final DataField HITS_OVER_TIME = new DataField("hits-over-time");

   @Attribute(field = "duration_over_time", nullable = false)
   public static final DataField DURATION_OVER_TIME = new DataField("duration-over-time");

   @Attribute(field = "failure_over_time", nullable = false)
   public static final DataField FAILURE_OVER_TIME = new DataField("failure-over-time");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   public static final Readset<SqlReportRecord> READSET_FULL = new Readset<SqlReportRecord>(ID, DOMAIN, NAME, STATEMENT, TOTAL_COUNT, FAILURE_COUNT, LONG_SQLS, MIN_VALUE, MAX_VALUE, AVG2_VALUE, SUM_VALUE, SUM2_VALUE, SAMPLE_LINK, TRANSACTION_DATE, CREATION_DATE, DURATION_DISTRIBUTION, HITS_OVER_TIME, DURATION_OVER_TIME, FAILURE_OVER_TIME);

   public static final Readset<SqlReportRecord> READSET_DOMAIN = new Readset<SqlReportRecord>(DOMAIN);

   public static final Updateset<SqlReportRecord> UPDATESET_FULL = new Updateset<SqlReportRecord>(ID, DOMAIN, NAME, STATEMENT, TOTAL_COUNT, FAILURE_COUNT, LONG_SQLS, MIN_VALUE, MAX_VALUE, AVG2_VALUE, SUM_VALUE, SUM2_VALUE, SAMPLE_LINK, TRANSACTION_DATE, DURATION_DISTRIBUTION, HITS_OVER_TIME, DURATION_OVER_TIME, FAILURE_OVER_TIME);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", SqlReportRecordEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", SqlReportRecordEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>) ON DUPLICATE KEY UPDATE <FIELD name='creation-date'/> = NOW()");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", SqlReportRecordEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", SqlReportRecordEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_ALL_BY_DOMAIN_AND_DATE = new QueryDef("findAllByDomainAndDate", SqlReportRecordEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='domain'/> = ${domain} AND <FIELD name='transaction-date'/> >= ${transaction-date} AND <FIELD name='transaction-date'/> < ADDTIME(${transaction-date}, '1:00:00')");

   public static final QueryDef FIND_ALL_DISTINCT_BY_DATE = new QueryDef("findAllDistinctByDate", SqlReportRecordEntity.class, QueryType.SELECT, 
      "SELECT distinct <FIELDS/> FROM <TABLE/> WHERE <FIELD name='transaction-date'/> >= ${transaction-date} AND <FIELD name='transaction-date'/> < ADDTIME(${transaction-date}, '1:00:00')");

}
