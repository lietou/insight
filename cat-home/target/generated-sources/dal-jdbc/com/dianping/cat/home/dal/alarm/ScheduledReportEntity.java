package com.dianping.cat.home.dal.alarm;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "scheduled-report", physicalName = "scheduledReport", alias = "s")
public class ScheduledReportEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "domain")
   public static final DataField DOMAIN = new DataField("domain");

   @Attribute(field = "names")
   public static final DataField NAMES = new DataField("names");

   @Attribute(field = "creation_date", insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Attribute(field = "modify_date", insertExpr = "NOW()", updateExpr = "NOW()")
   public static final DataField MODIFY_DATE = new DataField("modify-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   public static final Readset<ScheduledReport> READSET_FULL = new Readset<ScheduledReport>(ID, DOMAIN, NAMES, CREATION_DATE, MODIFY_DATE);

   public static final Updateset<ScheduledReport> UPDATESET_FULL = new Updateset<ScheduledReport>(ID, DOMAIN, NAMES, MODIFY_DATE);

   public static final Updateset<ScheduledReport> UPDATESET_UPDATE_REPORTS = new Updateset<ScheduledReport>(NAMES, MODIFY_DATE);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", ScheduledReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", ScheduledReportEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", ScheduledReportEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", ScheduledReportEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_ALL = new QueryDef("findAll", ScheduledReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> ORDER BY <FIELD name='domain'/> ASC");

}
