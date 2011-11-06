format long;
clear;
clc;
system('CNProj1.jar');
load('A_data.txt')
fprintf('Total packets sent from node A=%d\n',A_data(1));
fprintf('Average frame interval at node A=%d usec\n',A_data(2));
fprintf('Average queue delay at node A=%d usec\n',A_data(3));
fprintf('Average access delay at node A=%d usec\n',A_data(4));

fprintf('Average end to end throughput from A to B=%d Mb/s\n',A_data(5)/10^6);
fprintf('Simulation end time=%d sec\n',A_data(6)/10^6);


load('A.dat');
interArrivalTime = A(:,1);
queueingDelay = A(:,2);
accessDelay = A(:,3);
figure(1);

%Queue Delay 
subplot(2,2,1)
plot(1:size(queueingDelay,1),queueingDelay(1:end))
axis([0 length(queueingDelay) 0 max(queueingDelay)])
xlabel('Packet sequence #');
ylabel('Delay in \mu sec');
title('Queue delay at node A');
        
%Access Delay
subplot(2,2,2)
plot(1:size(accessDelay,1),accessDelay(1:end))
axis([0 length(accessDelay) 0 max(accessDelay)])
xlabel('Packet sequence #');
ylabel('Delay in \mu sec');
title('Access delay at node A');

% Frame interval
subplot(2,2,3)
plot(1:size(interArrivalTime,1),interArrivalTime/1000)
axis([0 length(interArrivalTime) 0 max(interArrivalTime)/1000])
xlabel('Packet sequence #');
ylabel('Frame interval in msec');
title('Frame intervals at node A');
        
% Histogram to verify if the distribution is exp
subplot(2,2,4)
hist(interArrivalTime,60);
xlabel('frame intervals in \mu sec');
ylabel('# of frames');



load('B_data.txt')
fprintf('\n\n\n\n\nTotal packets sent from node B=%d\n',B_data(1));
fprintf('Average frame interval at node B=%d usec\n',B_data(2));
fprintf('Average queue delay at node B=%d usec\n',B_data(3));
fprintf('Average access delay at node B=%d usec\n',B_data(4));

fprintf('Average end to end throughput from B to B=%d Mb/s\n',B_data(5)/10^6);
fprintf('Simulation end time=%d sec\n',B_data(6)/10^6);


load('B.dat');
interArrivalTime = B(:,1);
queueingDelay = B(:,2);
accessDelay = B(:,3);
figure(2);

%Queue Delay 
subplot(2,2,1)
plot(1:size(queueingDelay,1),queueingDelay(1:end))
axis([0 length(queueingDelay) 0 max(queueingDelay)])
xlabel('Packet sequence #');
ylabel('Delay in \mu sec');
title('Queue delay at node B');
        
%Access Delay
subplot(2,2,2)
plot(1:size(accessDelay,1),accessDelay(1:end))
axis([0 length(accessDelay) 0 max(accessDelay)])
xlabel('Packet sequence #');
ylabel('Delay in \mu sec');
title('Access delay at node B');

% Frame interval
subplot(2,2,3)
plot(1:size(interArrivalTime,1),interArrivalTime/1000)
axis([0 length(interArrivalTime) 0 max(interArrivalTime)/1000])
xlabel('Packet sequence #');
ylabel('Frame interval in msec');
title('Frame intervals at node B');
        
% Histogram to verify if the distribution is exp
subplot(2,2,4)
hist(interArrivalTime,60);
xlabel('frame intervals in \mu sec');
ylabel('# of frames');






