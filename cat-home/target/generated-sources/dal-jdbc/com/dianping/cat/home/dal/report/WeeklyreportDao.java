package com.dianping.cat.home.dal.report;

import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class WeeklyreportDao extends AbstractDao {
   public Weeklyreport createLocal() {
      Weeklyreport proto = new Weeklyreport();

      return proto;
   }

   public int deleteByPK(Weeklyreport proto) throws DalException {
      return getQueryEngine().deleteSingle(
            WeeklyreportEntity.DELETE_BY_PK,
            proto);
   }
   
   public Weeklyreport findByPK(int keyId, Readset<Weeklyreport> readset) throws DalException {
      Weeklyreport proto = new Weeklyreport();

      proto.setKeyId(keyId);

      Weeklyreport result = getQueryEngine().querySingle(
            WeeklyreportEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public Weeklyreport findReportByDomainNamePeriod(java.util.Date period, String domain, String name, Readset<Weeklyreport> readset) throws DalException {
      Weeklyreport proto = new Weeklyreport();

      proto.setPeriod(period);
      proto.setDomain(domain);
      proto.setName(name);

      Weeklyreport result = getQueryEngine().querySingle(
            WeeklyreportEntity.FIND_REPORT_BY_DOMAIN_NAME_PERIOD, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { WeeklyreportEntity.class };
   }

   public int insert(Weeklyreport proto) throws DalException {
      return getQueryEngine().insertSingle(
            WeeklyreportEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Weeklyreport proto, Updateset<Weeklyreport> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            WeeklyreportEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
