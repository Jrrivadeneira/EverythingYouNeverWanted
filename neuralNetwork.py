""" Jack Rivadeneira 
Connor Flannigan 
Chris Knapp"""
from numpy import *
class NeuralNetwork(object):
	"""docstring for NeuralNetwork"""
	def __init__(self,NumberOfInputNodes,NumberOfHiddenNodes,NumberOfOutputNodes):
		self.numberOfInputNodes = NumberOfInputNodes
		self.numberOfHiddenNodes = NumberOfHiddenNodes
		self.numberOfOutputNodes =NumberOfOutputNodes
		self.layerOneWeights = random.rand(self.numberOfInputNodes,self.numberOfHiddenNodes) * 2 - 1
		self.layerTwoWeights = random.rand(self.numberOfHiddenNodes,self.numberOfOutputNodes) * 2 - 1
	"""The sigmoid function for this NeuralNetwork"""
	def sigmoid(self, t):
		return 1 / (1 + exp( - t))

	def sigmoidPrime(self,t):
		return exp(t) / power((exp(t) + 1),2)
	
	"""To Clean Up Calculations"""
	def deltaThree(self,error):
		return multiply(-error,self.sigmoidPrime(self.unactivatedValuesOfLayerThree))
	
	"""To clean Up Calculations"""
	def deltaTwo(self,error):
		# print self.deltaThree(error).shape
		# print self.layerTwoWeights.T.shape
		# print self.sigmoidPrime(self.unactivatedValuesOfLayerTwo).shape
		# a = self.deltaThree(error)
		# b = multiply(self.layerTwoWeights.T,self.sigmoidPrime(self.unactivatedValuesOfLayerTwo))
		# print a.shape
		# print b.shape
		# return dot(a.T,b)
		a = self.deltaThree(error)
		b = self.layerTwoWeights.T
		c = self.sigmoidPrime(self.unactivatedValuesOfLayerTwo)
		d = dot(a,b)
		r = multiply(d,c)
		return r

	"""takes a matrix of inputs and returns a matrix of outputs"""
	def predict(self,inputMatrix):
		self.unactivatedValuesOfLayerTwo = dot(inputMatrix,self.layerOneWeights)
		self.activatedValuesOfLayerTwo = self.sigmoid(self.unactivatedValuesOfLayerTwo)
		self.unactivatedValuesOfLayerThree = dot(self.activatedValuesOfLayerTwo,self.layerTwoWeights)
		self.activatedValuesOfLayerThree = self.sigmoid(self.unactivatedValuesOfLayerThree)
		return self.activatedValuesOfLayerThree
	
	"""U GOn L3rn2DAY"""
	def teach(self,inputMatrix,answersMatrix,alpha):
		error = (answersMatrix - self.predict(inputMatrix))
		acceptableError = 1e-8
		while(error.mean()>acceptableError):
			print error.mean()
			derivativeOfOutputWithRespectToWeightTwo = dot(self.activatedValuesOfLayerTwo.T,self.deltaThree(error))
			derivativeOfOutputWithRespectToWeightOne = dot(inputMatrix.T, self.deltaTwo(error))
			self.layerOneWeights -= alpha*derivativeOfOutputWithRespectToWeightOne
			self.layerTwoWeights -= alpha*derivativeOfOutputWithRespectToWeightTwo
			error = (answersMatrix - self.predict(inputMatrix))

"""------------------TestArea------------------"""
from dataReader import *
dat = readData("testFile.csv")
x = NeuralNetwork(2,4,1)
# print x.numberOfHiddenNodes
# print x.layerOneWeights
# print x.layerTwoWeights
teachMat = []
answerMat = []
for k in dat:
	if(k!=['']):
		teachMat += [k[:2]]
		answerMat += k[2:]
teachMat = matrix(teachMat)
answerMat = matrix(answerMat).T
myMat = matrix(dat)
# print answerMat
print x.teach(teachMat,answerMat,0.01)
# print x.predict(random.rand(5,2))


