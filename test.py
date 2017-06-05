# -*- coding: utf-8 -*-
# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice

# Connects to the current device, returning a MonkeyDevice object
device = MonkeyRunner.waitForConnection()

# Installs the Android package. Notice that this method returns a boolean, so you can test
# to see if the installation worked.
device.installPackage('C:\\Users\\gianluca\\AndroidStudioProjects\\ingsw_unisannio\\app\\build\\outputs\\apk\\app-debug.apk')

# sets a variable with the package's internal name
package = 'unisannio.ingsoft.bbm'

# sets a variable with the name of an Activity in the package
activity = 'unisannio.ingsoft.bbm.MainActivity'

# sets the name of the component to start
runComponent = package + '/' + activity

# Runs the component
device.startActivity(component=runComponent)


#Select a beer ("Chimay Red") to get it's info
device.touch(150.0,316.0, MonkeyDevice.DOWN_AND_UP)

#We need to sleep here, to make sure we will get the correct screenshot
MonkeyRunner.sleep(5)

# Takes a screenshot
result = device.takeSnapshot()

# Writes the screenshot to a file
result.writeToFile('C:\\Users\\gianluca\\Desktop\\UI_TEST\\infobeer.png','png')

#Go Back to Main Activity
device.touch(51.0,85.0, MonkeyDevice.DOWN_AND_UP)

#We need to sleep here, to make sure we will get the correct screenshot
MonkeyRunner.sleep(5)

# Takes a screenshot
result = device.takeSnapshot()

result.writeToFile('C:\\Users\\gianluca\\Desktop\\UI_TEST\\main.png','png')

MonkeyRunner.sleep(5)
#Filter Beer by Typing keyword in the searchview
device.touch(90.0,160.0, MonkeyDevice.DOWN_AND_UP)
#In this test case was type letter "d"
device.touch(152.0,610.0,MonkeyDevice.DOWN_AND_UP)
#We need to sleep here, to make sure we will get the correct screenshot
MonkeyRunner.sleep(5)
# Takes a screenshot
result = device.takeSnapshot()
result.writeToFile('C:\\Users\\gianluca\\Desktop\\UI_TEST\\filter_beer.png','png')

#Reset searchview, by press on "X"
device.touch(433.0,154.0, MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(2)
#Press on Action Bar button "Breweries Map" to go to mapActivity
device.touch(247.0,86.0, MonkeyDevice.DOWN_AND_UP)

#We need to sleep here, to make sure we will get the correct screenshot
MonkeyRunner.sleep(5)
# Takes a screenshot
result = device.takeSnapshot()

result.writeToFile('C:\\Users\\gianluca\\Desktop\\UI_TEST\\map.png','png')


