function [ outMat ] = siderealControl( inMat )
%UNTITLED3 Summary of this function goes here
%   Detailed explanation goes here

    outMat = inMat;
    score = 0;

    for i = 1:1000
    
        newMat = makeBoard(inMat(i,1:42));
        
        for x = 1:7
            for y = 1:6
                if newMat(x,y) == 1
                    score = score + abs(4-x);
                else if newMat(x,y) == 2
                    score = score - abs(4-x); 
                end
            end
        end
       
        outMat(i,45) = score;
        
    end

end

