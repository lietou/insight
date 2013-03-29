package com.dianping.cat.home.dal.alarm;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "alarm-rule", physicalName = "alarmRule", alias = "a2")
public class AlarmRuleEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "template_id", nullable = false)
   public static final DataField TEMPLATE_ID = new DataField("template-id");

   @Attribute(field = "domain")
   public static final DataField DOMAIN = new DataField("domain");

   @Attribute(field = "content")
   public static final DataField CONTENT = new DataField("content");

   @Attribute(field = "creation_date", insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Attribute(field = "modify_date", insertExpr = "NOW()", updateExpr = "NOW()")
   public static final DataField MODIFY_DATE = new DataField("modify-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   public static final Readset<AlarmRule> READSET_FULL = new Readset<AlarmRule>(ID, TEMPLATE_ID, DOMAIN, CONTENT, CREATION_DATE, MODIFY_DATE);

   public static final Updateset<AlarmRule> UPDATESET_FULL = new Updateset<AlarmRule>(ID, TEMPLATE_ID, DOMAIN, CONTENT, MODIFY_DATE);

   public static final Updateset<AlarmRule> UPDATESET_UPDATE_CONTENT = new Updateset<AlarmRule>(CONTENT, MODIFY_DATE);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", AlarmRuleEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", AlarmRuleEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", AlarmRuleEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", AlarmRuleEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_ALL_ALARM_RULE = new QueryDef("findAllAlarmRule", AlarmRuleEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> ORDER BY <FIELD name='domain'/> ASC");

   public static final QueryDef FIND_ALL_ALARM_RULE_BY_TEMPLATE_ID = new QueryDef("findAllAlarmRuleByTemplateId", AlarmRuleEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='template-id'/> = ${template-id} ORDER BY <FIELD name='domain'/> ASC");

}
