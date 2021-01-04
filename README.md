# RN System Brightness

Access and update the system brightness on a device Android vs IOS

## Install

[![npm version](https://img.shields.io/npm/v/rn-system-brightness?color=green)](https://www.npmjs.com/package/rn-system-brightness) [![npm download](https://img.shields.io/npm/dm/rn-system-brightness?color=blue)](https://www.npmjs.com/package/rn-system-brightness)

```shell
yarn add rn-system-brightness
```

```shell
npm i --save rn-system-brightness
```

## Setup

#### Android

Also update you manifest params and include xmlns:tools

```js
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
  package="com.baseproject"
  xmlns:tools="http://schemas.android.com/tools"> // <---add
```

Open the manifest file of your Android project and add

```js
<uses-permission
  android:name="android.permission.WRITE_SETTINGS"
  tools:ignore="ProtectedPermissions"
/>
```

#### IOS

```js
cd ios
pod install
```

#### API

```js
import Brightness from "rn-system-brightness";

// SET
await Brightness.setBrightness(0.8); // between 0 and 1

// GET
const brightness = await Brightness.getBrightness();

// GET max brightness
const maxBrightness = await Brightness.getMaxBrightness();

// GET Permission ANDROID
const per = await Brightness.hasPermission(); // boolean true or false

// REQUEST ANDROID > 6
await Brightness.requestPermission();
```
