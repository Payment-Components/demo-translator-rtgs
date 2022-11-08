package com.paymentcomponents.converter.rtgs.demo;

import gr.datamation.converter.common.exceptions.InvalidMxMessageException;
import gr.datamation.converter.common.exceptions.StopTranslationException;
import gr.datamation.converter.common.exceptions.TranslationUnhandledException;
import gr.datamation.converter.common.utils.MxMessageValidationUtils;
import gr.datamation.converter.rtgs.RtgsTranslator;
import gr.datamation.converter.rtgs.converters.mx.Pacs009ToMt202;
import gr.datamation.converter.rtgs.interfaces.Target2ToMtTranslator;
import gr.datamation.iso20022.target2.pacs.FinancialInstitutionCreditTransfer08Rtgs;
import gr.datamation.mt.common.SwiftMessage;
import gr.datamation.mt.processor.SwiftMsgProcessor;

public class TranslateMxToMt {

    public static void main(String... args) {
        translatePacs009toMt202_Auto();
        translatePacs009toMt202_ExplicitText();
        translatePacs009toMt202_ExplicitObject();
    }

    public static void translatePacs009toMt202_Auto() {
        // You have the option to provide the RTGS message in text format and get back the MT message in text format.
        // Translator auto detects the translation mapping.
        // In order to handle MT and RTGS messages, advice README.md
        try {
            String mtMessage = RtgsTranslator.translateMxToMt(validMXMessage);
            System.out.println("Translated Message is: \n" + mtMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("Target2 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }

    public static void translatePacs009toMt202_ExplicitText() {
        // If you do not want to use the auto-translation functionality, you have the option to provide the RTGS message
        // in text format and get back the MT message in text format. In this case you need to know the exact translation mapping.
        // In order to handle MT and RTGS messages, advice README.md
        try {
            Target2ToMtTranslator<?> mxToMtTranslator = new Pacs009ToMt202();
            String mtMessage = mxToMtTranslator.translate(validMXMessage);
            System.out.println("Translated Message is: \n" + mtMessage);
        } catch (InvalidMxMessageException e) {
            System.out.println("Target2 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }

    public static void translatePacs009toMt202_ExplicitObject() {
        // If you do not want to use the auto-translation functionality, you have the option to provide the RTGS message
        // in Object format and get back the MT message in Object format. In this case you need to know the exact translation mapping.
        // In order to handle MT and RTGS messages, advice README.md
        try {
            FinancialInstitutionCreditTransfer08Rtgs coreMessage = MxMessageValidationUtils.parseAndValidateMxMessage(validMXMessage, FinancialInstitutionCreditTransfer08Rtgs.class);
            //or RtgsMessageValidationUtils.autoParseAndValidateRtgsMessage(validMXMessage);
            Target2ToMtTranslator<FinancialInstitutionCreditTransfer08Rtgs> mxToMtTranslator = new Pacs009ToMt202();
            SwiftMessage mtMessage = mxToMtTranslator.translate(coreMessage);
            System.out.println("Translated Message is: \n" + new SwiftMsgProcessor().BuildMsgStringFromObject(mtMessage));
        } catch (InvalidMxMessageException e) {
            System.out.println("Target2 message is invalid");
            e.getValidationErrorList().forEach(System.out::println);
        } catch (StopTranslationException e) {
            System.out.println("Translation errors occurred");
            e.getTranslationErrorList().forEach(System.out::println);
        } catch (TranslationUnhandledException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("MT message is invalid");
            e.printStackTrace();
        }
    }

    private static final String validMXMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>BBBB/120928-FICT/JPY/430</MsgId>\n" +
            "            <CreDtTm>2012-09-28T16:00:00+13:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                <ClrSys>\n" +
            "                    <Cd>TGT</Cd>\n" +
            "                </ClrSys>\n" +
            "            </SttlmInf>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>BBBB/120928-FICT</InstrId>\n" +
            "                <EndToEndId>ABC/4562/2012-09-08</EndToEndId>\n" +
            "                <UETR>00000000-0000-4000-8000-000000000000</UETR>\n" +
            "            </PmtId>\n" +
            "            <IntrBkSttlmAmt Ccy=\"JPY\">10000000</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2012-09-29</IntrBkSttlmDt>\n" +
            "            <SttlmTmIndctn>\n" +
            "                <CdtDtTm>2012-09-28T16:00:00+13:00</CdtDtTm>\n" +
            "            </SttlmTmIndctn>\n" +
            "            <SttlmTmReq>\n" +
            "                <TillTm>12:12:12+13:00</TillTm>\n" +
            "                <FrTm>13:12:12+13:00</FrTm>\n" +
            "            </SttlmTmReq>\n" +
            "            <PrvsInstgAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICDXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </PrvsInstgAgt1>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33XXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCJPJTXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "            <IntrmyAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>INTERBICXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </IntrmyAgt1>\n" +
            "            <IntrmyAgt1Acct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>INTERAGTACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </IntrmyAgt1Acct>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33XXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <DbtrAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>DBTRACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </DbtrAcct>\n" +
            "            <CdtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAAJPJTXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </CdtrAgt>\n" +
            "            <CdtrAgtAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>CDTRAGTACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </CdtrAgtAcct>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAAGB2LXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "            <CdtrAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>CDTRACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </CdtrAcct>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>";

}
