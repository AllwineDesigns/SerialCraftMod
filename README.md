# SerialCraft Minecraft Mod

The SerialCraft mod adds the ability to interface with Minecraft over a serial port. Messages can be 
sent to Minecraft from a device such as an Arduino to control your character, trigger redstone signals,
send chat messages and more. Messages are also sent from Minecraft to your device, such as health or
hunger level changes, redstone signals, nearest creeper distance and more. See the [SerialCraft Arduino
library](https://github.com/AllwineDesigns/serialcraft-arduino) for more information about programming
an Arduino to communicate with the SerialCraft mod.

## Video examples

* [DIY Potentiometer Example](https://www.youtube.com/watch?v=vZDvubHXgLI)
* [Giant Breadboard Example](https://youtu.be/xBf9fIEuX_o)

## Serial Redstone block

The Serial Redstone block can be built with the following recipe:

![Serial Redstone Recipe](https://github.com/AllwineDesigns/SerialCraftMod/raw/master/docs/images/serialRedstone.png)

When you place the block, you must enter an ID or password that is stored on the block. Once set, it cannot be change.
Instead, you must break the block, place it again and set a new ID. Once the ID is set, you can activate a redstone
signal from that block by sending the message "redstone <ID> <signal strength>" and replace <ID> with the ID you set
on your block and <signal strength> with a number between 0-15. Here is an [example using the SerialCraft Arduino
library](https://github.com/AllwineDesigns/serialcraft-arduino/blob/master/examples/digital/BasicRedstone/BasicRedstone.ino).
to trigger a redstone signal on when a button is pressed and turn it off when the button is released.

## Send Serial Message block

The Send Serial Message block can be built with the following recipe:

![Send Serial Message Recipe](https://github.com/AllwineDesigns/SerialCraftMod/raw/master/docs/images/sendSerialMessage.png)

Similar to the Serial Redstone block, when you place the block, you must enter a message. Once set, it cannot be change.
Instead, you must break the block, place it again and set a new message. Once the message is set, you can connect
the block to a redstone signal to send a message to your serial device. A level, button or pressure plate are the
easiest ways to trigger the message, but any LOW to HIGH signal will trigger the message, even by using a Serial Redstone
block!
