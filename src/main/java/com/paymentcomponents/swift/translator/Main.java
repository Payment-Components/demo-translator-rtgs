package com.paymentcomponents.swift.translator;

public class Main {

    public static void main(String[] args) {
        TranslateMtToMx.translateMt202ToPacs009_Auto();
        TranslateMtToMx.translateMt202ToPacs009_ExplicitText();
        TranslateMtToMx.translateMt202ToPacs009_ExplicitObject();

        TranslateMxToMt.translatePacs009toMt202_Auto();
        TranslateMxToMt.translatePacs009toMt202_ExplicitText();
        TranslateMxToMt.translatePacs009toMt202_ExplicitObject();
    }

}
