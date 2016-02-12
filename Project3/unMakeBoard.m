

function [states] = unMakeBoard(board)
    
    states = 1:42;
    a = 1;
    
    for i = 1:7
        for j = 1:6
            states(a) = board(i,j);
            a = a+1;
        end
    end

end