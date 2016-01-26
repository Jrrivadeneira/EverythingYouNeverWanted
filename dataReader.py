""" Jack Rivadeneira 
Connor Flannigan 
Chris Knapp"""
def readData(filePath):
	f = open(filePath,"r")
	fileData = f.read().split("\n")
	ret = []
	for eachLine in fileData:
		ret+=[eachLine.split(",")]
	return ret
	