""" Jack Rivadeneira 
Connor Flannigan 
Chris Knapp"""
print "PROGRAM START!"

def readData():
	f = open("testFile.csv","r")
	fileData = f.read().split("\n")
	ret = []
	for eachLine in fileData:
		ret+=[eachLine.split(",")]
	return ret
	