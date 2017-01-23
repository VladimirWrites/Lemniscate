# Lemniscate Progress View

Lemniscate is a library that will help you to make your progress view nice and sleek.


Setup
-----
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

    dependencies {
            compile 'com.github.vlad1m1r990:Lemniscate:1.0.0'
    }

Usage
-----

    <com.vlad1m1r.lemniscate.roulette.BernoullisProgressView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        customParam:duration="1000"
        customParam:hasHole="false"
        customParam:lineColor="@color/colorPrimary"
        customParam:lineLength="0.6"
        customParam:lineLengthChangeable="true"
        customParam:maxLineLength="0.8"
        customParam:minLineLength="0.4"
        customParam:sizeMultiplier="1"
        customParam:strokeWidth="5dp"/>

###### Lemniscates
`BernoullisProgressView`, `GeronosProgressView`

###### Roulettes
`EpitrochoidProgressView`, `HypotrochoidProgressView`

###### Funny
`HeartProgressView`, `CannabisProgressView`



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