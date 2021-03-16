package com.paymentcomponents.swift.translator;

import gr.datamation.swift.translator.Converter;
import gr.datamation.swift.translator.exceptions.InvalidMtMessageException;
import gr.datamation.swift.translator.exceptions.InvalidMxMessageException;

public class ConvertMTtoMX {

    public static void main(String... args) {
        convertMT200toMXpacs009();
    }

    public static void convertMT200toMXpacs009() {
        try {
            // In order to translate an MT Message to MX Message and vice versa, you only need to call the library
            // with the message that you want to translate and it automatically translates the message to the relevant format.
            // The only restriction is that the message should be included in the supported messages (advice README).
            //
            //The input and the output are simple texts.
            String mxMessage = Converter.convertMtToMx(validMtMessage);
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