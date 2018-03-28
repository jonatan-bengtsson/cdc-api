package com.tingcore.cdc.charging.repository;

import com.tingcore.payments.cpo.model.ApiCharge;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import com.tingcore.cdc.charging.model.ChargingSession;
import com.tingcore.cdc.charging.model.ChargingSessionStatus;
import com.tingcore.cdc.charging.repository.ChargingSessionRepository;
import com.tingcore.payments.cpo.model.ApiCharge;

public class ChargingSessionRepositoryTests {

  @Test
  public void test() {

    Instant now = Instant.now();
    Instant later = Instant.now().plus(10,ChronoUnit.SECONDS);

    ApiCharge apiCharge = new ApiCharge();
    apiCharge.setId(123L);
    apiCharge.setUser(456L);
    apiCharge.setStartTime(now.toEpochMilli());
    apiCharge.setStopTime(later.toEpochMilli());
    //apiCharge.setState(ChargingSessionStatus.STARTED);

//    ChargingSession session = ChargingSessionRepository.apiSessionToModel(apiCharge);
//
//    assertEquals((Long)123L, session.id.value);
//    assertEquals((Long)456L, session.customerKeyId.value);
//    assertEquals(now, session.startTime);
//    assertEquals(later, session.endTime);
//    assertEquals(ChargingSessionStatus.STARTED, session.status);
  }
}
