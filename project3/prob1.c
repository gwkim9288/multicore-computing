#include <stdio.h>
#include <omp.h>

#define Num_steps 20000

void main(int argc, char* argv[]){
    int num_thread;
        if (argc==2) {
            num_thread = atoi(argv[1]);
        } else {
            printf("wrong input. ex) a.out [thread number]\n");
            exit(0);
        }
    omp_set_num_threads(num_thread);
    int now;
    int count =0;
    double startTime = omp_get_wtime();
    if(atoi(argv[0])==1){
#pragma omp parallel for schedule(static)
        for(now=1;i<=Num_steps;i++){
            if(isPrime(i))
                count++;
        }
    }
    else if(atoi(argv[0])==2){
#pragma omp parllel for schedule(dynamic,4)
        for(now=1;i<=Num_steps;i++){
            if(isPrime(i))
                count++;
        }
    }
    else if(atoi(argv[0])==3){
#pragma omp parllel for schedule(guided,4)
        for(now=1;i<=Num_steps;i++){
            if(isPrime(i))
                count++;
        }
    }
    double endTime = omp_get_wtime();
    double excution_time = endTime - startTime;
    printf("num of thread: %d, num of prime number: %d, excution time: %lfs",num_thread,count,excution_time);
}

bool isPrime (int x){
    if(x<=1) return false;
    for(int i=2;i<x;i++) {
        if((x%i==0)&&(i!=x)) return false;
    }
    return true;
}
