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
			derivativeOfOutputWithRespectToWeightTwo = dot(self.activatedValuesOfLayerTwo.T,self.deltaThree(error))
			derivativeOfOutputWithRespectToWeightOne = dot(inputMatrix.T, self.deltaTwo(error))
			self.layerOneWeights -= alpha*derivativeOfOutputWithRespectToWeightOne
			self.layerTwoWeights -= alpha*derivativeOfOutputWithRespectToWeightTwo
			error = (answersMatrix - self.predict(inputMatrix))
			
	# l3Rn $uM $Hap3s
	def learn(self, inputMatrix, answersMatrix, testMat, testAns,alpha):
		
		bestNet = self
		bestError = (testAns - self.predict(testMat)).mean()
		
		print bestError
		
		error = (answersMatrix - self.predict(inputMatrix))
		acceptableError = 1e-8
		print error.mean()
		
		while(abs(error.mean())>acceptableError):
			derivativeOfOutputWithRespectToWeightTwo = dot(self.activatedValuesOfLayerTwo.T,self.deltaThree(error))
			derivativeOfOutputWithRespectToWeightOne = dot(inputMatrix.T, self.deltaTwo(error))
			self.layerOneWeights -= alpha*derivativeOfOutputWithRespectToWeightOne
			self.layerTwoWeights -= alpha*derivativeOfOutputWithRespectToWeightTwo
			
			testErr = testAns - self.predict(testMat)
			if (abs(testErr.mean()) < bestError):
				bestError = testErr.mean()
				bestNet = self
				
			error = (answersMatrix - self.predict(inputMatrix).round())
		
		print bestError
		print error.mean()
		
		return bestNet