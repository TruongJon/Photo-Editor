### README: written by Christopher Burke and Jonathan Truong
# Interfaces
## Model
## IImageProcessorState
The `IImageProcessorState` interface is designed to represent a "photo-album" of images. This
interface only contains methods that will allow someone using the code to obtain information
regarding the current state of the model, not allowing them to mutate or change it in any way.
## IImageProcessor
The `IImageProcessor` interface serves the same purpose as the `IImageProcessorState` interface with
the addition of being able to mutate the model. Images/layers can be added, removed, transformed,
updated, amongst other commands.
### IImage
The `IImage` interface was constructed with implementing new file types in the future in mind. The
methods held within this interface allow for easy extension of our design by being able to create a
new image class that implements `IImage`.
### ITransform
The `ITransform` interface was designed in order to represent various image transformations that can
be applied to an image. So far in this assignment we have implemented filtering and color
transformations, but with this design we can easily extend to have many more transformations by
creating new classes that implement `ITransform`. The only method in `ITransform` is
`apply(*arguments*)`which is called only one time by the image class (applies to any transformation)
.
### IPixel
The `IPixel` interface was designed in order to represent any type of pixel within an image. For
right now only one class implements this interface and that is `Pixel`. For future extension, we can
easily create a new kind of pixel by creating a new class that implements `IPixel` and add new
fields to represent new aspects of the pixel (i.e. alpha values for opacity).
## Controller
### IImageProcessorController
The `IImageProcessorController` interface was designed to represent any kind of controller a user
may want in order to process user commands. The controller interacts with both the model and view,
but without code-syntax input required from the user.
### IOFile
The `IOFile` interface represents functionality for input and output based commands for images.
Methods to 'read' and 'export' exist so that each functionality may differ slightly in
implementation depending on the file type being exported. At this time "ppm", "jpg", and "png" file
types are supported.
## View
### IImageProcessorView
The `IImageProcessorView` interface represents the 'view' of the MVC design structure and handles
all functionality related to output of the model state to the user.
# Classes
### Main
The `Main` class is provided to start the image processing program. The controller within the main
class may be initialized either to take in the batch command script file, or run without it where it
defaults to waiting for individual user input through the console.
## Model:
### SimpleImageProcessor
The `SimpleImageProcessor` class is an implementation of the `IImageProcessor` interface. It
supports the ability to add/remove layers, replace layers, transform existing layers and represent
the current state of the image processor just to name a few.
### PPMImage
The `PPMImage` class is an implementation of the `IImage` interface for `.ppm` filetypes. It
supports creating a PPM Image based upon a file being imported into the system, as well as
programmatically creating a checkerboard. We create the checkerboard through one of the `PPMImage`
constructors.
### Filter
`Filter` is an implementation of the `ITransform` interface that allows for the ability to create
new images by the process of filtering each pixel. Currently, the two types of filter
transformations available are blur and sharpen. To support future filter transformations, the only
required change would be a new kernel supplied to the apply method.
### ColorTransformation
`ColorTransformation` is an implementation of the `ITransform` interface that supports the creation
of new images by applying color transformations. We currently have the greyscale and sepia color
transformations available. To add further color transformations in the future, we simply need a new
kernel for the new color transformation to pass into the apply method.
### KernelMatrix
`KernelMatrix` is a stand-alone class to represent a 2-dimensional array of values that gets passed
into the methods that apply a transformation to an image. A `KernelMatrix` can be used in any type
of image transformation and are valid at any size as long as there is a "middle" position (which is
enforced in the constructor). Because of this, it can be used for any transformation at any size,
providing a lot of freedom to the user in regard to transforming images.
### Pixel
`Pixel` is an implementation of the `IPixel` interface that represents a pixel within a "24-bit
image". Each pixel has a position within the image and 3 8-bit color channels (red, green, and blue)
. Including a position field within the pixel itself is important as when we find neighboring pixels
to apply a filter transformation, having a unique Posn ensures that even if there are two pixels
with the same channel values, the pixel is unique in the image.
### Posn
`Posn` is a simple, stand-alone class to represent a graphical x and y position. `Posn` is only used
in the creation of `Pixel` and to set values within a `KernelMatrix` by indicating what
position/indices in the kernel to set a value to.
## Controller
### SimpleImageProcessorController
The `SimpleImageProcessorController` class implements the `ImageProcessorController` interface and
provides functionality for a user to input a variety of commands to run the image processor.
Commands can be provided either one by one through the console after running the provided `Main`
method, or through a provided text file in which all valid commands are given all at one once. For
more insight on the functionality and what constitutes a valid input, refer to the provided `
USEME.md` file.
### AbstractIOFile
The `AbstractIOFile` class implements the `IOFile` interface and provides functionality to
read/export non-traditional image file formats (jpg and png).

### IOPPMFile
The `IOPPMFile` class implements the `IOFile` interface and provides functionality to read/export
the traditional image file format (ppm).
## View
### SimpleImageProcessorView
The `SimpleImageProcessorView` implements the `IImageProcessorView` interface and provides
functionality to output the current state of the model and other important information regarding
controller interaction to the user.
# Enumerations
## Model
### DefaultKernel
`DefaultKernel` creates and store the kernels needed for the blur, sharpen, greyscale, and sepia
effects. Each `DefaultKernel` has a single field which is essentially the kernel to apply to the
corresponding transformation, and can be called upon whenever needed as a convenience.
### Channel
`Channel` contains each of the three possible 8-bit channels a pixel in an image can have. The three
available `Channel`'s are RED, GREEN, and BLUE.
## Controller
### FileType
`FileType` represents the valid file types the image processor supports. At this time, this image
processing application supports jpeg, png, and ppm file formats.
## Citation of Images Used
All images used in this project are owned and authorized for use by Christopher Burke.