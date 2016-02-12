function [ board ] = makeBoard( states )
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here
    board = 5;
    a = 1;
    for i = 1:7
        for j = 1:6
            board(i,j) = states(a);
            a = a + 1;
        end
    end

end