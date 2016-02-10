""" Jack Rivadeneira 
Connor Flannigan 
Chris Knapp"""
def myFloat(lis):
	return map(float,lis)

def readData(filePath):
	f = open(filePath,"r")
	fileData = f.read().split("\n")
	ret = []
	for eachLine in fileData:
		if(eachLine!=''):
			ret+=[eachLine.split(" ")]
	ret = map(myFloat,ret)

	return ret
	