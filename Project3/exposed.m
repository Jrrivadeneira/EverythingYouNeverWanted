function [ score ] = exposed( mat, x, y )
%UNTITLED7 Summary of this function goes here
%   Detailed explanation goes here

score = 0;

for i = -1:1
    if x+i <= 7 && x+i > 0
        for j = -1:1
            if y+j <= 6 && y+j > 0
                
                if mat(x+i,y+j) == 0
                    if mat(x,y) == 1
                        score = 1;
                    else 
                        score = -1;
                    end
                    
                end
            end
        end
    end
    
    
end

