package com.tingcore.cdc.payments.api;

import io.swagger.annotations.ApiModel;

import java.util.Map;

@ApiModel
public class ApiCreateAccountRequest {

  private Account account;

  public Account getAccount() {
    return account;
  }

  public void setAccount(final Account account) {
    this.account = account;
  }

  public static class Account {
    private com.tingcore.payments.emp.model.Account.PaymentMethodEnum paymentMethod;
    private String accountOwnerId;

    private Map<String, Object> data;

    public com.tingcore.payments.emp.model.Account.PaymentMethodEnum getPaymentMethod() {
      return paymentMethod;
    }

    public void setPaymentMethod(final com.tingcore.payments.emp.model.Account.PaymentMethodEnum paymentMethod) {
      this.paymentMethod = paymentMethod;
    }

    public String getAccountOwnerId() { return accountOwnerId; }

    public void setAccountOwnerId(final String accountOwnerId) { this.accountOwnerId = accountOwnerId; }

    public Map<String, Object> getData() {
      return data;
    }

    public void setData(final Map<String, Object> data) {
      this.data = data;
    }
  }
}
