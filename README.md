# Aadhar Verifier Android SDK - Secure and Easy On-Device Aadhar Verification

![Aadhar Verification](https://www.uidai.gov.in/images/langPage/Page-1.svg)

## Overview
![Latest Release](https://img.shields.io/github/v/tag/Ashish2000L/Aadhar_Verifier_Android.svg?label=Latest%20Release)
![Platform](https://img.shields.io/badge/platform-Android-brightgreen.svg)
![SDK Version](https://img.shields.io/badge/SDK-v1.0.0-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)
![Build](https://img.shields.io/badge/build-passing-brightgreen.svg)
![Android API](https://img.shields.io/badge/API-21+-orange.svg)
![Architecture](https://img.shields.io/badge/Architecture-ARM,%20x86-brightgreen.svg)
![AAR](https://img.shields.io/badge/Includes-.aar%20File-blue.svg)
![Java](https://img.shields.io/badge/Language-Java-orange.svg)

This repository demonstrates how to integrate **Aadhar Verification** directly on Android devices, without relying on any external or third-party APIs. The SDK ensures that no sensitive user data is stored or shared with third-party servers, maintaining user privacy and preventing data leakage. By using this SDK, developers can seamlessly integrate Aadhar verification into their applications, guaranteeing secure, on-device processing.

## Why Avoid Third-Party APIs for Aadhar Verification?

Using third-party APIs to handle Aadhar verification can expose sensitive data to external servers, which may lead to privacy issues, data breaches, and misuse of critical user information. Since Aadhar contains personal identification data, it's essential to ensure the highest level of security by processing the verification directly on the device. This approach:
- Keeps all sensitive information within the user’s device.
- Removes the risk of unauthorized access or data breaches on external servers.
- Provides full control over the verification process and user data.

With our SDK, you can achieve all this **without any dependency on third-party APIs**. The SDK is reusable across multiple activities or platforms, making it easy to integrate into your project.

## Features

- **Complete on-device Aadhar verification** without third-party dependencies.
- **Easy integration** into Android applications using the provided SDK.
- No sensitive data is stored or shared with external servers.
- **Reusable** across multiple activities or platforms.

## SDK Integration

### Prerequisites

- **Android Studio** 4.x or higher
- **Gradle** 6.x or higher
- Minimum **Android API 21+**

This repository includes a demo application to demonstrate how easy it is to integrate Aadhar verification using the provided SDK.

**Aadhar Verifier SDK File**: `Verifier_1_0_0_release_7eb65a217f851e91c04fea3a3d988a05.aar`  
This file contains the SDK and should be placed in your project's `libs` folder.

### Installation

1. **Download the SDK** from the [Releases Page](https://github.com/Ashish2000L/Aadhar_Verifier_Android/releases).
2. Create a `libs` folder in your project (if not already present) and place the `.aar` file inside it.
3. Load the `.aar` file into your project’s Gradle by adding the following to your `build.gradle`:

   **Kotlin DSL**:
   ```kotlin
     dependencies {
         implementation(files("libs/Verifier_1_0_0_release_7eb65a217f851e91c04fea3a3d988a05.aar"))
     }
   ```
   
   **Groovy DSL**:
   ```
    dependencies {
      implementation fileTree(dir: 'libs', include: ['*.aar'])
    }
   ```
5. **Add the Release Libraries**:  
   The `ReleaseLibs.zip` file contains necessary library files. Extract it and place them in the correct directory.

   You can manage versions and download the zip either from your own server or from the [GitHub releases page](https://github.com/Ashish2000L/Aadhar_Verifier_Android/releases).

## Demo Application Walkthrough

Below is a step-by-step guide to integrate Aadhar verification into your project:

1. **Implement the Aadhar Verification Interface**

   ```
   class YourActivity implements IAadharVerifierResponse {
      @Override
      public <T> void onSuccess(EAadharResponse resp, T data) {
        // Handle success response
      }

      @Override
      public void onError(Exception ex) {
        // Handle error response
      }
    }

   ```

2. **Create Instance of AadharVerifier Class**

   ```
     AadharVerifier aadharVerifier = AadharVerifier.getInstance(this).setCallback(this);
   ```

3. **Check and Load the Required Files**

   Ensure the necessary verification files are loaded. If the files don't exist, download or load them from storage.

   ```
     if (!new File(getFilesDir(), "source").exists()) {
      // Download or load files from storage (refer to the demo application)
    } else {
      aadharVerifier.loadLibrary().init(); // Load the pre-downloaded file
    }

   ```

4. **Load Captcha for User Verification**

   ```
     aadharVerifier.getCaptcha();
   ```

5. **Request OTP for Aadhar Verification**

   ```
     aadharVerifier.getOTP(etAadhar.getText().toString(), etCaptcha.getText().toString());

   ```

6. **Get User Information**

   ```
     aadharVerifier.getUserInfo(etOtp.getText().toString());
   ```

## Demo Application

For a full working example, please refer to the demo application in the repository. The demo shows how to handle file loading, verification flow, and interacting with the SDK in real-time.

## Project Contributors

- **Ashish** ([@Ashish2000L](https://github.com/Ashish2000L))  
  Feel free to raise issues if you encounter any challenges during integration.

## License

This SDK and demo application are licensed under the MIT License. See the LICENSE file for details.
