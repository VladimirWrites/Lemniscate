![Lemniscate header](http://i.imgur.com/i9t5vUm.png)


[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/vlad1m1r990/Lemniscate/blob/master/LICENSE)
[![](https://jitpack.io/v/vlad1m1r990/Lemniscate.svg)](https://jitpack.io/#vlad1m1r990/Lemniscate) 
[![API](https://img.shields.io/badge/API-14%2B-green.svg?style=flat)](https://android-arsenal.com/api?level-11) 
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Lemniscate-green.svg?style=flat)](https://android-arsenal.com/details/1/5142)
[![Build Status](https://travis-ci.org/vlad1m1r990/Lemniscate.svg?branch=master)](https://travis-ci.org/vlad1m1r990/Lemniscate)
[![codecov](https://codecov.io/gh/vlad1m1r990/Lemniscate/branch/master/graph/badge.svg)](https://codecov.io/gh/vlad1m1r990/Lemniscate)

-----

Lemniscate is a library that will help you to make your progress view nice and sleek.

![Lemniscate gif](http://i.imgur.com/xPRHWdv.gif)

Demo
-----

Demo application is available on Google Play.

<a href='https://play.google.com/store/apps/details?id=com.vlad1m1r.lemniscate.sample'>
    <img alt='Get it on Google Play' src='http://i.imgur.com/tka3Exw.png'/>
</a>

The application is intentionally simple, without any libraries, to be understandable to more developers.

Setup
-----

Add to your module's build.gradle:

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

and to your app build.gradle:

    dependencies {
            compile 'com.github.vlad1m1r990:Lemniscate:1.4.3'
    }

Usage
-----

Example of usage:

    <com.vlad1m1r.lemniscate.BernoullisProgressView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:duration="1000"
        app:hasHole="false"
        app:lineColor="@color/colorPrimary"
        app:maxLineLength="0.8"
        app:minLineLength="0.4"
        app:sizeMultiplier="1"
        app:strokeWidth="5dp"/>

###### Params available in all views:

* **duration** (int) - duration of one animation cycle in millisecondes
* **lineColor** (color) - color of the line
* **maxLineLength** (float) - max length of line (in percentage; 1.0 is full length, 0.5 is half of length)
* **minLineLength** (float) - min length of line (in percentage; 1.0 is full length, 0.5 is half of length)
* **sizeMultiplier** (float) - default size of view will be multiplied with that number
* **strokeWidth** (dimension) - width of line 
* **precision** (int) - number of points in curve calculated in one cycle

#### Lemniscates
* `BernoullisProgressView` - [Lemniscate of Bernoulli](https://en.wikipedia.org/wiki/Lemniscate_of_Bernoulli),
* `GeronosProgressView` - [Lemniscate of Gerono](https://en.wikipedia.org/wiki/Lemniscate_of_Gerono)
* `BernoullisBowProgressView`
* `BernoullisSharpProgressView`

###### Additional params:
* **hasHole** (boolean) - hole in a middle of Lemniscates

#### Roulettes
* `EpitrochoidProgressView` - [Epitrochoid](https://en.wikipedia.org/wiki/Epitrochoid),
* `HypotrochoidProgressView` - [Hypotrochoid](https://en.wikipedia.org/wiki/Hypotrochoid)

###### Additional params:
* **radiusFixed** (float) - radius of fixed circle
* **radiusMoving** (float) - radius of moving circle
* **distanceFromCenter** (float) -  distance from the center of the moving circle
* **numberOfCycles** (float) - for one **duration** curve will be drawn on interval [0, 2 \* mNumberOfCycles \* π]

#### Scribble
* `RoundScribbleProgressView`
* `ScribbleProgressView`

#### Funny
* `HeartProgressView` - [Heart Curve](http://mathworld.wolfram.com/HeartCurve.html),
* `CannabisProgressView` - [Cannabis Curve](http://mathworld.wolfram.com/CannabisCurve.html)

#### Other
* `XProgressView`

Contributing
-------

Want to contribute? You are welcome! 
Note that all pull request should go to [`development`](https://github.com/vlad1m1r990/Lemniscate/tree/development) branch.

Credits
-------

+ [Vladimir Jovanovic](https://github.com/vlad1m1r990)

License
-------

    Copyright 2016 Vladimir Jovanovic

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.