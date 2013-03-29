package com.dianping.cat.home.dal.report;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "dailygraph", physicalName = "dailygraph", alias = "d")
public class DailygraphEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "name", nullable = false)
   public static final DataField NAME = new DataField("name");

   @Attribute(field = "ip")
   public static final DataField IP = new DataField("ip");

   @Attribute(field = "domain", nullable = false)
   public static final DataField DOMAIN = new DataField("domain");

   @Attribute(field = "period", nullable = false)
   public static final DataField PERIOD = new DataField("period");

   @Attribute(field = "type", nullable = false)
   public static final DataField TYPE = new DataField("type");

   @Attribute(field = "detail_content", nullable = false)
   public static final DataField DETAIL_CONTENT = new DataField("detail-content");

   @Attribute(field = "summary_content", nullable = false)
   public static final DataField SUMMARY_CONTENT = new DataField("summary-content");

   @Attribute(field = "creation_date", nullable = false)
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   @Variable
   public static final DataField START_DATE = new DataField("start-date");

   @Variable
   public static final DataField END_DATE = new DataField("end-date");

   public static final Readset<Dailygraph> READSET_FULL = new Readset<Dailygraph>(ID, NAME, IP, DOMAIN, PERIOD, TYPE, DETAIL_CONTENT, SUMMARY_CONTENT, CREATION_DATE);

   public static final Readset<Dailygraph> READSET_DOMAIN = new Readset<Dailygraph>(DOMAIN);

   public static final Readset<Dailygraph> READSET_IP = new Readset<Dailygraph>(IP);

   public static final Updateset<Dailygraph> UPDATESET_FULL = new Updateset<Dailygraph>(ID, NAME, IP, DOMAIN, PERIOD, TYPE, DETAIL_CONTENT, SUMMARY_CONTENT, CREATION_DATE);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", DailygraphEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", DailygraphEntity.class, QueryType.INSERT, 
      "INSERT IGNORE INTO <TABLE/> (<FIELDS/>) VALUES (<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", DailygraphEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", DailygraphEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_BY_DOMAIN_NAME_IP_DURATION = new QueryDef("findByDomainNameIpDuration", DailygraphEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='name'/> = ${name} AND <FIELD name='domain'/> = ${domain} AND <FIELD name='ip'/> = ${ip} AND <FIELD name='period'/> >= ${start-date} AND <FIELD name='period'/> < ${end-date} ORDER BY <FIELD name='period'/> ASC");

   public static final QueryDef FIND_SINGAL_BY_DOMAIN_NAME_IP_DURATION = new QueryDef("findSingalByDomainNameIpDuration", DailygraphEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='name'/> = ${name} AND <FIELD name='domain'/> = ${domain} AND <FIELD name='ip'/> = ${ip} AND <FIELD name='period'/> = ${start-date}");

   public static final QueryDef FIND_IP_BY_DOMAIN_NAME_DURATION = new QueryDef("findIpByDomainNameDuration", DailygraphEntity.class, QueryType.SELECT, 
      "SELECT DISTINCT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='domain'/> = ${domain} AND <FIELD name='name'/> = ${name} AND <FIELD name='period'/> >= ${start-date} AND <FIELD name='period'/> < ${end-date}");

   public static final QueryDef FIND_DOMAIN_BY_NAME_DURATION = new QueryDef("findDomainByNameDuration", DailygraphEntity.class, QueryType.SELECT, 
      "SELECT DISTINCT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='name'/> = ${name} AND <FIELD name='period'/> >= ${start-date} AND <FIELD name='period'/> < ${end-date}");

   public static final QueryDef DELETE_BY_DOMAIN_NAME_PERIOD_IP = new QueryDef("deleteByDomainNamePeriodIp", DailygraphEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='period'/> = ${period} <IF type='NOT_NULL' field='domain'> AND <FIELD name='domain'/> = ${domain} </IF> <IF type='NOT_NULL' field='name'> AND <FIELD name='name'/> = ${name} </IF> <IF type='NOT_NULL' field='ip'> AND <FIELD name='ip'/> = ${ip} </IF>");

}
