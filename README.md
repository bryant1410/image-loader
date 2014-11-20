# image-loader [![](http://ci.novoda.com/buildStatus/icon?job=image-loader)](http://ci.novoda.com/job/image-loader/lastBuild/console) [![](https://raw.githubusercontent.com/novoda/novoda/master/assets/btn_apache_lisence.png)](LICENSE.txt)

Image loader for Android

## Description

image-loader is a simple library that makes it easy to download, display and cache remote images in Android apps. Image download happens off the UI thread and the images are cached with a two-level in-memory/SD card cache.


## Adding to your project

To start using this library, add these lines to the `build.gradle` of your project:

```groovy
repositories {
    maven {
        credentials {
            username 'BINTRAY_USERNAME'
            password 'BINTRAY_KEY'
        }
        url 'http://dl.bintray.com/novoda/maven-private'
    }
}

depedencies {
  compile 'com.novoda:image-loader-core:2.0-BETA'
}
```

## Simple usage

Create the ImageManager. *This can be built with a variety of parameters, check out the example project for more details*

```java
LoaderSettings settings = new SettingsBuilder().build(this);
ImageManager imageManager = new ImageManager(this, settings);
```

Create a tag to wrap the url of a image and set it on the ImageView which will hold the image

```java
ImageTag tag = imageTagFactory.build("www.myimage.com/foo.png");
imageView.setTag(tag);
```

Use the `ImageManager` to get an instance of the `ImageLoader` and provide an `ImageView` with a tag set
```java
imageManager.getLoader().load(imageView);
```

## Links

Here are a list of useful links:

 * We always welcome people to contribute new features or bug fixes, [here is how](https://github.com/novoda/novoda/blob/master/CONTRIBUTING.md)
 * If you have a problem check the [Issues Page](https://github.com/novoda/image-loader/issues) first to see if we are working on it
 * For further usage or to delve more deeply checkout the [Project Wiki](https://github.com/novoda/image-loader/wiki)
 * Looking for community help, browse the already asked [Stack Overflow Questions](http://stackoverflow.com/questions/tagged/support-image-loader) or use the tag: `support-image-loader` when posting a new question
