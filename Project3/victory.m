function [ outMat ] = victory( inMat )
%UNTITLED6 Summary of this function goes here
%   Detailed explanation goes here

outMat = inMat;
score = 0;

for i = 1:1000
    
    newMat = makeBoard(inMat(i,1:42));
    
    for x = 1:7
        for y = 1:6
            if newMat(x,y) == 0
                break
            end
            up = 0;
            right = 0;
            left = 0;
            if y < 4
                up = 1;
            end
            if x < 5
                right = 1;
            end
            if x > 4
                left = 1;
            end
            mult = 1;
            if newMat(x,y) == 2
                mult = -1;
            end
            scoreUp = 100;
            scoreUpRight = 100;
            scoreUpLeft = 100;
            scoreRight = 100;
            if up == 1
                scoreUp = checkUp(newMat,x,y);
                if left == 1
                    scoreUpLeft = checkUpLeft(newMat,x,y);
                end
                if right == 1
                    scoreUpRight = checkUpRight(newMat,x,y);
                end
            end
            if right == 1
                scoreRight = checkRight(newMat,x,y);
            end
            score = score + mult*min(min(scoreUp,scoreUpRight),min(scoreUpLeft,scoreRight));
            
        end
    end
    
    outMat(i,47) = score;
    
end

end
