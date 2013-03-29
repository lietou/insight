package com.dianping.cat.home.dal.alarm;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "mail-record", physicalName = "mailRecord", alias = "m")
public class MailRecordEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "type", nullable = false)
   public static final DataField TYPE = new DataField("type");

   @Attribute(field = "rule_id", nullable = false)
   public static final DataField RULE_ID = new DataField("rule-id");

   @Attribute(field = "receivers", nullable = false)
   public static final DataField RECEIVERS = new DataField("receivers");

   @Attribute(field = "title", nullable = false)
   public static final DataField TITLE = new DataField("title");

   @Attribute(field = "content", nullable = false)
   public static final DataField CONTENT = new DataField("content");

   @Attribute(field = "status", nullable = false)
   public static final DataField STATUS = new DataField("status");

   @Attribute(field = "creation_date", nullable = false, insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   @Variable
   public static final DataField RULE_IDS = new DataField("rule-ids");

   public static final Readset<MailRecord> READSET_FULL = new Readset<MailRecord>(ID, TYPE, RULE_ID, RECEIVERS, TITLE, CONTENT, STATUS, CREATION_DATE);

   public static final Updateset<MailRecord> UPDATESET_FULL = new Updateset<MailRecord>(ID, TYPE, RULE_ID, RECEIVERS, TITLE, CONTENT, STATUS);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", MailRecordEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", MailRecordEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", MailRecordEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", MailRecordEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_LAST_REPORT_RECORD = new QueryDef("findLastReportRecord", MailRecordEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='type'/> = 1 ORDER BY <FIELD name='id'/> desc limit 100");

   public static final QueryDef FIND_ALARM_RECORD_BY_RULE_ID = new QueryDef("findAlarmRecordByRuleId", MailRecordEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='rule-id'/> in <IN>${rule-ids}</IN> AND <FIELD name='type'/> > 1 ORDER BY <FIELD name='id'/> desc limit 100");

   public static final QueryDef FIND_REPORT_RECORD_BY_RULE_ID = new QueryDef("findReportRecordByRuleId", MailRecordEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='rule-id'/> in <IN>${rule-ids}</IN> AND <FIELD name='type'/> = 1 ORDER BY <FIELD name='id'/> desc limit 100");

}
