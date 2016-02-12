tds = trainDataSet;
tds(:,48) = tds(:,43);
tds(:,43) = tds(:,1);
tds = centerControl(tds);
tds = siderealControl(tds);
tds = frontier(tds);
tds = victory(tds);
xlswrite('trainDataFeatureSet', tds)