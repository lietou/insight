package com.dianping.cat.home.dal.alarm;

public class _INDEX {
   public static Class<?>[] getEntityClasses() {
      return new Class<?>[] { AlarmTemplateEntity.class, AlarmRuleEntity.class, AlarmRuleSubscriptionEntity.class, MailRecordEntity.class, ScheduledReportEntity.class, ScheduledReportSubscriptionEntity.class };
   }

   public static Class<?>[] getDaoClasses() {
      return new Class<?>[] { AlarmTemplateDao.class, AlarmRuleDao.class, AlarmRuleSubscriptionDao.class, MailRecordDao.class, ScheduledReportDao.class, ScheduledReportSubscriptionDao.class };
   }

   public static Class<?>[] getDoClasses() {
      return new Class<?>[] { AlarmTemplate.class, AlarmRule.class, AlarmRuleSubscription.class, MailRecord.class, ScheduledReport.class, ScheduledReportSubscription.class };
   }

}
