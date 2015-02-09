# CS408
CS 408 - Software Testing group project

### Members and Works
  - Fullscreen ( Jack )
  - Gamemode ( Yifei )
  - Login ( Jason )
  - Menu ( Aaron )
  - Score Logic ( Mingsheng )
  - Weapons ( Zhihao )

### Version
1.0

### Known bugs
  - crashes
```sh
  Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.awt.image.DataBufferInt.<init>(DataBufferInt.java:75)
	at java.awt.image.Raster.createPackedRaster(Raster.java:470)
	at java.awt.image.DirectColorModel.createCompatibleWritableRaster(DirectColorModel.java:1032)
	at sun.java2d.opengl.CGLGraphicsConfig.createAcceleratedImage(CGLGraphicsConfig.java:303)
	at sun.lwawt.LWComponentPeer.createImage(LWComponentPeer.java:984)
	at java.awt.Component.createImage(Component.java:3555)
	at java.awt.Component.createImage(Component.java:3552)
	at java.awt.Component.createImage(Component.java:3552)
	at java.awt.Component.createImage(Component.java:3552)
	at GUI.gameRender(GUI.java:249)
	at GUI.gamePlay(GUI.java:373)
	at GUI.main(GUI.java:361)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:601)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:134)
  Exception in thread "main" java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:819)
	at java.util.ArrayList$Itr.next(ArrayList.java:791)
	at GUI.gameRender(GUI.java:293)
	at GUI.gamePlay(GUI.java:352)
	at GUI.main(GUI.java:339)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:601)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:134)
```
  - Unconditional speed change
  - Lag when fullscreen
