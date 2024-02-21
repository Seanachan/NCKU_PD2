#include<iostream>
using namespace std;
class Rectangle{
public:
    Rectangle(float length,float width){
        Length=length;
        Width=width;
    }
    float area(){
        return Length*Width;
    }
    float perimeter(){
        return 2*(Length+Width);
    }
private:
    float Length,Width;

};

int main(){
    Rectangle rect1 = Rectangle(3,4);
    rect1 = Rectangle(4,5);
    cout<<rect1.area()<<"\n";
    cout<<rect1.perimeter()<<"\n";
    return 0;
}