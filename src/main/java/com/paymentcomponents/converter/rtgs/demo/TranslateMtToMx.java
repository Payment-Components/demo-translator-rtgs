package com.paymentcomponents.converter.rtgs.demo;

import gr.datamation.converter.common.exceptions.InvalidMtMessageException;
import gr.datamation.converter.common.exceptions.InvalidMxMessageException;
import gr.datamation.converter.common.exceptions.StopTranslationException;
import gr.datamation.converter.common.exceptions.TranslationUnhandledException;
import gr.datamation.converter.common.utils.MtMessageValidationUtils;
import gr.datamation.converter.common.utils.MxMessageValidationUtils;
import gr.datamation.converter.rtgs.RtgsTranslator;
import gr.datamation.converter.rtgs.converters.mt.Mt202ToPacs009;
import gr.datamation.converter.rtgs.interfaces.MtToTarget2Translator;
import gr.datamation.converter.rtgs.utils.RtgsMessageValidationUtils;
import gr.datamation.iso20022.target2.pacs.FinancialInstitutionCreditTransfer08Rtgs;
import gr.datamation.mt.common.SwiftMessage;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;

public class TranslateMtToMx {

    public static void main(String... args) {
        translateMt202ToPacs009_Auto();
        translateMt202ToPacs009_ExplicitText();
        translateMt202ToPacs009_ExplicitObject();
    }

    public static void translateMt202ToPacs009_Auto() {
        // You have the option to provide the MT message in text format and get back the RTGS message in text format.
        // Translator auto detects the translation mapping.
        // In order to handle MT and RTGS messages, advice README.md
        String mxMessage = null;
        try {
            mxMessage = RtgsTranslator.translateMtToMx(validMtMessage);
        } catch (InvalidMtMessageException e) {
            System.out.println("MT message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
            return;
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
            return;
        }

        //Validate the Translated message
        try {
            RtgsMessageValidationUtils.autoParseAndValidateRtgsMessage(mxMessage);
            System.out.println("Translated Message is: \n" + mxMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("Target2 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        }
    }

    public static void translateMt202ToPacs009_ExplicitText() {
        // If you do not want to use the auto-translation functionality, you have the option to provide the MT message
        // in text format and get back the RTGS message in text format. In this case you need to know the exact translation mapping.
        // In order to handle MT and RTGS messages, advice README.md
        String mxMessage = null;
        try {
            MtToTarget2Translator<?> mtToMxTranslator = new Mt202ToPacs009();
            mxMessage = mtToMxTranslator.translate(validMtMessage);
        } catch (InvalidMtMessageException e) {
            System.out.println("MT message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
            return;
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
            return;
        }

        //Validate the Translated message
        try {
            RtgsMessageValidationUtils.autoParseAndValidateRtgsMessage(mxMessage);
            System.out.println("Translated Message is: \n" + mxMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("Target2 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        }
    }

    public static void translateMt202ToPacs009_ExplicitObject() {
        // If you do not want to use the auto-translation functionality, you have the option to provide the MT message
        // in Object format and get back the RTGS message in Object format. In this case you need to know the exact translation mapping.
        // In order to handle MT and RTGS messages, advice README.md
        FinancialInstitutionCreditTransfer08Rtgs mxMessage = null;
        try {
            SwiftMessage swiftMessage = MtMessageValidationUtils.parseMtMessage(validMtMessage);
            MtToTarget2Translator<FinancialInstitutionCreditTransfer08Rtgs> mtToMxTranslator = new Mt202ToPacs009();
            mxMessage = mtToMxTranslator.translate(swiftMessage);
        } catch (InvalidMtMessageException e) {
            System.out.println("MT message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
            return;
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
            return;
        }

        //Validate the Translated message
        try {
            MxMessageValidationUtils.validateMxMessage(mxMessage);
            System.out.println("Translated Message is: \n" + mxMessage.convertToXML());
        } catch (InvalidMxMessageException e) {
            System.out.println("Target2 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (JAXBException | UnsupportedEncodingException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
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
