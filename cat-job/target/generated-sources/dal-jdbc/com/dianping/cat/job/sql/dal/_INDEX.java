package com.dianping.cat.job.sql.dal;

public class _INDEX {
   public static Class<?>[] getEntityClasses() {
      return new Class<?>[] { LocationRecordEntity.class, SqlReportRecordEntity.class };
   }

   public static Class<?>[] getDaoClasses() {
      return new Class<?>[] { LocationRecordDao.class, SqlReportRecordDao.class };
   }

   public static Class<?>[] getDoClasses() {
      return new Class<?>[] { LocationRecord.class, SqlReportRecord.class };
   }

}
