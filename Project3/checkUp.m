function [ score ] = checkUp(newMat,x,y)
%UNTITLED8 Summary of this function goes here
%   Detailed explanation goes here
    score = 0;
    
    for i = 1:3
        if newMat(x,y) ~= 0 && newMat(x,y+i) ~= newMat(x,y)
            score = 100;
            return
        end
        if newMat(x,y+i) == 0
            score = score + 1;
        end
    end

end

