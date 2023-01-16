# Image Processing 

## Table of contents 
* [Updates (6/25)](#updates-625)
* [Updates (6/19)](#updates-619)
* [Overview](#overview)
    * [Model](#model)
    * [View](#view)
    * [Controller](#controller)
    * [How to load an image](#loading-an-image)

## UPDATES 6/25
* Program can now be visualized as a graphical user interface, which is implemented through a
separate view class and interface(ProcessorViewGui and ProcessorViewGUIImpl) because it supports
very different functionality compared to the view class that already existed
* Can also now support partialImageManipulation, which requires the input of a mask from the user
and the filter is applied to where the mask is black
    * Required reconfiguration of the controller in order for it to call the methods for partial
    image manipulation based on whether a mask is given
    * Model was updated with required methods for partial image manipulation as well as a method
    to retrieve histogram data
    * We believed it was ok to make these changes because our code was not mature enough where
    any changes would affect things outside of what we tested in this program. Additionally,
    we figured the disadvantages outweigh since it doesn't make sense to make a new class for
    getter methods, and it would make our code unnecessarily complicated.

## UPDATES 6/19
* Can now support loading, saving and transforming between JPG, BMP and PNG files using respective
classes of each that implement the Converter interface
* Supports new functionality: blurring, sharpening, and color transformations to sepia and grayscale
using kernels
* ImageProcessorFilter and ImageProcessorFilterImpl extend the previous model interface and class
to allow for this new functionality; the old model remains unchanged and should still be usable
* Controller was modified to add support for these new text commands and new Command objects
that implement ControllerCommands interface were added
* Converter interface and classes now deal with writing to a file; moved from the model earlier
* Pixel color components now stored in an array instead of a hashmap as searching through the
hashmap became an expensive operation

## Overview 
* This program represents an image manipulation tool.
* Takes in user input to:
    * load a file from a file path
    * greyscaling through several methods
    * flipping (horizontally & vertically)
    * brightening
    * saving to a different file path.
<br>
Made in Object-Oriented Design for a class project.

### Model:

* Contains ImageProcessor interface and ImageProcessorImpl class (one implementation of ImageProcessor)
* Support the commands that the user can input
* Image is represented through a 2D array of Pixel objects
* All images are loaded into a hash map that maps the image name to the 2D array.(Note
that if two images are inputted using the same image name, the first will be replaced by the second.)

* Pixel interface represents any type of pixel; supports the methods to manipulate a copy of
a given Pixel.
* PixelRGB class implements Pixel; represents a pixel that is made
of red, green and blue color components, along with the x and y components.(Note intensity and luma
 are rounded and then casted into an int)

### View:

* ProcessorView interface handles displaying output to the reader;ProcessorViewImpl is one
implementation that supports displaying a string to the reader through an appendable object.

### Controller:

* ProcessorController interface represents methods needed for any controller;ProcessorControllerImpl
is one implementation
* Uses command design pattern
    * ControllerCommands is the interface for any of these command objects.
    * AbstractImageCommand houses the the common fields and constructors.
    * Each command has its own class in the Commands package with a method to execute

### Loading an image:

* ImageReader interface supports the ability to read in any type of file and return its contents
as a 2D array of Pixels.
* PPMImageReader class implements the readerfunctionality for a PPM file (Currently, only PPM files
are supported, but more file extensions can be supported by creating an additional class that
implements this interface)


