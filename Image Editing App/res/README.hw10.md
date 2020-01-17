# ImageProcessor (5004 Assignment10)

Written and designed by Deanna Dang and Kristin White.

April 19, 2019

## Introduction

The Image Processor model can be used to filter an image and generate images.

The available filters include:
* Blur
* Sharpen
* Greyscale
* Sepia
* Dither
* Mosaic

The options for generating images include:
* Rainbow Stripes (Vertical and Horizontal)
* Checkerboard Pattern (image in black & white)
* Flags for France, Greece, and Switzerland

Read descriptions on each filter and generator below.

## Design Changes

In this version we added individual command classes in the controller that can be called from the buttons and menu options in the view. The controller now has a separate function for executing a single command line.

The view creates the GUI that the user interacts with. The buttons and menu options in the view send the user interactions to the command classes in the controller package.

## Using the ImageProcessor
Run the jarfile and then there is an option to enter in a batch file and run the entries in that, or
to use a gui which is done by entering in "-interactive" as a parameter. 

Example with File:
"java -jar assignment10.jar basicScript.txt"

Example with GUI:
"java -jar assignment10.jar -interactive"

## Using the GUI
In order to use the ImageProcessor you can either press buttons on the GUI, use the menu options, or 
use the keyboard shortcuts which are labeled in the menu as well. 

Buttons

The buttons we have available are "Load an image", "Save image as", "Upload script", "Undo",
"Redo", "blur", "sharpen", "sepia", "greyscale", "dither", "mosaic", "create French flag",
"create Swiss Flag", "create checkerboard", "create rainbow." 


Menu

The "Main Menu" has the option to load an image, save an image, upload a script, create a new 
script, undo a image operation, and redo an image operation. In the "Filter Operations" you have
the option to use sepia, greyscale, sharpen, blur, dither, and mosaic. In the "Generator 
Operations" menu you have the option to create a French, Swiss, and Greek flag, in addition to
a checkerboard, and a vertical or horizontal rainbow. 


Shortcut Keys

- Load an Image "CTRL + L "
- Save Image as "CTRL + S"
- upload script "CTRL + A"
- create new script "CTRL + N"
- undo "CTRL + U"
- redo "CTRL + R"
- sepia "CTRL + P"
- greyscale "CTRL + G"
- sharpen "CTRL + E"
- blur "CTRL + B"
- dither "CTRL + D"
- mosaic "CTRL + M"
- french "CTRL + F"
- swiss "CTRL + W"
- greek "CTRL + K"
- checker "CTRL + C"
- rainbow "CTRL + I"

For Buttons, Menu, and Shortcut Keys

The actions that require user input such as size of the flags, vertical or horizontal rainbow, and
checker square size, will have a window appear that has a text box that the user can enter it in 
and a button to press once they enter the input, which will run the action. 

All actions done by the user will appear in the Image View panel. 
 

### In the Terminal

An example format is provided below.

FORMAT: "java -jar hw9.jar (filename)"

## The Commands

Inside of the file, the commands need to be written in their correct file format and in the correct order.

A list of the commands is provided below. For more detailed information on the commands, please 
read their individual descriptions below this section.

* load
    File Format: "load (image name)"

* save
    File Format: "save (image name)"

* sepia
    File Format: "sepia"

* greyscale
    File Format: "greyscale"

* sharpen
    File Format: "sharpen"

* blur
    File Format: "blur"

* dither
    File Format: "dither"

* mosaic
    File Format: "mosaic (integer)"

* french
    File Format: "french (integer)"

* swiss
    File Format: "swiss (integer)"

* greek
    File Format: "greek (integer)"

* checker
    File Format: "checker (integer)"
    
* rainbow
    File Format: "rainbow (integer) (integer) (string)"

### Load Command - 'load'
File Format: "load (image name)"

The load command notifies the program which image to apply the following commands to. You must 
load an image before applying filters to it.

### Save Command - 'save'
File Format: "save (image name)"

The save command notifies the program that the most recently create image should be saved as the 
given image name. You must save an image to keep newly generated images or completed filtered images.

### Sepia Command - 'sepia'
File Format: "sepia"

This filter adjusts the RGB value for each pixel in the given image to create a 'sepia' version of 
the image.

To save, make sure to use the save command after you filter the loaded image.

### Greyscale Command - 'greyscale'
File Format: "greyscale"

This filter adjusts the RGB value for each pixel in the given image to create a 'greyscale' version
 of the image.

To save, make sure to use the save command after you filter the loaded image.

### Blur Command - 'blur'
File Format: "blur"

This filter 'blurs' each pixel in the given image to create a blured image. 

To save, make sure to use the save command after you filter the loaded image.

### Sharpen Command - 'sharpen'
File Format: "sharpen"

This filter 'sharpens' each pixel in the given image to create a sharpened image.

To save, make sure to use the save command after you filter the loaded image.

### Dither Command - 'dither'
File Format: "dither"

This filter 'dithers' each pixel in the given image to create a dithered image.

To save, make sure to use the save command after you filter the loaded image.

### Mosaic Command - 'mosaic'
File Format: "mosaic (integer)"

This filter makes a mosaic version of the loaded image using the number of seeds given in the 
command's line. The integer is the number of seeds/panels that will be in the final mosaic image.

To save, make sure to use the save command after you filter the loaded image.

### French Command - 'french'
File Format: "french (integer)"

This method generates the French flag using the given integer on this command's line. The integer is
 the width of the flag that will be created.

To save, make sure to use the save command after you create the image.

### Swiss Command - 'swiss'
File Format: "swiss (integer)"

This method generates the Swiss flag using the given integer on this command's line. The integer is 
the width of the flag that will be created.

To save, make sure to use the save command after you create the image.

### Greek Command - 'greek'
File Format: "greek (integer)"

This method generates the Greek flag using the given integer on this command's line. The integer is
 the width of the flag that will be created.

To save, make sure to use the save command after you create the image.

### Checker Command - 'checker'
File Format: "checker (integer)"

This method generates a black and white checkerboard using the given integer on this command's line.
 The integer is the width of the flag that will be created.

To save, make sure to use the save command after you create the image.

### Rainbow Command - 'rainbow'
File Format: "rainbow (integer) (integer) (string)"

This method generates either a horizontal or vertical rainbow depending on the input given on the 
command's line. The integer is the width of the flag that will be created.

To save, make sure to use the save command after you create the image.

## Using the Commands in the File

The file you need to submit must be a simple, plain text file. Each line of the file represents a 
command you would like to execute. Each command line in the file needs to follow the format of which 
ever command you are trying to execute. An exception will be thrown if you do not follow the correct
 format. Also, if you do not save when you are done with an operation, you will not be able to save 
 that version of the image and the file will continue to the next command.

### Filtering
You must use the load command to upload an image to use a filter command. 
Once you have the image loaded, you can use a filter command on the next line.

Filter commands include:
* sepia
* greyscale
* sharpen
* blur
* dither
* mosaic

Example file text:
load suzy.jpeg
sepia
save suzySepia.jpeg

You can also run multiple filters on the same object. Once you load an image, write the filters you
want to execute on the following lines, in the correct format. An example is provided below.

Example file text:
load suzy.jpeg
greyscale
blur
save suzyGB.jpeg

### Generating
You do not have to use the load command to create an image using a generate command.
Run the generating command and save on the next line to keep the newly generated image. 

Generator commands include:
* french
* swiss
* greek
* checker
* rainbow

Example file text:
rainbow 300 400 vertical
save rainbow1.jpeg

### The "res" Folder Details

The res folder has the jarfile, the README.md, the two copies of the original images used in the test, 
filtered versions of the original images, and examples of all the generated images. The original
images are suzy.jpeg, llama.jpeg, and flower.jpeg.

## Photo Citations

The image 'flower.jpeg' is owned by Kristin White, who authorizes its use in this project.
The image 'llama.jpeg' is owned by Deanna Dang, who authorizes its use in this project.
The image 'suzy.jpeg' is owned by Deanna Dang, who authorizes its use in this project.

