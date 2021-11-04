package com.paymentcomponents.swift.translator;

import gr.datamation.mx.message.pacs.rtgs.FinancialInstitutionCreditTransfer08Rtgs;
import gr.datamation.swift.common.SwiftMessage;
import gr.datamation.swift.translator.common.exceptions.InvalidMtMessageException;
import gr.datamation.swift.translator.common.exceptions.InvalidMxMessageException;
import gr.datamation.swift.translator.common.utils.MtMessageValidationUtils;
import gr.datamation.swift.translator.rtgs.RtgsTranslator;
import gr.datamation.swift.translator.rtgs.interfaces.MtToMxTranslator;
import gr.datamation.swift.translator.rtgs.translators.mt.Mt202ToPacs009;

public class TranslateMtToMx {

    public static void main(String... args) {
        translateMt202ToPacs009_Auto();
        translateMt202ToPacs009_ExplicitText();
        translateMt202ToPacs009_ExplicitObject();
    }

    public static void translateMt202ToPacs009_Auto() {
        try {
            // You have the option to provide the MT message in text format and get back the RTGS message in text format.
            // Translator auto detects the translation mapping.
            // In order to handle MT and RTGS messages, advice README.md
            String mxMessage = RtgsTranslator.translateMtToMx(validMtMessage);
            System.out.println("Translated Message is: \n" + mxMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("The following errors occurred");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (InvalidMtMessageException e) {
            System.out.println("The following errors occurred");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (Exception ex) {
            System.out.println("Unexpected error occurred");
            System.err.println(ex.getMessage());
        }
    }

    public static void translateMt202ToPacs009_ExplicitText() {
        try {
            // If you do not want to use the auto-translation functionality, you have the option to provide the MT message
            // in text format and get back the RTGS message in text format. In this case you need to know the exact translation mapping.
            // In order to handle MT and RTGS messages, advice README.md
            MtToMxTranslator<?> mtToMxTranslator = new Mt202ToPacs009();
            String mxMessage = mtToMxTranslator.translate(validMtMessage);
            System.out.println("Translated Message is: \n" + mxMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("The following errors occurred");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (InvalidMtMessageException e) {
            System.out.println("The following errors occurred");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (Exception ex) {
            System.out.println("Unexpected error occurred");
            System.err.println(ex.getMessage());
        }
    }

    public static void translateMt202ToPacs009_ExplicitObject() {
        try {
            // If you do not want to use the auto-translation functionality, you have the option to provide the MT message
            // in Object format and get back the RTGS message in Object format. In this case you need to know the exact translation mapping.
            // In order to handle MT and RTGS messages, advice README.md
            SwiftMessage swiftMessage = MtMessageValidationUtils.parseMtMessage(validMtMessage);
            MtToMxTranslator<FinancialInstitutionCreditTransfer08Rtgs> mtToMxTranslator = new Mt202ToPacs009();
            FinancialInstitutionCreditTransfer08Rtgs mxMessage = mtToMxTranslator.translate(swiftMessage);
            System.out.println("Translated Message is: \n" + mxMessage.convertToXML());
        } catch (InvalidMxMessageException e) {
            System.out.println("The following errors occurred");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (InvalidMtMessageException e) {
            System.out.println("The following errors occurred");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (Exception ex) {
            System.out.println("Unexpected error occurred");
            System.err.println(ex.getMessage());
        }
    }

    private static final String validMtMessage = "{1:F01BBBBUS33XXXX1111111111}{2:O2020726210316CCCCJPJTXXXX11111111112103160726N}{3:{121:00000000-0000-4000-8000-000000000000}}{4:\n" +
            ":20:BBBB/120928-FICT\n" +
            ":21:ABC/4562/2012-09\n" +
            ":13C:/RNCTIME/0400+1300\n" +
            ":13C:/FROTIME/0112+1300\n" +
            ":13C:/TILTIME/1212+1300\n" +
            ":32A:120929JPY10000000,\n" +
            ":52A:/DBTRACCT\n" +
            "BBBBUS33XXX\n" +
            ":56A:/INTERAGTACCT\n" +
            "INTERBICXXX\n" +
            ":57A:/CDTRAGTACCT\n" +
            "AAAAJPJTXXX\n" +
            ":58A:/CDTRACCT\n" +
            "AAAAGB2LXXX\n" +
            ":72:/INS/TESTBICDXXX\n" +
            "-}";

}
