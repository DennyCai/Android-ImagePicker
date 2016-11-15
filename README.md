# Android-ImagePicker
[ ![Download](https://api.bintray.com/packages/dennycai/maven/android-imagepicker-light/images/download.svg) ](https://bintray.com/dennycai/maven/android-imagepicker-light/_latestVersion)
[![API](https://img.shields.io/badge/API-11%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=11)

Imitation Wechat pick images.

ScreenShot
--
![](https://null)

Download
--
#### Gradle
```groovy
compile 'com.dennycai:Android-ImagePicker-Light:0.6.0'
```
#### Eclipse
![go home](https://github.com/DennyCai/Android-ImagePicker/blob/PickImage-Light/screenshot/drunk.jpg)

Usage
--

### Choose multiple pictures
```java
    new Picker().multi(9).start(MainActivity.this);
```

### Pick one picture
```java
    new Picker().single().start(MainActivity.this);
```

Author
--
* Email: dennycai2015@gmail.com

License
--
```
Copyright 2016 DennyCai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
