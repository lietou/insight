package com.dianping.cat.home.dal.report;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "location", physicalName = "location", alias = "l")
public class LocationEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "lat", nullable = false)
   public static final DataField LAT = new DataField("lat");

   @Attribute(field = "lng", nullable = false)
   public static final DataField LNG = new DataField("lng");

   @Attribute(field = "total", nullable = false)
   public static final DataField TOTAL = new DataField("total");

   @Attribute(field = "transaction_date", nullable = false)
   public static final DataField TRANSACTION_DATE = new DataField("transaction-date");

   @Attribute(field = "creation_date", nullable = false)
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   public static final Readset<Location> READSET_FULL = new Readset<Location>(ID, LAT, LNG, TOTAL, TRANSACTION_DATE, CREATION_DATE);

   public static final Updateset<Location> UPDATESET_FULL = new Updateset<Location>(ID, LAT, LNG, TOTAL, TRANSACTION_DATE, CREATION_DATE);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", LocationEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", LocationEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", LocationEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", LocationEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

}
