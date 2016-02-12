function [ count ] = cascade(newMat,x,y)
%UNTITLED12 Summary of this function goes here
%   Detailed explanation goes here

    count = 0;
    i = y;
    while i > 0
        if newMat(x,y) ~= 0
            return;
        end
        count = count + 1;
        i = i - 1;
    end
end

