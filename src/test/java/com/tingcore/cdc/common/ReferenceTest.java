package com.tingcore.cdc.common;

import com.tingcore.cdc.common.Reference.ReferenceElement;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ReferenceTest {

  @Test
  public void buildReferenceFromAbsoluteString() {
    final String absoluteReference = "//asdad.com/v1/asds/dsf/asds/dsf";
    final ReferenceElement referenceElement = Reference.fromString(absoluteReference);

    assertThat(referenceElement.asString()).isEqualTo(absoluteReference);
  }

  @Test
  public void buildReferenceFromRelativeString() {
    final String relativeReference = "v1/asds/dsf/asds/dsf";
    final ReferenceElement referenceElement = Reference.fromString(relativeReference);

    assertThat(referenceElement.asString()).isEqualTo(relativeReference);
  }

  @Test
  public void buildReferenceFromRelativeStringStartingWithSeparator() {
    final String relativeReference = "v1/asds/dsf/asds/dsf";
    final ReferenceElement referenceElement = Reference.fromString("/" + relativeReference);

    assertThat(referenceElement.asString()).isEqualTo(relativeReference);
  }

}
