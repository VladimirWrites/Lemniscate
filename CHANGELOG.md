Change Log
==========
Version 1.4.2 *(2018-01-16)*
----------------------------

* Fixed bug where View would not show inside ScrollView.  [#5](https://github.com/vlad1m1r990/Lemniscate/issues/5)

Version 1.4.1 *(2018-01-06)*
----------------------------

* Fixed bug where SizeMultiplier property was not working when set from `xml`.  [#4](https://github.com/vlad1m1r990/Lemniscate/issues/4)

Version 1.4.0 *(2017-11-09)*
----------------------------

* Project rewritten in Kotlin.  
* Organization of base classes improved
* Fixed bugs in Sample app

Version 1.3.0 *(2017-11-03)*
----------------------------

* `lineLength` and `lineLengthChangeable` do not exist anymore. If `maxLineLength` and `minLineLength` are the same then `lineLengthChangeable==false`, otherwise line length will be changeable
`getGraphX` and `getGraphY` now return `float` and not `double`
* `mLemniscateParamX` and `mLemniscateParamY` are not used anymore and are replaced by `viewSize.getSize()`, where `mLemniscateParamX == mLemniscateParamY == viewSize.getSize()/2`
* `minSdkVersion` moved from 11 to 14

Version 1.2.0 *(2017-02-16)*
----------------------------

 * New curves added: `BernoullisBowProgressView`, `BernoullisSharpProgressView`, `XProgressView`, `RoundScribbleProgressView`, `ScribbleProgressView`
 * `colorAccent` is now being used as default line color

Version 1.1.1 *(2017-01-26)*
----------------------------

 * Optimization of function that is doing sampling of curve

Version 1.1.0 *(2017-01-26)*
----------------------------

 * Abstract functions `getGraphX()` and `getGraphY()` now receive value of `getT()`

Version 1.0.2 *(2017-01-24)*
----------------------------

 * Fix: Added `onSaveState` for Roulette curves
 * Fix: Precision is being saved `onSaveState` for all curves


Version 1.0.1 *(2017-01-23)*
----------------------------

 * Fix: Crash on `setColor(int color)` in `BaseCurveProgressBar`, when called from constructor.


Version 1.0.0 *(2017-01-23)*
----------------------------

Initial release.


