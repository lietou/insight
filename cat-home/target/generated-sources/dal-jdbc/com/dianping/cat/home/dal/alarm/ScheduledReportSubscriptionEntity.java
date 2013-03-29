package com.dianping.cat.home.dal.alarm;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "scheduled-report-subscription", physicalName = "scheduledReportSubscription", alias = "s2")
public class ScheduledReportSubscriptionEntity {

   @Attribute(field = "scheduled_report_id", nullable = false, primaryKey = true)
   public static final DataField SCHEDULED_REPORT_ID = new DataField("scheduled-report-id");

   @Attribute(field = "user_id", nullable = false, primaryKey = true)
   public static final DataField USER_ID = new DataField("user-id");

   @Attribute(field = "creation_date", insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Variable
   public static final DataField KEY_SCHEDULED_REPORT_ID = new DataField("key-scheduled-report-id");

   @Variable
   public static final DataField KEY_USER_ID = new DataField("key-user-id");

   public static final Readset<ScheduledReportSubscription> READSET_FULL = new Readset<ScheduledReportSubscription>(SCHEDULED_REPORT_ID, USER_ID, CREATION_DATE);

   public static final Updateset<ScheduledReportSubscription> UPDATESET_FULL = new Updateset<ScheduledReportSubscription>(SCHEDULED_REPORT_ID, USER_ID);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", ScheduledReportSubscriptionEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='scheduled-report-id'/> = ${key-scheduled-report-id} AND <FIELD name='user-id'/> = ${key-user-id}");

   public static final QueryDef INSERT = new QueryDef("insert", ScheduledReportSubscriptionEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", ScheduledReportSubscriptionEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='scheduled-report-id'/> = ${key-scheduled-report-id} AND <FIELD name='user-id'/> = ${key-user-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", ScheduledReportSubscriptionEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='scheduled-report-id'/> = ${key-scheduled-report-id} AND <FIELD name='user-id'/> = ${key-user-id}");

   public static final QueryDef FIND_BY_USER_ID = new QueryDef("findByUserId", ScheduledReportSubscriptionEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='user-id'/> = ${user-id}");

   public static final QueryDef FIND_BY_SCHEDULED_REPORT_ID = new QueryDef("findByScheduledReportId", ScheduledReportSubscriptionEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='scheduled-report-id'/> = ${scheduled-report-id}");

}
