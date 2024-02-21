#include<iostream>
#include<string>
#include<string.h>
using namespace std;
class CatAge{
    virtual void whatsTheAge()=0;
    //force every class who has this abstract class to assign implementation to the method whatsTheAge()
    //
};
class Cat:CatAge{
    //protected://if using this, the derived class can get access to the variables define here
    public://can get access from outside
    //throught public method, interact with private properties
    Cat(string animalName){
        name=animalName;
        barking = "Meow";
    }
    void saySth(){
        cout<<this->name<<": "<<this->barking<<"\n";
        //參數名字跟成員變數名字衝突的時候，使用關鍵字去強調你要調用的是成員變數
    }
    void setName(string name){
        if(name.length()>10){
            //輸出程式錯誤訊息
            cerr<<"name.length() should <=10"<<"\n";

        }
    }
    string getName(){
        return name;
    }
    void whatsTheAge(){
        cout<<name<<"'s age is "<<12<<"\n";
        //after assign this for the 1 time, don't have to assign for the folowing class who is assigned to the abstract 'CatAge'
    }
    private://Encapsulation
    string name;
    string barking;
};
class Developer:public Cat{
public:
    string ProgrammingLanguage;
    Developer(string name,string programmingLanguage):Cat(name){
        ProgrammingLanguage = programmingLanguage;
    }
    void fixBug(){
        cout<<getName()<<" fix bugs using "<<ProgrammingLanguage<<"\n";
    }
};
int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);
    //two ways to constuct
    Cat c1 = Cat("Alice");
    Cat c2("Bob");

    c1.saySth();
    c2.saySth();
    c1.whatsTheAge();
    Developer d = Developer("Sean","Python");
    d.fixBug();

    Cat *c = &d;

    return 0;  
}
