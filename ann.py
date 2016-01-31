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

in_handle = sys.argv[1]
if (len(sys.argv) > 2):
    if(sys.argv[2] == "h"):
        numHidden = sys.argv[3]
        if(len(sys.argv) > 4):
            if(sys.argv[4] == "p"):
                percHold = sys.argv[5]
    if(sys.argv[2] == "p"):
        percHold = sys.argv[3]
        if(len(sys.argv) > 4):
            if(sys.argv[4] == "h"):
                numHidden = sys.argv[5]

dat = readData(in_handle)
x = NeuralNetwork(2, numHidden, 1)
teachMat = []
answerMat = []
for k in dat:
    if(k!=['']):
        teachMat += [k[:2]]
        answerMat += k[2:]
teachMat = matrix(teachMat)
answerMat = matrix(answerMat).T
myMat = matrix(dat)
print x.teach(teachMat, answerMat, 0.01)
