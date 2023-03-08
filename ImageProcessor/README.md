# Image Processor

## changelog 
#### Refactored ColorComp
The color component enumeration – <code>ImageReadWrite.ColorComp</code> – was refactored
to <code>ImageReadWrite.Channel</code>. It no longer contains "Value", "Intensity", 
or "Luma." Instead, the Channel enumeration only supports "Red", "Green",
and "Blue." It was determined that this was more intuitive from a design 
perspective, as value, intensity, and luma are related to RGB values, 
but they are not the same thing. 

#### Greyscale method in ImageProcessor
The ImageProcessor interface no longer supports an all-encompassing 
"greyscale" method. Previously, this method contained a switch statement that 
took an <code>ImageReadWrite.ColorComp</code> and performed the appropriate greyscale
operation. However, this resulted in duplicate code, and passing color 
components around was found to be indirect and loosely enforced. As 
a result, the <code>greyscale</code> method was torn apart into separate methods.
These methods include: <code>redChannel</code>, <code>greenChannel</code>,
<code>blueChannel</code>, <code>maxVal</code>, <code>intensity</code>,
<code>luma</code>, and <code>customGreyscale</code>. Each of these 
new methods were abstracted from their previous iterations to use 
the new <code>colorTransformation</code> method. This method performs,
in essence, a matrix-vector multiplication for the RGB values in each 
pixel. This operation is <i>nearly identical</i> to the previous version
of <code>greyscale</code>'s <i>cases</i>. Hence, these updated methods
retain their original functionality from <code>greyscale</code> – they have
simply been abstracted to align with the novel <code>colorTransformation</code> method.
The limitation of this design is that the interface must be expanded to 
support <i>pre-defined</i> greyscale operations. Nonetheless, we offer
a custom greyscale option, and the <code>colorTransformation</code> method is 
public facing with support for any custom matrix from the client. 
<p></p>
<i>It should be noted that the previous version of greyscale has been
preserved almost entirely in the form of testing to crosscheck the new
matrix-multiplication based operations.</i>

#### Calling greyscale from the controller
As a result of refactoring greyscale, each of the methods above can 
be called more directly from the controller. Therefore, a helper
method is no longer required to abstract the delegation to the model; 
each method in the model can be called directly. Thus, the controller
is no longer concerned with the implementation of <code>greyscale</code> 
or <code>ImageReadWrite.ColorComp</code>. This is beneficial 
for single-responsibility, encapsulation, and enforcement through the 
ImageProcessor's interface. 

#### Removed <code>extensionValid</code>
This method used to check that a file had a ".ppm" file extension.  
With the introduction of new image formats, this method became 
obsolete. Rather than asking the model if an extension "is valid,"
the model can return information about the image formats it supports. 
Specifically, the <code>getSupportedFormats</code> method returns 
an array of file extensions, denoting the file support for this 
image processor implementation. 

#### Refactored <code>ImageProcessorPPM</code> to <code>ImageProcessorImpl</code>
It was previously believed that new image formats would have to 
be supported in separate implementations of the <code>ImageProcessor</code>
interface. However, after writing the methods to load and save 
with other file formats, it became apparent that any format could 
be supported in a single image processor implementation, hence 
the name <code>ImageProcessorImpl</code>. 

#### The <code>saveImage</code> method takes an additional parameter
In order to check whether a file format is supported before writing to
a file, the <code>saveImage</code> method needs to know what the 
extension of the file is. The limitation with <code>saveImage</code> is
that it isn't responsible for constructing the output stream to 
write to. This is actually performed in the controller at the recommendation
of a TA, as we wanted to support any <code>OutputStream</code> format
in <code>saveImage</code> so that clients could save image data 
in alternative formats, such as a byte array, without having to 
worry about saving files to their computer. 

## /src

### ImageReadWrite
Any application that needs to read and/or write to an image file. 
This interface defines the available color components for a loaded image.

### ImageProcessor
The image processor model interface. This interface
represents a general image processing application. It
extends the ImageReadWrite interface and defines the 
functionality for an image processing application. 

### ImageProcessorPPM
A PPM-specific image processor. This is an implementation
of the ImageProcessor interface. It serves as the model
for a PPM-based implementation. 

### ImageUtil
Utility methods to read an image from a file.

### ImageController
Handles user input and communication between
the model and the view for an image processing 
application. 

### ImageControllerImpl
The implementation for an image processor's 
controller; it is not specific to any image format.

### ImageView
Defines the functionality for an image application's view.

### ImageTextView
Renders the CLI for an image application;
it is not specific to any implementation of the 
ImageReadWrite interface.

### ImageProcessorMain
An image processing application; this is the 
entry point for the program. 

## /test

### CorruptAppend
A mock appendable that always throws an IOException.

### CorruptOutputStream
A mock output stream that always throws an IOException.

### CorruptRead
A mock readable that always throws an IOException.

### ImageControllerTest
To test the image processor controller. 

### ImageProcessorImplTest 
To test the implementation of a PPM image processor
model. 

### ImageViewTest
To test the view for an image application. 

### MockModelController
To test transmission between the controller and the model. 

### MockViewController
To test transmission between the controller and the view. 

## Design Choices ##
The most important clarification is regarding our greyscale.\
The assignment mentions how the greyscale should be a renewed luma component but with matrix multiplication.\
We chose to design greyscale to be a custom one instead\
This is because our previous luma-component is the exact same as the new one (matrix multiplication concept)\
We decided to simply have the greyscale be a custom one\
To still do what the professors intended, we had the original luma in the tests to show how the old luma and new are the exact same.\


## Example Commands Script
"load res/Kirby.ppm Kirby 
load res/controllerTest.ppm overwriteImage 
brighten 100 Kirby brightKirby 
vertical-flip brightKirby verticalBrightKirby 
horizontal-flip brightKirby horizontalBrightKirby 
red-component verticalBrightKirby redVerticalKirby 
intensity-component verticalBrightKirby intenseKirby 
load res/nyc.ppm overwriteImage 
save res/verticalKirby.ppm verticalBrightKirby 
save res/horizontalKirby.ppm horizontalBrightKirby 
save res/greyscaleKirby.ppm redVerticalKirby 
save res/brightKirby.ppm brightKirby q"

**If you would like to check if the image was overwritten
you may run "save res/overwrite.ppm overwriteImage after line 2
Do the command "save res/afterOverwrite.ppm overwriteImage again after line 8"

**Lastly, all commands can be found in the program itself by simply entering menu!\
If you ever forget the names you assigned, use "stored-images."\
Make sure to load an image first before trying anything else.

## Image Credits: 

deadmau5.ppm – Image Credit: Troy Acevedo, Exchange LA, https://www.facebook.com/ExchangeLA/photos/t.100044343943391/4711558285525136/

Kirby.ppm – Image Credit: Play Nintendo, https://play.nintendo.com/themes/friends/kirby/

NyanCat.ppm – Image Credit: Harvard Law Today, https://today.law.harvard.edu/memes-for-sale-making-sense-of-nfts/

nyc.ppm – Image Credit: Joshua, https://www.pinterest.com/joshuaauerbach/new-york/

one.ppm – Image credit: Shueisha (One Piece Volume 100 Cover Art), https://japan-forward.com/wp-content/uploads/2021/09/ONE-PIECE-Volume-100-Calligraphy-%C2%A9-Eiichiro-Oda-Shueisha.jpg