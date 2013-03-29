package com.dianping.cat.home.dal.report;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "dailyreport", physicalName = "dailyreport", alias = "dr")
public class DailyreportEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "name", nullable = false)
   public static final DataField NAME = new DataField("name");

   @Attribute(field = "ip", nullable = false)
   public static final DataField IP = new DataField("ip");

   @Attribute(field = "domain", nullable = false)
   public static final DataField DOMAIN = new DataField("domain");

   @Attribute(field = "period", nullable = false)
   public static final DataField PERIOD = new DataField("period");

   @Attribute(field = "type", nullable = false)
   public static final DataField TYPE = new DataField("type");

   @Attribute(field = "content", nullable = false)
   public static final DataField CONTENT = new DataField("content");

   @Attribute(field = "creation_date", nullable = false)
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Attribute(field = "", selectExpr = "COUNT(*)")
   public static final DataField COUNT = new DataField("count");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   @Variable
   public static final DataField START_DATE = new DataField("start-date");

   @Variable
   public static final DataField END_DATE = new DataField("end-date");

   public static final Readset<Dailyreport> READSET_FULL = new Readset<Dailyreport>(ID, NAME, IP, DOMAIN, PERIOD, TYPE, CONTENT, CREATION_DATE);

   public static final Readset<Dailyreport> READSET_DOMAIN_NAME = new Readset<Dailyreport>(DOMAIN, NAME);

   public static final Readset<Dailyreport> READSET_COUNT = new Readset<Dailyreport>(COUNT);

   public static final Updateset<Dailyreport> UPDATESET_FULL = new Updateset<Dailyreport>(ID, NAME, IP, DOMAIN, PERIOD, TYPE, CONTENT, CREATION_DATE);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", DailyreportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", DailyreportEntity.class, QueryType.INSERT, 
      "INSERT IGNORE INTO <TABLE/> (<FIELDS/>) VALUES (<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", DailyreportEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", DailyreportEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_REPORT_BY_DOMAIN_NAME_PERIOD = new QueryDef("findReportByDomainNamePeriod", DailyreportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> == ${period} AND <FIELD name='domain'/> = ${domain} AND <FIELD name='name'/> = ${name}");

   public static final QueryDef FIND_ALL_DOMAINS_BY_NAME_DURATION = new QueryDef("findAllDomainsByNameDuration", DailyreportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> >= ${start-date} AND <FIELD name='period'/> < ${end-date} AND <FIELD name='name'/> = ${name} AND type = 1");

   public static final QueryDef FIND_ALL_BY_DOMAIN_NAME_DURATION = new QueryDef("findAllByDomainNameDuration", DailyreportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> >= ${start-date} AND <FIELD name='period'/> < ${end-date} AND <FIELD name='domain'/> = ${domain} AND <FIELD name='name'/> = ${name} AND type = 1");

   public static final QueryDef FIND_BY_NAME_DOMAIN_PERIOD = new QueryDef("findByNameDomainPeriod", DailyreportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} <IF type='NOT_NULL' field='domain'> AND <FIELD name='domain'/> = ${domain} </IF> <IF type='NOT_NULL' field='name'> AND <FIELD name='name'/> = ${name} </IF> AND type = 1");

   public static final QueryDef FIND_ALL_BY_PERIOD = new QueryDef("findAllByPeriod", DailyreportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> >= ${start-date} AND <FIELD name='period'/> < ${end-date} AND type = 1");

   public static final QueryDef DELETE_BY_DOMAIN_NAME_PERIOD = new QueryDef("deleteByDomainNamePeriod", DailyreportEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} <IF type='NOT_NULL' field='domain'> AND <FIELD name='domain'/> = ${domain} </IF> <IF type='NOT_NULL' field='name'> AND <FIELD name='name'/> = ${name} </IF> AND type =1;");

   public static final QueryDef FIND_DATABASE_ALL_BY_DOMAIN_NAME_DURATION = new QueryDef("findDatabaseAllByDomainNameDuration", DailyreportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> >= ${start-date} AND <FIELD name='period'/> < ${end-date} AND <FIELD name='domain'/> = ${domain} AND <FIELD name='name'/> = ${name} AND type = 2");

   public static final QueryDef FIND_DATABASE_BY_NAME_DOMAIN_PERIOD = new QueryDef("findDatabaseByNameDomainPeriod", DailyreportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} <IF type='NOT_NULL' field='domain'> AND <FIELD name='domain'/> = ${domain} </IF> <IF type='NOT_NULL' field='name'> AND <FIELD name='name'/> = ${name} </IF> AND type = 2");

   public static final QueryDef FIND_DATABASE_ALL_BY_PERIOD = new QueryDef("findDatabaseAllByPeriod", DailyreportEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='period'/> >= ${start-date} AND <FIELD name='period'/> < ${end-date} AND type =2");

   public static final QueryDef DELETE_DATABASE_BY_DOMAIN_NAME_PERIOD = new QueryDef("deleteDatabaseByDomainNamePeriod", DailyreportEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} <IF type='NOT_NULL' field='domain'> AND <FIELD name='domain'/> = ${domain} </IF> <IF type='NOT_NULL' field='name'> AND <FIELD name='name'/> = ${name} </IF> AND type =2;");

}
