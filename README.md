# SWIFT Message Translator RTGS Demo

The project is here to demonstrate how our [SDK](https://www.paymentcomponents.com/messaging-libraries/) for RTGS
Message Translator works. For our demonstration we are going to use the demo SDK which can translate SWIFT MT to CBPR+ messages. 

This documentation describes how to incorporate the RTGS Translator Library into your project. The SDK is written in Java.  
By following this guide you will be able to translate SWIFT MT(ISO 15022) messages to RTGS messages 
and vice versa according to RTGS guidelines.

It's a simple maven project, you can download it and run it, with Java 1.8 or above.

## SDK setup
Incorporate the SDK [jar](https://nexus.paymentcomponents.com/repository/public/gr/datamation/translator-rtgs/3.0.0/translator-rtgs-3.0.0-demo.jar)
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
    <version>3.0.0</version>
    <classifier>demo</classifier>
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
implementation 'gr.datamation:translator-rtgs:3.0.0:demo@jar'
```

## Supported MT > MX Translations

| MT message    | MX message      | Translator Class     | Available in Demo |
| ----------    | ----------      | ----------------     | :---------------: |
| MT103         | pacs.008.001.08 | Mt103ToPacs008       |                   |
| MT103(Return) | pacs.004.001.09 | Mt103ToPacs004       |                   |
| MT202         | pacs.009.001.08 | Mt202ToPacs009       | &check;           |
| MT202COV      | pacs.009.001.08 | Mt202ToPacs009       |                   |
| MT202(Return) | pacs.004.001.09 | Mt202ToPacs004       |                   |

## Supported MX > MT Translations

| MT message          | MX message     | Translator Class     | Multiple MT support | Available in Demo |
| ----------          | ----------     | ----------------     | :-----------------: | :---------------: |
| camt.053.001.08     | MT940          | Camt053ToMt940       | &check;             |                   |
| camt.054.001.08     | MT900          | Camt054ToMt900       | &cross;             |                   |
| camt.054.001.08     | MT910          | Camt054ToMt910       | &cross;             |                   |
| pacs.004.001.09     | MT103 (Return) | Pacs004ToMt103       | &cross;             |                   |
| pacs.004.001.09     | MT202 (Return) | Pacs004ToMt202       | &cross;             |                   |
| pacs.008.001.08     | MT103          | Pacs008ToMt103       | &cross;             |                   |
| pacs.009.001.08     | MT202          | Pacs009ToMt202       | &cross;             | &check;           |
| pacs.009.001.08     | MT202COV       | Pacs009ToMt202COV    | &cross;             |                   |

## Instructions

### Auto Translation

You have the option to provide the MT or RTGS message and the library auto translates it to its equivalent.  
Both input and output are in text format.  
You need to call the following static methods of `RtgsTranslator` class.  
In case of no error, you will get the formatted translated message.
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

`MtToMxTranslator` interface provides the following methods for both text and object format translations.
```java
String translate(String swiftMtMessageText) throws Exception;
CoreMessage translate(SwiftMessage swiftMtMessage) throws Exception;
```

`MxToMtTranslator` interface provides the following methods.
```java
String translate(String mxMessageText)throws Exception;
SwiftMessage translate(CoreMessage coreMessage)throws Exception;
SwiftMessage[]translateMultipleMt(CoreMessage coreMessage)throws Exception;
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

When we translate a message, both input and output messages are validated. For example, in a MT→MX translation, the
first step is to validate the MT message and we proceed to translation only if the message is valid.  
This is the reason why both methods throw `InvalidMtMessageException` and `InvalidMxMessageException`.  
Both Exceptions contain a `validationErrorList` attribute which contains a description of the error occurred.

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

### Code Samples

In this project you can see code for all the basic manipulation of an MX message, like:
- [Translate MT to MX](src/main/java/com/paymentcomponents/swift/translator/TranslateMtToMx.java)
- [Translate MX to MT](src/main/java/com/paymentcomponents/swift/translator/TranslateMxToMt.java)

### Other resources

- More information about our implementation of **SWIFT MT library** can be found in our demo on [PaymentComponents GitHub](https://github.com/Payment-Components/demo-swift-mt).
- More information about our implementation of **ISO20022 library** can be found in our demo on [PaymentComponents GitHub](https://github.com/Payment-Components/demo-iso20022).
- More information about our implementation of **RTGS library** can be found in our demo on [PaymentComponents GitHub](https://github.com/Payment-Components/demo-iso20022#target2-rtgs-messages).
