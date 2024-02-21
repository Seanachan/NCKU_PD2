#include<iostream>
#include<algorithm>
using namespace std;
class CMath{
public:
    void getMax(int num1,int num2){
        cout<<max(num1,num2)<<"\n";
    }
};

class SonCMath:public CMath{
public:
    void getFactorial(int n){
        int ans=1;
        for(int i=1;i<=n;i++)
            ans*=i;
        cout<<ans<<"\n";
    }
};

class GrandSonCMath:public SonCMath{
public:
    // GrandSonCMath(){}
    void getAverage(int num1,int num2){
        cout<<(num1+num2)/2<<"\n";
    }
}; 

int main(){
    SonCMath math1 = SonCMath();
    math1.getMax(10,20);
    math1.getFactorial(5);
    
    GrandSonCMath math2 = GrandSonCMath();
    math2.getMax(20,30);
    math1.getFactorial(6);
    math2.getAverage(12,34);
    return 0;  
}