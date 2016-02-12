function [ outMat ] = frontier( inMat )
%UNTITLED6 Summary of this function goes here
%   Detailed explanation goes here

    outMat = inMat;
    score = 0;

    for i = 1:1000
    
        newMat = makeBoard(inMat(i,1:42));
        
        for x = 1:7
            for y = 1:6
                if newMat(x,y) ~= 0
                    
                    score = score + exposed(newMat,x,y);
                    
                end
            end
        end
       
        outMat(i,46) = score;
        
    end

end

