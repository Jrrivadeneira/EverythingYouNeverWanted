""" Jack Rivadeneira
Connor Flannigan
Chris Knapp
Special Thanks To:
Welch Labs

"""
print "PROGRAM START!"
import sys
from dataReader import *
from neuralNetwork import *

numHidden = 5
percHold = .2

giraffe = sys.argv[1]

if (len(sys.argv) > 2):
    print sys.argv
    if(sys.argv[2] == 'h'):
        numHidden = int(sys.argv[3])
        if(len(sys.argv) > 4):
            if(sys.argv[4] == 'p'):
                percHold = float(sys.argv[5])
    if(sys.argv[2] == 'p'):
        percHold = float(sys.argv[3])
        if(len(sys.argv) > 4):
            if(sys.argv[4] == 'h'):
                numHidden = int(sys.argv[5])

# read data from file
data = readData( giraffe)

# construct the neural network with 2 input nodes, numHidden hidden nodes, and 1 output node
net = NeuralNetwork(2, numHidden, 1)

# init matrices, teachmat being the input and answerMat being the output
teachMat = []
answerMat = []

#for each line in the input file
for k in data:
	# if that line is not empty
    if(k!=['']):
		# add the first two values to a new line of teachMat and the last value to a new line of answerMat
        teachMat += [k[:2]]
        answerMat += k[2:]

# create and correctly format the matrices from the arrays
teachMat = matrix(teachMat)
answerMat = matrix(answerMat).T
myMat = matrix(data)

# divide  teachMat and answerMat  by the holdout percentage
holdCount = floor(percHold*200)
net.learn(teachMat[:holdCount,:],answerMat[:holdCount],teachMat[holdCount:,:],answerMat[holdCount:],0.01)

# hand these matrices to the network

# print whatever the hell it is this is, which is nothing
