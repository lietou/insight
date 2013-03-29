package com.dainping.cat.consumer.dal.report;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "report", physicalName = "report", alias = "r")
public class ReportEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "type", nullable = false)
   public static final DataField TYPE = new DataField("type");

   @Attribute(field = "name", nullable = false)
   public static final DataField NAME = new DataField("name");

   @Attribute(field = "ip")
   public static final DataField IP = new DataField("ip");

   @Attribute(field = "domain")
   public static final DataField DOMAIN = new DataField("domain");

   @Attribute(field = "period", nullable = false)
   public static final DataField PERIOD = new DataField("period");

   @Attribute(field = "content", nullable = false)
   public static final DataField CONTENT = new DataField("content");

   @Attribute(field = "creation_date", nullable = false, insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   @Variable
   public static final DataField START_DATE = new DataField("start-date");

   @Variable
   public static final DataField END_DATE = new DataField("end-date");

   public static final Readset<Report> READSET_FULL = new Readset<Report>(ID, TYPE, NAME, IP, DOMAIN, PERIOD, CONTENT, CREATION_DATE);

   public static final Readset<Report> READSET_DOMAIN_NAME = new Readset<Report>(DOMAIN, NAME);

   public static final Updateset<Report> UPDATESET_FULL = new Updateset<Report>(ID, TYPE, NAME, IP, DOMAIN, PERIOD, CONTENT);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", ReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", ReportEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>) ON DUPLICATE KEY UPDATE <FIELD name='content'/> = ${content}, <FIELD name='creation-date'/> = NOW()");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", ReportEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", ReportEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_ALL_BY_DOMAIN_NAME_DURATION = new QueryDef("findAllByDomainNameDuration", ReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> >= ${start-date} AND <FIELD name='period'/> < ${end-date} <IF type='NOT_NULL' field='domain'> AND <FIELD name='domain'/> = ${domain} </IF> <IF type='NOT_NULL' field='name'> AND <FIELD name='name'/> = ${name} </IF> AND type =1");

   public static final QueryDef FIND_ALL_BY_PERIOD_DOMAIN_NAME = new QueryDef("findAllByPeriodDomainName", ReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} AND <FIELD name='domain'/> = ${domain} AND <FIELD name='name'/> = ${name} AND type =1");

   public static final QueryDef FIND_ALL_BY_PERIOD_DOMAIN_TYPE_NAME = new QueryDef("findAllByPeriodDomainTypeName", ReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} AND <FIELD name='domain'/> = ${domain} AND <FIELD name='type'/> = ${type} AND <FIELD name='name'/> = ${name} AND type =1");

   public static final QueryDef FIND_ALL_BY_PERIOD_TYPE_NAME = new QueryDef("findAllByPeriodTypeName", ReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} AND <FIELD name='type'/> = ${type} AND <FIELD name='name'/> = ${name} AND type =1");

   public static final QueryDef FIND_DATABASE_ALL_BY_DOMAIN_NAME_DURATION = new QueryDef("findDatabaseAllByDomainNameDuration", ReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> >= ${start-date} AND <FIELD name='period'/> < ${end-date} <IF type='NOT_NULL' field='domain'> AND <FIELD name='domain'/> = ${domain} </IF> <IF type='NOT_NULL' field='name'> AND <FIELD name='name'/> = ${name} </IF> AND type=2");

   public static final QueryDef FIND_DATABASE_ALL_BY_PERIOD_DOMAIN_NAME = new QueryDef("findDatabaseAllByPeriodDomainName", ReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} AND <FIELD name='domain'/> = ${domain} AND <FIELD name='name'/> = ${name} AND type=2");

   public static final QueryDef FIND_DATABASE_ALL_BY_PERIOD_DOMAIN_TYPE_NAME = new QueryDef("findDatabaseAllByPeriodDomainTypeName", ReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} AND <FIELD name='domain'/> = ${domain} AND <FIELD name='type'/> = ${type} AND <FIELD name='name'/> = ${name} AND type=2");

   public static final QueryDef FIND_DATABASE_ALL_BY_PERIOD_TYPE_NAME = new QueryDef("findDatabaseAllByPeriodTypeName", ReportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} AND <FIELD name='type'/> = ${type} AND <FIELD name='name'/> = ${name} AND type=2");

}
