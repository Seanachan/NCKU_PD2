#include<iostream>
#include<math.h>
using namespace std;
class Circle{
public:
    void area(){
        cout<<R*R*M_PI;
    }
    void circumference(){
        cout<<2*R*M_PI;
    }
    void setRadius(float r){
        R=r;
    }
    float getRadius(){
        return R;
    }
private:
    float R;
};
int main(){
    Circle c1;
    c1.setRadius(4);
    cout<<"The area of c1 is ";
    c1.area();
    cout<<" m^2\n";
    c1.circumference();
    return 0;
}