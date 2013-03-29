package com.dianping.cat.home.dal.report;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class DailyreportDao extends AbstractDao {
   public Dailyreport createLocal() {
      Dailyreport proto = new Dailyreport();

      return proto;
   }

   public int deleteByPK(Dailyreport proto) throws DalException {
      return getQueryEngine().deleteSingle(
            DailyreportEntity.DELETE_BY_PK,
            proto);
   }
   
   public int deleteByDomainNamePeriod(Dailyreport proto) throws DalException {
      return getQueryEngine().deleteSingle(
            DailyreportEntity.DELETE_BY_DOMAIN_NAME_PERIOD,
            proto);
   }
   
   public int deleteDatabaseByDomainNamePeriod(Dailyreport proto) throws DalException {
      return getQueryEngine().deleteSingle(
            DailyreportEntity.DELETE_DATABASE_BY_DOMAIN_NAME_PERIOD,
            proto);
   }
   
   public List<Dailyreport> findAllDomainsByNameDuration(java.util.Date startDate, java.util.Date endDate, String name, Readset<Dailyreport> readset) throws DalException {
      Dailyreport proto = new Dailyreport();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setName(name);

      List<Dailyreport> result = getQueryEngine().queryMultiple(
            DailyreportEntity.FIND_ALL_DOMAINS_BY_NAME_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Dailyreport> findAllByDomainNameDuration(java.util.Date startDate, java.util.Date endDate, String domain, String name, Readset<Dailyreport> readset) throws DalException {
      Dailyreport proto = new Dailyreport();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setDomain(domain);
      proto.setName(name);

      List<Dailyreport> result = getQueryEngine().queryMultiple(
            DailyreportEntity.FIND_ALL_BY_DOMAIN_NAME_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Dailyreport> findAllByPeriod(java.util.Date startDate, java.util.Date endDate, Readset<Dailyreport> readset) throws DalException {
      Dailyreport proto = new Dailyreport();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);

      List<Dailyreport> result = getQueryEngine().queryMultiple(
            DailyreportEntity.FIND_ALL_BY_PERIOD, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Dailyreport> findDatabaseAllByDomainNameDuration(java.util.Date startDate, java.util.Date endDate, String domain, String name, Readset<Dailyreport> readset) throws DalException {
      Dailyreport proto = new Dailyreport();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);
      proto.setDomain(domain);
      proto.setName(name);

      List<Dailyreport> result = getQueryEngine().queryMultiple(
            DailyreportEntity.FIND_DATABASE_ALL_BY_DOMAIN_NAME_DURATION, 
            proto,
            readset);
      
      return result;
   }
   
   public List<Dailyreport> findDatabaseAllByPeriod(java.util.Date startDate, java.util.Date endDate, Readset<Dailyreport> readset) throws DalException {
      Dailyreport proto = new Dailyreport();

      proto.setStartDate(startDate);
      proto.setEndDate(endDate);

      List<Dailyreport> result = getQueryEngine().queryMultiple(
            DailyreportEntity.FIND_DATABASE_ALL_BY_PERIOD, 
            proto,
            readset);
      
      return result;
   }
   
   public Dailyreport findByPK(int keyId, Readset<Dailyreport> readset) throws DalException {
      Dailyreport proto = new Dailyreport();

      proto.setKeyId(keyId);

      Dailyreport result = getQueryEngine().querySingle(
            DailyreportEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public Dailyreport findReportByDomainNamePeriod(java.util.Date period, String domain, String name, Readset<Dailyreport> readset) throws DalException {
      Dailyreport proto = new Dailyreport();

      proto.setPeriod(period);
      proto.setDomain(domain);
      proto.setName(name);

      Dailyreport result = getQueryEngine().querySingle(
            DailyreportEntity.FIND_REPORT_BY_DOMAIN_NAME_PERIOD, 
            proto,
            readset);
      
      return result;
   }
   
   public Dailyreport findByNameDomainPeriod(java.util.Date period, String domain, String name, Readset<Dailyreport> readset) throws DalException {
      Dailyreport proto = new Dailyreport();

      proto.setPeriod(period);
      proto.setDomain(domain);
      proto.setName(name);

      Dailyreport result = getQueryEngine().querySingle(
            DailyreportEntity.FIND_BY_NAME_DOMAIN_PERIOD, 
            proto,
            readset);
      
      return result;
   }
   
   public Dailyreport findDatabaseByNameDomainPeriod(java.util.Date period, String domain, String name, Readset<Dailyreport> readset) throws DalException {
      Dailyreport proto = new Dailyreport();

      proto.setPeriod(period);
      proto.setDomain(domain);
      proto.setName(name);

      Dailyreport result = getQueryEngine().querySingle(
            DailyreportEntity.FIND_DATABASE_BY_NAME_DOMAIN_PERIOD, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { DailyreportEntity.class };
   }

   public int insert(Dailyreport proto) throws DalException {
      return getQueryEngine().insertSingle(
            DailyreportEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Dailyreport proto, Updateset<Dailyreport> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            DailyreportEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
