package com.paymentcomponents.converter.rtgs.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TranslateMxToMtTest {

    @Test
    public void translateMxToMtTest() {
        try {
            TranslateMxToMt.translatePacs009toMt202_Auto();
            TranslateMxToMt.translatePacs009toMt202_ExplicitText();
            TranslateMxToMt.translatePacs009toMt202_ExplicitObject();
        } catch (Exception ex) {
            Assertions.fail("Failed to translate message", ex);
        }
    }

}
