### USEME: written by Jonathan Truong and Christopher Burke

# Supported Commands

#### "create" - creates a new layer that can be processed by the controller.

    This command must be followed by a unique name for the layer, separated with a space.
    
    Example: "create layer1"

#### "remove" - removes a layer from the image processor.

    This command must be followed by the name of an existing layer, separated with a space.
    
    Example: "remove layer1"

#### "current" - sets a layer as the current layer to be processed by the controller.

    This command must be followed by the name of an existing layer, separated with a space.
    
    Example: "current layer1"

#### "save" - exports the topmost visible layer to the designated filepath.

    This command must be followed by a designated filepath, separated with a space.
    
    Example: "save res/processedImage.ppm"

#### "save all" - exports the multi-layered image as a specified file type to the designated directory.

    This command must be followed by a directory name, then a file type (only PPM, JPG, and PNG are
    currently supported), each separated with a space. A multi-layered image is saved as a
    collection of files, where each layer is saved as the specified file type. A text file is also
    generated containing the location of every layer file. A new directory is created if one under
    the provided name does not exist.

    Example: "save all MyImages JPG"

#### "load" - loads a layer to replace the current layer.

    This command must be followed by a designated filepath that already exists,
    separated with a space.
    
    Example: "load res/processedImage.ppm"

#### "visible" - renders the current layer as visible.

    An existing layer must be set as the current layer before this command is called.    
    
    Example: "visible"

#### "invisible" - renders the current layer as invisible.

    An existing layer must be set as the current layer before this command is called.    
    
    Example: "visible"

#### "blur" - applies the blur transformation to the current layer.

    An existing layer must be set as the current layer before this command is called.    
    
    Example: "blur"

#### "sharpen" - applies the sharpen transformation to the current layer.

    An existing layer must be set as the current layer before this command is called.    
    
    Example: "sharpen"

#### "greyscale" - applies the greyscale transformation to the current layer.

    An existing layer must be set as the current layer before this command is called.    
    
    Example: "greyscale"

#### "sepia" - applies the sepia transformation to the current layer.

    An existing layer must be set as the current layer before this command is called.    
    
    Example: "sepia"

#### "close" - ends the image processor program.

    Must be called once the user is done interacting with the controller.

    Example: "close"

# Example Run

All commands must be separated by new lines.

    "create layer"
    "create layer2"
    "create layer3"
    "current layer"
    "load imageFolder/imageToEdit.ppm"
    "blur"
    "save imageFolder/blurredImage.png"
    "current layer2"
    "load imageFolder/imageToEdit2.ppm"
    "greyscale"
    "save imageFolder/greyscaleImage.png"
    "remove layer3"
    "save all ProcessedImagesBackup PPM"
    "close"