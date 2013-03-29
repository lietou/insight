package com.dainping.cat.home.dal.user;

import static com.dainping.cat.home.dal.user.DpAdminLoginEntity.ADMIN_ID;
import static com.dainping.cat.home.dal.user.DpAdminLoginEntity.EMAIL;
import static com.dainping.cat.home.dal.user.DpAdminLoginEntity.KEY_LOGIN_ID;
import static com.dainping.cat.home.dal.user.DpAdminLoginEntity.LOGIN_ID;
import static com.dainping.cat.home.dal.user.DpAdminLoginEntity.LOGIN_NAME;
import static com.dainping.cat.home.dal.user.DpAdminLoginEntity.MOBILE_NO;
import static com.dainping.cat.home.dal.user.DpAdminLoginEntity.PASSWORD;
import static com.dainping.cat.home.dal.user.DpAdminLoginEntity.REAL_NAME;

import org.unidal.dal.jdbc.DataObject;

public class DpAdminLogin extends DataObject {
   private int m_loginId;

   private int m_adminId;

   private String m_loginName;

   private String m_password;

   private String m_email;

   private String m_realName;

   private String m_mobileNo;

   private int m_keyLoginId;

   @Override
   public void afterLoad() {
      m_keyLoginId = m_loginId;
      super.clearUsage();
      }

   public int getAdminId() {
      return m_adminId;
   }

   public String getEmail() {
      return m_email;
   }

   public int getKeyLoginId() {
      return m_keyLoginId;
   }

   public int getLoginId() {
      return m_loginId;
   }

   public String getLoginName() {
      return m_loginName;
   }

   public String getMobileNo() {
      return m_mobileNo;
   }

   public String getPassword() {
      return m_password;
   }

   public String getRealName() {
      return m_realName;
   }

   public void setAdminId(int adminId) {
      setFieldUsed(ADMIN_ID, true);
      m_adminId = adminId;
   }

   public void setEmail(String email) {
      setFieldUsed(EMAIL, true);
      m_email = email;
   }

   public void setKeyLoginId(int keyLoginId) {
      setFieldUsed(KEY_LOGIN_ID, true);
      m_keyLoginId = keyLoginId;
   }

   public void setLoginId(int loginId) {
      setFieldUsed(LOGIN_ID, true);
      m_loginId = loginId;
   }

   public void setLoginName(String loginName) {
      setFieldUsed(LOGIN_NAME, true);
      m_loginName = loginName;
   }

   public void setMobileNo(String mobileNo) {
      setFieldUsed(MOBILE_NO, true);
      m_mobileNo = mobileNo;
   }

   public void setPassword(String password) {
      setFieldUsed(PASSWORD, true);
      m_password = password;
   }

   public void setRealName(String realName) {
      setFieldUsed(REAL_NAME, true);
      m_realName = realName;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder(1024);

      sb.append("DpAdminLogin[");
      sb.append("admin-id: ").append(m_adminId);
      sb.append(", email: ").append(m_email);
      sb.append(", key-login-id: ").append(m_keyLoginId);
      sb.append(", login-id: ").append(m_loginId);
      sb.append(", login-name: ").append(m_loginName);
      sb.append(", mobile-no: ").append(m_mobileNo);
      sb.append(", password: ").append(m_password);
      sb.append(", real-name: ").append(m_realName);
      sb.append("]");
      return sb.toString();
   }

}
