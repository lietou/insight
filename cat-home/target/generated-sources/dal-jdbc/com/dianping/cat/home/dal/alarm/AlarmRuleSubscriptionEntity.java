package com.dianping.cat.home.dal.alarm;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "alarm-rule-subscription", physicalName = "alarmRuleSubscription", alias = "a3")
public class AlarmRuleSubscriptionEntity {

   @Attribute(field = "alarm_rule_id", nullable = false, primaryKey = true)
   public static final DataField ALARM_RULE_ID = new DataField("alarm-rule-id");

   @Attribute(field = "user_id", nullable = false, primaryKey = true)
   public static final DataField USER_ID = new DataField("user-id");

   @Attribute(field = "creation_date", insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Variable
   public static final DataField KEY_ALARM_RULE_ID = new DataField("key-alarm-rule-id");

   @Variable
   public static final DataField KEY_USER_ID = new DataField("key-user-id");

   public static final Readset<AlarmRuleSubscription> READSET_FULL = new Readset<AlarmRuleSubscription>(ALARM_RULE_ID, USER_ID, CREATION_DATE);

   public static final Updateset<AlarmRuleSubscription> UPDATESET_FULL = new Updateset<AlarmRuleSubscription>(ALARM_RULE_ID, USER_ID);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", AlarmRuleSubscriptionEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='alarm-rule-id'/> = ${key-alarm-rule-id} AND <FIELD name='user-id'/> = ${key-user-id}");

   public static final QueryDef INSERT = new QueryDef("insert", AlarmRuleSubscriptionEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", AlarmRuleSubscriptionEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='alarm-rule-id'/> = ${key-alarm-rule-id} AND <FIELD name='user-id'/> = ${key-user-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", AlarmRuleSubscriptionEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='alarm-rule-id'/> = ${key-alarm-rule-id} AND <FIELD name='user-id'/> = ${key-user-id}");

   public static final QueryDef FIND_BY_USER_ID = new QueryDef("findByUserId", AlarmRuleSubscriptionEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='user-id'/> = ${user-id}");

   public static final QueryDef FIND_BY_ALARM_RULE_ID = new QueryDef("findByAlarmRuleId", AlarmRuleSubscriptionEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='alarm-rule-id'/> = ${alarm-rule-id}");

}
