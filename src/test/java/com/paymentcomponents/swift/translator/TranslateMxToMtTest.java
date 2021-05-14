package com.paymentcomponents.swift.translator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TranslateMxToMtTest {

    @Test
    public void translateMxToMtTest() {
        try {
            TranslateMxToMt.translatePacs009toMt200_Auto();
            TranslateMxToMt.translatePacs009toMt200_ExplicitText();
            TranslateMxToMt.translatePacs009toMt200_ExplicitObject();
        } catch (Exception ex) {
            Assertions.fail("Failed to translate message", ex);
        }
    }

}
