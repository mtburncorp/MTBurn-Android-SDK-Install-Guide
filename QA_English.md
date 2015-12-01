# Table of Contents

* [How to make Inquiries](#howto)
* [What Type of Information Can be Acquired From the Terminal?](#info)
* [Can the SDK Log Output be Controlled? ](#log)
* [Is there a 〇〇 Function?](#function)
* [Are there any Sample Projects? ](#sample)
* [Problems with the Advertisement Display（Not Displayed, Overlapped etc.)）](#not_found_ad)
* [What Does the Version Number Mean? ](#version)
* [What Does Instream Mean in the Code?](#instream)

<a name="howto"></a>
# How to Make Inquires

### Confirm the following before making an inquiry. 

1. Consult the `Install-Guide` and [API specification](http://mtburn.github.io/MTBurn-Android-SDK-Install-Guide/javadoc/latest/)
 doc to check that no method of resolution is given there. 
2. Try running a [demo project](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/tree/master/demo)
3. Check [issues](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/issues)

### Inquiry

- Please utilize the [issues](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/issues)
- When it is difficult to inquire using `issues`, contact your representative.
 - In this case, refer to this item for methods other than using your point of inquiry

### Inquiry Format

- Copy and paste the following format and replace `---` with the actual content. 
 - Where `no log/project files are supplied`, it may take time to provide an answer; thus, please try to provide these where possible.

```
- From when: ---
- With what type of advertising: ---
- Symptoms: ---
- Reproduction conditions: ---
- Log/project files when reproducing (where they exist): ---
```

<a name="info"></a>
# What Type of Information Can be Acquired from the Terminal? 

- The following information can be acquired

| Name | API | Details |
| --- | --- | --- |
| advertising ID | `AdvertisingIdClient.Info getId()` | Is not available when the user has the opt-out setting |
| OS version | `Build.VERSION.RELEASE` | `2.2`, `4.0.3 ` etc |
| Terminal name | `Build.MODEL` | `Nexus One`, `X06HT` etc |
| Language settings | `Locale.getDefault().getLanguage()` | `ja`, `en` etc |
| Country code | `Locale.getDefault().getCountry()` | `JP`, `US` etc |

<a name="log"></a>
# Can the SDK Log Output be Controlled? 

Write the following code in the place of your choice.

```Java
AppDavis.setMuteLogOutput(true);
```

<a name="function"></a>
# Is there a 〇〇 Function?

The public API is published in [javadoc](http://mtburn.github.io/MTBurn-Android-SDK-Install-Guide/javadoc/latest/).

<a name="sample"></a>
# Are there any Sample Projects?
Please refer to [demo](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/tree/master/demo).

<a name="not_found_ad"></a>
# Problems with the Advertisement Display (Not Displayed, Overlapped, etc.)

The cause can be generally identified in the following order. The method of handling each of the respective causes is shown.

1. There is a problem with the account
2. There is a problem with the method of calling the application

### There is a problem with the account

Here, `account` refers to the `media ID (media_id)` and the associated `advertisement ID (adspot_id)`.


First, there may be a problem with the settings of the issued `account`. To test this, please try using a separate `account`. To use this separate `account`, refer to the `adspot ids` linked to `media_id 1 or media_id 2` in this document.

As in the case of in-feed advertisements, `media_id 2` and its associated `adspot id` (hereafter `the test account displaying the main advertisement`) may, in rare cases, be unable to acquire the advertisement.  
If the advertisement cannot be displayed even with `the test account used for displaying the main advertisement`, test `this by using a test account to display a test advertisement`. 

`The test account for displaying the test advertisements` is `the media_id 1`, with the associated `advertisement ID`, is as follows.

| Ad Spot ID corresponding to media_id 1 | Ad Spot ID corresponding to media_id 2 | Advertising image size | Advertisement format |
| --- | --- | --- | --- |
| NDgzOjE | NDQ0OjMx | 114x114 pixel | ThumnailMiddle |
| Njc4OjI | OTA2OjMy | 114x114 pixel | ThumnailSmall |
| NzA3OjM | ODEzOjMz | 640x200 pixel | LandscapePhoto |
| MTY5OjQ | OTIyOjM0 | 640x320 pixel | PhotoBottom |
| OTMzOjU | NzA2OjM1 | 640x320 pixel | PhotoMiddle |
| MzUxOjk | MzA3OjM2 | 114x114 pixel | TextOnly |
| OTgxOjUy | MTI2OjU1 | 114x114 pixel | WebView（Small） |
| MjQxOjUz | OTkzOjU2 | 640x200 pixel | WebView（Medium） |
| NjA0OjU0 | MzEzOjU3 | 640x320 pixel | WebView（Large） |

### There is a problem in the method for calling the application

If the problem is not resolved after trying all three `issued accounts`, `the test account for displaying the main advertisement` and `the test account for displaying the test advertisement`, check whether there is a problem with `the account` or a problem with the application or with SDK.

By default, [the demo project that came with the SDK](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/tree/master/demo) uses `the test account to display the main advertisement`. By testing the display using this, you can establish whether there is a problem in the method for calling the application. As `the account` can be set as desired using [main.xml](https://github.com/mtburn/MTBurn-Android-SDK-Install-Guide/blob/master/demo/res/values/main.xml#L18-L43), use this to troubleshoot `the account`.

Contact your representative if there is a problem with `your account.`

<a name="version"></a>
# What does the Version Number mean? 

This follows [semver](http://semver.org/)

<a name="instream"></a>
# What does Instream mean in the code?

In the guide this has the same meaning as `in-feed`.
