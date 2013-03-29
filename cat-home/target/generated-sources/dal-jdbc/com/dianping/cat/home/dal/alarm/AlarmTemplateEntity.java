package com.dianping.cat.home.dal.alarm;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "alarm-template", physicalName = "alarmTemplate", alias = "a")
public class AlarmTemplateEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "name")
   public static final DataField NAME = new DataField("name");

   @Attribute(field = "content")
   public static final DataField CONTENT = new DataField("content");

   @Attribute(field = "creation_date", insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Attribute(field = "modify_date", insertExpr = "NOW()", updateExpr = "NOW()")
   public static final DataField MODIFY_DATE = new DataField("modify-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   public static final Readset<AlarmTemplate> READSET_FULL = new Readset<AlarmTemplate>(ID, NAME, CONTENT, CREATION_DATE, MODIFY_DATE);

   public static final Updateset<AlarmTemplate> UPDATESET_FULL = new Updateset<AlarmTemplate>(ID, NAME, CONTENT, MODIFY_DATE);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", AlarmTemplateEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", AlarmTemplateEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", AlarmTemplateEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", AlarmTemplateEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_ALL_ALARM_TEMPLATE = new QueryDef("findAllAlarmTemplate", AlarmTemplateEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/>");

   public static final QueryDef FIND_ALARM_TEMPLATE_BY_NAME = new QueryDef("findAlarmTemplateByName", AlarmTemplateEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='name'/> = ${name}");

}
