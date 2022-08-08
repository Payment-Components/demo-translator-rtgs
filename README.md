# Message Translator Target2 (RTGS) Demo

The project is here to demonstrate how our [SDK](https://www.paymentcomponents.com/messaging-libraries/) for Target2 (RTGS)
Message Translator works. For our demonstration we are going to use the demo SDK which can translate MT to CBPR+ messages. 

This documentation describes how to incorporate the Target2 (RTGS) Translator Library into your project. The SDK is written in Java.  
By following this guide you will be able to translate MT(ISO 15022) messages to Target2 (RTGS) messages 
and vice versa according to Target2 (RTGS) guidelines.

It's a simple maven project, you can download it and run it, with Java 1.8 or above.

## SDK setup
Incorporate the SDK [jar](https://nexus.paymentcomponents.com/repository/public/gr/datamation/translator-rtgs/3.37.0/translator-rtgs-3.37.0-demo.jar)
into your project by the regular IDE means.  
This process will vary depending upon your specific IDE and you should consult your documentation on how to deploy a bean.  
For example in Intellij all that needs to be done is to import the jar files into a project. Alternatively, you can import it as a Maven or Gradle dependency.

### Maven

Define repository in the repositories section
```xml
<repository>
    <id>paymentcomponents</id>
    <url>https://nexus.paymentcomponents.com/repository/public</url>
</repository>
```

Import the SDK
```xml
<dependency>
    <groupId>gr.datamation</groupId>
    <artifactId>translator-rtgs</artifactId>
    <version>3.37.0</version>
    <classifier>demo</classifier>
</dependency>
```
Import additional dependencies if not included in your project
```xml
<dependency>
    <groupId>org.codehaus.groovy</groupId>
    <artifactId>groovy-all</artifactId>
    <version>2.5.11</version>
    <scope>compile</scope>
    <type>pom</type>
</dependency>

<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.1</version>
</dependency>

<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>2.3.1</version>
</dependency>
```

### Gradle 

Define repository in the repositories section
```groovy
repositories {
    maven {
        url "https://nexus.paymentcomponents.com/repository/public"
    }
}
```

Import the SDK
```groovy
implementation 'gr.datamation:translator-rtgs:3.37.0:demo@jar'
```

Import additional dependencies if not included in your project
```groovy
implementation group: 'org.codehaus.groovy', name: 'groovy-all', version: '2.5.11', ext: 'pom'
implementation group: 'javax.xml.bind', name: 'jaxb-api', version: '2.3.1'
implementation group: 'org.glassfish.jaxb', name: 'jaxb-runtime', version: '2.3.1'
```

## Supported MT > MX Translations

| MT message    | MX message      | Translator Class | Multiple MTX support | Available in Demo |
|---------------|-----------------|------------------|:--------------------:|:-----------------:|
| MT103         | pacs.008.001.08 | Mt103ToPacs008   |       &cross;        |                   |
| MT103+        | pacs.008.001.08 | Mt103ToPacs008   |       &cross;        |                   |
| MT103(Return) | pacs.004.001.09 | Mt103ToPacs004   |       &cross;        |                   |
| MT202         | pacs.009.001.08 | Mt202ToPacs009   |       &cross;        |      &check;      |
| MT202COV      | pacs.009.001.08 | Mt202ToPacs009   |       &cross;        |                   |
| MT202(Return) | pacs.004.001.09 | Mt202ToPacs004   |       &cross;        |                   |
| MT204         | pacs.010.001.03 | Mt204ToPacs010   |       &check;        |                   |

_* Multiple MX are splitted in text by an empty line_

## Supported MX > MT Translations

| MT message      | MX message     | Translator Class  | Multiple MT support | Available in Demo |
|-----------------|----------------|-------------------|:-------------------:|:-----------------:|
| camt.053.001.08 | MT940          | Camt053ToMt940    |       &check;       |                   |
| camt.054.001.08 | MT900          | Camt054ToMt900    |       &cross;       |                   |
| camt.054.001.08 | MT910          | Camt054ToMt910    |       &cross;       |                   |
| pacs.004.001.09 | MT103 (Return) | Pacs004ToMt103    |       &cross;       |                   |
| pacs.004.001.09 | MT202 (Return) | Pacs004ToMt202    |       &cross;       |                   |
| pacs.008.001.08 | MT103          | Pacs008ToMt103    |       &cross;       |                   |
| pacs.009.001.08 | MT202          | Pacs009ToMt202    |       &cross;       |      &check;      |
| pacs.009.001.08 | MT202COV       | Pacs009ToMt202COV |       &cross;       |                   |
| pacs.010.001.03 | MT204          | Pacs010ToMt204    |       &cross;       |                   |
| pacs.002.001.10 | MT012          | Pacs002ToMt012    |       &cross;       |                   |
| pacs.002.001.10 | MT019          | Pacs002ToMt019    |       &cross;       |                   |

## Instructions

### Auto Translation

You have the option to provide the MT or Target2 (RTGS) message and the library auto translates it to its equivalent.  
Both input and output are in text format.  
You need to call the following static methods of `RtgsTranslator` class.  
In case of no error of the input message, you will get the formatted translated message.  
When MT message is used as input it is not validated.  
Translated message is not validated.  
```java
public static String translateMtToMx(String mtMessage) throws InvalidMxMessageException, InvalidMtMessageException
```
```java
public static String translateMxToMt(String mxMessage) throws InvalidMxMessageException, InvalidMxMessageException
```

### Explicit Translation

If you do not want to use the auto-translation functionality, you can call directly the Translator you want.  
In this case you need to know the exact translation mapping.  
Translator classes implement the `MtToMxTranslator` or `MxToMtTranslator` interface.  
The `translate(Object)`, does not validate the message.  
The `translate(String)`, validates the message only in case of MX as input.  
Translated message is not validated.  

`MtToMxTranslator` interface provides the following methods for both text and object format translations.
```java
String translate(String swiftMtMessageText) throws Exception;
T translate(SwiftMessage swiftMtMessage) throws Exception;
```

`MxToMtTranslator` interface provides the following methods.
```java
String translate(String mxMessageText) throws Exception;
SwiftMessage translate(T coreMessage) throws Exception;
SwiftMessage[] translateMultipleMt(T coreMessage) throws Exception;
```

The method `translateMultipleMt` translates an RTGS message to multiple MT messages.  
You can see in [table above](#supported-mx--mt-translations) which translations support this.  
In case that a translation uses this logic, the translation in text format will return the MT messages splitted with `$`.  
For example:
```
:56A:INTERBIC
-}${1:F01TESTBICAXXXX1111111111}
```

### Error Handling

When we translate a message, input message is validated(MT is excluded). For example, in a MX→MT translation, the
first step is to validate the MX message and we proceed to translation only if the message is valid.  
This is the reason why it throws `InvalidMxMessageException`.  
Exception contains a `validationErrorList` attribute which contains a description of the error occurred.  
In order to validate the translated MX message, you can use `RtgsMessageValidationUtils.validateRtgsMessage(T message)`, 
`RtgsMessageValidationUtils.autoParseAndValidateRtgsMessage(String messageText)` or any other way you prefer.

### Modify the generated message

Once you have the translated message as text, you can use our other Financial Messaging
Libraries ([Other Resources](#other-resources)) in order to create a Java Object and make any changes you want.

In order to create an RTGS Java Object use the below code. The object class `FinancialInstitutionCreditTransfer08Rtgs` may vary depending on the ISO20022 Message Type.   
_Other message types are available [here](https://github.com/Payment-Components/demo-iso20022#supported-target2-message-types)_
```java
FinancialInstitutionCreditTransfer08Rtgs financialInstitutionCreditTransfer08Rtgs = new FinancialInstitutionCreditTransfer08Rtgs();
financialInstitutionCreditTransfer08Rtgs.parseXML(xml);
```

In order to create an MT Java Object use the below code.
```java
SwiftMessage swiftMessage = new SwiftMsgProcessor().ParseMsgStringToObject(translatedMessage);
```

### Notes for Pacs002 to MT012 and MT019
#### Explicitly set values
When you choose the Explicit Translation, you can set values that cannot be retrieved from pacs.002 before calling the translation.  
For MT012:
```java
    Pacs002ToMt012 pacs002ToMt012 = new Pacs002ToMt012();
    pacs002ToMt012.setTime("1037"); //Tag175
    pacs002ToMt012.setMir("210610TESTBICAAXXX0945872653"); //Tag106
    pacs002ToMt012.setMur("AAAA"); //Tag108
    pacs002ToMt012.setSwiftAddress("TESTBICBXXXX"); //Tag102
    pacs002ToMt012.setPaymentReleaseInformationSender("103723103723GR000RTGS331956001"); //Tag114
    String mtMessage = pacs002ToMt012.translate(message);
```
For MT019:
```java
    Pacs002ToMt019 pacs002ToMt019 = new Pacs002ToMt019();
    pacs002ToMt019.setTime("1037"); //Tag175
    pacs002ToMt019.setMir("210610TESTBICAAXXX0945872653"); //Tag106
    pacs002ToMt019.setMur("AAAA"); //Tag108
    pacs002ToMt019.setSwiftAddress("TESTBICBXXXX"); //Tag102
    pacs002ToMt019.setMor("103723103723GR000RTGS331956001"); //Tag107
    String mtMessage = pacs002ToMt019.translate(message);
```

After translation is completed, we have a message with dummy values in for sender and receiver in Heade Blocks.  
In order to set the correct values we call method `RtgsMx2MtUtils.fillHeaderBlocks()`.  
If we have the BusinessApplicationHeader, we can call it like this:
```java
    //bah is an instance of BusinessApplicationHeader01Rtgs
    RtgsMx2MtUtils.fillHeaderBlocks(bah, swiftMessage, "O", "012", null, null, null);
```
We can also call it without the header:
```java
    //bah is an instance of BusinessApplicationHeader01Rtgs
    RtgsMx2MtUtils.fillHeaderBlocks(swiftMessage, "O", "012", "TESTBICZ", "TESTBICA", null, null, null, null)
```

### Code Samples

In this project you can see code for all the basic manipulation of an MX message, like:
- [Translate MT to MX](src/main/java/com/paymentcomponents/converter/rtgs/demo/TranslateMtToMx.java)
- [Translate MX to MT](src/main/java/com/paymentcomponents/converter/rtgs/demo/TranslateMxToMt.java)

### Other resources

- More information about our implementation of **MT library** can be found in our demo on [PaymentComponents GitHub](https://github.com/Payment-Components/demo-swift-mt).
- More information about our implementation of **ISO20022 library** can be found in our demo on [PaymentComponents GitHub](https://github.com/Payment-Components/demo-iso20022).
- More information about our implementation of **RTGS library** can be found in our demo on [PaymentComponents GitHub](https://github.com/Payment-Components/demo-iso20022#target2-rtgs-messages).
