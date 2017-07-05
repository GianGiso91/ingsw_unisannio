# -*- coding: utf-8 -*-
# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
# Connects to the current device, returning a MonkeyDevice object
device = MonkeyRunner.waitForConnection()
# Installs the Android package. Notice that this method returns a boolean, so
# you can test to see if the installation worked.
device.installPackage('C:\\Users\\Utente\\AndroidStudioProjects\\ingsw_unisannio\\app\\build\\outputs\\apk\\app-debug.apk')
# sets a variable with the package’s internal name
package = 'unisannio.ingsoft.bbm'
# sets a variable with the name of an Activity in the package
activity = '.MainActivity'
# sets the name of the component to start
runComponent = package + '/' + activity
# Runs the component
device.startActivity(component=runComponent)
# Wait for few seconds
MonkeyRunner.sleep(2)
# Takes a screenshot
result = device.takeSnapshot()
# Wait for few seconds
MonkeyRunner.sleep(5)
# Writes the screenshot to a file
result.writeToFile('C:\\Users\\Utente\\Desktop\\screenshoot_monkey\\shot1.png','png')
#Touch the new status button
device.touch(80.0, 267.0, MonkeyDevice.DOWN_AND_UP)
# Wait for few seconds
MonkeyRunner.sleep(5)
# Takes a screenshot
result = device.takeSnapshot()
# Wait for few seconds
MonkeyRunner.sleep(5)
# Writes the screenshot to a file
result.writeToFile('C:\\Users\\Utente\\Desktop\\screenshoot_monkey\\shot2.png','png')
device.touch(223.0,1254.0, MonkeyDevice.DOWN_AND_UP)
# Wait for few seconds
MonkeyRunner.sleep(5)
device.touch(575.0,102.0, MonkeyDevice.DOWN_AND_UP)
# Wait for few seconds
MonkeyRunner.sleep(5)
# Takes a screenshot
result = device.takeSnapshot()
# Wait for few seconds
MonkeyRunner.sleep(5)
# Writes the screenshot to a file
result.writeToFile('C:\\Users\\Utente\\Desktop\\screenshoot_monkey\\shot3.png','png')
MonkeyRunner.sleep(5)
device.touch(281.0,817.0, MonkeyDevice.DOWN_AND_UP)
# Wait for few seconds
MonkeyRunner.sleep(5)
# Takes a screenshot
result = device.takeSnapshot()
# Wait for few seconds
MonkeyRunner.sleep(5)
# Writes the screenshot to a file
result.writeToFile('C:\\Users\\Utente\\Desktop\\screenshoot_monkey\\shot4.png','png')
MonkeyRunner.sleep(5)
device.touch(465.0,680.0, MonkeyDevice.DOWN_AND_UP)
# Wait for few seconds
MonkeyRunner.sleep(5)
# Takes a screenshot
result = device.takeSnapshot()
# Wait for few seconds
MonkeyRunner.sleep(5)
# Writes the screenshot to a file
result.writeToFile('C:\\Users\\Utente\\Desktop\\screenshoot_monkey\\shot5.png','png')
MonkeyRunner.sleep(5)
device.touch(133.0,852.0, MonkeyDevice.DOWN_AND_UP)
# Wait for few seconds
MonkeyRunner.sleep(5)
# Takes a screenshot
result = device.takeSnapshot()
# Wait for few seconds
MonkeyRunner.sleep(5)
# Writes the screenshot to a file
result.writeToFile('C:\\Users\\Utente\\Desktop\\screenshoot_monkey\\shot6.png','png')


