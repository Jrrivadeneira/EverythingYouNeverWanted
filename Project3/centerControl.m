function [ outMat ] = centerControl( inMat )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here

    outMat = inMat;
    
    for i = 1:1000
        p1count = 0;
        p2count = 0;
        
        for j = 13:30
           if outMat(i,j) == 1
               p1count = p1count + 1;
           end
           if outMat(i,j) == 2
               p2count = p2count + 1;
           end
        end
        
        if p1count ~= p2count
            if p1count > p2count
                outMat(i,44) = 1;
            else
                outMat(i,44) = 2;
            end
        else
            outMat(i,44) = 0;
        end
        
    end

end

