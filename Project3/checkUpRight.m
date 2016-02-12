function [ score ] = checkUpRight(newMat,x,y)
%UNTITLED8 Summary of this function goes here
%   Detailed explanation goes here
    score = 0;
    
    for i = 1:3
        if newMat(x,y) == 0 && newMat(x+i,y+i) ~= newMat(x,y)
            score = 100;
            return
        end
        if newMat(x+i,y+i) == 0
            score = score + cascade(newMat,x+i,y+i);
        end
    end

end